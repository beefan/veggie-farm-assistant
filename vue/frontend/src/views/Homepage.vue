<template>
<body>
<div class="headerMain">
<div id="app">
   <nav class="main-nav">
     <div class="logo">
       <router-link :to="{ name: 'home' }"><img class="mainLogo" :src="logo" /></router-link>
     </div>
     <Burger></Burger>
   </nav>
   <Sidebar>
     <ul class="sidebar-panel-nav">
       <li><router-link :to="{ name: 'home' }">Home</router-link></li>
       <li><router-link :to="{ name: 'login' }">Login</router-link></li>
       <li><a href="#contact">Contact</a></li>
     </ul>
   </Sidebar>
   </div>
</div>
<br><br>
<section class="bodyMain">
   
    <div class="imgSlider">
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
</div>
</section> 

<br><br><br>
</body>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel';
import Burger from '../components/Burger.vue';
import Sidebar from '../components/Sidebar.vue';

export default {
    name: 'home',
    components: {
    Carousel,
    Slide,
    Burger,
    Sidebar,
    
  },

    data() {
        return {
            seedlings: require('../images/seedlings.jpg'),
            harvest: require('../images/harvest.jpg'),
            farm: require('../images/farm.jpg'),
            logo: require('../images/harvestassistlogo.png')
        }
    
    }
};
</script>
