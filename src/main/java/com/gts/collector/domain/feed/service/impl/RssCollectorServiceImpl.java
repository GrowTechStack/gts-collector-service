package com.gts.collector.domain.feed.service.impl;

import com.gts.collector.domain.content.entity.Content;
import com.gts.collector.domain.content.entity.ContentType;
import com.gts.collector.domain.content.repository.ContentRepository;
import com.gts.collector.domain.feed.service.RssCollectorService;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.ParsingFeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * RSS 피드 수집 서비스 구현체.
 * Rome 라이브러리를 사용하여 RSS/Atom 피드를 파싱하고 Content 엔티티로 저장합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RssCollectorServiceImpl implements RssCollectorService {

    /** HTTP 요청 시 사용하는 User-Agent (일부 사이트의 봇 차단 우회) */
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 GrowTechStackBot/1.0";

    private final ContentRepository contentRepository;

    /**
     * 지정된 RSS URL에서 피드를 파싱하고 신규 콘텐츠를 저장합니다.
     * 기존에 저장된 URL은 중복 저장되지 않으며, 썸네일이 없는 경우에만 업데이트합니다.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int collect(String rssUrl, String siteName) {
        log.info("Starting RSS collection from: {} ({})", siteName, rssUrl);
        int savedCount = 0;

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(rssUrl).openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);

            SyndFeed feed = new SyndFeedInput().build(new XmlReader(connection));
            List<SyndEntry> entries = feed.getEntries();

            for (SyndEntry entry : entries) {
                if (saveOrUpdate(entry, siteName)) {
                    savedCount++;
                }
            }

            log.info("Finished RSS collection for {}. Processed {} entries.", siteName, savedCount);
            return savedCount;

        } catch (BusinessException e) {
            throw e;
        } catch (UnknownHostException e) {
            log.error("DNS 조회 실패 - 도메인을 찾을 수 없습니다: site={}, url={}", siteName, rssUrl);
            throw new BusinessException(ErrorCode.RSS_UNKNOWN_HOST);
        } catch (SocketTimeoutException e) {
            log.error("연결 시간 초과: site={}, url={}, 원인={}", siteName, rssUrl, e.getMessage());
            throw new BusinessException(ErrorCode.RSS_CONNECTION_TIMEOUT);
        } catch (ParsingFeedException e) {
            log.error("RSS 피드 파싱 실패 (유효하지 않은 XML 형식): site={}, url={}, 원인={}", siteName, rssUrl, e.getMessage());
            throw new BusinessException(ErrorCode.RSS_INVALID_FEED);
        } catch (Exception e) {
            log.error("RSS 수집 중 알 수 없는 오류: site={}, url={}, 원인={}", siteName, rssUrl, e.getMessage(), e);
            throw new BusinessException(ErrorCode.RSS_PARSE_ERROR);
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    /**
     * RSS 엔트리를 Content로 저장하거나, 기존 콘텐츠의 썸네일을 업데이트합니다.
     * - 신규 URL: 새로 저장 후 true 반환
     * - 기존 URL + 썸네일 null: 썸네일 업데이트 후 true 반환
     * - 기존 URL + 썸네일 있음: 스킵 후 false 반환
     */
    private boolean saveOrUpdate(SyndEntry entry, String siteName) {
        String originalUrl = entry.getLink();
        String tags = extractTags(entry);
        String thumbnailUrl = extractThumbnail(entry);
        log.debug("[{}] thumbnail={}, url={}", siteName, thumbnailUrl, originalUrl);

        Optional<Content> existingContent = contentRepository.findByOriginalUrl(originalUrl);

        if (existingContent.isPresent()) {
            Content content = existingContent.get();
            // TODO: H2 파일 모드에서 dirty checking 및 명시적 save() 호출 시에도 업데이트가 반영되지 않는 문제 있음
            //       MySQL로 DB 전환 후 정상 동작 여부 재확인 및 적용 필요
            if (content.getThumbnailUrl() == null && thumbnailUrl != null) {
                content.updateMetadata(siteName, tags, thumbnailUrl);
                contentRepository.save(content);
                return true;
            }
            return false;
        }

        Content content = Content.builder()
                .type(ContentType.EXTERNAL)
                .title(entry.getTitle())
                .summary(entry.getDescription() != null ? entry.getDescription().getValue() : "")
                .originalUrl(originalUrl)
                .siteName(siteName)
                .thumbnailUrl(thumbnailUrl)
                .tags(tags)
                .publishedAt(entry.getPublishedDate() != null ?
                        entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
                .commentEnabled(false)
                .build();

        contentRepository.save(content);
        return true;
    }

    /**
     * RSS 엔트리에서 썸네일 이미지 URL을 추출합니다.
     * 우선순위:
     * 1. Enclosures (이미지 타입)
     * 2. ForeignMarkup - media:content, media:thumbnail, thumbnail 커스텀 태그
     * 3. content:encoded - preload 링크 또는 img 태그
     * 4. Description HTML - 첫 번째 img 태그
     * 5. OG 이미지 fallback (원문 페이지 스크래핑)
     */
    private String extractThumbnail(SyndEntry entry) {
        // 1. Enclosures 확인 (이미지 타입)
        for (SyndEnclosure enclosure : entry.getEnclosures()) {
            if (enclosure.getType() != null && enclosure.getType().contains("image")) {
                return enclosure.getUrl();
            }
        }

        // 2. ForeignMarkup 확인 (media:content, media:thumbnail, thumbnail 커스텀 태그)
        List<Element> foreignMarkup = entry.getForeignMarkup();
        for (Element element : foreignMarkup) {
            String name = element.getName();
            String prefix = element.getNamespacePrefix();

            if ("media".equals(prefix) && ("content".equals(name) || "thumbnail".equals(name))) {
                String url = element.getAttributeValue("url");
                if (url != null) return url;
            }

            if ("thumbnail".equals(name) && element.getText() != null && !element.getText().isBlank()) {
                return element.getText().trim();
            }
        }

        // 3. content:encoded 에서 추출 (SyndEntry.getContents()로 접근)
        for (var content : entry.getContents()) {
            String encoded = content.getValue();
            if (encoded == null) continue;

            // Toss Tech: <link rel="preload" as="image" href="...">
            Pattern preloadPattern = Pattern.compile("<link[^>]+as=['\"]image['\"][^>]+href=['\"]([^'\"]+)['\"]");
            Matcher matcher = preloadPattern.matcher(encoded);
            if (matcher.find()) return matcher.group(1);

            // 일반 img 태그
            Pattern imgPattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"]");
            matcher = imgPattern.matcher(encoded);
            if (matcher.find()) return matcher.group(1);
        }

        // 4. Description HTML에서 첫 번째 img 태그 추출
        if (entry.getDescription() != null) {
            String html = entry.getDescription().getValue();
            Pattern imgPattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
            Matcher matcher = imgPattern.matcher(html);
            if (matcher.find()) return matcher.group(1);
        }

        // 5. OG 이미지 fallback (RSS에 썸네일 없을 때 원문 페이지에서 추출)
        if (entry.getLink() != null) {
            return extractOgImage(entry.getLink());
        }

        return null;
    }

    /**
     * 원문 페이지의 HTML에서 og:image 메타 태그 값을 추출합니다.
     * property/content 속성 순서가 다른 두 가지 패턴을 모두 처리합니다.
     */
    private String extractOgImage(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            String html = new String(connection.getInputStream().readAllBytes());

            Pattern ogPattern = Pattern.compile("<meta[^>]+property=['\"]og:image['\"][^>]+content=['\"]([^'\"]+)['\"]");
            Matcher matcher = ogPattern.matcher(html);
            if (matcher.find()) return matcher.group(1);

            // content/property 순서가 반대인 경우
            ogPattern = Pattern.compile("<meta[^>]+content=['\"]([^'\"]+)['\"][^>]+property=['\"]og:image['\"]");
            matcher = ogPattern.matcher(html);
            if (matcher.find()) return matcher.group(1);

        } catch (Exception e) {
            log.debug("Failed to extract OG image from: {}", url);
        }
        return null;
    }

    /**
     * RSS 엔트리의 카테고리 정보에서 태그를 추출합니다.
     * 카테고리가 없으면 제목과 설명 텍스트를 분석하여 backend, frontend, ai, design 태그를 자동 부여합니다.
     * 아무것도 해당하지 않으면 'tech'를 기본 태그로 사용합니다.
     */
    private String extractTags(SyndEntry entry) {
        String categories = entry.getCategories().stream()
                .map(category -> category.getName().toLowerCase())
                .collect(Collectors.joining(","));

        if (!categories.isEmpty()) return categories;

        String contentText = (entry.getTitle() + " " +
                (entry.getDescription() != null ? entry.getDescription().getValue() : "")).toLowerCase();

        StringBuilder autoTags = new StringBuilder();
        if (contentText.contains("backend") || contentText.contains("server") || contentText.contains("spring")) autoTags.append("backend,");
        if (contentText.contains("frontend") || contentText.contains("react") || contentText.contains("javascript")) autoTags.append("frontend,");
        if (contentText.contains("ai") || contentText.contains("ml") || contentText.contains("data")) autoTags.append("ai,");
        if (contentText.contains("design") || contentText.contains("ui") || contentText.contains("ux")) autoTags.append("design,");

        return autoTags.length() > 0 ? autoTags.substring(0, autoTags.length() - 1) : "tech";
    }
}
