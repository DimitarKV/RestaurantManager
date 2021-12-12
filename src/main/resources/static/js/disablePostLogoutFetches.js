export let fetchesDisabled = false;

document.getElementById("logoutForm")
.addEventListener("submit", () => {
    fetchesDisabled = true;
})