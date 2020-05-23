<template>
    <div class="participant_registration">
        <h1>Participant Registration</h1>
        <b-container fluid>
            <b-row>
                <b-col sm="3"> <label for="text">First Name</label> </b-col>
                <b-col sm="9"> <b-form-input id="firstName" v-model="participant.firstName"/> </b-col>
                <b-col sm="3"> <label for="text">Last Name</label> </b-col>
                <b-col sm="9"> <b-form-input id="lastName" v-model="participant.lastName"/> </b-col>
                <b-col sm="3"> <label for="text">Birthday</label> </b-col>
                <b-col sm="9"> <b-form-input id="birthday" v-model="participant.birthday"/> </b-col>
                <b-col sm="3"> <label for="text">Gender</label> </b-col>
                <b-col sm="9"> <b-form-input id="gender" v-model="participant.gender"/> </b-col>
            </b-row>
        </b-container>
        <b-button variant="primary" id="registerParticipant" @click="registerParticipant()">Register</b-button>
        <b-alert :show="showResponse" variant="success">Participant registered with ID <span id="registrationId">{{ id }}</span></b-alert>
    </div>
</template>

<script>
    import  api from '../scripts/backend-api.js'
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
                    this.$router.push('/participant/'+this.id);
                })
                .catch(e => {
                    console.log(e);
                })
            }
        }
    }
</script>
