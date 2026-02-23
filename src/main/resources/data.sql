-- 초기 RSS 수집 대상 등록 (중복 방지를 위해 MERGE INTO 사용)
MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Toss Tech', 'https://toss.tech/rss.xml', 'https://static.toss.im/tds/favicon/favicon.ico', 'https://toss.tech', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Kakao Tech', 'https://tech.kakao.com/feed/', 'https://www.kakaocorp.com/page/favicon.ico', 'https://tech.kakao.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('LY Engineering', 'https://techblog.lycorp.co.jp/ko/feed/index.xml', 'https://techblog.lycorp.co.jp/favicon.ico', 'https://techblog.lycorp.co.jp/ko', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('우아한형제들', 'https://techblog.woowahan.com/feed', 'https://techblog.woowahan.com/wp-content/uploads/2020/08/favicon.ico', 'https://techblog.woowahan.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('Naver D2', 'https://d2.naver.com/d2.atom', 'https://d2.naver.com/favicon.ico', 'https://d2.naver.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('카카오페이', 'https://tech.kakaopay.com/rss', 'https://tech.kakaopay.com/favicon.ico', 'https://tech.kakaopay.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('쿠팡', 'https://medium.com/feed/coupang-engineering', 'https://miro.medium.com/v2/resize:fill:160:160/1*PwmOErfwUKqKYo8G92nNXA.jpeg', 'https://medium.com/coupang-engineering', false);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('당근마켓', 'https://medium.com/feed/daangn', 'https://miro.medium.com/v2/resize:fill:160:160/1*Bm8_nGjfNiKV0PASwiPELg.png', 'https://medium.com/daangn', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('올리브영', 'https://oliveyoung.tech/rss.xml', '
https://oliveyoung.tech/favicon-32x32.png', 'https://oliveyoung.tech', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('뱅크샐러드', 'https://blog.banksalad.com/rss.xml', 'https://blog.banksalad.com/favicon-32x32.png?v=a0f8dfab85709bd32e8bfd56c885f0fe', 'https://blog.banksalad.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('하이퍼커넥트', 'https://hyperconnect.github.io/feed.xml', '
https://hyperconnect.github.io/assets/favicon.svg', 'https://hyperconnect.github.io', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('왓챠', 'https://medium.com/feed/watcha', 'https://miro.medium.com/v2/resize:fill:160:160/1*3YwZ4pRivcBi8nWl7u98gA.png', 'https://medium.com/watcha', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('여기어때', 'https://techblog.gccompany.co.kr/feed', 'https://miro.medium.com/v2/resize:fill:160:160/1*rGdUGkMoxT5SfrVKVAqbEw.png', 'https://techblog.gccompany.co.kr', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('무신사', 'https://medium.com/feed/musinsa-tech', 'https://miro.medium.com/v2/resize:fill:160:160/1*Qs-0adxK8doDYyzZXMXkmg.png', 'https://medium.com/musinsa-tech', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('쏘카', 'https://tech.socarcorp.kr/feed', 'https://tech.socarcorp.kr/assets/icon/favicon.ico', 'https://tech.socarcorp.kr', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('요기요', 'https://techblog.yogiyo.co.kr/feed', '
https://miro.medium.com/v2/resize:fill:40:40/1*gZk0QZMhjKVGGxEu7JBasw.png', 'https://techblog.yogiyo.co.kr', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('29CM', 'https://medium.com/feed/29cm', 'https://miro.medium.com/v2/resize:fill:160:160/1*TP1aY6ZJaPSPs3fKA6sYKA.png', 'https://medium.com/29cm', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('마켓컬리', 'https://helloworld.kurly.com/feed.xml', 'https://helloworld.kurly.com/favicon.ico', 'https://helloworld.kurly.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('카카오엔터프라이즈', 'https://tech.kakaoenterprise.com/feed', 'https://tech.kakaoenterprise.com/favicon.ico', 'https://tech.kakaoenterprise.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('데브시스터즈', 'https://tech.devsisters.com/rss.xml', 'https://tech.devsisters.com/favicon.ico', 'https://tech.devsisters.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('직방', 'https://medium.com/feed/zigbang', 'https://miro.medium.com/v2/resize:fill:160:160/1*_SRVUENl0RXh1VwmFuoHrQ.png', 'https://medium.com/zigbang', false);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('리디', 'https://www.ridicorp.com/feed', 'https://www.ridicorp.com/favicon.ico', 'https://www.ridicorp.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('NHN Toast', 'https://meetup.toast.com/rss', 'https://meetup.toast.com/favicon.ico', 'https://meetup.toast.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('GeekNews', 'https://news.hada.io/rss/news', 'https://news.hada.io/favicon.ico', 'https://news.hada.io', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('개발자스럽다', 'https://blog.gaerae.com/feeds/posts/default?alt=rss', 'https://blog.gaerae.com/favicon.ico', 'https://blog.gaerae.com', true);

MERGE INTO rss_sources (site_name, rss_url, logo_url, site_url, active) KEY (rss_url)
VALUES ('44BITS', 'https://www.44bits.io/ko/feed/all', 'https://www.44bits.io/favicon.ico', 'https://www.44bits.io', true);
