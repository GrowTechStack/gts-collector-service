package com.gts.collector.domain.feed.service.impl;

import com.gts.collector.domain.content.service.ContentService;
import com.gts.collector.domain.feed.dto.RssSourceRequest;
import com.gts.collector.domain.feed.dto.RssSourceResponse;
import com.gts.collector.domain.feed.dto.RssSourceStatsResponse;
import com.gts.collector.domain.feed.entity.RssSource;
import com.gts.collector.domain.feed.repository.RssSourceRepository;
import com.gts.collector.domain.feed.service.RssSourceService;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gts.collector.domain.content.dto.SiteStatsDto;
import java.util.List;
import java.util.Map;

/**
 * RSS 출처 관리 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RssSourceServiceImpl implements RssSourceService {

    private final RssSourceRepository rssSourceRepository;
    private final ContentService contentService;

    @Override
    public List<RssSourceResponse> getAllSources() {
        return rssSourceRepository.findAll().stream()
                .map(RssSourceResponse::from)
                .toList();
    }

    /**
     * 각 출처별 콘텐츠 수, 총 조회수, 최신 발행일을 포함한 통계 목록을 반환합니다.
     * 단일 GROUP BY 쿼리로 통계를 한 번에 조회하여 N+1 문제를 방지합니다.
     * 총 조회수 기준 내림차순 정렬됩니다.
     */
    @Override
    public List<RssSourceStatsResponse> getAllSourcesWithStats() {
        Map<String, SiteStatsDto> statsMap = contentService.findAllSiteStatsMap();

        return rssSourceRepository.findAll().stream()
                .map(s -> {
                    SiteStatsDto stats = statsMap.getOrDefault(
                            s.getSiteName(), new SiteStatsDto(0, 0, null));
                    return new RssSourceStatsResponse(
                            s.getId(),
                            s.getSiteName(),
                            s.getRssUrl(),
                            s.getSiteUrl(),
                            s.getLogoUrl(),
                            s.isActive(),
                            stats.postCount(),
                            stats.totalViewCount(),
                            stats.latestPublishedAt()
                    );
                })
                .sorted((a, b) -> Long.compare(b.totalViewCount(), a.totalViewCount()))
                .toList();
    }

    @Override
    public RssSourceResponse getSource(Long id) {
        RssSource source = rssSourceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RSS_SOURCE_NOT_FOUND));
        return RssSourceResponse.from(source);
    }

    @Override
    @Transactional
    public RssSourceResponse createSource(RssSourceRequest request) {
        RssSource source = RssSource.builder()
                .siteName(request.siteName())
                .rssUrl(request.rssUrl())
                .logoUrl(request.logoUrl())
                .siteUrl(request.siteUrl())
                .active(request.active())
                .build();
        return RssSourceResponse.from(rssSourceRepository.save(source));
    }

    @Override
    @Transactional
    public RssSourceResponse updateSource(Long id, RssSourceRequest request) {
        RssSource source = rssSourceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RSS_SOURCE_NOT_FOUND));
        source.update(request.siteName(), request.rssUrl(), request.logoUrl(), request.siteUrl(), request.active());
        return RssSourceResponse.from(source);
    }

    @Override
    @Transactional
    public void deleteSource(Long id) {
        if (!rssSourceRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.RSS_SOURCE_NOT_FOUND);
        }
        rssSourceRepository.deleteById(id);
    }
}
