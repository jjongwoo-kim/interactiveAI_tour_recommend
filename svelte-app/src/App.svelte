<script>
	import { onMount } from "svelte";

    let weathers = []; // 받은 데이터를 저장할 변수

    // API에서 데이터를 가져오는 비동기 함수
    async function fetchData() {
        try {
            const response = await fetch("http://localhost:8081/"); // 백엔드 엔드포인트에 GET 요청
            if (!response.ok) {
                throw new Error('Failed to fetch data');
            }
            weathers = await response.json(); // JSON 형식으로 응답된 데이터를 변수에 저장
            console.log(weathers); // 받은 데이터를 콘솔에 출력하여 확인
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    // 페이지가 로드되면 fetchData 함수 호출
    onMount(fetchData);
</script>
<div>
	<div>aaa</div>
<!-- 받은 데이터를 화면에 표시하는 부분 -->
{#if weathers.length > 0}
	<ul>
		{#each weathers as weather}
			<li>
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