/* 특정카테고리의 게시글 가져오기 */
const categoryBtn = document.querySelectorAll(".category-btn");

categoryBtn.forEach(categoryBtn => {
  categoryBtn.addEventListener("click", () => {
    console.log("클릭됨");
      const categoryNo = categoryBtn.dataset.categoryNo;
      const boardCode = window.location.pathname.split("/")[2];
      location.href = `/board/${boardCode}/category/${categoryNo}`;
    });
});


/* 글쓰기 버튼 / 검색 요소 */
const insertBtn = document.querySelector("#insertBtn");

insertBtn.addEventListener("click", e => {
  
  if(boardCode == 4){
    if(authority =='N'){
      alert("관리자만 작성할 수 있습니다.");
      e.preventDefault();
      return;
    }
  }
  
  location.href = `/editBoard/${boardCode}/insert`;

});

