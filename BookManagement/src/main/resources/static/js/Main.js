const selectBook = document.querySelector("#selectBook"); // 버튼
const bookList = document.querySelector("#bookList"); // tbody


const createTd = (text) => {
    const td = document.createElement("td");
    td.innerText = text;
    return td;
}

if(selectBook != null ) {

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

}


// --------------------- 삽입 실패 시 --------------------------
const bookTitle = document.querySelector("#bookTitle");
const bookWriter = document.querySelector("#bookWriter");
const bookPrice = document.querySelector("#bookPrice");
const insert = document.querySelector("#insert"); // 등록하기 form

if(insert != null) {

    insert.addEventListener("submit", e => {
    
        if(bookTitle.value.trim().length === 0) {
            alert("책 제목을 입력해 주세요.");
            e.preventDefault();
            bookTitle.focus();
            return;
        }
    
        if(bookWriter.value.trim().length === 0) {
            alert("글쓴이를 입력해 주세요.");
            e.preventDefault();
            bookWriter.focus();
            return;
        }
    
        if(bookPrice.value == 0 || bookPrice == '') { //????????????
            alert("책 가격을 입력해 주세요.");
            e.preventDefault();
            bookPrice.focus();
            return;
        }
    });
}





 // -------------------- 책 검색, 수정, 삭제 --------------------
 
//const bookTitle = document.querySelector("#bookTitle"); 요소 겹침

const searchBook = document.querySelector("#searchBook");
const bookSelect = document.querySelector("#bookSelect"); // tbody

if(searchBook != null) {

    searchBook.addEventListener("click", () => {
    
        fetch("/book/updateBook", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : bookTitle.value
        })

        .then(resp => resp.json())
        .then(bookList => {

            console.log(bookList);

            bookSelect.innerHTML="";

            for(let book of bookList) {
                const tr = document.createElement("tr");

                const arr = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate'];
                
                for(let key of arr) {

                    const td = document.createElement("td");

                    td.innerText = book[key];

                    const updateBtn = document.createElement("button");
                    updateBtn.innerText = "수정";
                    updateBtn.id = "updateBtn";

                    const deleteBtn = document.createElement("button");
                    deleteBtn.innerText = "삭제"
                    deleteBtn.id = "deleteBtn"; // 버튼의 id 설정
                    
                    td.append(updateBtn);
                    td.append(deleteBtn);

                    tr.append(td); 
                }
                bookSelect.append(tr);
            }
        })
    });   
}

/*  const updateBtn = document.createElement("button");
 updateBtn.innerText = "수정";
 
 const deleteBtn = document.createElement("button");
 deleteBtn.innerText = "삭제" */
 
/*  
 td.appendChild(updateBtn);
 td.appendChild(deleteBtn); */