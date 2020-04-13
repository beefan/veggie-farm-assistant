<template>
  <div>
    <div class="sectionHeader">Harvest Assist Action Items</div>
    <div id="dailies">
      <div id="planting" v-if="this.dailies.planting.length > 0">
        <h2>Planting</h2>
        <ul>
          <li v-for="item in this.dailies.planting" v-bind:key="item">{{item}}</li>
        </ul>
      </div>
      <div id="transplant" v-if="this.dailies.transplant.length > 0">
        <h2>Transplants</h2>
        <ul>
          <li v-for="item in this.dailies.transplant" v-bind:key="item">{{item}}</li>
        </ul>
      </div>
      <div id="harvest" v-if="this.dailies.harvest.length > 0">
        <h2>Harvests</h2>
        <ul>
          <li v-for="item in this.dailies.harvest" v-bind:key="item">{{item}}</li>
        </ul>
      </div>
      <div id="expiration" v-if="this.dailies.expiration.length > 0">
        <h2>Expirations</h2>
        <ul>
          <li v-for="item in this.dailies.expiration" v-bind:key="item">{{item}}</li>
        </ul>
      </div>
    </div>
    <div>
      <label for="submitEmailRegistration">Check to receive daily email notifications</label>
      <input
        @click="registerForNotifications"
        v-model="checked"
        type="checkbox"
        id="submitEmailRegistration"
      />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      demoEmail: "noahwalcher@gmail.com",
      notifyApi: process.env.VUE_APP_REMOTE_API_NOTIFY,
      checked: false,
      dailies: ""
    };
  },
  created() {
    this.getCheckedFromJava();
    this.getDailies();
  },
  methods: {
    makePretty(message) {
      let messageArray = message.split("\n\n");
      let dailiesJson = {};

      dailiesJson.planting = messageArray[1].split("-> ");
      dailiesJson.planting.shift();

      dailiesJson.transplant = messageArray[2].split("-> ");
      dailiesJson.transplant.shift();

      dailiesJson.harvest = messageArray[3].split("-> ");
      dailiesJson.harvest.shift();

      dailiesJson.expiration = messageArray[4].split("-> ");
      dailiesJson.expiration.shift();

      console.log(dailiesJson);

      return dailiesJson;
    },
    getDailies() {
      fetch(this.notifyApi)
        .then(response => {
          return response.text();
        })
        .then(data => {
          this.dailies = this.makePretty(data);
        })
        .catch(err => {
          console.error(err);
        });
    },
    getCheckedFromJava() {
      fetch(this.notifyApi + "/isRegistered")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.checked = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    registerForNotifications() {
      fetch(this.notifyApi, {
        method: "POST",
        body: this.demoEmail
      })
        .then(response => {
          if (response.ok) {
            if (this.checked) {
              alert(
                "You have successfully registered " +
                  this.demoEmail +
                  " for notifications!"
              );
            }
          } else {
            alert(
              "There was an error registering your email. \nIf this continues, please call support"
            );
          }
        })
        .catch(err => {
          console.error(err);
        });
    }
  }
};
</script>

<style>
/* #emailNotify {
  background-color: #66a3c7;
  padding: 2%;
  width: 80%;
  text-align: center;
  font-family: "Muli", sans-serif;
  border-radius: 10px;
  border: 3px solid white;
  box-shadow: 2px 2px 5px #588aa7 inset, -2px -2px 5px #85cbf3 inset;
} */
/* #emailRegister {
  width: 95%;
} */

#submitEmailRegistration:hover {
  box-shadow: 1px 1px 3px #588aa7 inset, -1px -1px 3px #85cbf3 inset;
}
</style>