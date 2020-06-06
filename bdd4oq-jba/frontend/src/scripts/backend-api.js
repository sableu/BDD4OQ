import axios from 'axios'

const AXIOS = axios.create({
  baseURL: '/api',
  timeout: 1000,
  headers: {'Content-type' : 'application/json'}
});

export default {
    registerParticipant(participant) {
        var data = {
            lastName: participant.lastName,
            firstName: participant.firstName,
            birthday: participant.birthday,
            gender: participant.gender
        }
        console.log(data)
        return AXIOS.post('/participant', data);
    },
    getParticipant(participantId) {
            return AXIOS.get('/participant/'+participantId);
    },
    getParticipants() {
            return AXIOS.get('/participant');
    },
    setBaselineMeasurement(baselineWeightEntry, participantId) {
            var data = {
                weight: baselineWeightEntry.weight,
                dateTime: baselineWeightEntry.dateTime,
                comment: baselineWeightEntry.comment
            }
            console.log(data)
            return AXIOS.post('/participant/'+ participantId+'/weights/baseline', data);
    },
    getBaselineWeightMeasurement(participantId){
            return AXIOS.get('/participant/'+ participantId+'/weights/baseline');
    }
}
