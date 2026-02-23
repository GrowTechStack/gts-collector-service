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

## Roadmap

- [ ] **배포 + MySQL 마이그레이션** (AWS / OCI)
    - [ ] H2 → MySQL 전환 (드라이버 교체 + schema.sql 방언 조정)
    - [ ] CI/CD 파이프라인 구성 (GitHub Actions → Docker Build → 자동 배포) / 방식 변경 가능성 있음
    - [ ] MySQL 전환 후 썸네일 업데이트 정상 동작 여부 재확인

- [ ] **MSA 1단계** — 프론트 분리 + Gateway 도입
    - [ ] 프론트 별도 레포 분리 (Thymeleaf → React/Next.js, REST API 방식으로 전환)
    - [ ] Spring Cloud Gateway 도입
    - [ ] `ContentViewController` 제거, REST API만 유지
~~~~
- [ ] **Auth 서버 개발** — 회원 시스템 + 관리자 기능 통합
    - [ ] JWT + Redis 기반 세션 관리 (Access Token 30분, Refresh Token 7일)
    - [ ] 로그인/회원가입/북마크 연동
    - [ ] Gateway에서 토큰 검증 후 사용자 정보 헤더 전달 구조로 Security 전면 수정
    - [ ] MAU 등 비즈니스 지표 커스텀 메트릭 코드 작성

- [ ] **모니터링** — Prometheus + Grafana
    - [ ] spring-boot-actuator + micrometer-prometheus 연동
    - [ ] 사용자 접속 수, 머무른 시간, MAU 등 커스텀 메트릭 구성

- [ ] **사용자 콘텐츠** — 직접 글 작성 기능 (`ContentType.USER`) 활성화

- [x] **AI 요약** — Kafka 기반 비동기 AI 요약 연동 완료 (gts-ai-summary-service / Groq LLaMA 3.1)
    - [ ] AI 태그 — OutputParser 활용 JSON 구조화 태그 추출

- [ ] **뉴스레터** — 이메일 알림 + 선호 블로그 구독 설정 (AWS SES / Gmail SMTP)

- [ ] **UI 개선** — 라이트/다크 모드, 카드 뷰 필터 (상세/간략)

- [ ] **MSA 2단계** — 필요 시 User/MNG 분리, Config 서버 도입
    - [x] Kafka 도입 — RSS 수집 완료 이벤트 → AI 요약 서비스 비동기 consume
    - [ ] 뉴스레터 등 추가 서비스 Kafka 연동 (AWS MSK 검토)

---

