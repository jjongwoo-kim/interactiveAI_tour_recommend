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

</script>

<div>
	<input type="text" id="locationInput" placeholder="지역 이름을 입력하세요">
	<button on:click={inputValue}>전송</button>
	<!-- 임시로 만든 받은 데이터를 화면에 표시하는 부분 -->
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

<style>

</style>