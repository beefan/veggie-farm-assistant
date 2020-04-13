
<template>  
<body class="dashboard">
  <section>
  <Header></Header>
  </section>
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
