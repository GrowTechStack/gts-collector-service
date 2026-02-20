package com.gts.collector.domain.content.repository;

import com.gts.collector.domain.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
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

    /**
     * 제목 또는 요약에 키워드가 포함된 콘텐츠를 페이징 조회합니다.
     * TODO: MySQL 전환 후 FULLTEXT INDEX + MATCH AGAINST 방식으로 교체 권장
     */
    @Query("SELECT c FROM Content c WHERE c.title LIKE %:keyword% OR c.summary LIKE %:keyword%")
    Page<Content> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Content c WHERE c.siteName = :siteName")
    long countBySiteName(@Param("siteName") String siteName);

    @Query("SELECT COALESCE(SUM(c.viewCount), 0) FROM Content c WHERE c.siteName = :siteName")
    long sumViewCountBySiteName(@Param("siteName") String siteName);

    @Query("SELECT MAX(c.publishedAt) FROM Content c WHERE c.siteName = :siteName")
    Optional<LocalDateTime> findLatestPublishedAtBySiteName(@Param("siteName") String siteName);
}
