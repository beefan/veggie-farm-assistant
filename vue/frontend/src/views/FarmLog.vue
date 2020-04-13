<template>
<body>
  <Header></Header>
  <br />
  <br />
  <div id="log">
    <div class="logEntry">
      <div class="sectionHeader">Harvest Log</div>
      <div class="farmLogContent">
        <br><br>
        <select id="field" name="fields" @change="showCrops($event)">
          <option value>-------- select a field --------</option>
          <option v-for="field in fields" :value="field.id" v-bind:key="field.id">{{ field.name }}</option>
        </select>
        <div class="farmLogContent">
        <select v-if="fieldSelected && beds" id="crop" name="crops" @change="showInput($event)">
          <option value>-------- select a bed/crop --------</option>
          <option
            v-for="bed in beds"
            :value="beds.indexOf(bed)"
            v-bind:key="bed.bedId"
          >{{ '(' + String(beds.indexOf(bed) + 1) + ') ' + bed.cropName }}</option>
        </select>
        <div class="farmLogContent">
        <div class="farmLogContent" v-if="bedSelected">
          <label for="harvestWeight">Weight:</label>
          <input type="text" id="harvestWeight" placeholder="harvest weight (lbs)" />
        </div>
        <div class="farmLogContent" v-if="bedSelected">
          <label for="harvestCount">Count:</label>
          <input type="text" id="harvestCount" placeholder="          (optional)" />
        </div>
        <button v-if="bedSelected" @click="submitHarvest">Log Harvest</button>
      </div>
      <br><br>
    </div>
    </div>
    </div>

    <div class="logEntry">
      <div class="sectionHeader">Sales Log</div>
      <div class="farmLogContent">
        <br><br>
      <select id="salesCrop" name="salesCrop" @change="showSalesInput($event)">
        <option value>-------- select a crop --------</option>
        <option v-for="crop in crops" :value="crop" v-bind:key="crop">{{ crop }}</option>
      </select>
      <div class="farmLogContent">
      <select id="salesType" v-if="cropSelected">
        <option value>-------- type of sale --------</option>
        <option value="market">Farmer's Market</option>
        <option value="wholesale">Wholesale</option>
        <option value="csa">CSA</option>
        <option value="other">Other</option>
      </select>
      <div class="farmLogContent" v-if="cropSelected">
        <label for="salesAmount">Sales: $</label>
        <input type="text" id="salesAmount" placeholder="12.39" />
      </div>
      <button v-if="cropSelected" @click="submitSale">Log Sale</button>
    </div>
    <br><br>
    </div>
    </div>
    
  </div>
  <br><br>
  <Footer></Footer>
</body>
</template>

<script>
import Header from "../components/header.vue";
import Footer from "../components/footer.vue";
export default {
  components: { Header: Header, Footer: Footer },
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
      fetch(this.harvestApiUrl + "/sale", {
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
.farmLogContent{
  margin-left:auto;
  margin-right:auto;
}
</style>