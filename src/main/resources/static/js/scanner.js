let html5QrcodeScanner = new Html5QrcodeScanner(
    "reader",
    { fps: 10, qrbox: {width: 250, height: 250} },
    /* verbose= */ false);
html5QrcodeScanner.render(onSuccessfulRead, onError);


function onSuccessfulRead(decodedText, decodedResult) {
    document.getElementById("result").textContent = decodedText;
    html5QrcodeScanner.clear();
}

function onError(err) {
}