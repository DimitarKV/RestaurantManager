import {html, render} from 'https://unpkg.com/lit-html?module';

let myModal = new bootstrap.Modal(document.getElementById("myModal"));
let itemId = document.getElementById("itemId");
let itemName = document.getElementById("productName");
let notesField = document.getElementById("notes");
let placeOrderButton = document.getElementById("placeOrderButton");

document.addEventListener("submit", orderHandler);

async function orderHandler(e) {
    if (e.target.classList.contains("order-form")) {
        e.preventDefault();
        let formData = new FormData(e.target);
        itemId.value = formData.get("itemId").trim();
        itemName.textContent = e.target.parentNode.querySelector(".item-name").textContent;
        notesField.value = "";
        myModal.show();
    }
}
