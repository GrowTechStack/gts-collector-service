-- 초기 RSS 수집 대상 등록 (중복 방지를 위해 MERGE INTO 사용)
MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Toss Tech', 'https://toss.tech/rss.xml', 'https://static.toss.im/tds/favicon/favicon.ico', 'https://toss.tech', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Kakao Tech', 'https://tech.kakao.com/feed/', 'https://www.kakaocorp.com/page/favicon.ico', 'https://tech.kakao.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Line Engineering', 'https://engineering.linecorp.com/ko/feed/', 'https://engineering.linecorp.com/icons/icon-144x144.png?v=6d6085f233d02c34273fa8a8849b502a', 'https://techblog.lycorp.co.jp/ko', true);
