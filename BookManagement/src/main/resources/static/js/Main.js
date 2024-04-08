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


 // -------------------- 책 검색, 수정, 삭제 --------------------

 const bookTitle = document.querySelector("#bookTitle");
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