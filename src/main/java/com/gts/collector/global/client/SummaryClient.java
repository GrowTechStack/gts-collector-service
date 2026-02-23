package com.gts.collector.global.client;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Optional;

/**
 * ai-summary-service와 통신하는 HTTP 클라이언트
 */
@Slf4j
@Component
public class SummaryClient {

    @Value("${ai-summary.base-url}")
    private String baseUrl;

    private RestClient restClient;

    @PostConstruct
    void init() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(30000);

        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    /**
     * ai-summary-service에 요약 요청을 보냅니다.
     * 실패 시 빈 Optional을 반환하여 수집 흐름에 영향을 주지 않습니다.
     */
    public Optional<String> summarize(String title, String content) {
        try {
            SummaryApiResponse response = restClient.post()
                    .uri("/api/v1/summarize")
                    .body(new SummaryRequest(title, content))
                    .retrieve()
                    .body(SummaryApiResponse.class);

            if (response != null && response.success() && response.data() != null) {
                return Optional.ofNullable(response.data().summary());
            }
            log.warn("[SummaryClient] 요약 응답 실패: title={}", title);
            return Optional.empty();

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                log.warn("[SummaryClient] Rate Limit 초과, 요약 건너뜀: title={}", title);
            } else {
                log.error("[SummaryClient] 요약 요청 오류: title={}, status={}, 원인={}", title, e.getStatusCode(), e.getMessage());
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("[SummaryClient] 요약 요청 오류: title={}, 원인={}", title, e.getMessage());
            return Optional.empty();
        }
    }

    private record SummaryRequest(String title, String content) {}

    private record SummaryApiResponse(boolean success, SummaryData data, Object error) {
        private record SummaryData(String summary) {}
    }
}
