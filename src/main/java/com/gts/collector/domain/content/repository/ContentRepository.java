package com.gts.collector.domain.content.repository;

import com.gts.collector.domain.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
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
     * 지정된 사이트명 목록에 해당하는 콘텐츠를 페이징 조회합니다.
     */
    @Query("SELECT c FROM Content c WHERE c.siteName IN :siteNames")
    Page<Content> findAllBySiteNameIn(@Param("siteNames") Collection<String> siteNames, Pageable pageable);

    /**
     * 지정된 사이트명 목록 + 태그 필터로 콘텐츠를 페이징 조회합니다.
     */
    @Query("SELECT c FROM Content c WHERE c.siteName IN :siteNames AND c.tags LIKE %:tag%")
    Page<Content> findAllBySiteNameInAndTagsContaining(@Param("siteNames") Collection<String> siteNames,
                                                       @Param("tag") String tag,
                                                       Pageable pageable);

    /**
     * 제목 또는 요약에 키워드가 포함된 콘텐츠를 페이징 조회합니다.
     * TODO: MySQL 전환 후 FULLTEXT INDEX + MATCH AGAINST 방식으로 교체 권장
     */
    @Query("SELECT c FROM Content c WHERE c.title LIKE %:keyword% OR c.summary LIKE %:keyword%")
    Page<Content> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 특정 사이트명의 총 콘텐츠 수를 반환합니다.
     */
    @Query("SELECT COUNT(c) FROM Content c WHERE c.siteName = :siteName")
    long countBySiteName(@Param("siteName") String siteName);

    /**
     * 특정 사이트명의 총 조회수 합계를 반환합니다.
     */
    @Query("SELECT COALESCE(SUM(c.viewCount), 0) FROM Content c WHERE c.siteName = :siteName")
    long sumViewCountBySiteName(@Param("siteName") String siteName);

    /**
     * 특정 사이트명의 가장 최근 발행일을 반환합니다.
     */
    @Query("SELECT MAX(c.publishedAt) FROM Content c WHERE c.siteName = :siteName")
    Optional<LocalDateTime> findLatestPublishedAtBySiteName(@Param("siteName") String siteName);

    /**
     * 요약이 없고 본문이 있는 콘텐츠 목록을 조회합니다. (재요약 대상)
     */
    List<Content> findAllBySummaryIsNullAndBodyIsNotNull();

    /**
     * 모든 사이트의 통계(콘텐츠 수, 총 조회수, 최신 발행일)를 siteName 기준으로 그룹핑하여 한 번에 조회합니다.
     * N+1 문제 방지를 위해 getAllSourcesWithStats()에서 사용합니다.
     */
    @Query("SELECT c.siteName, COUNT(c), COALESCE(SUM(c.viewCount), 0), MAX(c.publishedAt) " +
           "FROM Content c GROUP BY c.siteName")
    List<Object[]> findAllSiteStats();
}
