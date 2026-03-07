-- 초기 태그 등록
INSERT IGNORE INTO tags (name) VALUES ('ai');
INSERT IGNORE INTO tags (name) VALUES ('backend');
INSERT IGNORE INTO tags (name) VALUES ('frontend');
INSERT IGNORE INTO tags (name) VALUES ('design');
INSERT IGNORE INTO tags (name) VALUES ('devops');
INSERT IGNORE INTO tags (name) VALUES ('architecture');
INSERT IGNORE INTO tags (name) VALUES ('etc');

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
VALUES ('GeekNews', 'https://news.hada.io/rss/news', 'https://news.hada.io/favicon.ico', 'https://news.hada.io', false);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('개발자스럽다', 'https://blog.gaerae.com/feeds/posts/default?alt=rss', 'https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhoVtq5iot4FBRLA29jUDX-zVf7vcL7q5CBEZakJ6aolro0hg-1o5m20TcLjutCUi2yJl5q0HlDZsj87V8ZAQ3JyTwc8DUEfBJ6lInD31uTa5PznoZU_DW3i0G6PdeDcYR4NYpxz0u545F1H_NUb7jNl_TYfoW3ONpHKI9teefnWb9hGQB9Td6DvNg8g2U/w35-h35-p-k-no-nu/gaeraecom_logo_black.webp', 'https://blog.gaerae.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('44BITS', 'https://www.44bits.io/ko/feed/all', 'https://www.44bits.io/favicon.png', 'https://www.44bits.io', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('카카오뱅크', 'https://tech.kakaobank.com/index.xml', 'https://t0.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=https://tech.kakaobank.com/&size=16', 'https://tech.kakaobank.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('데보션', 'https://devocean.sk.com/blog/rss.do', 'https://devocean.sk.com/resource/images/external/logo/logo_favicon.ico', 'https://devocean.sk.com/blog/index.do?p=BLOG', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('AWS', 'https://aws.amazon.com/ko/blogs/tech/feed/', 'https://a0.awsstatic.com/main/images/site/fav/favicon.ico', 'https://aws.amazon.com/ko/blogs/tech', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('딜라이트룸', 'https://medium.com/feed/delightroom', 'https://miro.medium.com/v2/resize:fill:160:160/1*Kd5NIGYivhZISqC47JiD6A.png', 'https://medium.com/delightroom', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('인포그랩', 'https://insight.infograb.net/blog/rss.xml', 'https://insight.infograb.net/img/favicon.ico', 'https://insight.infograb.net/blog', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('마이리얼트립', 'https://medium.com/feed/myrealtrip-product', 'https://about.myrealtrip.com/favicon.ico?v=4', 'https://medium.com/myrealtrip-product', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('라포랩스', 'https://blog.rapportlabs.kr/rss', 'https://image.inblog.dev/?url=https%3A%2F%2Fsource.inblog.dev%2Ffavicon%2F2025-04-22T06%3A37%3A36.379Z-bda6be14-29d4-49cc-ad41-52e7695eb460&w=48&h=48&q=100&format=png&objectFit=contain', 'https://blog.rapportlabs.kr', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('교보DTS', 'https://blog.kyobodts.co.kr/feed/', 'https://blog.kyobodts.co.kr/wp-content/uploads/2022/01/cropped-logo_2214-removebg-preview-1-32x32.png', 'https://blog.kyobodts.co.kr', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('KT Cloud', 'https://ktcloudplatform.tistory.com/rss', 'https://tistory1.daumcdn.net/tistory/4226485/2c005b1f17254356b53868e19f84f441', 'https://tech.ktcloud.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('가비아', 'https://library.gabia.com/feed/', 'https://library.gabia.com/wp-content/uploads/2024/09/cropped-Favicon-2-32x32.png', 'https://library.gabia.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('SSG', 'https://medium.com/feed/ssgtech', 'https://miro.medium.com/v2/resize:fill:160:160/1*IFPbUqRn__nXbkPrxhi38A.png', 'https://medium.com/ssgtech', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('한글과컴퓨터', 'https://tech.hancom.com/feed/', 'https://tech.hancom.com/wp-content/uploads/2024/04/favicon_32.ico', 'https://tech.hancom.com', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('농심NDS', 'https://tech.cloud.nongshim.co.kr/feed/', 'https://tech.cloud.nongshim.co.kr/wp-content/uploads/nds_cloud_icon-150x150.png', 'https://tech.cloud.nongshim.co.kr/post', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('카카오모빌리티', 'https://developers.kakaomobility.com/techblogs.xml', 'https://developers.kakaomobility.com/favicon.ico', 'https://developers.kakaomobility.com/techblogs', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('티맵모빌리티', 'https://brunch.co.kr/rss/@@dsaj', 'https://img1.daumcdn.net/thumb/C500x500.fjpg/?fname=http://k.kakaocdn.net/dn/bjFlpf/btrnQkik5af/PFU4rZ6G02xIYuBH3d4a7K/img_640x640.jpg', 'https://brunch.co.kr/@tmapmobility', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('크림', 'https://medium.com/feed/kream-%EA%B8%B0%EC%88%A0-%EB%B8%94%EB%A1%9C%EA%B7%B8', 'https://miro.medium.com/v2/resize:fill:160:160/1*qI4UU92oN36TWf2cNBWF8A.png', 'https://medium.com/kream-%EA%B8%B0%EC%88%A0-%EB%B8%94%EB%A1%9C%EA%B7%B8', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('캐치테이블', 'https://medium.com/feed/catchtable', 'https://miro.medium.com/v2/resize:fill:160:160/1*dTopsrBdT8HvLgfsPR58wg.png', 'https://medium.com/catchtable', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('더스윙', 'https://www.theswing.tech/rss/', 'https://www.theswing.tech/content/images/2025/01/favicon.ico', 'https://www.theswing.tech', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('펫프렌즈', 'https://techblog.pet-friends.co.kr/feed', 'https://miro.medium.com/v2/resize:fill:192:192/1*aDabmlrxvOf4FMmALFd7LQ.jpeg', 'https://techblog.pet-friends.co.kr', true);

INSERT IGNORE INTO rss_sources (site_name, rss_url, logo_url, site_url, active)
VALUES ('SK플래닛', 'https://techtopic.skplanet.com/rss.xml', 'https://techtopic.skplanet.com/favicon-32x32.png?v=b9b4c68bfc2efbb18cf4e6dddd56222e', 'https://techtopic.skplanet.com', true);
