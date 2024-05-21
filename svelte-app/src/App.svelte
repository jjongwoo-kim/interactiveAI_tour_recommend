<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TripWiz</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap">
<style>
    body {
        font-family: Arial, sans-serif;
        text-align: center;
        margin-top: 6em; /* 상단 여백 추가 */
    }

    h1 {
        font-size: 18px;
    }

    #site-name {
        font-family: 'Pacifico', cursive; /* Pacifico 글꼴 적용 */
        font-size: 72px; /* TripWiz 사이즈 변경 */
        font-weight: bold;
        margin-top: 40px; /* 간격 더 넓게 설정 */
        margin-bottom: 20px;
    }

    #chatbot-description {
        font-size: 14px; /* 기본 사이즈로 변경 */
        margin-bottom: 20px; /* 간격 조절 */
    }

    #input-area {
        margin-top: 20px;
    }

    input[type="text"] {
        padding: 8px 16px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    #submit-input {
        display: inline-block;
        padding: 10px 20px;
        font-size: 16px;
        background-color: #000; /* 색깔 검정색으로 변경 */
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        text-decoration: none;
        margin-top: 20px; /* 챗봇 시작하기 버튼 아래로 위치시키기 */
    }

    #submit-input:hover {
        background-color: #333; /* hover 색깔 변경 */
    }

    #weather-info ul {
        list-style-type: none;
        padding: 0;
    }

    #weather-info li {
        background-color: #f9f9f9;
        border: 1px solid #ddd;
        margin: 10px;
        padding: 10px;
        border-radius: 5px;
        text-align: left;
    }
</style>
</head>
<body>

<h1 id="site-name">TripWiz</h1> <!-- TripWiz 사이즈 변경 -->

<p id="chatbot-description">관광지 추천 Chatbot을 사용하여 지역의 날씨 정보를 검색해보세요!</p> <!-- 설명 추가 -->

<div id="input-area">
    <p>안녕하세요.</p>
    <p>원하시는 지역을 입력해주세요.</p>
    <input type="text" id="locationInput" placeholder="지역 이름을 입력하세요">
    <button id="submit-input">입력 완료</button> <!-- 입력 완료 버튼 추가 -->
</div>

<br><br>

<!-- 날씨 정보를 표시할 공간 추가 -->
<div id="weather-info"></div>

<script>
    let weathers = []; // 받은 데이터를 저장할 변수
    let tripLocation;

    document.getElementById('submit-input').addEventListener('click', inputValue);

    const inputValue = () => { // 지역을 적은 값 전달 함수
        tripLocation = document.getElementById('locationInput').value.trim();
        console.log("Input Value: ", tripLocation); // type String
        if (tripLocation) {
            sendDataToServer({ location: tripLocation });
        } else {
            console.error("No location input provided");
        }
    }

    // API에서 데이터를 가져오는 비동기 함수
    async function fetchData() {
        try {
            const response = await fetch("http://localhost:8081/save"); // 백엔드 엔드포인트에 GET 요청
            if (!response.ok) {
                throw new Error('Failed to fetch data');

