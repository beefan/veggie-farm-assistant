
<template>
<body>
  <Header></Header>
  <div class="harvestTimesContainer">
    <div class="havestTimesUpload">
      <div>
        <br>
        <email-notify></email-notify>
        
      </div>
    </div>
  </div>
  <br>
  <br>
  <Footer></Footer>
</body>
</template>


<script>
import Header from "../components/header.vue";
import Notify from "../components/notify.vue";
import Footer from "../components/footer.vue";

export default {
  components: {
    Header,
    Footer,
    "email-notify": Notify
  }
};
</script>

<style>
.dashboardWrapper {
  width: 80%;
  height: 100%;
  margin-right: auto;
  margin-left: auto;
}

.dashboardContent {
  width: 90%;
  height: 100%;
  background-color: gainsboro;
  border: 0.05rem solid #130f40;
  margin-left: auto;
  margin-right: auto;
}
</style>