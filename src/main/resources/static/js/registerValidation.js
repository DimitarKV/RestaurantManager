let form = document.getElementById("registerForm");
let usernameField = document.getElementById("username");
let passwordField = document.getElementById("password");
let repeatPasswordField = document.getElementById("repeatPassword");
let usernameTaken = document.getElementById("usernameTaken");
let usernameTakenCheckingField = document.getElementById("usernameTakenChecking");

function valid(field) {
    return !(field.value.length < 4 || field.value.length > 20);
}

form.addEventListener("submit", (e) => {
    let isValid = true;
    if (!valid(usernameField) || !valid(passwordField) || !valid(repeatPasswordField)) {
        isValid = false;
    }
    if (passwordField.value !== repeatPasswordField.value) {
        document.getElementById("passwordsMatchError").style.display = 'block';
        isValid = false;
    }
    if (!isValid)
        e.preventDefault();
});

usernameField.addEventListener('input', () => {
    if (!valid(usernameField)) {
        usernameField.classList.remove("is-valid");
        usernameField.classList.add("is-invalid");
    } else {
        usernameField.classList.remove("is-invalid");
    }
});

passwordField.addEventListener('input', () => {
    if (!valid(passwordField)) {
        usernameField.classList.remove("is-valid");
        passwordField.classList.add("is-invalid");
    } else {
        passwordField.classList.remove("is-invalid");
    }
});

repeatPasswordField.addEventListener('input', () => {
    if (!valid(repeatPasswordField)) {
        usernameField.classList.remove("is-valid");
        repeatPasswordField.classList.add("is-invalid");
    } else {
        repeatPasswordField.classList.remove("is-invalid");
    }
});

repeatPasswordField.addEventListener("input", () => {
    if (passwordField.value === repeatPasswordField.value)
        document.getElementById("passwordsMatchError").style.display = 'none';
})

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

