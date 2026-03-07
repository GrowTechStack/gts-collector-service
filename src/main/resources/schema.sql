-- Contents 통합 테이블 (수집 글 + 사용자 글)
CREATE TABLE IF NOT EXISTS contents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,
    body LONGTEXT,
    summary LONGTEXT,
    original_url VARCHAR(500) UNIQUE,
    site_name VARCHAR(100),
    thumbnail_url VARCHAR(500),
    tags VARCHAR(255),
    author_id BIGINT,
    view_count BIGINT DEFAULT 0,
    comment_enabled BOOLEAN DEFAULT FALSE,
    published_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- RSS 출처 관리 테이블
CREATE TABLE IF NOT EXISTS rss_sources (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(100) NOT NULL,
    rss_url VARCHAR(500) NOT NULL UNIQUE,
    logo_url VARCHAR(500),
    site_url VARCHAR(500),
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
    start_time TIMESTAMP NULL,
    end_time TIMESTAMP NULL
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
    body LONGTEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 태그 테이블
CREATE TABLE IF NOT EXISTS tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 인덱스 설정
CREATE INDEX IF NOT EXISTS idx_contents_type ON contents(type);
CREATE INDEX IF NOT EXISTS idx_contents_published_at ON contents(published_at);
