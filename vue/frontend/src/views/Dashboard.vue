
<template>  
<body>
  <Header></Header>
  <div class="dashboardWrapper">
    <br>
    <div class="dashboardContent">
      Welcome Registered User!
      This is your dashboard.
    </div>
  </div>
  <Footer></Footer>
  </body>
</template>


<script>
import Header from '../components/header.vue';
import Footer from "../components/footer.vue";

export default {
  components: {Header: Header, Footer: Footer,}
}

</script>

<style>
.dashboardWrapper{
  width: 80%;
  height: 100%;
  margin-right:auto;
  margin-left:auto;
}

.dashboardContent{
  width:90%;
  height: 100%;
  background-color:gainsboro;
  border:.05rem solid #130f40;
  margin-left:auto;
  margin-right: auto;
}

</style>