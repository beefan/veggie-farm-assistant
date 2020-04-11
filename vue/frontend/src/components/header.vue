<template>
    <div id="app" class="sticky">
    <img :src= "logoUrl" />
    <div id="nav">
      <router-link class="spacing" :to = "{name:'home'}">Home</router-link>
      <router-link class="spacing" :to = "{name:'harvesttimes'}">Upload/Edit Harvest Times</router-link>
      <router-link class="spacing" :to = "{name: 'expiration'}">Waste Info</router-link> 
      <router-link class="spacing" :to = "{name: 'farmlog'}">Farm Log</router-link>
      <router-link class="spacing1" :to = "{name: 'cropplanner'}">Crop Planner</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
data () {
    return {
      logoUrl: require('../images/harvestassistlogo.png')
    }
  }
}
</script>

<style>

</style>