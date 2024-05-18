<script>
    import {Route, Router, Link} from "svelte-routing";
	import ChatBot from "../chatbot/ChatBot.svelte";
    import Home from "./chatbot/Home.svelte";

</script>


<Router>
	<nav>
		<Link to="/">Home</Link>
		<Link to="chatbot">ChatBot</Link>
	</nav>
	<div>
		<Route path="/" component={Home} />
		<Route path="/chatbot" component="{ChatBot}"/>
	</div>
</Router>
