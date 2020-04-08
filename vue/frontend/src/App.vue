<template>

  <div id="app">
    <img :src= "logoUrl" />
    <div id="nav">
      <router-link class="spacing" :to = "{name:'home'}">Home</router-link>
      <router-link class="spacing" :to = "{name:'login'}">Login</router-link>
      <router-link class="spacing1" :to = "{name:'harvesttimes'}" v-bind:apiUrl="apiUrl">Upload/Edit Harvest Times</router-link>
    </div>
    <router-view/>
  </div>
</template>


<style scoped>

</style>
<script>
//import HarvestTimes from './UploadHarvestTimes.vue'
export default {
  name: 'home',
  //components: {'HarvestTimes':HarvestTimes},
  data () {
    return {
      apiUrl: process.env.VUE_APP_REMOTE_API,
      logoUrl: require('./images/harvestassistlogo.png')
    }
  }
}
</script>