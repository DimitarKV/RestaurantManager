import {validate, valid} from './validator.js';

let form = document.getElementById("editForm");
let usernameField = document.getElementById("username");
let firstNameField = document.getElementById("firstName");
let lastNameField = document.getElementById("lastName");
let ageField = document.getElementById("age");
let passwordField = document.getElementById("password");
let repeatPasswordField = document.getElementById("repeatPassword");
let usernameTaken = document.getElementById("usernameTaken");
let usernameTakenCheckingField = document.getElementById("usernameTakenChecking");

function ageValidator(field){
    return field.value > 0;
}

form.addEventListener("submit", (e) => {
    let isValid = true;
    isValid = isValid && validate(usernameField)
    isValid = isValid && validate(firstNameField, true);
    isValid = isValid && validate(lastNameField, true);
    isValid = isValid && validate(ageField, true, ageValidator);
    isValid = isValid && validate(passwordField, true);
    isValid = isValid && validate(repeatPasswordField, true);
    if (passwordField.value !== repeatPasswordField.value) {
        document.getElementById("passwordsMatchError").style.display = 'block';
        isValid = false;
    }
    if (!isValid)
        e.preventDefault();
});

usernameField.addEventListener('input', () => {
    validate(usernameField);
});

firstNameField.addEventListener('input', () => {
    validate(firstNameField, true);
});

lastNameField.addEventListener('input', () => {
    validate(lastNameField, true);
});

ageField.addEventListener('input', () => {
    validate(ageField, true, ageValidator)
});

passwordField.addEventListener('input', () => {
    validate(passwordField, true);
});

repeatPasswordField.addEventListener('input', () => {
    validate(repeatPasswordField, true);
    if (passwordField.value.trim() === repeatPasswordField.value.trim())
        document.getElementById("passwordsMatchError").style.display = 'none';
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
    let http = await fetch("http://localhost:8080/users/register/check/" + username);
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

