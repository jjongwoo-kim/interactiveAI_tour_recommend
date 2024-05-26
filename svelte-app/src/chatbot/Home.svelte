<script>
    let weathers = []; // 받은 데이터를 저장할 변수
    let tripLocation = ''; // 입력한 지역을 저장할 변수

    const inputValue = () => { // 지역을 적은 값 전달 함수
        sendDataToServer(tripLocation);
    }

    // API에서 데이터를 가져오는 비동기 함수
    async function fetchData() {
        try {
            const response = await fetch("http://localhost:8081/weather"); // 백엔드 엔드포인트에 GET 요청 (엔드포인트 URL 확인 필요)
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
                body: JSON.stringify({ location: data })
            });
            if (!response.ok) {
                throw new Error('Failed to send data to server');
            }
            console.log('Data sent to server successfully');
            await fetchData(); // 데이터를 성공적으로 보낸 후 날씨 데이터를 가져옴
        } catch (error) {
            console.error('Error sending data to server:', error);
        }
    }
</script>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    #weatherContainer {
        background-color: #ffffff;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        width: 90%;
        max-width: 500px;
        margin: 20px auto;
        text-align: center;
    }

    #locationInput {
        width: calc(100% - 22px);
        padding: 12px;
        margin-bottom: 12px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        box-sizing: border-box;
    }

    button {
        padding: 12px 20px;
        background-color: #6c757d;
        color: #ffffff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
    }

    button:hover {
        background-color: #5a6268;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        background-color: #f9f9f9;
        margin: 10px 0;
        padding: 15px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .title {
        font-size: 2em;
        font-weight: bold;
        margin-bottom: 10px;
    }

    .subtitle {
        font-size: 1.2em;
        color: #555;
        margin-bottom: 20px;
    }
</style>

<div id="weatherContainer">
    <div class="title">TripWiz</div>
    <div class="subtitle">원하는 지역을 입력해주세요</div>
    <input type="text" id="locationInput" bind:value={tripLocation} placeholder="지역 이름을 입력하세요">
    <button on:click={inputValue}>전송</button>
    <!-- 받은 데이터를 화면에 표시하는 부분 -->
    {#if weathers.length > 0}
        <ul>
            {#each weathers as weather}
                <li>
                    <p>{tripLocation} 날씨 입니다.</p>
                    <p>Date: {weather.date}</p>
                    <p>Low Temperature: {weather.low_temperature}</p>
                    <p>High Temperature: {weather.high_temperature}</p>
                    <p>Weather: {weather.weather}</p>
                </li>
            {/each}
        </ul>
    {:else}
        <p>No weather data available</p>
    {/if}
</div>















