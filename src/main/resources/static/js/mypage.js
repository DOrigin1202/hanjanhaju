// 뒤로가기 버튼 기능
// function goBack() {
  // window.location.href = "result.html";
//}

// 로컬에서 리뷰 수, 찜 수 불러오기
// document.addEventListener("DOMContentLoaded", () => {
  // const reviewCount = localStorage.getItem("reviewCount") || 0;
  // const wishlistCount = localStorage.getItem("wishlistCount") || 0;

  // document.getElementById("review-count").textContent = reviewCount;
  // document.getElementById("like-count").textContent = wishlistCount;
//});

// 로그아웃 버튼 기능
function logout() {
  // (1) 로컬 저장소 정리 (선택사항)
  localStorage.removeItem("wishlist");
  localStorage.removeItem("reviewCount");
  localStorage.removeItem("wishlistCount");
  localStorage.removeItem("selectedStore");
  localStorage.removeItem("selectedData");

  // (2) Spring 서버 로그아웃 요청
  fetch("/logout", { method: "POST" })
    .then(() => {
      alert("로그아웃 되었습니다.");
      window.location.href = "/start"; // 시작화면으로 이동
    })
    .catch((err) => {
      console.error("Logout error:", err);
      alert("로그아웃 중 오류가 발생했습니다.");
    });
}
