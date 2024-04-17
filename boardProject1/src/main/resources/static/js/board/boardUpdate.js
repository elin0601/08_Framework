/* 선택된 이미지 미리보기 */
const inputImageList = document.getElementsByClassName("inputImage"); // input 태그 5개
const deleteImageList = document.getElementsByClassName("delete-image"); // x 버튼 5개

// x 버튼이 눌러져 삭제된 이미지의 순서를 저장
// * Set : 중복 저장 X, 순서 유지 X
const deleteOrder = new Set();



// ------------------------------------------------------

// 제출 시 유효성 검사
const boardUpdateFrm = document.querySelector("#boardUpdateFrm");

boardUpdateFrm.addEventListener("submit", e => {

    const boardTitle = document.querySelector("[name='boardTitle']");
    const boardContent = document.querySelector("[name='boardContent']");

    if(boardTitle.value.trim().length == 0) {
        alert("제목을 작성해 주세요.");
        boardTitle.focus();
        e.preventDefault();
        return;
    }
    
    if(boardContent.value.trim().length == 0) {
        alert("내용을 작성해 주세요.");
        boardContent.focus();
        e.preventDefault();
        return;
    }


    // input 태그에 삭제할 이미지 순서(Set)를 배열로 만든 후 대입
    // -> value(문자열) 저장 시 배열은 toString() 이 호출되서 양쪽 []가 사라짐
    document.querySelector("[name='deleteOrder']").value = Array.from(deleteOrder);
    document.querySelector("[name='queryString']").value = location.search;


});