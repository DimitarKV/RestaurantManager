import {html, render} from 'https://unpkg.com/lit-html?module';
import {styleMap} from 'https://unpkg.com/lit-html/directives/style-map?module';
import {address} from "/js/fetchApi.js";

let card = (orderId, imageUrl, name, description, handler, cooking = true, handler2) => html`
    <div class="col-lg-3 mt-3">
        <div class="card rounded-back bg-dark pt-4">
            <img src=${imageUrl}/>
            <div class="card-body text-center text-light">
                <h5 class="item-name">${name}</h5>

                <a class="btn btn-primary mb-3" data-bs-toggle="collapse" href=${'#collapse' + orderId}
                   role="button" aria-expanded="false">Бележка</a>
                
                <button style=${styleMap({display: cooking ? 'none' : 'inline'})} @click=${handler} id="acceptButton" class="btn btn-primary mb-3">Поеми</button>
                <button style=${styleMap({display: cooking ? 'inline' : 'none'})} @click=${handler} id="readyButton" class="btn btn-primary mb-3">Готово</button>
                <button style=${styleMap({display: cooking ? 'inline' : 'none'})} @click=${handler2} id="cancelButton" class="btn btn-primary mb-3">Откажи</button>
                <div class="collapse" id=${'collapse' + orderId}>
                    <div>${description}</div>
                </div>

                <div id="orderId" hidden>${orderId}</div>

            </div>
        </div>
    </div>
`;

let waitingContainer = document.getElementById("waitingContainer");
let currentCookContainer = document.getElementById("currentCookContainer");

async function doFetch() {
    let http = await fetch(address + "/personnel/cook/orders-rest");
    let json = await http.json();
    let templates = [];
    for (const order of json) {
        templates.push(card(order.id, order.imageUrl, order.name, order.description, acceptHandler, false));
    }
    render(templates, waitingContainer);

    http = await fetch(address + "/personnel/cook/current/orders-rest");
    json = await http.json();
    templates = [];
    for (const order of json) {
        templates.push(card(order.id, order.imageUrl, order.name, order.description, orderReadyHandler, true, cancelOrderHandler));
    }
    render(templates, currentCookContainer);
}

let lastFetchDone = true;
function display() {
    if(lastFetchDone){
        lastFetchDone = false;
        doFetch().then(() => lastFetchDone = true);
    }
}

function acceptHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch(address + "/personnel/cook/order/" + orderId + "/accept-rest");
}

function orderReadyHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch(address + "/personnel/cook/order/" + orderId + "/ready-rest");
}

function  cancelOrderHandler(e){
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch(address + "/personnel/cook/order/" + orderId + "/cancel-rest");
}

display();

setInterval(display, 200);