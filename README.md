# DevFeed

국내외 기술 블로그 콘텐츠를 자동 수집·통합하여 제공하는 **기술 블로그 애그리게이터** 서비스입니다.

RSS 피드 기반으로 주요 기술 블로그(Toss, Kakao, Line 등)의 최신 글을 자동 수집하며,
향후 사용자가 직접 지식을 공유하고 성장할 수 있는 플랫폼을 지향합니다.

---

## Tech Stack

| 분류 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot 3.x, Spring Data JPA, Spring Security |
| Database | H2 File (→ MySQL 마이그레이션 예정) |
| Frontend | Thymeleaf, Bootstrap 5, JavaScript (→ 분리 예정) |
| Messaging | Apache Kafka (KRaft 모드) |
| Infra | (배포 예정: AWS / OCI) |
| Library | Rome (RSS Parsing), SpringDoc (Swagger/OpenAPI) |

---

## Project Structure

```
src/main/java/com/gts/collector/
├── domain/
│   ├── content/       # 콘텐츠 조회·검색·노출
│   ├── feed/          # RSS 수집·출처 관리
│   ├── user/          # 회원 (구현 예정)
│   ├── bookmark/      # 북마크 (구현 예정)
│   └── comment/       # 댓글 (구현 예정)
└── global/
    ├── config/        # Security, Swagger 설정
    ├── error/         # 전역 예외 처리
    ├── kafka/         # Kafka Producer/Consumer
    └── scheduler/     # RSS 수집 스케줄러
```

---

## Key Features

- **RSS 자동 수집**: 등록된 기술 블로그 RSS를 매시간 자동 수집 (Rome 라이브러리)
- **AI 요약 연동**: 수집된 콘텐츠를 Kafka를 통해 gts-ai-summary-service로 비동기 요약 요청
- **썸네일 추출**: Enclosure → Media RSS → content:encoded → OG Image 순서로 fallback 추출
- **태그 자동 분류**: RSS 카테고리 또는 본문 키워드 분석을 통한 자동 태그 부여
- **콘텐츠 검색**: 제목·요약 기반 키워드 검색 (MySQL 전환 후 Fulltext 검색으로 교체 예정)
- **조회수 집계**: 쿠키 기반 중복 조회 방지

---

