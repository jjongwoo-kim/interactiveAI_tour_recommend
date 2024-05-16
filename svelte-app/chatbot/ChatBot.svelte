<script>
    let messages = []; // 내 메세지를 담은 배열

    const apiKey = 'api-key'; // chat GPT api key
    const apiEndpoint = 'https://api.openai.com/v1/chat/completions';

    const inputChat = () => { // 내 메시지를 입력하는 함수
        const messageInput = document.getElementById("messageInput");
        const message = messageInput.value.trim();
        if(message != "") {
            messages = [...messages, { text: message, isUser: true }];
            messageInput.value="";
            addMessage(message);
        }
    }

    const enterkeyMessage = (e) => { // 엔터키 작동하게 하는 함수
        if(e.keyCode === 13) {
            inputChat();
        }
    }

    const addMessage = async (message) => { // ai 메세지를 추가하는 함수
        try {
            const aiResponse = await fetchAIResponse(message);
            messages = [...messages, { text: aiResponse, isUser: false }];
        } catch (error) {
            console.log('API 요청 중 에러가 발생하였습니다.', error);
            messages = [...messages, {text: 'API 요청 중 에러가 발생하였습니다.'}];
        }
    }

    // ChatGPT API 요청
    async function fetchAIResponse(prompt) {
        // API 요청에 사용할 옵션들 정의
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${apiKey}`
            },
            body: JSON.stringify({
                model: "gpt-3.5-turbo",  // 사용할 AI 모델
                messages: [{
                    role: "user", // 메시지 역할을 user로 설정
                    content: prompt // 사용자가 입력한 메시지
                }, ],
                temperature: 0.8, // 모델의 출력 다양성
                max_tokens: 1024, // 응답받을 메시지 최대 토큰(단어) 수 설정
                top_p: 1, // 토큰 샘플링 확률을 설정
                frequency_penalty: 0.5, // 일반적으로 나오지 않는 단어를 억제하는 정도
                presence_penalty: 0.5, // 동일한 단어나 구문이 반복되는 것을 억제하는 정도
                stop: ["Human"], // 생성된 텍스트에서 종료 구문을 설정
        }),
        };
        try { // API 요청 후 응답 처리
            const response = await fetch(apiEndpoint, requestOptions);
            const data = await response.json();
            console.log(data);
            const aiResponse = data.choices[0].message.content;
            return aiResponse;
        } catch (error) {
		    console.error('OpenAI API 호출 중 오류 발생:', error);
            return error;
        }
    }
</script>

<div id="chatWrap">
    <div id="chatHeader">TripWiz</div>
    <div id="chatLog">
        {#each messages as message}    <!-- 내 메시지 부분 -->
            {#if message.isUser}
                <div id="myMsg">
                    <div class="msg">{message.text}</div>
                </div>
            {:else}
                <div id="aiMsg">
                    <div class="msg">{message.text}</div>
                </div>
            {/if}
        {/each}
    </div>
    <div id="chatForm">     <!-- 메시지입력, 전송버튼 -->
        <input type="text" autocomplete="off" on:keydown={enterkeyMessage} id="messageInput" placeholder="enter message" />
        <button type="button" class="sendButton" value="enter" on:click={inputChat}>Send</button>
    </div>
</div>

<style>
#chatWrap {
    width: 600px;
    border: 1px solid #ddd;
}

#chatHeader {
    height: 60px;
    text-align: center;
    line-height: 60px;
    font-size: 25px;
    font-weight: 900;
    border-bottom: 1px solid #ddd;
}

#chatLog {
    height: 700px;
    overflow-y: auto;
    padding: 10px;
}

#myMsg {
    text-align: right;
    max-width: 100%;
}

#aiMsg {
    text-align: left;
    margin-bottom: 5px;
}

.msg {
    display: inline-block;
    border-radius: 15px;
    padding: 7px 15px;
    margin-bottom: 10px;
    margin-top: 5px;
    max-width: 220px;
    word-wrap: break-word;
}

#aiMsg .msg {
    background-color: #f1f0f0;

}

#myMsg .msg {
    background-color: #000000;
    color: #fff;
}

#chatForm {
    display: block;
    width: 100%;
    height: 50px;
    border-top: 2px solid #f0f0f0;
}

#messageInput {
    width: 88%;
    height: calc(100% - 1px);
    border: none;
    padding-bottom: 0;
}

#messageInput:focus {
    outline: none;
}

.sendButton {
    background-color: rgba(70, 98, 211, 0.679);
    color: white;
    width: 60px;
    height: 35px;
    align-items: center;
	font-family: "Roboto", sans-serif;
	font-weight: bold;
    vertical-align: middle;
    border-radius: 10px;
}


</style>