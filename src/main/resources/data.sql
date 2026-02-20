-- 초기 RSS 수집 대상 등록 (중복 방지를 위해 MERGE INTO 사용)
MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Toss Tech', 'https://toss.tech/rss.xml', 'https://static.toss.im/tds/favicon/favicon.ico', 'https://toss.tech', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Kakao Tech', 'https://tech.kakao.com/feed/', 'https://www.kakaocorp.com/page/favicon.ico', 'https://tech.kakao.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('LY Engineering', 'https://techblog.lycorp.co.jp/ko/feed/index.xml', 'https://techblog.lycorp.co.jp/favicon.ico', 'https://techblog.lycorp.co.jp/ko', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('우아한형제들', 'https://techblog.woowahan.com/feed', 'https://techblog.woowahan.com/wp-content/uploads/2020/06/favicon.png', 'https://techblog.woowahan.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Naver D2', 'https://d2.naver.com/d2.atom', 'https://d2.naver.com/favicon.ico', 'https://d2.naver.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('카카오페이', 'https://tech.kakaopay.com/rss', 'https://tech.kakaopay.com/favicon.ico', 'https://tech.kakaopay.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('쿠팡', 'https://medium.com/coupang-engineering/feed', 'https://miro.medium.com/v2/1*ERkFJTEqaGNPNyyzF5Ldlw.png', 'https://medium.com/coupang-engineering', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('당근마켓', 'https://medium.com/daangn/feed', 'https://miro.medium.com/v2/1*06GAbBAMZaGu4F2d-sGtqA.png', 'https://medium.com/daangn', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('올리브영', 'https://oliveyoung.tech/rss.xml', 'https://oliveyoung.tech/favicon.ico', 'https://oliveyoung.tech', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('뱅크샐러드', 'https://blog.banksalad.com/rss.xml', 'https://blog.banksalad.com/favicon.ico', 'https://blog.banksalad.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('하이퍼커넥트', 'https://hyperconnect.github.io/feed.xml', 'https://hyperconnect.github.io/favicon.ico', 'https://hyperconnect.github.io', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('왓챠', 'https://medium.com/watcha/feed', 'https://miro.medium.com/v2/1*SoGPNEb8LBCCzlJHc6PUPA.png', 'https://medium.com/watcha', false);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('여기어때', 'https://techblog.gccompany.co.kr/feed', 'https://techblog.gccompany.co.kr/favicon.ico', 'https://techblog.gccompany.co.kr', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('무신사', 'https://medium.com/musinsa-tech/feed', 'https://miro.medium.com/v2/1*MQDOFbzwuFaEEOp8DkiZqQ.png', 'https://medium.com/musinsa-tech', false);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('쏘카', 'https://tech.socarcorp.kr/feed', 'https://tech.socarcorp.kr/favicon.ico', 'https://tech.socarcorp.kr', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('요기요', 'https://techblog.yogiyo.co.kr/feed', 'https://techblog.yogiyo.co.kr/favicon.ico', 'https://techblog.yogiyo.co.kr', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('29CM', 'https://medium.com/29cm/feed', 'https://miro.medium.com/v2/1*Uqz6RNqzTB7fHLbQ8YXHXA.png', 'https://medium.com/29cm', false);
