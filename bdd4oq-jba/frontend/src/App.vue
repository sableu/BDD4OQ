<template>
    <div id="app">
        <b-nav id ="nav">
            <b-nav-item><router-link to="/">Home</router-link></b-nav-item>
            <b-nav-item><router-link to="/"><router-link to="/participantRegistration">Participant Registration</router-link></router-link></b-nav-item>
            <b-nav-item><router-link to="/"><router-link to="/weightEntry">Weight Measurement Entry</router-link></router-link></b-nav-item>
        </b-nav>
        <div style="margin-left:45px; margin-right:45px; margin-top:20px">
            <router-view/>
        </div>
    </div>
</template>