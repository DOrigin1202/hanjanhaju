function goBack() {
  window.location.href = "main.html";
}

function goToMyPage() {
  window.location.href = "mypage.html";
}

const selectedData = JSON.parse(localStorage.getItem("selectedData"));
const filters = document.getElementById("filters");
const storeList = document.getElementById("store-list");
const loading = document.getElementById("loading");

function renderSelectedFilters(data) {
  const district = data.district || "";
  const neighborhoods = data.neighborhoods || "";
  const situations = data.situations || [];
  const emotions = data.emotions || [];

  filters.innerText = `선택: ${district}${
    neighborhoods ? " > " + neighborhoods : ""
  } | 상황: ${situations.join(", ")} | 감정: ${emotions.join(", ")}`;
}

renderSelectedFilters(selectedData);

let page = 1;
const perPage = 5;

function isWished(store) {
  const wishlist = JSON.parse(localStorage.getItem("wishlist")) || [];
  return wishlist.some((s) => s.name === store.name);
}

function toggleWish(store, likeBtn) {
  let wishlist = JSON.parse(localStorage.getItem("wishlist")) || [];
  let likeCount = parseInt(localStorage.getItem("likeCount")) || 0;

  const exists = wishlist.find((s) => s.name === store.name);

  if (exists) {
    wishlist = wishlist.filter((s) => s.name !== store.name);
    likeCount--;
    likeBtn.textContent = "♡";
  } else {
    wishlist.push(store);
    likeCount++;
    likeBtn.textContent = "♥";
  }

  localStorage.setItem("wishlist", JSON.stringify(wishlist));
  localStorage.setItem("likeCount", likeCount);
}

function fetchStores(page) {
  loading.style.display = "block";

  const exampleStores = Array.from({ length: perPage }, (_, i) => {
    const situationTags = selectedData.situations || [];
    const emotionTags = selectedData.emotions || [];
    const allTags = [...situationTags, ...emotionTags];

    return {
      name: `술집 ${i + 1 + (page - 1) * perPage}`,
      address: `${selectedData.district} ${
        selectedData.neighborhoods[0] || "중심지"
      }`,
      phone: `010-1234-56${String(i).padStart(2, "0")}`,
      tags: allTags,
    };
  });

  setTimeout(() => {
    exampleStores.forEach((store) => {
      const tagHTML = store.tags.map((tag) => `<span>${tag}</span>`).join("");

      const div = document.createElement("div");
      div.className = "store";
      div.innerHTML = `
        <div class="like-button">♡</div>
        <h2>${store.name}</h2>
        <p>${store.address}</p>
        <p>전화: ${store.phone}</p>
        <div class="tags">${tagHTML}</div>
      `;

      const likeBtn = div.querySelector(".like-button");
      if (isWished(store)) {
        likeBtn.textContent = "♥";
      }

      likeBtn.addEventListener("click", (e) => {
        e.stopPropagation(); // 클릭 이벤트 버블 방지
        toggleWish(store, likeBtn);
      });

      div.addEventListener("click", () => {
        localStorage.setItem("selectedStore", JSON.stringify(store));
        window.location.href = "store_detail.html";
      });

      storeList.appendChild(div);
    });
    loading.style.display = "none";
  }, 500);
}

window.addEventListener("scroll", () => {
  if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
    page++;
    fetchStores(page);
  }
});

fetchStores(page);
