const tags = document.querySelectorAll(".tag");
tags.forEach((tag) => {
  tag.addEventListener("click", () => {
    tag.classList.toggle("selected");
  });
});

const dongData = {
  donggu: [
    "충장동",
    "동명동",
    "계림1동",
    "계림2동",
    "산수1동",
    "산수2동",
    "지산1동",
    "지산2동",
    "서남동",
    "학동",
    "학운동",
    "지원1동",
    "지원2동",
  ],
  seogu: [
    "서창동",
    "금호1동",
    "금호2동",
    "풍암동",
    "농성1동",
    "농성2동",
    "치평동",
    "유덕동",
    "동천동",
    "광천동",
    "양동",
    "양3동",
    "화정1동",
    "화정2동",
    "화정3동",
    "화정4동",
    "상무1동",
    "상무2동",
  ],
  namgu: [
    "양림동",
    "방림1동",
    "방림2동",
    "봉선1동",
    "봉선2동",
    "사직동",
    "월산동",
    "월산4동",
    "월산5동",
    "백운1동",
    "백운2동",
    "주월1동",
    "주월2동",
    "진월동",
    "효덕동",
    "송암동",
    "대촌동",
  ],
  bukgu: [
    "중흥동",
    "중흥1동",
    "중앙동",
    "임동",
    "신안동",
    "용봉동",
    "운암1동",
    "운암2동",
    "운암3동",
    "동림동",
    "우산동",
    "풍향동",
    "문화동",
    "문흥1동",
    "문흥2동",
    "두암1동",
    "두암2동",
    "두암3동",
    "삼각동",
    "일곡동",
    "매곡동",
    "오치1동",
    "오치2동",
    "석곡동",
    "건국동",
    "양산동",
    "신용동",
  ],
  gwangsan: [
    "송정1동",
    "송정2동",
    "도산동",
    "신흥동",
    "어룡동",
    "우산동",
    "월곡1동",
    "월곡2동",
    "운남동",
    "하남동",
    "첨단1동",
    "첨단2동",
    "비아동",
    "수완동",
    "신가동",
    "신창동",
    "평동",
    "임곡동",
    "동곡동",
    "삼도동",
    "본량동",
  ],
};

const districtTags = document.querySelectorAll("#districts .tag");
const neighborhoodsContainer = document.getElementById("neighborhoods");

districtTags.forEach((tag) => {
  tag.addEventListener("click", () => {
    districtTags.forEach((t) => t.classList.remove("selected"));
    tag.classList.add("selected");

    const dongKey = tag.getAttribute("data-dong");
    const dongs = dongData[dongKey] || [];

    neighborhoodsContainer.innerHTML = dongs
      .map((dong) => `<div class="tag">${dong}</div>`)
      .join("");

    document.querySelectorAll("#neighborhoods .tag").forEach((t) => {
      t.addEventListener("click", () => t.classList.toggle("selected"));
    });
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  const submitBtn = document.getElementById("submit-btn");

  form.addEventListener("submit", (e) => {
    const selectedTags = document.querySelectorAll('input[name="tags"]:checked');
    if (selectedTags.length === 0) {
      e.preventDefault(); // 폼 전송 막기
      alert("태그를 최소 하나 이상 선택해주세요!");
    }
  });
});



