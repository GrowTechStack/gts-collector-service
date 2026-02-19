-- Contents 통합 테이블 (수집 글 + 사용자 글)
CREATE TABLE IF NOT EXISTS contents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,          -- EXTERNAL, USER 구분
    title VARCHAR(255) NOT NULL,
    body CLOB,                          -- 사용자 글 본문
    summary CLOB,                       -- 외부 글 요약
    original_url VARCHAR(500) UNIQUE,   -- 외부 글 링크 (중복 수집 방지)
    site_name VARCHAR(100),             -- 출처 사이트명
    tags VARCHAR(255),                  -- 태그 (쉼표 구분)
    author_id BIGINT,                   -- 작성자 ID (USER 타입일 경우)
    view_count BIGINT DEFAULT 0,
    comment_enabled BOOLEAN DEFAULT FALSE,
    published_at TIMESTAMP,             -- 원문 발행일
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- RSS 출처 관리 테이블
CREATE TABLE IF NOT EXISTS rss_sources (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(100) NOT NULL,
    rss_url VARCHAR(500) NOT NULL UNIQUE,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- RSS 수집 로그 테이블
CREATE TABLE IF NOT EXISTS collector_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(100) NOT NULL,
    rss_url VARCHAR(500) NOT NULL,
    success BOOLEAN NOT NULL,
    collected_count INT DEFAULT 0,
    error_message VARCHAR(1000),
    start_time TIMESTAMP,
    end_time TIMESTAMP
);

-- Users 테이블 (Phase 2 대비)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL
);

-- Bookmarks 테이블
CREATE TABLE IF NOT EXISTS bookmarks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comments 테이블
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    body CLOB NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 인덱스 설정
CREATE INDEX idx_contents_type ON contents(type);
CREATE INDEX idx_contents_published_at ON contents(published_at);
