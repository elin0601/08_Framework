const inputImageList = document.getElementsByClassName("inputImage");
const deleteImageList = document.getElementsByClassName("delete-image");

const deleteOrder = new Set();

const backupInputList = new Array(inputImageList.length);

const changeImageFn = (inputImage, order) => {

  const maxSize = 1024 * 1024 * 10

  const file = inputImage.files[0];

  if(file == undefined){
    console.log("파일 선택 취소됨");

    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp);
    inputImage.remove();
    inputImage = temp;

    inputImage.addEventListener("change", e => {
      changeImageFn(e.target, order);
    });

    return;
  }

  if(file.size > maxSize){
    alert("10MB 이하의 이미지를 선택해주세요.");

    if(backupInputList[order] == undefined || backupInputList[order].value == ''){
      inputImage.value = "";
      return;
    }

    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp);
    inputImage.remove();
    inputImage = temp;
    
    inputImage.addEventListener("change", e => {
      changeImageFn(e.target, order);
    });

    return;
  }

  const reader = new FileReader();

  reader.readAsDataURL(file);

  reader.addEventListener("load", e => {
    const url = e.target.result;

    previewList[order].src = url;

    backupInputList[order] = inputImage.cloneNode(true);

    deleteOrder.delete(order);
  });

}


for(let i=0 ; i<inputImageList.length ; i++){

  inputImageList[i].addEventListener("change", e => {
    changeImageFn(e.target, i);
  })

  deleteImageList[i].addEventListener("click", () => {

    if(previewList[i].getAttribute("src") != null && previewList[i].getAttribute("src") != ""){
      
      if(orderList.includes(i)){

        deleteOrder.add(i);

      }
      
    }
    previewList[i].src = "";      
    inputImageList[i].value  = ""; 
    backupInputList[i] = undefined; 
  });
}


const boardWrite = document.querySelector("#boardWrite");

boardWrite.addEventListener("submit", e => {

  const boardTitle = document.querySelector("[name='boardTitle']");
  const boardContent = document.querySelector("[name='boardContent']");
  const categoryNo = document.querySelector("[name='categoryNo']");

  if(categoryNo.value == 0){
    alert("카테고리를 선택해 주세요.");
    categoryNo.focus();
    e.preventDefault();
    return;
  }


  if(boardTitle.value.trim().length == 0){
    alert("제목을 입력해 주세요.");
    boardTitle.focus();
    e.preventDefault();
    return;
  }

  if(boardContent.value.trim().length == 0){
    alert("내용을 입력해 주세요.");
    boardContent.focus();
    e.preventDefault();
    return;
  }

  document.querySelector("[name='deleteOrder']").value
    = Array.from(deleteOrder);
  document.querySelector("[name='querystring']").value = location.search
});