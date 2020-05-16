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
    }
}
