const popupClose = document.querySelector(".popupClose");
const popupLayer = document.querySelector(".popupLayer");

popupClose.addEventListener("click", () => {
    popupLayer.classList.add("popup-hidden")
});