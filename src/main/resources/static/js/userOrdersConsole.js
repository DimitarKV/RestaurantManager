import {html, render} from 'https://unpkg.com/lit-html?module';
import {styleMap} from 'https://unpkg.com/lit-html/directives/style-map?module';
import {classMap} from 'https://unpkg.com/lit-html/directives/class-map?module';

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
    'FINISHED': 'Завършена'
}

let statusClassMap = {
    'PENDING': {'btn-secondary': true},
    'COOKING': {'btn-warning': true},
    'READY': {'btn-primary': true},
    'FINISHED': {'btn-success': true}
}

async function display() {
    let http = await fetch("http://91.139.199.150/user/orders");
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

function cancelHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch("http://91.139.199.150/users/order/" + orderId + "/cancel")
        .then(() => display());
}

display();
setInterval(display, 2000);