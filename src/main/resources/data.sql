-- 초기 RSS 수집 대상 등록 (중복 방지를 위해 INSERT IGNORE 사용)
INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('Toss Tech', 'https://toss.tech/rss.xml', 'https://static.toss.im/tds/favicon/favicon.ico', 'https://toss.tech', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('Kakao Tech', 'https://tech.kakao.com/feed/', 'https://www.kakaocorp.com/page/favicon.ico', 'https://tech.kakao.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('LY Engineering', 'https://techblog.lycorp.co.jp/ko/feed/index.xml', 'https://techblog.lycorp.co.jp/favicon.ico', 'https://techblog.lycorp.co.jp/ko', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('우아한형제들', 'https://techblog.woowahan.com/feed', 'https://techblog.woowahan.com/wp-content/uploads/2020/08/favicon.ico', 'https://techblog.woowahan.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('Naver D2', 'https://d2.naver.com/d2.atom', 'https://d2.naver.com/favicon.ico', 'https://d2.naver.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('카카오페이', 'https://tech.kakaopay.com/rss', 'https://tech.kakaopay.com/favicon.ico', 'https://tech.kakaopay.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('쿠팡', 'https://medium.com/feed/coupang-engineering', 'https://miro.medium.com/v2/resize:fill:160:160/1*PwmOErfwUKqKYo8G92nNXA.jpeg', 'https://medium.com/coupang-engineering', false);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('당근마켓', 'https://medium.com/feed/daangn', 'https://miro.medium.com/v2/resize:fill:160:160/1*Bm8_nGjfNiKV0PASwiPELg.png', 'https://medium.com/daangn', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('올리브영', 'https://oliveyoung.tech/rss.xml', 'https://oliveyoung.tech/favicon-32x32.png', 'https://oliveyoung.tech', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('뱅크샐러드', 'https://blog.banksalad.com/rss.xml', 'https://blog.banksalad.com/favicon-32x32.png', 'https://blog.banksalad.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('하이퍼커넥트', 'https://hyperconnect.github.io/feed.xml', 'https://hyperconnect.github.io/assets/favicon.svg', 'https://hyperconnect.github.io', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('왓챠', 'https://medium.com/feed/watcha', 'https://miro.medium.com/v2/resize:fill:160:160/1*3YwZ4pRivcBi8nWl7u98gA.png', 'https://medium.com/watcha', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('여기어때', 'https://techblog.gccompany.co.kr/feed', 'https://miro.medium.com/v2/resize:fill:160:160/1*rGdUGkMoxT5SfrVKVAqbEw.png', 'https://techblog.gccompany.co.kr', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('무신사', 'https://medium.com/feed/musinsa-tech', 'https://miro.medium.com/v2/resize:fill:160:160/1*Qs-0adxK8doDYyzZXMXkmg.png', 'https://medium.com/musinsa-tech', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('쏘카', 'https://tech.socarcorp.kr/feed', 'https://tech.socarcorp.kr/assets/icon/favicon.ico', 'https://tech.socarcorp.kr', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('요기요', 'https://techblog.yogiyo.co.kr/feed', 'https://miro.medium.com/v2/resize:fill:40:40/1*gZk0QZMhjKVGGxEu7JBasw.png', 'https://techblog.yogiyo.co.kr', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('29CM', 'https://medium.com/feed/29cm', 'https://miro.medium.com/v2/resize:fill:160:160/1*TP1aY6ZJaPSPs3fKA6sYKA.png', 'https://medium.com/29cm', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('마켓컬리', 'https://helloworld.kurly.com/feed.xml', 'https://helloworld.kurly.com/assets/logo/ico_32.png', 'https://helloworld.kurly.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('카카오엔터프라이즈', 'https://tech.kakaoenterprise.com/feed', 'https://tistory4.daumcdn.net/tistory/3769165/07aa76d9ba224eb289bef9ca9e5f2e32', 'https://tech.kakaoenterprise.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('데브시스터즈', 'https://tech.devsisters.com/rss.xml', 'https://tech.devsisters.com/favicon-32x32.png', 'https://tech.devsisters.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('직방', 'https://medium.com/feed/zigbang', 'https://miro.medium.com/v2/resize:fill:160:160/1*_SRVUENl0RXh1VwmFuoHrQ.png', 'https://medium.com/zigbang', false);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('NHN Toast', 'https://meetup.toast.com/rss', 'https://meetup.nhncloud.com/resources/img/favicon.ico', 'https://meetup.toast.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('GeekNews', 'https://news.hada.io/rss/news', 'https://news.hada.io/favicon.ico', 'https://news.hada.io', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('개발자스럽다', 'https://blog.gaerae.com/feeds/posts/default?alt=rss', 'https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhoVtq5iot4FBRLA29jUDX-zVf7vcL7q5CBEZakJ6aolro0hg-1o5m20TcLjutCUi2yJl5q0HlDZsj87V8ZAQ3JyTwc8DUEfBJ6lInD31uTa5PznoZU_DW3i0G6PdeDcYR4NYpxz0u545F1H_NUb7jNl_TYfoW3ONpHKI9teefnWb9hGQB9Td6DvNg8g2U/w35-h35-p-k-no-nu/gaeraecom_logo_black.webp', 'https://blog.gaerae.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('44BITS', 'https://www.44bits.io/ko/feed/all', 'https://www.44bits.io/favicon.png', 'https://www.44bits.io', true);
