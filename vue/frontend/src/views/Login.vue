<template>
  <div
    class="min-h-screen bg-grey bg-cover flex items-end block md:fixed w-full md:w-1/2 shadow-md"
    :style="{ backgroundImage: `url(${coverImage})` }"
  >
    <homepageHeader />
    <br />
    <br />
    <div class="loginWrapper">
      <div id="login">
        <form class="form-signin" @submit.prevent="login">
          <div
            class="alert alert-danger"
            role="alert"
            v-if="invalidCredentials"
          >Invalid username and password!</div>
          <div
            class="alert alert-success"
            role="alert"
            v-if="this.$route.query.registration"
          >Thank you for registering, please sign in.</div>

          <label for="username" class="sr-only">Username</label>
          <input
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
          <br />
          <label for="password" class="sr-only">Password</label>
          <input
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            v-model="user.password"
            required
          />
          <br />
          <router-link class="login" :to="{ name: 'dashboard' }">
            <button class="btn createBtn" type="submit">Sign in</button>
          </router-link>
          <br />
          <router-link class="register" :to="{ name: 'register' }">Need an account?</router-link>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import auth from "../auth";

import homepageHeader from "../components/homepageHeader.vue";

export default {
  name: "login",
  
  components: {
    homepageHeader: homepageHeader,
  },
  data() {
    return {
      coverImage: require("../images/background.jpg"),

      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    noprevention() {
      return true;
    },
    login() {
      fetch(process.env.VUE_APP_REMOTE_API_LOGIN, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.user)
      })
        .then(response => {
          if (response.ok) {
            return response.text();
          } else {
            this.invalidCredentials = true;
          }
        })
        .then(token => {
          if (token != undefined) {
            if (token.includes('"')) {
              token = token.replace(/"/g, "");
            }
            auth.saveToken(token);
            this.$router.push("/");
          }
        })
        .catch(err => console.error(err));
    }
  }
};
</script>

<style>
#login > form > a {
  text-decoration: none;
  font-size: 1.1rem;
  color: white;
  font-family: "Muli", sans-serif;
}
label {
  color: white;
  font-family: "Muli", sans-serif;
}

#login {
  text-align: center;
  margin: 0;
  padding: 0;
  font-size: 1.1rem;
  color: white;
  font-family: "Baloo Paaji 2", cursive;
  align-items: center;
}
form.form-signin {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  align-content: center;
  width: 100%;
  height: 100%;
}

.loginWrapper {
  display: flex;
  margin: auto;
  background: rgba(12, 12, 12, 0.7);
  border: 2px solid #f7b254;
  border-radius: 6px;
  width: 16vw;
  height: 35vh;
}
.sr-only {
  margin-left: auto;
  margin-right: auto;
  font-family: "Muli", sans-serif;
}
label {
  color: white;
}
#username {
  width: 14vw;
  height: 1.2vw;
  font-family: "Baloo Paaji 2", cursive;
}
#password {
  width: 14vw;
  height: 1.2vw;
  font-family: "Baloo Paaji 2", cursive;
}
loginBtn {
  margin-left: auto;
  margin-right: auto;
  height: 2rem;
  width: 6rem;
  background: #f7b254;
  border-radius: 6px;
  border: 0.05rem solid black;
  color: black;
  font-weight: bolder;
  padding: 0;
  cursor: pointer;
  font-family: "Baloo Paaji 2", cursive;
}

@import url("https://fonts.googleapis.com/css2?family=Muli:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
</style>

