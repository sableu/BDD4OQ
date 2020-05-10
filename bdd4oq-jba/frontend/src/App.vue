<template>
  <div id="app">
    <ParticipantRegistration/>
    <p>An some more text</p>
</div>
</template>

<script>
import ParticipantRegistration from './components/ParticipantRegistration.vue'
export default {
  name: 'app',
  components: { ParticipantRegistration },
  data () {
    return {}
  }
}
</script>
