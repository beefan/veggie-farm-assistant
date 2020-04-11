<template>
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
   <div class="registerWrapper">
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h4 class="h3 mb-3 font-weight-normal">Create New Account</h4>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        There were problems registering this user.
      </div>
      <label for="username" class="sr-only">Username:   </label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <br>
      <label for="password" class="sr-only">Password:   </label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <br>
      <label for="confirmPassword" class="sr-only">Confirm Password:   </label>
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      /><br>
      <button class="btn createBtn" type="submit">
        Create New Account
      </button>
      <br>
      <router-link :to="{ name: 'login' }" class="registered">
        Already Have an Account?
      </router-link>
      <br>
      <br>
    </form>
    </div>
  </div>
  </div>
</template>

<script>
import Burger from '../components/Burger.vue';
import Sidebar from '../components/Sidebar.vue';

export default {
  name: 'register',
  components: {
    Burger,
   Sidebar,
  },
  data() {
    return {
      logo: require('../images/harvestassistlogo.png'),
      user: {  
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
    };
  },
  methods: {
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            this.$router.push({ path: '/login', query: { registration: 'success' } });
          } else {
            this.registrationErrors = true;
          }
        })

        .then((err) => console.error(err));
    },
  },
};
</script>

<style>
.registerWrapper{
     margin-right:auto;
     margin-left:auto;
     background: rgba(12,12,12,.6);
     border: 2px solid #f7b254;
     border-radius: 6px;
     width: 30vw;
     height:auto;
     display:flex;
     flex-direction:column;
     justify-content:center;
     align-items: center;
     align-content:center;
    }

    #username{
      margin-left: 9px;
      margin-bottom: 5px;
      width:14vw;
      height:1.3vw;
    }
    #password{
      margin-left: 9px;
      margin-bottom: 5px;
      width:14vw;
      height:1.3vw;
    }
    #confirmPassword{
      margin-left: 9px;
      margin-bottom: 10px;
      width:14vw;
      height:1.3vw;

    }
    label {
      color: white;
      margin-left: 15%;
      font-family: 'Muli', sans-serif;
    }
    h4{
      color: #f7b254;
      font-size: 2rem;
      text-align: center;
      font-family: 'Muli', sans-serif;
      margin:10px;
      padding:0;
    }
    button.btn.createBtn{
      margin-left: auto;
      margin-right:auto;
      height: 2rem;
      width: 6rem;
      background: #f7b254;
      border-radius: 6px;
      border: .05rem solid black;
      color: black;
      font-weight: bolder;
      padding: 0;
      cursor: pointer;
      font-family: 'Muli', sans-serif;
    }
    a.registered{
      display:flex;
      color:white;
      text-decoration: none;
      font-family: 'Muli', sans-serif;
      justify-self:center;

    }
    
      
  
    @import url('https://fonts.googleapis.com/css2?family=Muli:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
</style>
