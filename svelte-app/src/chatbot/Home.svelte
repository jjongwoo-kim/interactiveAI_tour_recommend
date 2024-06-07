<script>
    import { Link } from "svelte-routing";

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
            case '구름많음': return 'more_cloudy.png';
            case '구름많고 한때 비': return 'cloudy_rainy.png';
            case '눈': return 'snowy.png';
            case '흐림': return 'cloudy.jpeg'
            case '흐리고 한때 비':
            case '흐리고 비':
				return 'rainy.png';
            default: return 'default.png';
        }
    }
</script>

<body>
  <div class="container">
        <img src="/002.png" alt="Weather Icons" class="logo">
    <h1 class="title">TripWiz</h1>
    <span class="subtitle">[AI Chatbot Website]</span>
    <input type="text"
           id="locationInput"
           class="input-field"
           placeholder="이곳에 지역을 입력하세요.">
    <button class="submit-button" on:click={inputValue}>CLICK</button>
  </div>


<!-- 임시로 만든 받은 데이터를 화면에 표시하는 부분 -->
<div>
    {#if weathers.length > 0}
        {#each Array(Math.ceil(weathers.length / 2)) as _, i}
                   <div class="grid-container">
                       {#each weathers.slice(i * 2, i * 2 + 2) as weather}
            <div class="day">
                <div class="date">{weather.date}</div>
                <div class="LT">{weather.low_temperature}</div>
                <div class="HT">{weather.high_temperature}</div>
                <div class="Weather">{weather.weather}</div>
                <img class="Weatherimg" src="{getWeatherImg(weather.weather)}"/>
            </div>
            {/each}
          </div>
        {/each}
    {:else}
        <p> </p>
    {/if}
</div>

<div class="button-container">
    {#if weathers.length > 0}
        <Link to="chatbot">
            <button class="next-button">Chatbot START</button>
        </Link>
    {/if}
</div>
</body>


<style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
    @import url('https://fonts.googleapis.com/css2?family=Lobster&display=swap');
    @import url('https://fonts.googleapis.com/css2?family=Dancing+Script&display=swap');

        body{
              backgroud-color: white;
              margin: 0; /* 기본 여백 제거 */
              padding: 0;
              font-family: Arial, sans-serif; /* 기본 글꼴 설정 */
              height: 100vh; /* 전체 뷰포트 높이 설정 */
          }


        .container {
            text-align: center;
            width: 100%;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

       .grid-container {
           display: grid;
           grid-template-columns: repeat(2, 1fr); /* 5개의 열 *
           width: 90%; /* 전체 너비를 꽉 채우도록 설정 */
           gap: 5px; /* 그리드 아이템 간의 간격 */
       }



        .title {
            font-size: 7em; /* 글자 크기 증가 */
            font-weight: bold;
            font-family: 'Dancing Script'; /* 글씨체 설정 */
            color: #7A9EFF;
            margin-bottom: -12px;
            text-shadow: 3px 3px 0px white, /* 입체감 그림자 */
                         6px 6px 0px rgba(0,0,0,0.1),
                         9px 9px 0px rgba(0,0,0,0.05);
        }

        .subtitle {
             font-size: 1.5em; /* 서브타이틀 글자 크기 */
                    font-weight: bold;
                    font-family: 'Jua', sans-serif; /* 서브타이틀 글씨체 설정 */
                    color: #7A9EFF;
                    padding: 10px;
                    margin-bottom: 30px;
        }

        .logo {
                width: 800px; /* 이미지 너비 */
                margin-bottom: -100px; /* 이미지와 제목 사이 여백 */
                padding: 1px; /* 이미지 주위에 여백 추가 */
            }

        .input-field {
            padding: 16px;
            width: 420px;
            border: 3px solid #7A9EFF;
            border-radius: 14px;
            font-size: 15px;
            font-family: 'Jua', sans-serif;
        }

        .input-field:focus {
            outline: none;
            border-color: #6699CC;
        }

        .submit-button {
            padding: 30px 60px;
            background-color: #7A9EFF; /* 버튼 배경색 설정 */
            border: 3px solid white; /* 연한 회색 테두리 */
            border-radius: 80px; /* 둥근 모서리 */
            padding: 10px 20px; /* 내부 여백 */
            color: white;
            font-weight: bold;
            font-size: 18px;
            font-family: 'Jua', sans-serif;
            cursor: pointer;
            margin-top: 15px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .submit-button:hover {
            background-color: #6699CC; /* 호버 시 배경색 변경 */
            transform: translateY(-4px); /* 호버 시 살짝 위로 이동 */
        }

            .day {
                border-top: 3px solid #6699CC; /* 상단 테두리 */
                border-bottom: 3px solid #6699CC; /* 하단 테두리 */
                border-left: 3px solid #6699CC; /* 좌측 테두리 */
                border-right: 3px solid #6699CC; /* 우측 테두리 */
                border-radius: 12px;
                display: flex;
                justify-content: space-around;
                align-items: center;
                padding: 10px;
                margin: 5px;
                font-family: 'Jua', sans-serif;
                }

            .date {
                color: black;
                margin-right: 10px;
                font-size: 20px;
                font-weight: bold;
                justify-content: center;
                align-items: center;
                flex: 1;
                text-align: center;
                   }


            .LT {
                color: #191970;
                font-size: 15px;
                font-weight: bold;
                margin-right: 10px;
                justify-content: center;
                align-items: center;
                flex: 1;
                text-align: center;
                }


            .HT {
                color: #DAA520;
                font-size: 15px;
                font-weight: bold;
                margin-right: 10px;
                justify-content: center;
                align-items: center;
                flex: 1;
                text-align: center;
                }

            .Weather {
                    color: balck;
                    font-weight: bold;
                    font-size: 12px;
                     }

            .Weatherimg {
                width: 60px;
                height: 60px;
                        }

             .next-button {
                        padding: 30px 60px;
                        background-color: #6699CC; /* 버튼 배경색 설정 */
                        border: 3px solid white; /* 연한 회색 테두리 */
                        border-radius: 80px; /* 둥근 모서리 */
                        padding: 10px 20px; /* 내부 여백 */
                        color: white;
                        font-weight: bold;
                        font-size: 14px;
                        font-family: 'Jua', sans-serif;
                        cursor: pointer;
                        margin-top: 20px;
                        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
                        transition: background-color 0.3s ease, transform 0.3s ease; /* 부드러운 전환 효과 */
                    }

             .next-button:hover {
                         background-color: navy; /* 호버 시 배경색 변경 */
                         transform: translateY(-4px); /* 호버 시 살짝 위로 이동 */
                     }

             .button-container {
                         display: flex;
                         justify-content: center;
                         align-items: center;
                         width: 100%; /* 중앙 정렬을 위해 전체 너비 사용 */
                         margin-top: 100px; /* 버튼과 위의 표 사이에 여백 추가 */
                         margin-bottom: 20px; /* 아래쪽 여백 설정 */
                     }

    </style>
