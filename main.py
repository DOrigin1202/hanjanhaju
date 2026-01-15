from fastapi import FastAPI, Query
from fastapi.middleware.cors import CORSMiddleware
from typing import Optional
import pandas as pd

from sqlalchemy import create_engine, Column, Integer, String, Float, DateTime
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
import datetime

from situation_dict import situation_dict
from emotion_dict import emotion_dict

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)




# 점수 계산 (mid_tag만 사용, tag_group_dict 없음)


def calc_score(tag_list, emotion=None, situation=None):
    total = 0
    if emotion and emotion in emotion_dict:
        for tag in tag_list:
            total += emotion_dict[emotion].get(tag, 0)
    if situation and situation in situation_dict:
        for tag in tag_list:
            total += situation_dict[situation].get(tag, 0)
    return total


# CSV 파일 불러오기
df = pd.read_csv("cleanall_utf8.csv")

@app.get("/api/recommend")
async def recommend_api(dong: str = Query(...), emotion: str = Query(None), situation: str = Query(None)):
    print(f"[DEBUG] dong={dong}, emotion={emotion}, situation={situation}")

    # 우선 dong 컬럼으로 정확 매칭 필터링
    matched_df = df[df['dong'] == dong]
    print(f"[DEBUG] 1차 dong 매칭 결과: {len(matched_df)}개")
    
    # 후보 부족하면 address 포함된 것도 서브로 필터링
    if len(matched_df) < 20:
        fallback_df = df[df['address'].str.contains(dong, na=False) | df['clean_address'].str.contains(dong, na=False)]
        matched_df = pd.concat([matched_df, fallback_df]).drop_duplicates()
        print(f"[DEBUG] fallback 후 행 개수: {len(matched_df)}")
        

    print("[DEBUG] 샘플 row 3개:", matched_df.head(3).to_dict())
    
    # 추천점수 계산
    shop_results = []
    print("[DEBUG] 추천점수 계산 시작")
    
    for (idx, row) in matched_df.iterrows():
        tags = row['mid_tag'].split() if isinstance(row['mid_tag'], str) else []
        score = 0
        if emotion in emotion_dict:
            score += sum(emotion_dict[emotion].get(tag, 0) for tag in tags)
        if situation in situation_dict:
            score += sum(situation_dict[situation].get(tag, 0) for tag in tags)
        if idx < 3:  # 상위 3개만 상세출력
            print(f"[DEBUG] {idx}번째 상점, tags: {tags}, score: {score}")

        shop_results.append({
            'id': row['id'],
            'name': row['shop_name'],
            'imageUrl': row['photo_url'],
            'rating': row['rating'],
            'address': row['address'],
            'score': score
        })
    
    print(f"[DEBUG] 추천 상점 수: {len(shop_results)}")
    
    
    top_shops = sorted(shop_results, key=lambda x: x['score'], reverse=True)[:20]
    print("[DEBUG] 최종 반환 top 3:", top_shops[:3])
    return top_shops


DATABASE_URL = "mysql+pymysql://root:123456@127.0.0.1:3306/spring_db"

engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(bind=engine)
Base = declarative_base()

class RecommendedStore(Base):
    __tablename__ = "recommended_store"
    id = Column(Integer, primary_key=True, autoincrement=True)
    shop_id = Column(Integer)
    shop_name = Column(String(100))
    photo_url = Column(String(1000))
    rating = Column(Float)
    address = Column(String(200))
    score = Column(Float)
    created_at = Column(DateTime, default=datetime.datetime.now)
    
Base.metadata.create_all(bind=engine)

from fastapi import Depends
from sqlalchemy.orm import Session

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.post("/api/save_recommend")
def save_recommend(shops: list[dict], db: Session = Depends(get_db)):
    print("[DEBUG] /api/save_recommend called", shops)
    # 1. 이전 추천 삭제 (원하면)
    db.query(RecommendedStore).delete()
    db.commit()

    # 2. 추천 목록 저장
    for shop in shops:
        rec = RecommendedStore(
            shop_id=shop["id"],
            shop_name=shop["name"],
            photo_url=shop["imageUrl"],
            rating=shop["rating"],
            address=shop["address"],
            score=shop["score"],
        )
        db.add(rec)
    db.commit()
    return {"result": "success"}

# 삭제만 따로
@app.post("/api/reset_recommend")
def reset_recommend(db: Session = Depends(get_db)):
    db.query(RecommendedStore).delete()
    db.commit()
    return {"result": "reset"}

@app.get("/api/get_recommend")
def get_recommend(db: Session = Depends(get_db)):
    rows = db.query(RecommendedStore).all()
    return [
        {
            "id": r.shop_id,
            "name": r.shop_name,
            "imageUrl": r.photo_url,
            "rating": r.rating,
            "address": r.address,
            "score": r.score,
            "created_at": r.created_at
        }
        for r in rows
    ]
