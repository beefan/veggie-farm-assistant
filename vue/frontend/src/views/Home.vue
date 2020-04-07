<template>
  <div class="home">
    <h1>HOME</h1>
    <p>You must be authenticated to see this</p>
    <harvest-times v-bind:apiUrl="apiUrl"></harvest-times>
  </div>
</template>

<script>
import HarvestTimes from './UploadHarvestTimes.vue'
export default {
  name: 'home',
  components: {'HarvestTimes':HarvestTimes},
  data () {
    return {
      apiUrl: process.env.VUE_APP_REMOTE_API
    }
  }
}



</script>
