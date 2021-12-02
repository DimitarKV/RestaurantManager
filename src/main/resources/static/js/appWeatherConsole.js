let displayDiv = document.getElementById("appWeatherDiv");
let weatherTownField = document.getElementById("weatherTown");
let weatherImageField = document.getElementById("weatherImage");
let weatherTempField = document.getElementById("weatherTemp");
let weatherMaxTempField = document.getElementById("weatherMaxTemp");
let weatherMinTempField = document.getElementById("weatherMinTemp");
let weatherDescriptionField = document.getElementById("weatherDescription");

async function displayWeather() {
    fetch('http://api.openweathermap.org/data/2.5/weather?q=plovdiv&appid=4b44ff719a538a4be261d5ca252e916e&units=metric&lang=bg')
        .then(http => http.json())
        .then((json) => {
            weatherTownField.textContent = json.name;
            weatherImageField.src = '/images/icons/' + json.weather[0].icon + '.png';
            weatherTempField.textContent = Math.round(json.main.temp);
            weatherMaxTempField.textContent = Math.round(json.main.temp_max);
            weatherMinTempField.textContent = Math.round(json.main.temp_min);
            weatherDescriptionField.textContent = json.weather[0].description;
        });

}

displayWeather();