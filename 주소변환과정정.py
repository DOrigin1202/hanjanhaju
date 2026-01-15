import requests
import pandas as pd
import requests
import time
from dotenv import load_dotenv
import os

load_dotenv()
api_key = os.getenv('API_KEY')

def road_to_old_address(road_addr, key):
    url = "https://business.juso.go.kr/addrlink/addrLinkApi.do"
    params = {
        "confmKey": key,
        "currentPage": 1,
        "countPerPage": 1,
        "keyword": road_addr,
        "resultType": "json"
    }

    try:
        res = requests.get(url, params=params)
        if res.status_code == 200:
            data = res.json()
            dong = data['results']['juso'][0]['emdNm']
            return dong
        else:
            return "에러"
    except:
        return "요청실패"


def make_full_address(addr):
    if "광주광역시" in addr:
        return addr
    elif "광주 " in addr:
        return "광주광역시 " + addr.split(" ", 1)[1]
    else:
        return "광주광역시 " + addr

# API 키
api_key = api_key



# CSV 불러오기
df = pd.read_csv("./euc3.csv", encoding="euc-kr")
# full_address 생성
df['full_address'] = df['clean_address'].apply(make_full_address)
# dong 변환 적용
df['dong'] = df['full_address'].apply(lambda x: road_to_old_address(x, api_key))
time.sleep(0.2)
# 저장
df.to_csv("dddong.csv", index=False, encoding="euc-kr")




data=pd.read_csv("dddong.csv", encoding="euc-kr")
data3=pd.read_csv("./dddong.csv", encoding="euc-kr")



data[data['dong']=="요청실패"]

data = data[data['dong'] != "요청실패"]
data.to_csv("cleaned_store_all.csv", index=False, encoding="euc-kr")

df=pd.read_csv("./cleaned_store_all.csv", encoding="euc-kr")

df['dong'].unique()

df[['dong']].to_csv("dong.csv", index=False, encoding="euc-kr")

df2=pd.read_csv("./dong.csv", encoding="euc-kr")

df2

