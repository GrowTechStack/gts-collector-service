package com.gts.collector.domain.content.service.impl;

import com.gts.collector.domain.content.dto.RssSourceRequest;
import com.gts.collector.domain.content.dto.RssSourceResponse;
import com.gts.collector.domain.content.entity.RssSource;
import com.gts.collector.domain.content.repository.RssSourceRepository;
import com.gts.collector.domain.content.service.RssSourceService;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RSS 출처 관리 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RssSourceServiceImpl implements RssSourceService {

    private final RssSourceRepository rssSourceRepository;

    @Override
    public List<RssSourceResponse> getAllSources() {
        return rssSourceRepository.findAll().stream()
                .map(RssSourceResponse::from)
                .collect(Collectors.toList());
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
                .active(request.active())
                .build();
        
        return RssSourceResponse.from(rssSourceRepository.save(source));
    }

    @Override
    @Transactional
    public RssSourceResponse updateSource(Long id, RssSourceRequest request) {
        RssSource source = rssSourceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RSS_SOURCE_NOT_FOUND));
        
        source.update(request.siteName(), request.rssUrl(), request.active());
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
