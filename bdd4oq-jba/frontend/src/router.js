import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import ParticipantRegistration from './views/ParticipantRegistration.vue';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/participantRegistration',
            name: 'participantRegistration',
            component: ParticipantRegistration
        }
    ]
})
