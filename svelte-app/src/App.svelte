<!-- App.svelte -->

<script>
    import { onMount } from 'svelte';
    import Calendar from 'svelte-calendar';
  
    let selectedDate;
    let weatherInfo = null;
  
    async function fetchWeather() {
      if (!selectedDate) return;
  
      // 선택된 날짜를 기반으로 API 호출 및 처리를 구현함
      // API로부터 받은 날씨 정보를 weatherInfo 변수에 저장함

    try {
        const dateString = selectedDate.toISOString().split('T')[0]; // 선택된 날짜를 ISO 형식의 문자열로 변환 :(YYYY-MM-DDTHH:mm:ss.sssZ)형식
        const response = await fetch(`https://api.weatherapi.com/v1/history.json?key=YOUR_API_KEY&q=YOUR_LOCATION&dt=${dateString}`);
        // YOUR_API_KEY와 YOUR_LOCATION 여기에 날씨 API 키와 위치로 대체해야함 
        
        if (!response.ok) {
            throw new Error('Failed to fetch weather data');
        }
        
        const data = await response.json();
        
        // API에서 받은 데이터에서 필요한 날씨 정보를 추출
        const temperature = data.temperature;
        const condition = data.condition;
        
        // 추출된 날씨 정보를 weatherInfo 변수에 저장
        weatherInfo = { temperature, condition };
    } catch (error) {
        console.error('Error fetching weather data:', error);
    }
}

    $: fetchWeather(); //fetchWeather 함수 호출
  </script>
  
  <style>
    /* 스타일을 추가하여 웹사이트의 디자인 수정하기 */
    body {
      font-family: Arial, sans-serif;
      background-color: #f5f5f5; /* 화이트 */
      color: #333; /* 블랙 */
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
  
    /* 추가로 다른 요소 추가해서 홈페이지 꾸며야함 */
    .container {
      width: 80%;
      margin: 0 auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      text-align: center;
    }

    .calendar-header {
    margin-bottom: 10px;
    font-size: 20px;
  }
  </style>
  
  <!-- 웹사이트의 구성 요소 -->
  <div class="container">
    <h1>날씨 정보</h1>
      <!-- "원하는 날짜를 선택해주세요!" 문구 -->
  <div class="calendar-header">원하는 날짜를 선택해주세요!</div>
    <!-- Calendar 컴포넌트를 사용하여 날짜를 선택 -->
    <Calendar bind:selectedDate />
  
    <!-- 선택된 날짜에 대한 날씨 정보 표시 -->
    {#if weatherInfo}
      <p>날짜: {selectedDate.toLocaleDateString()}</p>
      <p>온도: {weatherInfo.temperature}</p>
      <p>날씨 상태: {weatherInfo.condition}</p>
    {:else}
      <p>날씨 정보를 불러오는 중입니다...</p>
    {/if}
  </div>
  