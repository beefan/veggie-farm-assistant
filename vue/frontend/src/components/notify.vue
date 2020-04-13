<template>
  <div id="emailNotify">
      <div v-if="!registered">
      <p>Enter your email to register for Harvest Assist Daily Notifcations</p>
      <input id="emailRegister" placeholder="email@harvestassistapp.com"/>
      <button id="submitEmailRegistration" @click="registerForNotifications">Register for Daily Notifcations!</button>
      </div>
  </div>
</template>

<script>
export default {
    data () {
        return {
        notifyApi: process.env.VUE_APP_REMOTE_API_NOTIFY,
        registered: false
        }
    },
    methods: {
        registerForNotifications() {

            let email = document.getElementById('emailRegister').value;

            if (!email || !email.indexOf('@')) {
                alert('Please enter a valid email to register!');
                return;
            }
            
            fetch(this.notifyApi, {
        method: "POST",
        body: email
      })
        .then(response => {
          if (response.ok) {
              this.registered = true;
              alert('You have successfully ' + email + ' for notifications!');
            }else {
                alert('There was an error registering your email. \nIf this continues, please call support');
            }
        })
        .catch(err => {
          console.error(err);
        });
    }
        }
    }
</script>

<style>
#emailNotify {
    background-color: #66a3c7;
    padding: 2%;
    width: 20%;
    text-align: center;
    font-family: 'Muli', sans-serif;
    border-radius: 10px;
    border: 3px solid white;
    box-shadow: 2px 2px 5px #588aa7 inset, -2px -2px 5px #85cbf3 inset;
}
#emailRegister {
    width: 95%;
}
#submitEmailRegistration{
    margin-top: 6%;
    width: 70%;
    border:none;
    padding: 2%;
    box-shadow: 2px 2px 5px #588aa7, -2px -2px 5px #85cbf3;
}
#submitEmailRegistration:hover {
    box-shadow: 1px 1px 3px #588aa7 inset, -1px -1px 3px #85cbf3 inset;
}
</style>