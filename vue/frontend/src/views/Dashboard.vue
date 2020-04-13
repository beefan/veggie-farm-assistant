
<template>  
<body>
  <Header></Header>
    <section>
<div>
  <email-notify></email-notify>
</div>
</section>
<Footer></Footer>
</body>

</template>


<script>
import Header from '../components/header.vue'
import Notify from "../components/notify.vue"
import Footer from "../components/footer.vue"

export default {
    components: { 
      Header, 
      Footer,
      'email-notify': Notify
  }
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