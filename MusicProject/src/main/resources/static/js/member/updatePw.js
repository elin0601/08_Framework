const updatePwForm = document.querySelector("#updatePwForm");
const newPw = document.querySelector("#newPw");
const newPwCheck = document.querySelector("#newPwCheck");

const checkObj = {
    "newPw" : false,
    "newPwCheck" : false
} 

/* 입력된 비밀번호 정규식 / 중복 검사 */
const pwMessage = document.querySelector("#pwMessage");

const checkPw = () => {

    if(newPw.value == newPwCheck.value){
        pwMessage.innerText = "비밀번호가 일치합니다.";
        pwMessage.classList.add('confirm');
        pwMessage.classList.remove('error');
        checkObj.newPwCheck = true;
        return;
    }

    pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
    pwMessage.classList.add('error');
    pwMessage.classList.remove('confrim');
    checkObj.newPwCheck = false;
}

const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;


newPw.addEventListener("input", e => {

    const inputNewPw = e.target.value;

    // 입력 여부
    if(inputNewPw.trim().length === 0){
        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        pwMessage.classList.remove('confirm', 'error');
        checkObj.newPw = false;
        newPw.value="";
        return;
    }

    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    /* 정규식 검사 */
    if( !regExp.test(inputNewPw) ){
        pwMessage.innerText = "비밀번호 형식이 옳바르지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove('confirm');
        checkObj.newPw = false;
        return;
    }

    pwMessage.innerText = "사용가능한 비밀번호 입니다.";
    pwMessage.classList.add('confirm');
    pwMessage.classList.remove('error');
    checkObj.newPw = true;

    if(newPwCheck.value.length > 0){
        checkPw();
    }
});

newPwCheck.addEventListener("input", e => {

    const inputNewPwCheck = e.target.value;

    if(inputNewPwCheck.trim().length === 0){
        newPwCheck.value = "";
        return;
    }

    if(checkObj.newPw){
        checkPw();
        return;
    }

    checkObj.newPwCheck = false;
});

updatePwForm.addEventListener("submit", e => {

    for(let obj in checkObj) {
        if(!checkObj[obj]){
            
            let str;

            switch(obj){
                case "newPw" : str = "새 비밀번호를 작성해 주세요."; break;
                case "newPwCheck" : str = "비밀번호 확인을 위해 작성해 주세요."; break;
            }

            alert(str);
            document.getElementById(obj).focus();
            e.preventDefault();
            return;
        }
    }
});