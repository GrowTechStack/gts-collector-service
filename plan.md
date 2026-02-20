# GrowTechStack 구현 상세 계획 (Implementation Plan)

## 1. 개요
본 프로젝트는 "벨로퍼트"와 같은 기술 블로그 어그리게이터를 지향합니다. 외부 기술 블로그(예: Toss Tech)의 콘텐츠를 자동으로 수집하여 제공하며, 향후 사용자가 직접 글을 작성할 수 있는 플랫폼으로의 확장을 고려하여 설계합니다.

---

## 2. 데이터베이스 설계 전략: 단일 테이블 (Single Table)
통합 검색, 정렬, 공통 기능(북마크, 조회수)의 효율적 처리를 위해 사용자 글과 외부 수집 글을 하나의 `Content` 테이블에서 관리합니다.

### 2.1 Content 엔티티 구조
- `id`: PK
- `type`: `EXTERNAL` (수집), `USER` (사용자 작성)
- `title`: 제목
- `summary`: 수집된 글의 요약 또는 사용자 작성 글의 서두
- `body`: 사용자 작성 글의 본문 (`EXTERNAL` 타입일 경우 null)
- `originalUrl`: 외부 블로그 원문 링크 (`USER` 타입일 경우 null)
- `viewCount`: 조회수
- `publishedAt`: 원문 발행일 또는 글 작성일

---

## 3. 단계별 구현 상세 (Phase 1 완료)

### Step 1: 공통 기반 및 도메인 모델 고도화 (완료)
- [x] 표준 응답 포맷 (`ApiResult`) 및 전역 예외 처리 구축.
- [x] 단일 테이블 기반의 `Content` 엔티티 및 Repository 생성.
- [x] 프로젝트 명칭 및 패키지 리팩토링 (**GrowTechStack**, `com.gts.collector`).

### Step 2: RSS 수집 엔진 구현 (Collector) (완료)
- [x] **`RssCollectorService` (Interface/Impl)**
    - Rome 라이브러리를 활용하여 RSS 피드 파싱 및 중복 방지 로직 구현.
- [x] **`CollectorScheduler` / `CollectorService`**
    - 1시간 간격 자동 수집 및 **즉시 수집 API** (`POST /api/v1/collector/collect`) 구현.

### Step 3: 콘텐츠 서비스 및 API (Content Service) (완료)
- [x] **목록 조회 및 필터링**
    - **태그별 필터링** 기능 추가 (Backend, Frontend, AI 등).
    - 페이징 및 최신순 정렬 적용.
- [x] **조회수 관리**
    - 단건 조회 시 **조회수 중복 증가 방지** (24시간 쿠키 기반) 로직 적용.

### Step 4: 웹 인터페이스 (UI) 구축 (완료)
- [x] **Thymeleaf 기반 프론트엔드**
    - 메인 피드 (가로 리스트형 레이아웃), 상세 요약 페이지 구현.
    - **게시글 읽음 표시** (LocalStorage 기반) 기능 추가.
- [x] **태그 내비게이션**
    - 상단 스티키 태그 탭 컴포넌트 (`fragments/components.html`) 분리 및 적용.
- [x] **관리자 페이지**
    - RSS 출처 관리(CRUD) 및 수집 실행 제어 UI 구현.

### Step 5: 수집 소스 관리 기능 (완료)
- [x] **`RssSourceService` / `Controller`**
    - 외부 기술 블로그 출처를 동적으로 관리하는 CRUD API 및 UI 연동.

---

## 4. 확장 로드맵 (Future Phases)
- **Phase 2 (회원 시스템)**: 로그인/회원가입 기능 추가 및 사용자 북마크 연동.
- **Phase 3 (사용자 콘텐츠)**: 사용자가 직접 글을 작성하는 기능 (`type = USER`) 활성화.
- **Phase 4 (AI 요약)**: 수집된 글에 대한 AI 자동 요약 기능 연동.
