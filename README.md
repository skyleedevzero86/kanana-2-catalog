# Kanana-2 Catalog & Inference
<br/>

<img width="951" height="526" alt="image" src="https://github.com/user-attachments/assets/59788a34-89b7-42e7-a343-c84115925e1a" />

<Br/>
**Kanana-2 LLM** 카탈로그, 벤치마크, 서빙 정보 조회 및 vLLM 연동 인퍼런스를 위한 API + 웹 프로젝트입니다.

---

## 개요

이 저장소는 두 개의 하위 프로젝트로 구성됩니다.

| 프로젝트 | 설명 |
|----------|------|
| **kanana-api** | Kanana-2 모델 카탈로그·벤치마크·서빙 프리셋 REST API 및 vLLM 인퍼런스 연동 (Spring Boot) |
| **kanana-web** | 위 API를 사용하는 React SPA — 카탈로그·벤치마크 차트·인퍼런스 UI |

카카오 오픈소스 **Kanana-2** 시리즈(Base, Mid, Instruct, Thinking)의 모델 정보를 조회하고, vLLM 서버와 연동해 텍스트 완성(chat completions)을 요청할 수 있습니다.

---

## 주요 기능

- **모델 카탈로그**: 등록된 Kanana-2 모델 목록 및 상세(스펙, 변형, Hugging Face 링크)
- **벤치마크**: 모델별 벤치마크 결과 조회 및 카테고리 필터, 웹에서 막대 차트로 시각화
- **서빙 프리셋**: vLLM 등 서빙 시 사용할 명령어·옵션 조회
- **인퍼런스**: vLLM 서버와 연동한 텍스트 완성 API + 웹에서 모델 선택 후 질의·응답

---

## 기술 스택

| 구분 | 스택 |
|------|------|
| **API** | Java 17, Spring Boot 3.2, WebFlux(WebClient), Validation |
| **Web** | pnpm, Vite, React 18, TypeScript, React Router v6, Recharts |

---

## 빠른 시작

### 1. API 실행 (kanana-api)

```bash
cd kanana-api
./gradlew bootRun
```

- 서버: **http://localhost:8081**
- vLLM 인퍼런스 사용 시: `app.llm.inference-base-url` 또는 환경 변수 `LLM_INFERENCE_URL` 로 vLLM 서버 URL 지정

### 2. 웹 실행 (kanana-web)

```bash
cd kanana-web
pnpm install
pnpm dev
```

- 개발 서버: **http://localhost:5173**
- `/api` 요청은 Vite proxy로 `http://localhost:8081`(kanana-api)로 전달됨

**참고**: 웹에서 모델 목록·인퍼런스를 쓰려면 kanana-api가 8081에서 실행 중이어야 합니다.

---

## API 요약

| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/v1/models` | 모델 목록 |
| GET | `/api/v1/models/{id}` | 모델 상세 |
| GET | `/api/v1/models/{id}/benchmarks` | 벤치마크 (`?category=MATHEMATICS` 등) |
| GET | `/api/v1/models/{id}/serving` | 서빙 프리셋 |
| POST | `/api/v1/complete` | LLM 텍스트 완성 (vLLM 연동) |

---

## 웹 라우팅

| 경로 | 화면 |
|------|------|
| `/` | 홈 — 요약 카드, 모델 변형 분포 차트 |
| `/models` | 모델 카탈로그 그리드 |
| `/models/:id` | 모델 상세·벤치마크 차트 |
| `/complete` | 인퍼런스 — 모델 선택·메시지 입력·응답 표시 |

---

## 프로젝트 구조

```
.
├── kanana-api/     # Spring Boot REST API (DDD, Port/Adapter)
├── kanana-web/     # React SPA (Repository → Hooks → Pages/Components)
└── kanana2Readme.md
```

- **kanana-api** 상세: [kanana-api/README.md](kanana-api/README.md) — 패키지 구조, API 명세, 설정
- **kanana-web** 상세: [kanana-web/README.md](kanana-web/README.md) — 디렉터리 구조, 라우팅, 스크립트

---

## 요약

- **역할**: Kanana-2 LLM 카탈로그·벤치마크·서빙 정보 제공 + vLLM 연동 인퍼런스
- **구성**: kanana-api(백엔드) + kanana-web(프론트엔드)
- **실행**: API `bootRun` → 웹 `pnpm dev` 후 http://localhost:5173 접속
