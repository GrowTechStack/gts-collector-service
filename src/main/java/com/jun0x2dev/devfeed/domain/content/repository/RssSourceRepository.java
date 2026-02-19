package com.jun0x2dev.devfeed.domain.content.repository;

import com.jun0x2dev.devfeed.domain.content.entity.RssSource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * RSS 출처 정보에 접근하기 위한 리포지토리
 */
public interface RssSourceRepository extends JpaRepository<RssSource, Long> {
    
    /** 
     * 활성화된 RSS 출처 목록만 조회합니다. 
     */
    List<RssSource> findAllByActiveTrue();
}
