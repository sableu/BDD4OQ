<template>
    <div class="participant">
        <h1>Participant</h1>
        <b-container fluid>
            <b-row>
                <b-col sm="3"> <label>ID</label> </b-col>
                <b-col sm="9" id="participantId"> {{participantId}} </b-col>
                <b-col sm="3"> <label>First Name</label> </b-col>
                <b-col sm="9" id="participantFirstName"> {{participant.firstName}} </b-col>
                <b-col sm="3"> <label>Last Name</label> </b-col>
                <b-col sm="9" id="participantLastName"> {{participant.lastName}} </b-col>
                <b-col sm="3"> <label>Birthday</label> </b-col>
                <b-col sm="9" id="participantBirthday"> {{participant.birthday}} </b-col>
                <b-col sm="3"> <label>Gender</label> </b-col>
                <b-col sm="9" id="participantGender"> {{participant.gender}} </b-col>
            </b-row>
        </b-container>
         <b-alert :show="showParticipantError" variant="danger">{{participantErrorMsg}}</b-alert>
        <br/>
        <h4>Baseline Weight Measurement</h4>
        <b-container fluid>
            <b-row>
                <b-col sm="3"> <label for="weight">Weight in kg</label> </b-col>
                <b-col sm="3"> <label for="dateTime">Date and Time</label> </b-col>
                <b-col sm="3"> <label for="comment">Comment</label> </b-col>
                <b-col sm="3"> </b-col>
            </b-row>
            <b-row>
                <b-col sm="3"> <b-form-input id="weight" type="number" v-model="baselineWeightEntry.weight"/> </b-col>
                <b-col sm="3"> <b-form-input id="dateTime" v-model="baselineWeightEntry.dateTime"/> </b-col>
                <b-col sm="3"> <b-form-input id="comment" v-model="baselineWeightEntry.comment"/> </b-col>
                <b-col sm="3"> <b-button :disabled="setBaselineWeightBtnDisabled" variant="primary" id="setBaselineWeight" @click="setBaselineMeasurement()">Set</b-button> </b-col>
            </b-row>
        </b-container>
         <b-alert :show="showBaselineSuccess" variant="success">Created baseline with ID: <span id="baselineId">{{baselineMeasurementId}}</span></b-alert>
         <b-alert :show="showBaselineWarning" variant="warning">{{baselineWarningMsg}}</b-alert>
    </div>
</template>

<script>
    import  api from '../scripts/backend-api.js'
    export default {
        name: 'participant',
        data()  {
            return {
                title: 'BDD4OQ JBA - Participant',
                participantId: -1,
                participant: {
                    firstName: '',
                    lastName:'',
                    birthday:'',
                    gender:''
                },
                baselineWeightEntry: {
                    weight: '',
                    dateTime: '',
                    comment: ''
                },
                participantErrorMsg : '',
                showParticipantError: false,
                baselineWarningMsg: '',
                showBaselineWarning: false,
                baselineMeasurementId: -1,
                showBaselineSuccess: false
            }
        },
        mounted(){
            document.title = this.title;
            this.fetchData();
        },
        computed: {
  	        setBaselineWeightBtnDisabled(){
  	            return (this.baselineWeightEntry.weight == '' || this.baselineWeightEntry.weight > 200 || this.baselineWeightEntry.weight < 0.5 );
            }
        },
        watch:{
            '$route':'fetchData'
        },
        methods: {
            fetchData(){
                this.showParticipantError = false;
                this.participantId=this.$route.params.participantId;
                api.getParticipant(this.participantId).then(response => {
                    console.log(response);
                    this.participant.firstName = response.data.firstName;
                    this.participant.lastName = response.data.lastName;
                    this.participant.birthday = response.data.birthday;
                    this.participant.gender = response.data.gender;
                })
                .catch(e => {
                    console.log(e);
                    this.participant.firstName = '';
                    this.participant.lastName = '';
                    this.participant.birthday = '';
                    this.participant.gender = '';
                    this.participantErrorMsg = 'Could not load participant';
                    this.showParticipantError = true;
                })
            },
            setBaselineMeasurement(){
                this.showBaselineWarning = false;
                this.showBaselineSuccess = false;
                api.setBaselineMeasurement(this.baselineWeightEntry, this.participantId).then(response => {
                    console.log(response);
                    this.baselineMeasurementId = response.data;
                    this.showBaselineSuccess = true;
                })
                .catch(e => {
                    console.log(e);
                    this.baselineWarningMsg = 'Failed to set baseline';
                    this.showBaselineWarning = true;
                })
            }
        }
    }
</script>
