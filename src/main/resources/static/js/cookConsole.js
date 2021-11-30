import {html, render} from 'https://unpkg.com/lit-html?module';
import {styleMap} from 'https://unpkg.com/lit-html/directives/style-map?module';

let card = (orderId, imageUrl, name, description, acceptHandler, cooking = true) => html`
    <div class="col-lg-3 mt-3">
        <div class="card rounded-back bg-dark pt-4">
            <img src=${imageUrl}/>
            <div class="card-body text-center text-light">
                <h4>${name}</h4>

                <a class="btn btn-primary mb-3" data-bs-toggle="collapse" href=${'#collapse' + orderId}
                   role="button" aria-expanded="false">Бележка</a>
                
                <button style=${styleMap({display: cooking ? 'none' : 'inline'})} @click=${acceptHandler} id="acceptButton" class="btn btn-primary mb-3">Поеми</button>
                <button style=${styleMap({display: cooking ? 'inline' : 'none'})} @click=${acceptHandler} id="readyButton" class="btn btn-primary mb-3">Готово</button>
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

async function display() {
    let http = await fetch("http://91.139.199.150/cooks/orders");
    let json = await http.json();
    let templates = [];
    for (const order of json) {
        templates.push(card(order.id, order.imageUrl, order.name, order.description, acceptHandler, false));
    }
    render(templates, waitingContainer);

    http = await fetch("http://91.139.199.150/cooks/current/orders");
    json = await http.json();
    templates = [];
    for (const order of json) {
        templates.push(card(order.id, order.imageUrl, order.name, order.description, orderReadyHandler));
    }
    render(templates, currentCookContainer);
}

function acceptHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch("http://91.139.199.150/cooks/order/" + orderId + "/accept");
}

function orderReadyHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch("http://91.139.199.150/cooks/order/" + orderId + "/ready");
}

display();

setInterval(display, 500);