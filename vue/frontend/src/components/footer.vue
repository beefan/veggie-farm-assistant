<template>
<div>
    <img class="footerLogo" :src="logoUrl"/>
    <br>
    <br>
</div>
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
.footerLogo {
  width: 10%;
  display: block;
  margin:auto;
}
</style>