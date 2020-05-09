<template>
  <div id="app">
  <p>Message: {{msg}}</p>
    <Hello/>
    <p>An some more text</p>
</div>
</template>

<script>
import Hello from './components/Hello.vue'
export default {
  name: 'app',
  components: { Hello },
  data () {
    return {
      msg: 'Hello to Vue.js!'
    }
  }
}
</script>
