<template>
  <div>
    <div class="sectionHeader">HarvestAssist Action Items</div>
    <div id="dailies">
      <div class="notify" id="planting" v-if="this.dailies.planting.length > 0">
        <h2 class="sectionHeaderNotify">Planting</h2>
        <ul>
          <li v-for="item in this.dailies.planting" v-bind:key="item">{{item}}</li>
        </ul>
        <br>
      </div>
      <div class="notify" id="transplant" v-if="this.dailies.transplant.length > 0">
        <h2 class="sectionHeaderNotify">Transplants</h2>
        <ul>
          <li v-for="item in this.dailies.transplant" v-bind:key="item">{{item}}</li>
        </ul>
        <br>
      </div>
      <div class="notify" id="harvest" v-if="this.dailies.harvest.length > 0">
        <h2 class="sectionHeaderNotify">Harvests</h2>
        <ul>
          <li v-for="item in this.dailies.harvest" v-bind:key="item">{{item}}</li>
        </ul>
        
      </div>
      <div class="notify" id="expiration" v-if="this.dailies.expiration.length > 0">
        <h2 class="sectionHeaderNotify">Expirations</h2>
        <ul>
          <li v-for="item in this.dailies.expiration" v-bind:key="item">{{item}}</li>
        </ul>
        <br>
      </div>
    </div>
    <div class="email">
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

<style>
#dailies{
  display:flex;
  flex-direction: row;
  flex-wrap:wrap;
  justify-content: center;
}

.notify ul{
  list-style:none;
  font-size: 1rem;
  font-family: 'Lato', sans-serif;
  margin-bottom: 5px;
  
}
.notify {
  width:40%;
  height:auto;
  font-family: 'Lato', sans-serif;
        font-size: 1.5vw;
        background: transparent;
        border-radius:30px;
        box-shadow: 3px 3px 5px rgb(180, 180, 180), -5px -5px 10px rgb(255, 255, 255);
        margin: 10px;
        color: #130f40;
        align-self: left;
        
}

.sectionHeaderNotify {
  
        background-color: #f7b254;
        width: 100%;
        
        color: #130f40;
        text-align: center;
        font-size: 2rem;
        font-family: 'Lato', sans-serif;
        margin: 0;
        height: auto;
        border-radius: 30px 30px 0px 0px;
    
}
.email label {
  color:#130f40;
}
.email{
  text-align: right;
  color:#130f40;
  margin-right: 10%;

}
</style>

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

