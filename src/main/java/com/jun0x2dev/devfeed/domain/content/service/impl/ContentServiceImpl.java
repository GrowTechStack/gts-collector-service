package com.jun0x2dev.devfeed.domain.content.service.impl;

import com.jun0x2dev.devfeed.domain.content.dto.ContentResponse;
import com.jun0x2dev.devfeed.domain.content.dto.ContentSummaryResponse;
import com.jun0x2dev.devfeed.domain.content.entity.Content;
import com.jun0x2dev.devfeed.domain.content.repository.ContentRepository;
import com.jun0x2dev.devfeed.domain.content.service.ContentService;
import com.jun0x2dev.devfeed.global.error.ErrorCode;
import com.jun0x2dev.devfeed.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 콘텐츠 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    @Override
    public Page<ContentSummaryResponse> getContents(String tag, Pageable pageable) {
        if (StringUtils.hasText(tag)) {
            return contentRepository.findAllByTagsContaining(tag, pageable)
                    .map(ContentSummaryResponse::from);
        }
        return contentRepository.findAll(pageable)
                .map(ContentSummaryResponse::from);
    }

    @Override
    public ContentResponse getContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        return ContentResponse.from(content);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        content.incrementViewCount();
    }
}
