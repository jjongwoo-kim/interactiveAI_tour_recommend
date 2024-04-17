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
        <button type="button" value="enter" on:click={inputChat}>Enter</button>
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

}

#aiMsg .msg {
    background-color: #f1f0f0;

}

#myMsg > .msg {
    background-color: #0084FF;
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
    width: 85%;
    height: calc(100% - 1px);
    border: none;
    padding-bottom: 0;
}

#messageInput:focus {
    outline: none;
}

/* #chatForm > input[type=submit] {
    outline: none;
    border: none;
    background: none;
    color: #0084FF;
    font-size: 17px;
} */
</style>