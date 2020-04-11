<template>
<body class="homepage">
 <div id="app">
   <nav class="main-nav">
     <div class="logo">
       <img class="mainLogo" :src="logo" />
     </div>
     <Burger></Burger>
   </nav>

   <Sidebar>
     <ul class="sidebar-panel-nav">
       <li><router-link :to="{ name: 'home' }" >Home</router-link></li>
       <li><router-link :to="{ name: 'login' }">Login</router-link></li>
       <li><a href="#contact">Contact</a></li>
     </ul>
   </Sidebar>
   <br>
   <div class="siteDescription">
       <p>From seed to table, we know every step of the way is equally important. </p>
           <p>With HarvestAssist, you can automate tracking your seeding and harvesting times and receive daily notifications when it's time to act.</p> 
          <p> Manage waste and shrinkage as well as harvest sales and generate reports to ensure next years harvest is more profitable than the last.</p>
         
       </div>
       </div>
</body>
</template>

<script>
import Burger from '../components/Burger.vue';
import Sidebar from '../components/Sidebar.vue';

export default {
    name: 'home',
    components: {
    Burger,
    Sidebar,
    
  },

    data() {
        return {
            seedlings: require('../images/seedlings.jpg'),
            harvest: require('../images/harvest.jpg'),
            farm: require('../images/farm.jpg'),
            logo: require('../images/harvestassistlogo.png'),
            
        }
    
    }
};
</script>
<style lang="scss">


html {
   height: 100%;
   
 }

body{
   border: 0; margin: 0; padding: 0;
   font-family: 'Lato';
   width:auto;
   height: 100%;
   background-image: url('../images/background.jpg');
        background-size:cover;
        background-repeat: no-repeat;
 }

 .mainLogo{
     width:23%;
     display: block;
     margin-left: auto;
     margin-right:auto;
 }

 .logo {
   align-self: center;
   color: #fff;
   font-weight: bold;
   font-family: 'Lato';
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
     background: rgba(0,0,0,.2);
     border: 2px solid #f7b254;
     border-radius: 6px;
 }
 .siteDescription > p {
     margin-left: 5px;
     margin-right: 5px;
     color: white;
 }

</style>