<template>

  <div id="app">
    <img :src= "logoUrl" />
    <div id="nav">
      <router-link class="spacing" :to = "{name:'home'}">Home</router-link>
      <router-link class="spacing" :to = "{name:'harvesttimes'}" v-bind:apiUrl="apiUrl">Upload/Edit Harvest Times</router-link>
      <router-link class="spacing" :to = "{name: 'expiration'}" v-bind:apiUrl="apiUrl">Waste Info</router-link> 
      <router-link class="spacing1" :to = "{name: 'cropplanner'}" >Crop Planner</router-link>
    </div>
    <router-view/>
  </div>
</template>


<style scoped>

</style>
<script>

export default {
  name: 'home',
  data () {
    return {
      apiUrl: process.env.VUE_APP_REMOTE_API,
      logoUrl: require('./images/harvestassistlogo.png')
    }
  }
}
</script>