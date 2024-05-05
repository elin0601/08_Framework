const idForm = document.querySelector("#idForm"); // 아이디 찾기 폼
const memberNickname = document.querySelector("#memberNickname");
const memberTel = document.querySelector("#memberTel");
const idBtn = document.querySelector("#idBtn") // 찾기 버튼


const idObj = {
    "memberNickname": false,
    "memberTel": false
}

idForm.addEventListener("submit", e => {

    memberNickname.addEventListener("input", e => {
        const inputMemberNickname = e.target.value;

        if (inputMemberNickname.trim().length === 0) {
            memberNickname.value = "";
            idObj.memberNickname = false;
            e.preventDefault();
            return;
        }

    });

    memberTel.addEventListener("input", e => {
        const inputMemberTel = e.target.value;

        if (inputMemberTel.trim().length === 0) {
            memberTel.value = "";
            idObj.memberTel = false;
            e.preventDefault();
            return;
        }
    });

    for (let obj in idObj) {

        if (idObj[obj]) {
            alert("이메일 또는 전화번호를 확인해주세요.");
            document.getElementById(obj).focus();
            e.preventDefault();
            return;
        }
    }
});



const pwForm = document.querySelector("#pwForm");
const memberEmail = document.querySelector("#memberEmail");
const pwBtn = document.querySelector("#pwBtn");

const pwObj = {
    "memberNickname": false,
    "memberTel": false,
    "memberEmail": false,
    "authKey" : false
}

pwForm.addEventListener("submit", e => {

memberNickname.addEventListener("input", e => {
    
    
    const inputMemberNickname = e.target.value;

    if (inputMemberNickname.trim().length === 0) {
        memberNickname.value = "";
        pwObj.memberNickname = false;
        e.preventDefault();
        return;
    }

    pwObj.memberNickname = true;
});

memberTel.addEventListener("input", e => {
    const inputMemberTel = e.target.value;

    if (inputMemberTel.trim().length === 0) {
        memberTel.value = "";
        pwObj.memberTel = false;
        e.preventDefault();
        return;
    }

    pwObj.memberTel = true;
});

memberEmail.addEventListener("input", e => {
    
    const inputMemberEmail = e.target.value;

    pwObj.authKey = false;

    if (inputMemberEmail.trim().length === 0) {
        memberEmail.value = "";
        pwObj.memberEmail = false;
        e.preventDefault();
        return;
    }

    pwObj.memberEmail = true;

});

    for (let obj in pwObj) {

      if (pwObj[obj]) {
            document.getElementById(obj).focus();
            return;
        }
    }
});


const setAuthSentStatus = () => {
    localStorage.setItem('authKey', 'true');
}

const checkAuthSentStatus = () => {
    return localStorage.getItem('authKey') === 'true';
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


sendAuthKeyBtn.addEventListener("click", e => {

    authKey.disabled = false;

    pwObj.authKey = false;
    authKeyMessage.innerText = "";

    if (memberEmail.value.trim() === "") {
        alert("이메일을 입력해 주세요.");
        return;
    }

    const inputs = document.querySelectorAll(".inputs");
    const inputsValue = Array.from(inputs).slice(3).map(input => input.value.trim());

    const emptyInput = inputsValue.findIndex(value => value === "");
    if (emptyInput !== -1) {
        alert("다른 정보도 작성해 주세요.");
        inputs[emptyInput + 3].focus();
        return;
    }

    min = setMin;
    sec = setSec;

    clearInterval(timer);

    pwObj.authKey = false;

    fetch("/email/sendMail", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: memberEmail.value
    })

        .then(resp => resp.text())
        .then(result => {

            /* 인증 번호 발송 여부 */
            if (result == 1) { }
            else { }
        })

    authKeyMessage.innerText = setTime;
    authKeyMessage.classList.remove('confirm', 'error');

    alert("인증번호가 발송 되었습니다.");

    timer = setInterval(() => {

        authKeyMessage.innerText = `${zero(min)} : ${zero(sec)}`

        if (min == 0 && sec == 0) {
            pwObj.authKey = false;
            clearInterval.apply(setTime);
            authKeyMessage.classList.add('error');
            authKeyMessage.classList.remove('confirm');
            return;
        }

        if (sec == 0) {
            sec = 60;
            min--;
        }

        sec--;

    }, 1000);

});

/* 인증하기 버튼 클릭 시 */
checkAuthKeyBtn.addEventListener("click", () => {

    if (min === 0 && sec === 0) {
        alert("인증 번호 입력 제한 시간을 초과 하였습니다.");
        return;
    }

    if (authKey.value.trim().length < 6) {
        alert("인증번호를 다시 입력 해주세요.");
        return;
    }

    const obj = {
        "email": memberEmail.value,
        "authKey": authKey.value
    };

    fetch("/email/checkAuthKey", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(obj)
    })
        .then(resp => resp.text())
        .then(result => {

            if (result == 0) {
                alert("인증번호가 일치하지 않습니다.");
                pwObj.authKey = false;
                return;
            }
            
            checkAuthSentStatus();
          /*   alert("새 인증번호를 입력해 주세요.");
            pwObj.authKey = false;
            return; */
            

            clearInterval(timer);

            authKeyMessage.innerText = "인증 완료 되었습니다.";
            authKeyMessage.classList.add('confirm');
            authKeyMessage.classList.remove('error');
            pwObj.authKey = true;

        })

    setAuthSentStatus();
});

const zero = (number) => {
    if (number < 10) return "0" + number;
    else return number;
}


// pwBtn.addEventListener("click", e => {
//     document.querySelector("#pwForm").addEventListener("submit", e => {
//         const inputs = document.getElementsByTagName("input");

//         const temp = false;

//         for(let i=0; i>inputs.length; i++){
//             if(inputs[i].value.trim() ===""){
//                 temp = true;
//                 break;
//             }
//         }

//         if(temp) {
//             e.preventDefault();
//             alert("회원님의 정보를 입력한 후 클릭해 주세요.");
//         }
//     });
// });