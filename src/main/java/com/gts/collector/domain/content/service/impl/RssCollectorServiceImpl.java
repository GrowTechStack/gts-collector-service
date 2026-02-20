package com.gts.collector.domain.content.service.impl;

import com.gts.collector.domain.content.entity.Content;
import com.gts.collector.domain.content.entity.ContentType;
import com.gts.collector.domain.content.repository.ContentRepository;
import com.gts.collector.domain.content.service.RssCollectorService;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * RSS 수집 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RssCollectorServiceImpl implements RssCollectorService {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 GrowTechStackBot/1.0";

    private final ContentRepository contentRepository;

    @Override
    @Transactional
    public int collect(String rssUrl, String siteName) {
        log.info("Starting RSS collection from: {} ({})", siteName, rssUrl);
        int savedCount = 0;
        
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(rssUrl).openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            SyndFeed feed = new SyndFeedInput().build(new XmlReader(connection));
            List<SyndEntry> entries = feed.getEntries();

            for (SyndEntry entry : entries) {
                if (saveOrUpdate(entry, siteName)) {
                    savedCount++;
                }
            }
            
            log.info("Finished RSS collection for {}. Processed {} entries.", siteName, savedCount);
            return savedCount;
        } catch (Exception e) {
            log.error("Failed to collect RSS from: {}", rssUrl, e);
            throw new BusinessException(ErrorCode.RSS_PARSE_ERROR);
        }
    }

    private boolean saveOrUpdate(SyndEntry entry, String siteName) {
        String originalUrl = entry.getLink();
        String tags = extractTags(entry);
        String thumbnailUrl = extractThumbnail(entry);
        log.debug("[{}] thumbnail={}, url={}", siteName, thumbnailUrl, originalUrl);

        Optional<Content> existingContent = contentRepository.findByOriginalUrl(originalUrl);

        if (existingContent.isPresent()) {
            Content content = existingContent.get();
            // thumbnailUrl이 null이고 새로 추출한 값이 있으면 업데이트
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

            // media:content, media:thumbnail (표준 Media RSS)
            if ("media".equals(prefix) && ("content".equals(name) || "thumbnail".equals(name))) {
                String url = element.getAttributeValue("url");
                if (url != null) return url;
            }

            // <thumbnail> 커스텀 태그 (Kakao Tech 등)
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
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        // 5. OG 이미지 fallback (RSS에 썸네일 없을 때 원문 페이지에서 추출)
        if (entry.getLink() != null) {
            return extractOgImage(entry.getLink());
        }

        return null;
    }

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
