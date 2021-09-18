<template>
	<div>
		我是websocket<br>
		<button @click="connection()">连接</button><br>
			发送者名字：<input type="text" v-model="messageInfo.username" /><br>
			接受者名字：<input type="text" v-model="messageInfo.toUser" /><br>
			发送内容：<input type="text" v-model="messageInfo.toMessage" /><br>
			<button @click="sendMessage()">发送</button><br>
	<span>{{message}}</span>
	</div>
</template>

<script>
	export default{
		data(){
			return{
				messageInfo:{
					username:'',
					toUser:'',
					toMessage:'',
				},
				message:'',
				websocket: null,
			}
		},
		mounted() {
			this.$message.success("哈哈哈");
		},
		methods:{
			connection(){
				console.log('来这里了')
				if('WebSocket' in window){
					this.websocket = new WebSocket(`ws://localhost:3001/websocket/${this.messageInfo.username}`)
				}else{
					this.$message.error('不支持websocket')
				}
				this.websocket.onopen = event => {
					console.log('建立连接了')
					this.message = '建立连接了';
				};
				this.websocket.onmessage= event =>{
					this.message = event.data;
				};
				this.websocket.onerror= error =>{
					console.log('出现异常了')
					this.message = '出现异常了';
				};
				this.websocket.onclose= event =>{
					console.log('连接关闭了')
					this.message = '连接关闭了';
				};
				//当浏览器的页面关闭的时候，此处应该关闭连接，防止服务器出现异常
				window.onbeforeunload= () => {
					if(this.websocket!=null){
						this.websocket.close()
					}
				};
			},
			sendMessage(){
				if(this.websocket!=null){
					this.websocket.send(JSON.stringify(this.messageInfo))
				}
			}
		}
	}
</script>

<style>
</style>
