const selectBook = document.querySelector("#selectBook"); // 버튼
const bookList = document.querySelector("#bookList"); // tbody


const createTd = (text) => {
    const td = document.createElement("td");
    td.innerText = text;
    return td;
}

selectBook.addEventListener("click", () => {

    
    fetch("/book/selectList")
    .then(resp => resp.json())
    .then(list => {
        const keyList = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate'];
        bookList.innerHTML="";

        list.forEach((book) => {

            const tr = document.createElement("tr");

            keyList.forEach( key => tr.append(createTd(book[key])));
            bookList.append(tr);
        })
    })

});

// --------------------- 삽입 실패 시 --------------------------
const bookTitle = document.querySelector("#bookTitle");
const bookWriter = document.querySelector("#bookWriter");
const bookPrice = document.querySelector("#bookPrice");
const insertBook = document.querySelector("#insertBook"); // 등록하기 버튼

insertBook.addEventListener("click", e => {

    if(bookTitle.value.trim() == 0) {
        alert("책 제목을 입력해 주세요.");
        e.preventDefault();
        focus();
        return;
    }

    if(bookWriter.value.trim() == 0) {
        alert("글쓴이를 입력해 주세요.");
        e.preventDefault();
        focus();
        return;
    }

    if(bookPrice.value ==  null) {
        alert("책 가격을 입력해 주세요.");
        e.preventDefault();
        focus();
        return;
    }


});




 // -------------------- 책 검색, 수정, 삭제 --------------------

// const bookTitle = document.querySelector("#bookTitle");
const searchBook = document.querySelector("#searchBook");

searchBook.addEventListener("click", e => {

    fetch("/book/searchBook", {
        method : "POST",
        headers :  {"Content-Type" : "application/json"},
        body : bookTitle
    })
    .then(resp => resp.text())
    .then ()
});