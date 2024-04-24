/* 다음 주소 API 활용 */
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {

            var addr = ''; 

            if (data.userSelectedType === 'R') { 
                addr = data.roadAddress;
            } else { 
                addr = data.jibunAddress;
            }

            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

/* 주소 버튼 클릭시 주소 검색 API 나옴 */
document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);

/* 유효성 검사 확인 */
const checkSignupObj = {
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwCheck" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "memberTel" : false,
    "authKey" : false, /* 인증  */
}


/* 이메일 인증 */
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn"); // 인증번호 받기 버튼
const authKey = document.querySelector("#authKey"); // input 태그
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn"); // 확인 버튼
const authKeyMessage = document.querySelector("#authKeyMessage"); // 메시지 출력 span

let timer;

const setMin = 4;
const setSec = 59;
const setTime = "05:00";

// 실제 시간 저장하는 변수
let min = setMin;
let sec = setSec;

sendAuthKeyBtn.addEventListener("click", () => {

    checkSignupObj.authKey = false;
    authKeyMessage.innerText = "";

    if(checkSignupObj.memberEmail) {
        alert("중복된 이메일 입니다.");
        return;
    }

    min = setMin;
    sec = setSec;

    clearInterval(timer);

    checkSignupObj.authKey = false;

    fetch("/email/sendMail", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value
    })

    .then(resp => resp.text())
    .then(result => {

        /* 인증 번호 발송 여부 */
        if(result == 1) {}
        else {}
    })

    authKeyMessage.innerText = setSec;
    authKeyMessage.classList.remove('confirm', 'error');

    alert("인증번호가 발송 되었습니다.");

    timer = setInterval( () => {

        if(min == 0 && sec == 0) {
            checkSignupObj.authKey = false;
            clearInterval.apply(setTime);
            authKeyMessage.classList.add('error');
            authKeyMessage.classList.remove('donfirm');
            return;
        }

        if(sec == 0) {
            sec=60;
            min--;
        }

        sec--;
    }, 1000);
});





/* 이메일 유효성 검사 */
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

memberEmail.addEventListener("input", e => {
    checkSignupObj.authKey = false;
    document.querySelector("#authKeyMessage").innerText = ""; // 실패 시 이전 내용 삭제

    const inputMemberEmail = e.target.value;

    /* 이메일 입력 X */
    if(inputMemberEmail.trim().length == 0) {
        emailMessage.innerText = "인증번호를 받을 수 있는 이메일을 입력해 주세요.";
        emailMessage.classList.remove('confirm', 'error');
        checkSignupObj.memberEmail=false;
        memberEmail.value="";
        return;
    }

    /* 입력 O */
    const regExp =  /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    /* 정규식 검사 */
    if( !regExp.test(inputMemberEmail) ){
        emailMessage.innerText = "이메일 형식이 옳바르지 않습니다.";
        emailMessage.classList.add('error');
        emailMessage.classList.remove('confirm');
        checkSignupObj.memberEmail = false;
        return;
    }

    /* 중복 여부 검사 */
    fetch("/member/checkEmail", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : inputMemberEmail
    })
    .then(resp => resp.text())
    .then(result => {
        
        if(result == 1) {
            emailMessage.innerText="이미 사용중인 이메일 입니다.";
            emailMessage.classList.add('error');
            emailMessage.classList.remove('congfirm');
            checkSignupObj.memberEmail = false;
            return;
        }
        
        emailMessage.innerText = "사용 가능한 이메일 입니다.";
        emailMessage.classList.add('confirm');
        emailMessage.classList.remove('error');
        checkSignupObj.memberEmail = true;
    })
    .catch( e => {
        console.log(e);
    })
});

/* 비밀번호 */
const memberPw = document.querySelector("#memberPw");
const memberPwCheck = document.querySelector("#memberPwCheck");
const pwMessage = document.querySelector("#pwMessage");

const checkPw = () => {

    if(memberPw.value == memberPwCheck.value){
        pwMessage.innerText = "비밀번호가 일치합니다.";
        pwMessage.classList.add('confirm');
        pwMessage.classList.remove('error');
        checkSignupObj.memberPwCheck = true;
        return;
    }

    pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
    pwMessage.classList.add('error');
    pwMessage.classList.remove('confrim');
    checkSignupObj.memberPwCheck = false;
}

memberPw.addEventListener("input", e => {

    const inputMemberPw = e.target.value;

    // 입력 여부
    if(inputMemberPw.trim().length === 0){
        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        pwMessage.classList.remove('confirm', 'error');
        checkSignupObj.memberPw = false;
        memberPw.value="";
        return;
    }

    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    /* 정규식 검사 */
    if( !regExp.test(inputMemberPw) ){
        pwMessage.innerText = "비밀번호 형식이 옳바르지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove('confirm');
        checkSignupObj.memberPw = false;
        return;
    }

    pwMessage.innerText = "사용가능한 비밀번호 입니다.";
    pwMessage.classList.add('confirm');
    pwMessage.classList.remove('error');
    checkSignupObj.memberPw = true;

    if(memberPwCheck.value.length > 0){
        checkPw();
    }
});

memberPwCheck.addEventListener("input", e => {

    const inputMemberPwCheck = e.target.value;

    if(inputMemberPwCheck.trim().length === 0){
        memberPwCheck.value = "";
        return;
    }

    if(checkSignupObj.memberPw){
        checkPw();
        return;
    }

    checkSignupObj.memberPwCheck = false;
});

/* 닉네임 */
const memberNickname = document.querySelector("#memberNickname");
const nickMessage = document.querySelector("#nickMessage");

memberNickname.addEventListener("input", e => {

    const inputMemberNickname = e.target.value;

    /* 입력 X */
    if(inputMemberNickname.trim().length === 0){
        inputMemberNickname.innerText = "한글,영어,숫자로만 2~10글자";
        nickMessage.classList.remove('confirm', 'error');
        checkSignupObj.memberNickname = false;
        memberNickname.value = "";
        return;
    }

    const regExp = /^[a-zA-Z0-9가-힣]{2,10}$/;

    if( !regExp.test(inputMemberNickname) ){
        nickMessage.innerText = "닉네임 형식이 옳바르지 않습니다.";
        nickMessage.classList.add('error');
        nickMessage.classList.remove('confirm');
        checkSignupObj.memberNickname = false;
        return;
    }

    fetch("/member/checkNickname", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : inputMemberNickname
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 1) {
            nickMessage.innerText = "이미 사용중인 닉네임 입니다.";
            nickMessage.classList.add('error');
            nickMessage.classList.remove('confirm');
            checkSignupObj.memberNickname = false;
            return;
        }

        nickMessage.innerText = "사용가능한 닉네임 입니다.";
        nickMessage.classList.add('confirm');
        nickMessage.classList.remove('error');
        checkSignupObj.memberNickname = true;
    })
    .catch(e=> {
        console.log(e);
    })
});

/* 전화번호 */
const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

memberPwCheck.addEventListener("input", e => {
    
    const inputMemeberTel = e.target.value;

    if(inputMemeberTel.trim().length == 0){
        telMessage.innerText = "전화번호를 입력해주세요.(- 제외)";
        telMessage.classList.remove('confirm', 'error');
        checkSignupObj.memberTel = false;
        memberNickname.value= "";
        return;
    }

    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

    if( !regExp.test(inputMemeberTel) ){
        telMessage.innerText = "올바르지 않는 전화번호 형식입니다.";
        telMessage.classList.add('error');
        telMessage.classList.remove('confrim');
        checkSignupObj.memberTel = false;
        return;
    }

    telMessage.innerText = "사용 가능한 전화번호 입니다.";
    telMessage.classList.add('confirm');
    telMessage.classList.remove('error');
    checkSignupObj.memberTel = true;
});

