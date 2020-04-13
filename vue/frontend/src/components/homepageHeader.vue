<template>
 <div>
   <nav class="main-nav">
     <div class="logo">
       <img class="mainLogo" :src="logo" />
     </div>
     <Burger></Burger>
   </nav>
   <Sidebar>
     <ul class="sidebar-panel-nav">
       <li><router-link :to="{ name: 'home' }" ><img class="sidebarImg" :src="home" />Home</router-link></li>
       <li><router-link :to="{ name: 'login' }"><img class="sidebarImg" :src="user" />Login</router-link></li>
       <li><router-link :to="{ name: 'contact' }"><img class="sidebarImg" :src="contact" />Contact</router-link></li>
     </ul>
   </Sidebar>
   </div>
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
            logo: require('../images/harvestassistlogo.png'),
            home: require('../images/home.svg'),
            user: require('../images/user.svg'),
            contact: require('../images/contact.svg'),
        }
    
    }
};
</script>

<style>
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
  display:flex;
  flex-direction: column;
  white-space:nowrap;
  font-family: 'Roboto', sans-serif;
  
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
     background: rgba(12,12,12,.6);
     border: 2px solid #f7b254;
     border-radius: 6px;
     
 }
 .siteDescription > p {
     margin-left: 10px;
     margin-right: 10px;
     color: white;
     font-family: 'Roboto', sans-serif;
 }
img.sidebarImg{
  width:13%;
  margin-right: 5px;
 
  
}


    @import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,400;0,700;1,400&display=swap');
</style>