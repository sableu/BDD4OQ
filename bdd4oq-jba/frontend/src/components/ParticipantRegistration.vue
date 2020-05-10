<template>
    <div class="participant_registration">
        <h1>Participant Registration</h1>
        <input type="text" id="firstName" v-model="participant.firstName" /> <br/>
        <input type="text" id="lastName" v-model="participant.lastName" /> <br/>
        <input type="text" id="birthday" v-model="participant.birthday" /> <br/>
        <input type="text" id="gender" v-model="participant.gender" /> <br/>
        <button id="registerParticipant" @click="registerParticipant()">Register</button>
        <div v-if="showResponse"> Participant registered with ID <span id="registrationId">{{ id }}</span></div>
    </div>
</template>

<script>
    import  api from './backend-api.js'
    export default {
        name: 'participant_registration',
        data()  {
            return {
                id: [],
                showResponse: false,
                participant: {
                    firstName: '',
                    lastName:'',
                    birthday:'',
                    gender:''
                 }
            }
        },
        methods: {
            registerParticipant() {
                api.registerParticipant(this.participant).then(response => {
                    console.log(response);
                    this.id = response.data;
                    this.showResponse = true;
                })
                .catch(e => {
                    console.log(e);
                })
            }
        }
    }
</script>
