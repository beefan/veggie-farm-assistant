<template>
    <section id="nav header heading" class="sticky">
    <div id="nav">
      <div class="title">
      <p class="spacing">HarvestAssist</p>
      </div>
      <div class="space"></div>
      <div class="links">
      <router-link class="spacing" :to = "{name:'dashboard'}">Dashboard</router-link>
      <router-link class="spacing" :to = "{name: 'cropplanner'}">Crop Planner</router-link>
      <router-link class="spacing" :to = "{name:'harvesttimes'}">Upload/Edit Harvest Times</router-link>
      <router-link class="spacing" :to = "{name: 'farmlog'}">Farm Log</router-link>
      <router-link class="spacing" :to = "{name: 'expiration'}">Waste Info</router-link> 
      
      
      </div>
      <div class="space1"></div>
      <div class="logout">
      <router-link class="spacing1" :to = "{name: 'home'}">Logout</router-link>
      </div>
    </div>
    
  </section>
</template>

<script>
export default {
data () {
    return {
      logoUrl: require('../images/harvestassistlogowhite.png')
    }
  }
}
</script>

<style>
#app\ header\ heading{
  padding:0;
  margin:0;
  height:20%;
  background-color:#175676;
}
.space {
  width: 7rem;
  margin:0;
  padding:0;
}
.space1 {
  width: 10rem;
  margin:0;
  padding:0;
}
</style>