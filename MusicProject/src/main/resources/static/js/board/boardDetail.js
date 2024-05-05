/* 목록으로 */
const goListBtn = document.querySelector("#goListBtn");

goListBtn.addEventListener("click", () => {
    let url = location.pathname;
    url = url.substring(0, url.lastIndexOf("/"));

    location.href = url + location.search;
})


/* 좋아요 */
const boardLike = document.querySelector("#boardLike");
boardLike.addEventListener("click", e => {

    if(loginMemberNo == null){
        alert("로그인 후 좋아요를 눌러주세요");
        return;
    }

    const obj = {
        "memberNo" : loginMemberNo,
        "boardNo" : boardNo,
        "likeCheck" : likeCheck
    };

    fetch("/board/like", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(count => {
        if(count == -1){
            console.log("좋아요 처리 실패");
            return;
        }

        likeCheck = likeCheck == 0 ? 1 : 0;

        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        e.target.nextElementSibling.innerText = count;
    });
});


const deleteBtn = document.querySelector("#deleteBtn");

deleteBtn.addEventListener("click", () => {

    if(confirm("삭제 하시겠습니까?")){
        location.href = `/editBoard/${boardCode}/${boardNo}/delete`;
        alert("삭제 되었습니다.");
    }else{
        alert("취소 되었습니다.");
    }
});


const updateBtn = document.querySelector("#updateBtn");

if(updateBtn != null){
    updateBtn.addEventListener("click", () => {
        location.href = location.pathname.replace('board', 'editBoard') + "/update" + location.search;
    });
    
}
