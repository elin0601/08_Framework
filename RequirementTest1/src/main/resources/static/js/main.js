const selectAllBtn = document.querySelector("#selectAllBtn");
const content = document.querySelector("#content");

const createTd = (text) => {
    const td = document.createElement("td");
    td.innerText = text;
    return td;
}


selectAllBtn.addEventListener("click", e => {

    fetch("/select")
    .then(resp => resp.json())
    .then(selectList => {

        content.innerHTML = "";


        for (let member of selectList) {
            const tr = document.createElement("tr");

            const arr = ['studentNo', 'studentName', 'studentMajor', 'studentGender'];

            for (let key of arr) {
                const td = document.createElement("td");

                td.innerText = member[key];
                tr.append(td);
            }

            content.append(tr);
        }

    })


});
