package com.gts.collector.domain.content.repository;

import com.gts.collector.domain.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/** 
 * 콘텐츠 데이터 접근을 위한 리포지토리 
 */
public interface ContentRepository extends JpaRepository<Content, Long> {
    
    /** 
     * 원문 URL을 기반으로 콘텐츠 존재 여부를 확인합니다.
     */
    boolean existsByOriginalUrl(String originalUrl);

    /**
     * 원문 URL을 기반으로 콘텐츠를 조회합니다.
     */
    Optional<Content> findByOriginalUrl(String originalUrl);

    /**
     * 특정 태그를 포함하는 콘텐츠 목록을 페이징 조회합니다.
     */
    Page<Content> findAllByTagsContaining(String tag, Pageable pageable);
}
