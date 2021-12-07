import {validate, valid} from './validator.js';
import {address} from "/js/fetchApi.js";

let form = document.getElementById("registerForm");
let usernameField = document.getElementById("username");
let passwordField = document.getElementById("password");
let repeatPasswordField = document.getElementById("repeatPassword");
let usernameTaken = document.getElementById("usernameTaken");
let usernameTakenCheckingField = document.getElementById("usernameTakenChecking");

form.addEventListener("submit", (e) => {
    let isValid = true;
    isValid = isValid && validate(usernameField);
    isValid = isValid && validate(passwordField);
    isValid = isValid && validate(repeatPasswordField);
    if (passwordField.value.trim() !== repeatPasswordField.value.trim()) {
        document.getElementById("passwordsMatchError").style.display = 'block';
        isValid = false;
    }
    if (!isValid)
        e.preventDefault();
});

usernameField.addEventListener('input', () => {
    validate(usernameField);
});

passwordField.addEventListener('input', () => {
    validate(passwordField);
});

repeatPasswordField.addEventListener('input', () => {
    if (passwordField.value === repeatPasswordField.value)
        document.getElementById("passwordsMatchError").style.display = 'none';
    validate(repeatPasswordField);
});

if (usernameTaken) {
    usernameField.addEventListener("input", usernameAvailabilityListener);
}

async function usernameAvailabilityListener(e) {
    if (!valid(e.target)) {
        return;
    }
    usernameField.removeEventListener("input", usernameAvailabilityListener);
    usernameTakenCheckingField.style.display = 'block';
    usernameTaken.style.display = 'none';
    usernameField.classList.remove("is-valid");
    let username = e.target.value;
    let http = await fetch(address + "/user/register/check/" + username);
    let json = await http.json();
    if (json.available) {
        usernameTaken.style.display = 'none';
        e.target.classList.add('is-valid');
    } else {
        usernameTaken.style.display = 'block';
    }
    usernameTakenCheckingField.style.display = 'none';
    usernameField.addEventListener("input", usernameAvailabilityListener);
}

