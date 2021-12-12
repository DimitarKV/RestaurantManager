import {html, render} from 'https://unpkg.com/lit-html?module';
import {styleMap} from 'https://unpkg.com/lit-html/directives/style-map?module';
import {classMap} from 'https://unpkg.com/lit-html/directives/class-map?module';
import {address} from './fetchApi.js';
import {fetchesDisabled} from "./disablePostLogoutFetches.js";

let card = (orderId, imageUrl, name, description, price, status, statusName, cancelable, handler) => html`
    <div class="col-lg-3 mt-3">
        <div class="card rounded-back bg-dark pt-4">
            <img src=${imageUrl}/>
            <div class="card-body text-center text-light">
                <h5 class="mt-3 item-name">${name}</h5>

                <a class="btn mb-3 ${classMap(status)}">${statusName}</a>

                <button style=${styleMap({display: cancelable ? 'inline' : 'none'})}
                        @click=${handler} id="cancelButton" class="btn btn-primary mb-3">Откажи
                </button>
                <a class="btn btn-primary mb-3" data-bs-toggle="collapse" href=${'#collapse' + orderId}
                   role="button" aria-expanded="false">Описание</a>


                <div class="collapse" id=${'collapse' + orderId}>
                    <div>${description}</div>
                </div>

                <h5 class="mt-3">${price} лв.</h5>

                <div id="orderId" hidden>${orderId}</div>

            </div>
        </div>
    </div>
`;
let container = document.getElementById("container");

let statusNames = {
    'PENDING': 'Изчакване',
    'COOKING': 'Готви се',
    'READY': 'Готова',
    'TRAVELING': 'Носи се',
    'FINISHED': 'Завършена',
    'PAYED': 'Платена'
}

let statusClassMap = {
    'PENDING': {'btn-secondary': true},
    'COOKING': {'btn-warning': true},
    'READY': {'btn-danger': true},
    'TRAVELING': {'btn-info': true},
    'FINISHED': {'btn-success': true},
    'PAYED': {'btn-primary': true},
}

async function doFetch() {
    let http = await fetch(address + "/user/orders-rest");
    let json = await http.json();
    let templates = [];
    for (const order of json) {
        templates.push(
            card(
                order.itemView.id,
                order.itemView.imageUrl,
                order.itemView.name,
                order.itemView.description,
                order.itemView.price.toFixed(2),
                statusClassMap[order.orderStatus],
                statusNames[order.orderStatus],
                order.orderStatus === 'PENDING',
                cancelHandler));
    }
    render(templates, container);
}

let lastFetchDone = true;
async function display() {
    if (lastFetchDone && !fetchesDisabled) {
        lastFetchDone = false;
        doFetch().then(() => lastFetchDone = true);
    }
}

function cancelHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch(address + "/user/order/" + orderId + "/cancel-rest")
        .then(() => display());
}

display();
setInterval(display, 2000);