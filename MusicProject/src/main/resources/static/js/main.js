if(document.querySelector(".swiper-container") != null) {

    /* banner swipe */
    var swiper = new Swiper(".swiper-container", {
        slidesPerView: 1,
        direction: 'horizontal',
        autoplay: {
            delay: 3000,
        },
        loop: true,
        spaceBetween: 30,
        centeredSlides: true,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        pagination: {
            el: ".swiper-pagination",
            clickable: true,
        },
    });
}

const popupClose = document.querySelector("#popupClose");
const popupLayer = document.querySelector("#popupLayer");
const loginForm = document.querySelector("#loginForm");

if(loginForm != null) {
    popupClose.addEventListener("click", () => {
        loginForm.classList.add("popup-hidden");
    });
}

const loginIcon = document.querySelector("#loginIcon")
if(loginIcon != null) {
    loginIcon.addEventListener("click", ()=>{
        loginForm.classList.remove("popup-hidden");
    });
}

/* 로그인 */
const getCookie = key => {
    const cookie = document.cookie;

    const cookieList = cookie.split(";").map( temp => temp.split("="));

    const obj = {};

    for(let i = 0; i < cookieList.length; i++){
        const k = cookieList[i][0];
        const v = cookieList[i][1];
        obj[k]=v;
    }

    return obj[key];
}

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

if(loginEmail != null){
    const saveId = getCookie("saveId");

    if(saveId != undefined) {
        loginEmail.value=saveId;
        document.querySelector("input[name='saveId']").checked=true;
    }
}

/* 이메일, 비밀번호 미작성 시 로그인 막기 */
const loginPw = document.querySelector("#loginForm input[name='memberPw']");

// 로그인 상태 아닐때
if (loginForm != null) {

    // 제출 이벤트 발생 시
    loginForm.addEventListener("submit", e => {

        // 이메일 미작성
        if(loginEmail.value.trim().length === 0) {
            alert("이메일을 작성해 주세요!");
            e.preventDefault(); 
            loginEmail.focus(); 
            return;
        }

        // 비밀번호 미작성
        if (loginPw.value.trim().length === 0) {
            alert("비밀번호를 작성 해주세요!");
            e.preventDefault(); 
            loginPw.focus(); 
            return;
        }

    });
}


//  빠른 로그인
// const quickLoginBtn = document.querySelector(".quick-login");

// // quickLoginBtns 요소를 하나씩 꺼내서 이벤트 리스너 추가
// quickLoginBtn.addEventListener("click", () => {
//     const email = quickLoginBtn.innerText; // 버튼에 작성된 이메일 얻어오기
//     location.href = "/member/quickLogin?memberEmail=" + email;

// });
/* 인기 게시글 / 미니 게시글 */
const hotBoard = document.querySelector(".hotBoard");
const hotBoradTable = document.querySelector("#hotBoardTable");
const hotBoardList = document.querySelector("#hotBoardList");

let mainCode;
const miniBoardLink = document.querySelectorAll(".board-link");
const boardLinks = document.querySelector("#board-links");
const postList = document.querySelector("#post-list");
const moreView = document.querySelector("#moreView")

document.addEventListener("DOMContentLoaded", () => {

    fetch("board/hotBoard")
    .then(response => response.json())
    .then(list => {
        console.log(list);
        hotBoardList.innerText = "";

        for(let board of list){
            console.log(board.boardCode);
            console.log(board.boardNo);
            const tr = document.createElement("tr");
            const arr = ['categoryName', 'boardTitle', 'memberNickname', 'likeCount'];

            for(let key of arr){
                const td = document.createElement("td");

                if(key === 'boardTitle'){
                    const a = document.createElement("a");
                    a.innerText = board[key];


                    a.href = "/board/" + board.boardCode + "/" + board.boardNo;
                    td.append(a);
                }else{
                    td.innerText = board[key];
                }
                tr.append(td);
            }
            hotBoardList.append(tr);
        }
    })

    miniBoardLink.forEach(miniBoardLink => {
        miniBoardLink.addEventListener("click", function() {
            const boardCode = this.getAttribute("data-board-code");
            moreView.dataset.boardCode = boardCode;
            postList(boardCode);
        });
    });

    moreView.addEventListener("click", function() {
        const boardCode = moreView.dataset.boardCode || "1";
        location.href = "/board/" + boardCode;
    });

    postList("1");

    function postList(boardCode) {
        fetch("board/selectMiniList/" + boardCode)
        .then(resp => resp.json())
        .then(list => {
            boardLinks.innerText = "";

            for(let mini of list) {
                const tr = document.createElement("tr");
                const arr = ['categoryName', 'boardTitle'];

                for(let key of arr) {
                    const td = document.createElement("td");

                    if(key === 'boardTitle') {
                        const a = document.createElement("a");
                        a.innerText = mini[key];

                        a.href = "/board/" + mini.boardCode + "/" + mini.boardNo;
                        td.append(a);
                    } else {
                        td.innerText = mini[key];
                    }
                    tr.append(td); 
                }
                boardLinks.append(tr);
                
            }
        });
    }
});
