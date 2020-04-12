<template>
<body>
  <Header></Header>
  <!-- <email-notify></email-notify> -->
  <div id="log">
    <div class="logEntry">
      <h1>Log Harvest</h1>
      <select id="field" name="fields" @change="showCrops($event)">
        <option value>-------- select a field --------</option>
        <option v-for="field in fields" :value="field.id" v-bind:key="field.id">{{ field.name }}</option>
      </select>
      <select v-if="fieldSelected && beds" id="crop" name="crops" @change="showInput($event)">
        <option value>-------- select a bed/crop --------</option>
        <option
          v-for="bed in beds"
          :value="beds.indexOf(bed)"
          v-bind:key="bed.bedId"
        >{{ '(' + String(beds.indexOf(bed) + 1) + ') ' + bed.cropName }}</option>
      </select>
      <div v-if="bedSelected">
        <label for="harvestWeight">Weight:</label>
        <input type="text" id="harvestWeight" placeholder="harvest weight (lbs)" />
      </div>
      <div v-if="bedSelected">
        <label for="harvestCount">Count:</label>
        <input type="text" id="harvestCount" placeholder="          (optional)" />
      </div>
      <button v-if="bedSelected" @click="submitHarvest">Log Harvest</button>
    </div>
    <div class="logEntry">
      <h1>Log Sales</h1>
      <select id="salesCrop" name="salesCrop" @change="showSalesInput($event)">
        <option value>-------- select a crop --------</option>
        <option v-for="crop in crops" :value="crop" v-bind:key="crop">{{ crop }}</option>
      </select>
      <select id="salesType" v-if="cropSelected">
        <option value>-------- type of sale --------</option>
        <option value="market">Farmer's Market</option>
        <option value="wholesale">Wholesale</option>
        <option value="csa">CSA</option>
        <option value="other">Other</option>
      </select>
      <div v-if="cropSelected">
        <label for="salesAmount">Sales: $</label>
        <input type="text" id="salesAmount" placeholder="12.39" />
      </div>
      <button v-if="cropSelected" @click="submitSale">Log Sale</button>
    </div>
  </div>
</body>
</template>

<script>
import Header from "../components/header.vue";
// import Notify from "../components/notify.vue"
export default {
  components: { 
      Header, 
//   'email-notify':Notify
  },
  data() {
    return {
      beds: [],
      fields: [],
      crops: [],
      selectedBed: {},
      fieldSelected: false,
      bedSelected: false,
      cropSelected: false,
      cropApiUrl: process.env.VUE_APP_REMOTE_API_CROP,
      harvestApiUrl: process.env.VUE_APP_REMOTE_API_HARVEST
    };
  },
  methods: {
    submitHarvest() {
      let harvest = {};
      harvest.cropName = this.selectedBed.cropName;
      harvest.bedId = this.selectedBed.bedId;
      harvest.cropWeight = document.getElementById("harvestWeight").value;
      harvest.cropCount = document.getElementById("harvestCount").value;

      if (
        isNaN(harvest.cropWeight) ||
        (harvest.cropCount && isNaN(harvest.cropCount))
      ) {
        alert("Please enter a number.");
        return;
      }
      if (!harvest.cropWeight) {
        alert("You must enter a crop weight.");
        return;
      }

      this.sendHarvestToJava(harvest);

      this.fieldSelected = false;
      this.bedSelected = false;
      document.getElementById("field").value = "";
    },
    sendHarvestToJava(harvest) {
      fetch(this.harvestApiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(harvest)
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
            alert("Your harvest has been recorded.");
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    submitSale() {
      let sale = {};
      sale.cropName = document.getElementById("salesCrop").value;
      sale.dollarAmount = document.getElementById("salesAmount").value;
      sale.saleType = document.getElementById("salesType").value;

      if (isNaN(sale.dollarAmount) || !sale.dollarAmount) {
        alert("Please enter a valid sale value.");
        return;
      }

      this.sendSaleToJava(sale);
      this.cropSelected = false;
      document.getElementById("salesCrop").value = "";
    },
    sendSaleToJava(sale) {
      fetch(this.harvestApiUrl + '/sale', {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(sale)
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
            alert("Your sale has been recorded.");
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    showCrops(event) {
      let fieldSelection = event.target.value;
      if (fieldSelection != "") {
        this.fieldSelected = true;
        this.beds = this.fields.filter(field => {
          return field.id === Number(fieldSelection);
        })[0].beds;
      } else {
        this.fieldSelected = false;
        this.bedSelected = false;
        fieldSelection = "";
      }
    },
    showSalesInput(event) {
      if (event.target.value) {
        this.cropSelected = true;
      } else {
        this.cropSelected = false;
      }
    },
    showInput(event) {
      let bed = event.target.value;
      if (bed) {
        this.bedSelected = true;
      } else {
        this.bedSelected = "";
      }
      this.selectedBed = this.beds[bed];
    },
    getFields() {
      fetch(this.cropApiUrl + "/user")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.fields = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    getCrops() {
      fetch(this.cropApiUrl + "/crops")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.crops = data;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    this.getFields();
    this.getCrops();
  }
};
</script>

<style>
</style>