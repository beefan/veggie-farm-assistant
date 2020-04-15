<template>
  <section id="nav header heading" class="sticky">
    <div id="nav">
      <div class="title">
        <p class="spacing">HarvestAssist</p>
      </div>

      <div class="links">
        <router-link class="spacing" :to="{name:'dashboard'}">Dashboard</router-link>
        <router-link class="spacing" :to="{name: 'cropplanner'}">Crop Planner</router-link>
        <router-link class="spacing" :to="{name:'harvesttimes'}">Upload/Edit Harvest Times</router-link>
        <router-link class="spacing" :to="{name: 'farmlog'}">Farm Log</router-link>
        <router-link class="spacing1" :to="{name: 'expiration'}">Waste Info</router-link>
      </div>

      <div class="logout" @click="loggedOut">
        <router-link class="spacing1" :to="{name: 'home'}">Logout</router-link>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      logoUrl: require("../images/harvestassistlogowhite.png")
    };
  },
  methods: {
    loggedOut() {
      alert("You Have Successfully Logged Out.");
    }
  }
};
</script>

<style>
#app\ header\ heading {
  padding: 0;
  margin: 0;
  height: 20%;
  background-color: #175676;
}
.title {
  margin-right: 10%;
}
.logout {
  margin-left: 15%;
}
#nav {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: center;
  align-items: center;
  height: 4vw;
  width: 100%;
  margin-left: auto;
  margin-right: auto;
  background-color: #130f40;
  border-bottom: 2px solid #282451;
  box-shadow: 10px 10px 50px rgb(100, 100, 100);
}

p.spacing {
  text-decoration: none;
  margin-right: 1%;
  font-family: "Baloo Paaji 2", cursive;
  color: #f7b254;
  font-size: 2vw;
}

a.spacing {
  text-decoration: none;
  margin-right: 3vw;
  font-family: "Baloo Paaji 2", cursive;
  color: #ffffff;
  font-size: 1.5vw;
}

a.spacing1 {
  text-decoration: none;
  font-family: "Baloo Paaji 2", cursive;
  color: #ffffff;
  font-size: 1.5vw;
}

a.spacing:hover {
  color: #f7b254;
}

a.spacing1:hover {
  color: #f7b254;
}

p {
  font-family: "Lato", sans-serif;
}
</style>