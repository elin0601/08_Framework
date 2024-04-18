/* REST(REpresentational State Transfer) API 

- 자원(데이터, 파일)을 이름(주소)으로 
  구분 (representitional) 하여
  자원의 상태(State)를 주고 받는 것(Transfer)

  -> 자원의 이름(주소)를 명시하고 
     HTTP Method(GET, POST, PUT, DELETE) 를 이용해 
     지정된 자원에 대한 CRUD 진행

    자원의 이름(주소)는 하나만 지정 (ex. /comment)

    삽입 == POST    (Create)
    조회 == GET     (Read)
    수정 == PUT     (Update)
    삭제 == DELETE  (Delete)
*/

/* **** 댓글 목록 조회(ajax) **** */
const selectCommentList = () => {

    // [GET]
    // fetch(주소?쿼리 스트링) 

    // [POST, PUT, DELETE, ... ]
    // fetch(주소, {method : "", headers : {}, body : ""})

    // resp.json()
    // 응답 받은 JSON 데이터를 -> JS 객체로 변환

    fetch("/comment?boardNo=" + boardNo) // GET 방식 요청
    .then(resp => resp.json())
    .then(commentList => {
        console.log(commentList);


        // 화면에 존재하는 기존 댓글 목록 삭제 후
        // 조회된 commentList를 이용해서 새로운 댓글 목록 출력
        
        // ul 태그(댓글 목록 감싸는 요소)
        const ul = document.querySelector("#commentList");
        ul.innerHTML = ""; // 기존 댓글 목록 삭제

        
        /* ******* 조회된 commentList를 이용해 댓글 출력 ******* */
        for(let comment of commentList){

            // 행(li) 생성 + 클래스 추가
            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");

            // 대댓글(자식 댓글)인 경우 "child-comment" 클래스 추가
            if(comment.parentCommentNo != 0) commentRow.classList.add("child-comment");
            
            // 만약 삭제된 댓글이지만 자식 댓글이 존재하는 경우
            if(comment.commentDelFl == 'Y') commentRow.innerText = "삭제된 댓글 입니다.";

            else { // 삭제되지 않은댓긓

                // 프로필 이미지, 닉네임 날짜 감싸는 요소
                const commentWriter = document.createElement("p");
                commentWriter.classList.add("comment-writer");
                
                // 프로필 이미지
                const profileImg = document.createElement("img");

                if(comment.profileImg == null) 
                    profileImg.src = userDefaultImg;  // 기본 이미지
                else 
                    profileImg.src = comment.profileImg; // 회원 이미지


                // 닉네임
                const nickName = document.createElement("span");
                nickName.innerText = comment.memberNickname;

                // 날짜 (작성일)
                const commentDate = document.createElement("span");
                commentDate.classList.add("comment-date");
                commentDate.innerText = comment.commentWriteDate;

                // 작성자 영역(commentWriter) 에 프로필, 닉네임, 날짜 추가
                commentWriter.append(profileImg, nickName, commentDate);

                // 댓글 행에 작성자 영역 추가
                commentRow.append(commentWriter);

                // -------------------------------------------------------------------------------

                // 댓글 내용
                const content = document.createElement("p");
                content.classList.add("comment-content");
                content.innerText = comment.commentContent;
                commentRow.append(content);

                // -------------------------------------------------------------------------------

                // 버튼 영역
                const commentBtnArea = document.createElement("div");
                commentBtnArea.classList.add("comment-btn-area");

                // 답글 버튼
                const childCommentBtn = document.createElement("button");
                childCommentBtn.innerText = "답글";

                // 답글 버튼이 onclick 이벤트 리스너 추가
                childCommentBtn.setAttribute("onclick", `showInsertComment(${comment.commentNo}, this)`);  

                // 버튼 영역에 답글 추가
                commentBtnArea.append(childCommentBtn);


                // 로그인한 회원 번호가 댓글 작성자 번호와 같을 때
                // 댓글 수정/삭제 버튼 출력
                if(loginMemberNo != null && loginMemberNo == comment.memberNo){

                    // 수정 버튼
                    const updateBtn = document.createElement("button");
                    updateBtn.innerText = "수정";
    
                    // 수정 버튼이 onclick 이벤트 리스너 추가
                    updateBtn.setAttribute("onclick", `showUpdateComment(${comment.commentNo}, this)`);


                    // 삭제 버튼
                    const deleteBtn = document.createElement("button");
                    deleteBtn.innerText = "삭제";
    
                    // 수정 버튼이 onclick 이벤트 리스너 추가
                    deleteBtn.setAttribute("onclick", `deleteComment(${comment.commentNo}`);


                    // 버튼 영역에 수정삭제 버튼 추가
                    commentBtnArea.append(updateBtn, deleteBtn);

                }

                // 행에 버튼 영역 추가
                commentRow.append(commentBtnArea);

            } // else 끝

            // 댓글 목록(ul)에 행(li) 추가
            ul.append(commentRow);

        } // for문 끝

    });

}