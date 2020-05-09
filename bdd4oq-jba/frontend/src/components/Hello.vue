<template>
    <div class="greeting">
        <h1>Say hi to the backend</h1>
         <input type="text" v-model="userName" placeholder="first name">
        <button @click="getGreeting()">Hi</button>
        <div v-if="showResponse"> Greetings {{ response }}</div>
    </div>
</template>

<script>
    import  api from './backend-api.js'
    export default {
        name: 'greeting',
        data()  {
            return {
                response: [],
                showResponse: false,
                userName: ''
            }
        },
        methods: {
            getGreeting() {
                api.hello_service(this.userName).then(response => {
                    console.log(response);
                    this.response = response.data;
                    this.showResponse = true;
                })
                .catch(e => {
                    console.log(e);
                })
            }
        }
    }
</script>
