const popupClose = document.querySelector("#popupClose");
const popupLayer = document.querySelector("#popupLayer");
const loginForm = document.querySelector("#loginForm")

if(loginForm != null) {
    popupClose.addEventListener("click", () => {
        popupLayer.classList.add("popup-hidden");
    });
}

const loginIcon = document.querySelector("#loginIcon")
if(loginIcon != null) {
    loginIcon.addEventListener("click", ()=>{
        popupLayer.classList.remove("popup-hidden");
    });
}
