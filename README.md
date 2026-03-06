# GrowTechStack Collector Service

기술 블로그 RSS 피드를 수집하고 콘텐츠 API를 제공하는 마이크로서비스입니다.

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.5, Spring Data JPA |
| Database | MySQL (AWS RDS) |
| Messaging | Apache Kafka (Confluent Cloud) |
| Service Discovery | Spring Cloud Netflix Eureka Client |
| RSS Parsing | Rome 2.1 |
| Docs | SpringDoc OpenAPI (Swagger) |

## 주요 기능

- RSS 피드 자동 수집 (매시간 스케줄러)
- 썸네일 추출 (Enclosure → Media RSS → content:encoded → OG Image)
- 태그 자동 분류 (RSS 카테고리 또는 키워드 분석)
- 수집된 콘텐츠를 Kafka로 AI 요약 요청 비동기 전송
- 콘텐츠 조회·검색 REST API 제공
- RSS 출처 관리 API

## API 엔드포인트

| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/v1/contents` | 콘텐츠 목록 (페이징, 태그/사이트 필터) |
| GET | `/api/v1/contents/search` | 콘텐츠 검색 |
| GET | `/api/v1/contents/{id}` | 콘텐츠 상세 |
| GET | `/api/v1/rss-sources` | RSS 출처 목록 |
| POST | `/api/v1/rss-sources` | RSS 출처 등록 |
| PUT | `/api/v1/rss-sources/{id}` | RSS 출처 수정 |
| DELETE | `/api/v1/rss-sources/{id}` | RSS 출처 삭제 |
| POST | `/api/v1/collector/collect` | 수동 수집 (`?full=true` 전체 수집) |
| POST | `/api/v1/collector/collect/{sourceId}` | 단건 수집 |
| GET | `/api/v1/collector/logs` | 수집 로그 |
| GET | `/api/v1/collector/logs/failures` | 실패 로그 |

## Kafka 토픽

| 토픽 | 방향 | 설명 |
|------|------|------|
| `content-summary-request` | 발행 | AI 요약 요청 |
| `content-summary-result` | 수신 | AI 요약 결과 수신 후 DB 업데이트 |

## 환경 변수

| 변수 | 설명 |
|------|------|
| `DB_HOST` | MySQL 호스트 |
| `DB_NAME` | 데이터베이스명 |
| `DB_USERNAME` | DB 사용자명 |
| `DB_PASSWORD` | DB 비밀번호 |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka 브로커 주소 |
| `KAFKA_USERNAME` | Kafka SASL 사용자명 |
| `KAFKA_PASSWORD` | Kafka SASL 비밀번호 |
| `EUREKA_URL` | Eureka 서버 주소 |

## 로컬 개발

```bash
./gradlew bootRun
```

Swagger UI: `http://localhost:29999/swagger-ui/index.html`

## 배포

`main` 브랜치 push → GitHub Actions → ECR push → EC2 자동 배포
