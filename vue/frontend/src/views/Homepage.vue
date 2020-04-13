<template>
<body class="homepage">
  <div class="bg-cover" :style="{ backgroundImage: `url(${coverImage})` }">
    <homepageHeader />
    <br />
    <div class="siteDescription">
      <div class="textDiv">
        <p class="homeText">From seed to table, we know every step of the way is equally important.</p>
        <p
          class="homeText"
        >Farmers are entrepreneurs by nature. But a struggling farmer can’t afford to take risks like other entrepreneurs do. His farming is limited to growing the food he needs for his own family’s survival.</p>
        <p class="homeText">
          With HarvestAssist, take control of tracking your seeding and harvesting times and receive daily notifications when it's time to act.
          Track profits and calculate waste and shrinkage by generating reports to ensure next years harvest is more profitable than the last.
        </p>
        <p
          class="homeText"
        >Harvest Assist is the platform you can depend on throughout every season.</p>
      </div>
    </div>
  </div>
</body>
</template>

<script>
import homepageHeader from "../components/homepageHeader.vue";

export default {
  name: "home",
  components: {
    homepageHeader: homepageHeader
  },

  data() {
    return {
      soil: require("../images/soil.jpg"),
      harvest: require("../images/harvest.jpg"),
      farm: require("../images/farm.jpg"),
      logo: require("../images/harvestassistlogo.png"),
      home: require("../images/home.svg"),
      user: require("../images/user.svg"),
      contact: require("../images/contact.svg"),
      coverImage: require("../images/background.jpg")
    };
  }
};
</script>
<style>
.bg-cover {
  width: 100%;
  height: 55vw;
  background-size: cover;
  background-repeat: no-repeat;
}
body {
  background-color: #e1faff;
  border: 0;
  margin: 0;
  padding: 0;
  font-family: "Roboto", sans-serif;
  width: auto;
  height: auto;
}

.mainLogo {
  width: 23%;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.logo {
  align-self: center;
  color: #fff;
  font-weight: bold;
  margin-left: 40px;
}

.main-nav {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0.8rem;
}

ul.sidebar-panel-nav {
  list-style-type: none;
}
.sidebar-panel-nav {
  display: flex;
  flex-direction: column;
  white-space: nowrap;
  font-family: "Roboto", sans-serif;
}

ul.sidebar-panel-nav > li > a {
  color: #fff;
  text-decoration: none;
  font-size: 1.5rem;
  display: block;
  padding-bottom: 0.5em;
}

.siteDescription {
  margin-left: auto;
  margin-right: auto;
  width: 60%;
  height: auto;
  background: rgba(12, 12, 12, 0.7);
  border: 2px solid #f7b254;
  border-radius: 6px;
  display: flex;
}
.homeText {
  margin-left: 10px;
  margin-right: 10px;
  color: white;
  font-family: "Roboto", sans-serif;
  font-size: 1.4rem;
}
img.sidebarImg {
  width: 13%;
  margin-right: 5px;
}
.soil {
  width: 200px;
  margin: 0;
  opacity: 80%;
  padding: 0;
}
.imgDiv {
  padding: 0;
  margin: 0;
}
.textDiv {
  padding: 0;
  margin: 0;
}
</style>