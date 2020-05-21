<template>
    <div class="participant">
        <h1>Participants</h1>
        <b-table hover :items="participants" :fields="fields"  @row-clicked="participantClicked" />
        <b-alert :show="!hasResults" variant="info">No participants registered</b-alert>
    </div>
</template>

<script>
    import  api from '../scripts/backend-api.js'
    export default {
        name: 'participant',
        data()  {
            return {
                fields: [
                    {
                        key: 'id',
                        label: 'ID',
                        sortable: true
                    },
                    {
                        key: 'firstName',
                        label: 'First Name',
                        sortable: true
                    },
                    {
                        key: 'lastName',
                        label: 'Last Name',
                        sortable: true
                    },
                    {
                        key: 'birthday',
                        label: 'Birthday',
                        sortable: true,
                    },
                    {
                        key: 'gender',
                        label: 'Gender',
                        sortable: true,
                    }
                ],
                participants: [],
                hasResults: false
            }
        },
        mounted(){
            this.fetchData();
        },
        watch:{
            '$route':'fetchData'
        },
        methods: {
            fetchData(){
                this.participants = [];
                this.hasResults = false;
                var self = this;
                api.getParticipants().then(response => {
                    console.log(response);
                    response.data.forEach(function (item, index) {
                        var participant = {};
                        participant.id = item.id;
                        participant.firstName = item.firstName;
                        participant.lastName = item.lastName;
                        participant.birthday = item.birthday;
                        participant.gender = item.gender;
                        self.participants.push(participant);
                        self.hasResults = true;
                    });
                })
                .catch(e => {
                    console.log(e);
                })
            },
            participantClicked(record, index){
                this.$router.push('/participant/'+record.id);
            }
        }
    }
</script>
