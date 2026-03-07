package com.gts.collector.domain.content.controller;

import com.gts.collector.domain.content.dto.ContentSummaryResponse;
import com.gts.collector.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/rss")
@RequiredArgsConstructor
public class RssFeedController {

    private static final String SITE_URL = "https://www.growtechstack.com";
    private static final int FEED_SIZE = 50;
    private static final DateTimeFormatter RFC_822 =
            DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                    .withZone(ZoneId.of("Asia/Seoul"));

    private final ContentService contentService;

    @GetMapping(produces = "application/rss+xml; charset=UTF-8")
    public ResponseEntity<String> getRssFeed() {
        List<ContentSummaryResponse> contents = contentService
                .getContents(null, PageRequest.of(0, FEED_SIZE, Sort.by(Sort.Direction.DESC, "publishedAt")))
                .getContent();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<rss version=\"2.0\">\n");
        xml.append("  <channel>\n");
        xml.append("    <title>GrowTechStack</title>\n");
        xml.append("    <link>").append(SITE_URL).append("</link>\n");
        xml.append("    <description>국내 IT 기업 기술 블로그 모음</description>\n");
        xml.append("    <language>ko</language>\n");

        for (ContentSummaryResponse content : contents) {
            xml.append("    <item>\n");
            xml.append("      <title>").append(escape(content.title())).append("</title>\n");
            xml.append("      <link>").append(SITE_URL).append("/contents/").append(content.id()).append("</link>\n");
            if (content.summary() != null) {
                xml.append("      <description>").append(escape(content.summary())).append("</description>\n");
            }
            xml.append("      <pubDate>").append(RFC_822.format(content.publishedAt().atZone(ZoneId.of("Asia/Seoul")))).append("</pubDate>\n");
            xml.append("    </item>\n");
        }

        xml.append("  </channel>\n");
        xml.append("</rss>");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/rss+xml; charset=UTF-8"))
                .body(xml.toString());
    }

    private String escape(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
