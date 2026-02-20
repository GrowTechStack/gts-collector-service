package com.gts.collector.domain.content.service.impl;

import com.gts.collector.domain.content.entity.Content;
import com.gts.collector.domain.content.entity.ContentType;
import com.gts.collector.domain.content.repository.ContentRepository;
import com.gts.collector.domain.content.service.RssCollectorService;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
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

        Optional<Content> existingContent = contentRepository.findByOriginalUrl(originalUrl);

        if (existingContent.isPresent()) {
            Content content = existingContent.get();
            // 기존 데이터가 있으나 사이트명이나 태그가 없는 경우 업데이트
            if (content.getSiteName() == null || content.getTags() == null || content.getTags().isEmpty()) {
                content.updateMetadata(siteName, tags);
                return true;
            }
            return false;
        }

        // 새 콘텐츠 저장
        Content content = Content.builder()
                .type(ContentType.EXTERNAL)
                .title(entry.getTitle())
                .summary(entry.getDescription() != null ? entry.getDescription().getValue() : "")
                .originalUrl(originalUrl)
                .siteName(siteName)
                .tags(tags)
                .publishedAt(entry.getPublishedDate() != null ? 
                        entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
                .commentEnabled(false)
                .build();

        contentRepository.save(content);
        return true;
    }

    private String extractTags(SyndEntry entry) {
        // 1. RSS 카테고리 정보 활용
        String categories = entry.getCategories().stream()
                .map(category -> category.getName().toLowerCase())
                .collect(Collectors.joining(","));

        if (!categories.isEmpty()) {
            return categories;
        }

        // 2. 키워드 기반 태그 추측
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
