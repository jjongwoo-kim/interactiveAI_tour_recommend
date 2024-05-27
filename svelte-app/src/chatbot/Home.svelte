<script>
    let weathers = []; // 받은 데이터를 저장할 변수
    let tripLocation;

    const inputValue = () => { // 지역을 적은 값 전달 함수
        tripLocation = document.getElementById('locationInput').value.trim();
        console.log(tripLocation); // type String
        sendDataToServer(tripLocation);
    }

    // API에서 데이터를 가져오는 비동기 함수
    async function fetchData() {
        try {
            const response = await fetch("http://localhost:8081/save"); // 백엔드 엔드포인트에 GET 요청
            if (!response.ok) {
                throw new Error('Failed to fetch data');
            }
            weathers = await response.json(); // JSON 형식으로 응답된 데이터를 변수에 저장
            console.log(weathers); // 받은 데이터를 콘솔에 출력하여 확인
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    async function sendDataToServer(data) { // 지역값 서버로 보내는 함수
        console.log(data);
        try {
            const response = await fetch("http://localhost:8081/save", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });
            if (!response.ok) {
                throw new Error('Failed to send data to server');
            }
            console.log('Data sent to server successfully');
        } catch (error) {
            console.error('Error sending data to server:', error);
        }
        fetchData();
    }


    function getWeatherImg(weather) {
        switch(weather) {
            case '맑음': return 'sunny.png';
            case '구름많음': return 'cloudy.png';
            case '눈': return 'snowy.png';
            case '흐리고 한때 비': return 'rainy.png';
            default: return 'default.png';
        }
    }
</script>


<div class="container">
    <h1 class="title">TripWiz</h1>
    <input type="text"
           id="locationInput"
           placeholder="지역 이름을 입력하세요">
    <button class="submit-button" on:click={inputValue}>전송</button>
</div>

<!-- 임시로 만든 받은 데이터를 화면에 표시하는 부분 -->
<div>
    {#if weathers.length > 0}
        {#each weathers as weather}
            <div class="day">
                <div class="date">{weather.date}</div>
                <div class="LT">{weather.low_temperature}</div>
                <div class="HT">{weather.high_temperature}</div>
                <div class="Weather">{weather.weather}</div>
                <img class="Weatherimg" src="{getWeatherImg(weather.weather)}"/>
            </div>
        {/each}
    {:else}
        <p>No weather data available</p>
    {/if}
</div>


<style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

    :global(body) {
        margin: 0;
        padding: 0;
        font-family: 'Jua', sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        background-color: #f0f0f0;
    }

    .container {
        text-align: center;
    }

    .title {
        font-size: 3em;
        font-weight: bold;
        color: #ffa500;
        margin-bottom: 20px;
    }

    .input-field {
        padding: 10px;
        width: 300px;
        border: 2px solid #ffa500;
        border-radius: 5px;
        font-size: 16px;
        font-family: 'Jua', sans-serif;
    }

    .input-field:focus {
        outline: none;
        border-color: #ff7f00;
    }

    .submit-button {
        padding: 10px 20px;
        background-color: #ffa500;
        border: none;
        border-radius: 5px;
        color: white;
        font-size: 16px;
        font-family: 'Jua', sans-serif;
        cursor: pointer;
        margin-top: 10px;
    }

    .submit-button:hover {
        background-color: #ff7f00;
    }


    .day {
        border: 3px solid lightgray;
        display: flex;
        justify-content: space-around;
        align-items: center;
        padding: 10px;
        margin: 5px;
        font-family: 'Jua', sans-serif;
        }

    .date {
        color: gray;
        margin-right: 10px;
        font-size: 20px;
        font-weight: bold;
        justify-content: center;
        align-items: center;
        flex: 1;
        text-align: center;
           }


    .LT {
        color: blue;
        font-size: 13px;
        margin-right: 10px;
        justify-content: center;
        align-items: center;
        flex: 1;
        text-align: center;
        }


    .HT {
        color: red;
        font-size: 13px;
        margin-right: 10px;
        justify-content: center;
        align-items: center;
        flex: 1;
        text-align: center;
        }

    .Weather {
            color: #8B4513;
            font-size: 10px;
             }

    .Weatherimg {
        width: 60px;
        height: 60px;
                 }
</style>
