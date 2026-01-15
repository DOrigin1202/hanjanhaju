// static/js/wishlist.js
function goBack() {
  window.location.href = "/mypage";
}

const placeList = document.getElementById("place-list");
const loading = document.getElementById("loading");

const wishlistData = JSON.parse(localStorage.getItem("wishlist")) || [];
const perPage = 5;
let page = 1;

function loadWishlist(page) {
  loading.style.display = "block";

  const items = wishlistData.slice((page - 1) * perPage, page * perPage);

  setTimeout(() => {
    items.forEach((place) => {
      const div = document.createElement("div");
      div.className = "place";
      div.innerHTML = `
        <h2>${place.name}</h2>
        <p>${place.address}</p>
        <p>${place.description}</p>
      `;
      div.addEventListener("click", () => {
        localStorage.setItem("selectedStore", JSON.stringify(place));
        window.location.href = "/store_detail";
      });
      placeList.appendChild(div);
    });
    loading.style.display = "none";
  }, 400);
}

window.addEventListener("scroll", () => {
  if (
    window.innerHeight + window.scrollY >=
    document.body.offsetHeight - 100
  ) {
    page++;
    loadWishlist(page);
  }
});

loadWishlist(page);
