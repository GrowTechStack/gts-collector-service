-- 초기 RSS 수집 대상 등록 (중복 방지를 위해 MERGE INTO 사용)
MERGE INTO rss_sources (site_name, rss_url, active) KEY (rss_url) 
VALUES ('Toss Tech', 'https://toss.tech/rss.xml', true);

MERGE INTO rss_sources (site_name, rss_url, active) KEY (rss_url) 
VALUES ('Kakao Tech', 'https://tech.kakao.com/feed/', true);

MERGE INTO rss_sources (site_name, rss_url, active) KEY (rss_url) 
VALUES ('Line Engineering', 'https://engineering.linecorp.com/ko/feed/', true);
