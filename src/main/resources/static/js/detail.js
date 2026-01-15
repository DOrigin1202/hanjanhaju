function goBack() {
  window.history.back();
}

const store = JSON.parse(localStorage.getItem("selectedStore"));
const container = document.getElementById("store-detail");
const likeBtn = document.getElementById("likeBtn");

function isWished(store) {
  const wishlist = JSON.parse(localStorage.getItem("wishlist")) || [];
  return wishlist.some((s) => s.name === store.name);
}

function toggleWish(store) {
  let wishlist = JSON.parse(localStorage.getItem("wishlist")) || [];
  let likeCount = parseInt(localStorage.getItem("likeCount")) || 0;

  const exists = wishlist.find((s) => s.name === store.name);

  if (exists) {
    wishlist = wishlist.filter((s) => s.name !== store.name);
    likeCount--;
    likeBtn.textContent = "♡";
    likeBtn.classList.remove("liked");
  } else {
    wishlist.push(store);
    likeCount++;
    likeBtn.textContent = "♥";
    likeBtn.classList.add("liked");
  }

  localStorage.setItem("wishlist", JSON.stringify(wishlist));
  localStorage.setItem("likeCount", likeCount);
}

// 술집 정보 표시
if (store) {
  const { name, address, description, tags } = store;

  const html = `
    <h2>${name}</h2>
    <p><strong>주소:</strong> ${address}</p>
    <p><strong>설명:</strong> ${
      description || tags?.join(" ") + " 감성을 담은 추천 술집입니다."
    }</p>
    <div class="tag-list">
      ${(tags || []).map((tag) => `<div class="tag">${tag}</div>`).join("")}
    </div>
  `;

  container.insertAdjacentHTML("beforeend", html);

  // 찜 상태 초기화
  if (isWished(store)) {
    likeBtn.textContent = "♥";
    likeBtn.classList.add("liked");
  }

  likeBtn.addEventListener("click", () => {
    toggleWish(store);
  });
} else {
  container.innerHTML += "<p>불러올 데이터가 없습니다.</p>";
}
