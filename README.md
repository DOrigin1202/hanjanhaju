# 음식점 추천 시스템 (Restaurant Recommendation System)

감정과 상황 기반 맞춤형 음식점 추천 FastAPI 백엔드

## 📋 프로젝트 개요

사용자의 **감정 상태**와 **상황(목적)**에 따라 광주 지역 음식점을 추천하는 시스템입니다.
- 감정(기쁨/슬픔/고독 등) + 상황(데이트/회식 등)을 점수화
- 음식점의 태그와 매칭하여 최적의 장소 추천
- MySQL 데이터베이스 연동으로 추천 결과 저장 및 조회

## 🗂️ 파일 구조

```
project/
├── main.py                      # FastAPI 메인 서버
├── emotion_dict.py              # 감정별 태그 점수 사전
├── situation_dict.py            # 상황별 태그 점수 사전
├── tag_group_dict.py            # 태그 그룹 분류 사전
├── cleanall_utf8.csv            # 음식점 데이터 (UTF-8)
├── cleaned_store_all.csv        # 음식점 데이터 (원본)
└── cleaned_store_all_utf8.csv   # 음식점 데이터 (UTF-8 변환)
```

## 🚀 주요 기능

### 1. 음식점 추천 (`/api/recommend`)
- **입력**: 동(지역), 감정, 상황
- **출력**: 상위 20개 추천 음식점 (점수 기반 정렬)
- **로직**:
  1. 지역(dong)으로 1차 필터링
  2. 음식점 태그와 감정/상황 사전 매칭
  3. 점수 계산 및 상위 20개 반환

### 2. 추천 결과 저장 (`/api/save_recommend`)
- 추천된 음식점 목록을 MySQL DB에 저장
- 기존 추천 데이터 삭제 후 새로 저장

### 3. 추천 결과 조회 (`/api/get_recommend`)
- DB에 저장된 추천 목록 조회

### 4. 추천 초기화 (`/api/reset_recommend`)
- DB에서 모든 추천 데이터 삭제

## 📊 데이터 구조

### CSV 데이터 (cleanall_utf8.csv)
```
컬럼:
- id: 음식점 ID
- shop_name: 음식점 이름
- dong: 동(지역)
- address: 주소
- clean_address: 정제된 주소
- mid_tag: 음식점 태그 (공백 구분)
- photo_url: 사진 URL
- rating: 평점
```

### 감정 사전 (emotion_dict.py)
```python
{
    "기쁨/신남": {"친구": 2, "회식": 2, "맥주": 1, ...},
    "슬픔": {"분위기": 2, "고독/외로움": 2, "혼술": 2, ...},
    "고독/감성적": {"와인": 1, "조용한": 1, "감성": 1, ...},
    ...
}
```

### 상황 사전 (situation_dict.py)
```python
{
    "데이트/썸": {"데이트": 1.0, "와인": 1.0, "분위기": 1.0, ...},
    "식사": {"한식": 1.0, "일식": 1.0, ...},
    "혼술": {"혼술": 1.0, "고독/외로움": 1.0, ...},
    ...
}
```

### 태그 그룹 (tag_group_dict.py)
```python
{
    "술": ["BAR", "광주술집", "동명동술집", ...],
    "한식": ["동명동밥집", "정육식당", ...],
    "일식": ["이자카야", "사시미", ...],
    ...
}
```

## 🛠️ 설치 및 실행

### 1. 필요 라이브러리 설치

```bash
pip install fastapi uvicorn pandas sqlalchemy pymysql
```

### 2. MySQL 데이터베이스 설정

```python
# main.py에서 DB 연결 정보 수정
DATABASE_URL = "mysql+pymysql://root:123456@127.0.0.1:3306/spring_db"
```

**테이블 자동 생성:**
- `recommended_store` 테이블이 자동으로 생성됩니다

### 3. 서버 실행

```bash
uvicorn main:app --reload
```

서버 실행 후: `http://localhost:8000`

## 📡 API 사용 예시

### 1. 음식점 추천

```bash
GET http://localhost:8000/api/recommend?dong=동명동&emotion=기쁨/신남&situation=회식/단체
```

**응답 예시:**
```json
[
  {
    "id": 123,
    "name": "광주포차",
    "imageUrl": "http://...",
    "rating": 4.5,
    "address": "광주 동구 동명동",
    "score": 8
  },
  ...
]
```

### 2. 추천 결과 저장

```bash
POST http://localhost:8000/api/save_recommend
Content-Type: application/json

[
  {
    "id": 123,
    "name": "광주포차",
    "imageUrl": "http://...",
    "rating": 4.5,
    "address": "광주 동구 동명동",
    "score": 8
  }
]
```

### 3. 추천 결과 조회

```bash
GET http://localhost:8000/api/get_recommend
```

### 4. 추천 초기화

```bash
POST http://localhost:8000/api/reset_recommend
```

## 🧮 점수 계산 로직

```python
def calc_score(tag_list, emotion=None, situation=None):
    total = 0
    
    # 감정 점수
    if emotion and emotion in emotion_dict:
        for tag in tag_list:
            total += emotion_dict[emotion].get(tag, 0)
    
    # 상황 점수
    if situation and situation in situation_dict:
        for tag in tag_list:
            total += situation_dict[situation].get(tag, 0)
    
    return total
```

**예시:**
```
음식점 태그: ["친구", "맥주", "포차"]
감정: "기쁨/신남" → 친구(2) + 맥주(1) + 포차(1) = 4점
상황: "회식/단체" → 친구(1) + 맥주(1) + 포차(1) = 3점
총점: 7점
```

## 🎯 추천 알고리즘 특징

### 장점
- ✅ **개인화**: 감정과 상황에 맞춤 추천
- ✅ **투명성**: 점수 계산 로직이 명확
- ✅ **확장성**: 새로운 감정/상황 추가 용이
- ✅ **지역화**: 광주 지역 특화

### 고려사항
- 태그가 없는 음식점은 점수 0
- 동(지역) 매칭 우선, 부족 시 주소 포함 검색
- 최대 20개 추천 반환

## 🔧 주요 기술 스택

- **백엔드**: FastAPI (Python)
- **데이터**: Pandas (CSV 처리)
- **데이터베이스**: MySQL + SQLAlchemy
- **CORS**: 프론트엔드 연동 가능

## 📝 데이터베이스 스키마

### recommended_store 테이블

```sql
CREATE TABLE recommended_store (
    id INT PRIMARY KEY AUTO_INCREMENT,
    shop_id INT,
    shop_name VARCHAR(100),
    photo_url VARCHAR(1000),
    rating FLOAT,
    address VARCHAR(200),
    score FLOAT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## 🐛 문제 해결

### 1. 한글 깨짐
- CSV 파일이 UTF-8로 인코딩되어 있는지 확인
- `cleanall_utf8.csv` 사용

### 2. DB 연결 실패
```python
# MySQL 서버 실행 확인
# 연결 정보 확인 (host, port, user, password)
DATABASE_URL = "mysql+pymysql://root:123456@127.0.0.1:3306/spring_db"
```

### 3. 추천 결과 없음
- `dong` 값이 정확한지 확인
- CSV 데이터의 `dong` 컬럼과 매칭되는지 확인

## 🚀 향후 개선 방향

1. **협업 필터링**: 사용자 평점 기반 추천 추가
2. **머신러닝**: 태그 점수 자동 학습
3. **실시간 업데이트**: 음식점 정보 API 연동
4. **개인화**: 사용자별 선호도 학습
5. **캐싱**: Redis로 추천 결과 캐싱

## 👨‍💻 개발자

- **이름**: 동인
- **역할**: 백엔드 개발 (FastAPI, 추천 알고리즘)
- **프로젝트**: 감정 기반 음식점 추천 시스템

## 📄 라이선스

이 프로젝트는 내부 프로젝트입니다.

---

**개발 기간**: 2024
**사용 언어**: Python 3.8+
**프레임워크**: FastAPI
