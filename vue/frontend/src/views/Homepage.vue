<template>
<body>
    <section class="headerMain">
    </section>
<section class="bodyMain">
<carousel
 :paginationEnabled=true
 :per-page="1">
  <slide>
    Slide 1 Content
  </slide>
  <slide>
    Slide 2 Content
  </slide>
</carousel>
</section>


</body>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel';
export default {
  components: {
    Carousel,
    Slide
  },
    name: 'home',
    data() {
        return {
            seedlings: require('../images/seedlings.jpg'),
            harvest: require('../images/harvest.jpg'),
            farm: require('../images/farm.jpg')
        }
    
    }
};
</script>
<style scoped>

</style>

