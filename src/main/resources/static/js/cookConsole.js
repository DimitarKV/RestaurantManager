import {html, render} from 'https://unpkg.com/lit-html?module';

let card = (orderId, imageUrl, name, description) => html`
    <div class="col-lg-3 mt-3">
        <div class="card">
            <img src=${imageUrl}/>
            <div class="card-body text-center">
                <h4>${name}</h4>

                <a class="btn btn-primary" data-bs-toggle="collapse" href=${'#collapse' + orderId}
                   role="button" aria-expanded="false" aria-controls="collapseExample">
                    Описание
                </a>
                <button id="acceptButton" class="btn btn-primary">Поеми</button>
                <div class="collapse" id=${'collapse' + orderId}>
                    <div>${description}</div>
                </div>

                <div id="orderId" hidden>${orderId}</div>

            </div>
        </div>
    </div>
`;

async function display() {
    let http = await fetch("http://91.139.199.150/cooks/orders")
    let json = await http.json();
    console.log(json);
}

display();

