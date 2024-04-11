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
const bookNo = document.querySelector("#bookNo");
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
                    tr.append(td); 
                }

                const td1 = document.createElement("td");
                const updateBtn = document.createElement("button");
                updateBtn.innerText = "수정";
                updateBtn.id = "updateBtn";

                updateBtn.addEventListener("click", ()=>{

                    let price = prompt("수정할 책 가격을 입력해 주세요.");

                    const obj = {"bookNo" : book.bookNo, "bookPrice" : price};


                    if(price !=null && price.trim() != ''){
                        
                        fetch("/book/update",{
                            method : "POST",
                            headers : {"Content-Type" : "application/json"},
                            body : JSON.stringify(obj)
                        })
                    
                        .then(resp => resp.text())
                        .then(result => {

                            if(result > 0) alert("수정되었습니다.");
                            else alert("다시 입력해 주세요.");
                        })
                    }

                });


                const td2 = document.createElement("td");
                const deleteBtn = document.createElement("button");
                deleteBtn.innerText = "삭제"
                deleteBtn.id = "deleteBtn"; // 버튼의 id 설정

                // 삭제
                deleteBtn.addEventListener("click", ()=>{

                    if(!confirm("정말 삭제 하시겠습니까?")) return;

                    fetch("/book/delete", {
                        method : "POST",
                        headers : {"Content-Type" : "application/json"},
                        body : book.bookNo // PK
                    })
                    .then(resp => resp.text())
                    .then(result => {
                        if(result > 0) alert("삭제 되었습니다.");
                        else {"삭제 실패"};
                    })

                });

                td1.append(updateBtn);
                td2.append(deleteBtn);

                tr.append(td1, td2);

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