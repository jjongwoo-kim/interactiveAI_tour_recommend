<script>
    let messages = []; // 내 메세지를 담은 배열

    // const backEndpoint = "http://localhost:8081/message";

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
            messages = [...messages, {text: 'API 요청 중 에러가 발생하였습니다. 클라이언트'}];
        }
    }

    // ChatGPT API 요청
    async function fetchAIResponse(prompt) {
        try {
            const response = await fetch("http://localhost:8081/api/message", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ message: prompt })
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
	        const data = await response.json();
			console.log(data);
            return data.responses[0];
        } catch (error) {
            console.error('Error fetching AI response:', error);
            return 'Error fetching AI response';
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