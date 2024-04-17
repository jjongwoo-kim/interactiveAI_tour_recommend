<script>
    let messages = [];
    const inputChat = () => {
        const messageInput = document.getElementById("messageInput");
        const message = messageInput.value.trim();

        if(message != "") {
            messages = [...messages, {text: message}]
            messageInput.value="";
        }
    }
</script>

<div id="chatWrap">
    <div id="chatHeader">TripWiz</div>
    <div id="chatLog">
        <div id="chatMessage">
            <div id="aiMsg">
                <span class="tripWiz">TripWiz</span>
                <span class="msg">Hello</span>
            </div>
            <div id="myMsg">
                <span class="msg">Hi</span>
            </div>
        </div>
        {#each messages as message}
            <div id="myMsg">
                <div class="msg">{message.text}</div>
            </div>
        {/each}
    </div>
    <div id="chatForm">
        <input type="text" autocomplete="off" size="30" id="messageInput" placeholder="enter message" />
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

.tripWiz {
    font-size: 12px;
    display: block;
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
}


</style>