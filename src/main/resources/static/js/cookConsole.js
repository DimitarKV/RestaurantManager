import {html, render} from 'https://unpkg.com/lit-html?module';

let card = (orderId, imageUrl, name, description, acceptHandler) => html`
    <div class="col-lg-3 mt-3">
        <div class="card rounded-back bg-dark pt-4">
            <img src=${imageUrl}/>
            <div class="card-body text-center text-light">
                <h4>${name}</h4>

                <a class="btn btn-primary" data-bs-toggle="collapse" href=${'#collapse' + orderId}
                   role="button" aria-expanded="false">Бележка</a>
                
                <button @click=${acceptHandler} id="acceptButton" class="btn btn-primary">Поеми</button>
                <div class="collapse" id=${'collapse' + orderId}>
                    <div>${description}</div>
                </div>

                <div id="orderId" hidden>${orderId}</div>

            </div>
        </div>
    </div>
`;

let renderingContainer = document.getElementById("renderingContainer");

async function display() {
    let http = await fetch("http://91.139.199.150/cooks/orders")
    let json = await http.json();
    let templates = [];

    for (const order of json) {
        templates.push(card(order.id, order.imageUrl, order.name, order.description, acceptHandler));
    }
    render(templates, renderingContainer);
}

async function acceptHandler(e){
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    let http = await fetch("http://91.139.199.150/cooks/order/" + orderId + "/accept");
    let json = await http.json();
    console.log(json);
}

display();

setInterval(display, 500);