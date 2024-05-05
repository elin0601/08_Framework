const selectCommentList = () => {

    fetch("/comment?boardNo=" + boardNo)
    .then(resp => resp.json())
    .then(commentList => {
        const ul = document.querySelector("#commentList");
        ul.innerHTML = "";

        /* 조회된 댓글 출력 */
        for(let comment of commentList) {

            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");

            // 대댓글인 경우 child-comment 클래스 추가
            if(comment.parentCommentNo != 0) commentRow.classList.add("child-comment");
            
            // 삭제된 댓글이지만 대댓글은 존재하는 경우
            if(comment.commentDelFl == 'Y') commentRow.innerText = "삭제된 댓글 입니다.";

            // 댓글 존재
            else{

                // 회원 정보
                const commentWriter = document.createElement("p");
                commentWriter.classList.add("comment-writer");

                const profileImg = document.createElement("img");

                if(comment.profileImg == null) profileImg.src = userDefaultImage;
                else profileImg.src = comment.profileImg;

                const nickname = document.createElement("span");
                nickname.innerText = comment.memberNickname;

                const commentDate = document.createElement("span");
                commentDate.classList.add("comment-date");
                commentDate.innerText = comment.commentWriteDate;

                commentWriter.append(profileImg, nickname, commentDate);
                commentRow.append(commentWriter);

                // 댓글 내용
                const content = document.createElement("p");
                content.classList.add("comment-content");
                content.innerText = comment.commentContent;
                commentRow.append(content);

                // ---- 버튼 -----

                // 버튼 영역 생성
                const commentBtnArea = document.createElement("div");
                commentBtnArea.classList.add("comment-btn-area");

                // 답글 버튼
                const childCommentBtn = document.createElement("button");
                childCommentBtn.innerText = "댓글";
                childCommentBtn.setAttribute("onclick", `showInsertComment(${comment.commentNo}, this)`);
                commentBtnArea.append(childCommentBtn);

                // 로그인한 회원 == 작성자
                if(loginMemberNo != null && loginMemberNo == comment.memberNo){

                    // 수정 버튼
                    const updateBtn = document.createElement("button");
                    updateBtn.innerText = "수정";
                    updateBtn.setAttribute("onclick", `showUpdateComment(${comment.commentNo}, this)`);
               
                    // 삭제 버튼
                    const deleteBtn = document.createElement("button");
                    deleteBtn.innerText = "삭제";
                    deleteBtn.setAttribute("onclick", `deleteComment(${comment.commentNo})`);

                    commentBtnArea.append(updateBtn, deleteBtn);
                }

                commentRow.append(commentBtnArea);
            }

            ul.append(commentRow);
        }

    });
}


/*  댓글 등록 */
const addComment = document.querySelector("#addComment"); // 버튼
const commentContent = document.querySelector("#commentContent");

addComment.addEventListener("click", () => {

    // 로그인 X
    if(loginMemberNo == null) {
        alert("로그인 후 이용 가능한 서비스 입니다.");
        return;
    }

    // 로그인 O
    if(commentContent.value.trim().length == 0) {
        alert("내용을 작성해 주세요.");
        return;
    }

    const dataObj = {
        "commentContent" : commentContent.value,
        "boardNo" : boardNo,
        "memberNo" : loginMemberNo
    };

    fetch("/comment", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(dataObj)
    })
    .then(resp => resp.text())
    .then(result => {
        
        if(result > 0) {
            alert("댓글이 등록 되었습니다.");
            commentContent.value = "";
            selectCommentList();

        } else {
            alert("댓글 정상 등록 실패");
        }
    })  
    .catch(e => {
        console.log(e);
    })
});

// 대댓글 작성 화면
const showInsertComment = (parentCommentNo, btn) => {

    const temp = document.getElementsByClassName("commentInsertContent");

    if(temp.length > 0){

        if(confirm("작성중인 댓글이 있습니다. 현재 댓글을 작성 하시겠습니까?")){
            temp[0].nextElementSibling.remove();
            temp[0].remove();

        } else return;
        
    }

    // 대댓글 작성 화면 상세
    const textarea = document.createElement("textarea");
    textarea.classList.add("commentInsertContent");
    
    btn.parentElement.after(textarea);

    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");

    const insertBtn = document.createElement("button");
    insertBtn.innerText="등록";
    insertBtn.setAttribute("onclick", "insertChildComment(" + parentCommentNo + ", this)");

    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "insertCancel(this)");

    commentBtnArea.append(insertBtn, cancelBtn);

    textarea.after(commentBtnArea);
};

// 댓글 등록중 취소 할 때
const insertCancel = (cancelBtn) => {
    cancelBtn.parentElement.previousElementSibling.remove();
    cancelBtn.parentElement.remove();
}

// 대댓글 등록
const insertChildComment = (parentCommentNo, btn) => {

    const textarea = btn.parentElement.previousElementSibling;

    if(textarea.value.trim().length == 0) {
        alert("내용을 작성해 주세요.");
        textarea.focus();
        return;
    }

    const insertData =  {
        "commentContent" : textarea.value,
        "boardNo" : boardNo,
        "memberNo" : loginMemberNo,
        "parentCommentNo" : parentCommentNo
    };

    fetch("/comment", {
        method : "POST", 
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(insertData)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("댓글이 등록 되었습니다.");
            selectCommentList();
        }
        
        else alert("등록 실패");
    })
    .catch(e => {
        console.log(e);
    })
};


/* 댓글 삭제 */
const deleteComment = (commentNo) =>{

    if(!confirm("정말 삭제 하시겠습니까?")) return;
    
        fetch("/comment", {
            method : "DELETE",
            headers : {"Content-Type" : "application/json"},
            body : commentNo
        })
        .then(resp => resp.text())
        .then(result => {

            if(result > 0)  {
                alert("삭제 되었습니다.");
                selectCommentList();
            }
            else alert("삭제 실패");
        })
        .catch(e => {
            console.log(e);
        })

};

/* 댓글 수정 */

// 백업용 변수
let backupComment;

const showUpdateComment = (commentNo, btn) => {

    const temp = document.querySelector(".update-textarea");

    if(temp != null) {
        if(confirm("이전에 수정하던 댓글이 있습니다." + "\n"  + "취소하고 현재 댓글을 수정 하시겠습니까?")){

            const commentRow = temp.parentElement; // 기존 댓글
            commentRow.after(backupComment); // 기존 댓글을 백업에 추가
            commentRow.remove(); // 기존 삭제 -> 백업 변수에 담겨 있음x 

        } else return; // 취소
    }

    // 수정이 클릭된 행 (가장 가까운 li 태그 선택)
    const commentRow = btn.closest("li");
    backupComment = commentRow.cloneNode(true);

    // 기존 댓글에 작성되어 있던 내용 comment 변수에 저장
    let content = commentRow.children[1].innerText;
    commentRow.innerText = "";

    const textarea = document.createElement("textarea");
    textarea.classList.add("update-textarea");
    textarea.value = content;

    // textarea 추가
    commentRow.append(textarea);

    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");

    const updateBtn = document.createElement("button");
    updateBtn.innerText = "수정";
    updateBtn.setAttribute("onclick", `updateComment(${commentNo}, this)`);

    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "updateCancel(this)");

    commentBtnArea.append(updateBtn, cancelBtn);
    commentRow.append(commentBtnArea);
}

//수정 취소
const updateCancel = (btn) => {

    if(confirm("취소 하시겠습니까?")) {
      const commentRow = btn.closest("li"); // 기존 댓글 행
      commentRow.after(backupComment); // 기존 댓글 다음에 백업 추가
      commentRow.remove(); // 기존 삭제 -> 백업이 기존 행 위치로 이동
    }
}

// 수정
const updateComment = (commentNo, btn) => {

    const textarea = btn.parentElement.previousElementSibling;

    if(textarea.value.trim().length == 0) {
        alert("작성된 댓글이 없습니다. 댓글을 작성해 주세요.");
        textarea.focus();
        return;
    }

    const updateObj = {
        "commentNo" : commentNo,
        "commentContent" : textarea.value
    };

    fetch("/comment", {
        method : "PUT", 
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(updateObj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("댓글이 수정 되었습니다.");
            selectCommentList();
        }
        else alert("등록 실패");
    })
    .catch(e => {
        console.log(e);
    })
}
