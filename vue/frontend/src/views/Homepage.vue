<template>
<body>
    <section class="headerMain">
    </section>
<section class="bodyMain">
<carousel :perPage="[1]" :autoplay="[true]" :centerMode="[true]" :autoplayHoverPause="[true]" :autoplayTimeout="[6000]" :paginationColor="[f7b254]" :loop="[true]">
  <slide>
    <img class="mainImages" :src= "seedlings"/>
    
  </slide>
  <slide>
    <img class="mainImages" :src= "harvest"/>
  </slide>
  <slide>
      <img class="mainImages" :src= "farm"/>
      </slide>
</carousel>
</section> 


</body>
</template>

<script>
  import { Carousel, Slide } from 'vue-carousel';
export default {
    name: 'home',
    
    components: {
    Carousel,
    Slide
  },

    data() {
        return {
            seedlings: require('../images/seedlings.jpg'),
            harvest: require('../images/harvest.jpg'),
            farm: require('../images/farm.jpg')
        }
    
    }
}
</script>

<style scoped>
div#app{
    visibility: hidden;
}
</style>