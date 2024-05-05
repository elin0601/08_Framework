const previewList = document.getElementsByClassName("preview");
const inputImageList = document.getElementsByClassName("inputImage");
const deleteImageList = document.getElementsByClassName("delete-image");


const backupInputList = new Array(inputImageList.length);

const changeImageFn = (inputImage, order) => {

  const maxSize = 1024 * 1024 * 10;
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
    alert("10MB 이하의 이미지를 선택해주세요");

    if(backupInputList[order] == undefined || backupInputList[order].value ==''){
      inputImage.value=""; 
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

  });

}

for(let i=0 ; i<inputImageList.length ; i++){

  inputImageList[i].addEventListener("change", e => {
    changeImageFn(e.target, i);
  })

  deleteImageList[i].addEventListener("click", () => {

    previewList[i].src = "";      
    inputImageList[i].value  = ""; 
    backupInputList[i].value = ""; 
  });
}

const boardWrite = document.querySelector("#boardWrite");

boardWrite.addEventListener("submit", e =>{

  const boardTitle = document.querySelector("[name='boardTitle']");
  const boardContent = document.querySelector("[name='boardContent']");
  const selectCategory = document.querySelector("#selectCategory");

  if(selectCategory.value == 0){
    alert("카테고리를 선택해주세요.");
    selectCategory.focus();
    e.preventDefault();
    return;
  }

  if(boardTitle.value.trim().length == 0) {
    alert("제목을 입력해주세요.");
    boardTitle.focus();
    e.preventDefault();
    return;
  }

  if(boardContent.value.trim().length == 0){
    alert("내용을 작성해 주세요");
    boardContent.focus();
    e.preventDefault();
    return;
  }

  if(boardContent.value.trim().length > 1332){
    alert("입력할 수 있는 글자 수를 초과했습니다.");
    boardContent.focus();
    e.preventDefault();
    return;
  }

});





