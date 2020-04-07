<template>
  <div class="home">
    <h1>Harvest Assist</h1>
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
