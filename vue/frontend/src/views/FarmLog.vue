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
          <input type="text" id="harvestCount" placeholder="(optional)" />
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
        <input type="text" id="salesAmount" placeholder="0.00" />
      </div>
      <button v-if="cropSelected" @click="submitSale">Log Sale</button>
    </div>
    <br><br>
    </div>
    </div>

    <div class="logEntry">
      <div class="sectionHeader">Loss Log</div>
      <div class="farmLogContent">
        <br><br>
      <select id="lossCrop" name="lossCrop" @change="showLossInput($event)">
        <option value>-------- select a crop --------</option>
        <option v-for="crop in crops" :value="crop" v-bind:key="crop">{{ crop }}</option>
      </select>
      <div class="farmLogContent">
      <select id="lossType" v-if="lossCropSelected">
        <option value>-------- type of loss --------</option>
        <option value="waste">Waste</option>
        <option value="shrink">Shrink</option>
        <option value="donate">Donate</option>
        <option value="other">Other</option>
      </select>
      <div class="farmLogContent" v-if="lossCropSelected">
        <label for="lossAmount">Loss: $</label>
        <input type="text" id="lossAmount" placeholder="0.00" />

      </div>
       <div class="farmLogContent" v-if="lossCropSelected">
        <label for="weightAmount">Weight: </label>
        <input type="text" id="weightAmount" placeholder="Loss weight (lbs)" />
        
      </div>
      <button v-if="lossCropSelected" @click="submitLoss">Log Loss</button>
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
import Footer from "../components/footer.vue"

export default {
  components: { 
      Header, 
      Footer
  },
  data() {
    return {
      beds: [],
      fields: [],
      crops: [],
      selectedBed: {},
      lossCropSelected: false,
      fieldSelected: false,
      bedSelected: false,
      cropSelected: false,
      lossApiUrl: process.env.VUE_APP_REMOTE_API_LOSS,
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
      console.log(harvest)
      fetch(this.harvestApiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(harvest)
      })
        .then(response => {
          if (response.ok) {
            alert("Your harvest has been recorded.");
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    submitLoss(){
      let loss= {};
      loss.cropName = document.getElementById("lossCrop").value;
      loss.dollarAmount = document.getElementById("lossAmount").value;
      loss.weightAmount = document.getElementById("weightAmount").value;
      loss.lossType = document.getElementById("lossType").value;
      

      if (isNaN(loss.dollarAmount) || !loss.dollarAmount) {
        alert("Please enter a valid loss value.");
        return;
      }
      if (isNaN(loss.weightAmount) || !loss.weightAmount) {
        alert("Please enter a valid weight.");
        return;
      }

      console.log(loss);
      this.sendLossToJava(loss);
      this.lossCropSelected = false;
      document.getElementById("lossCrop").value = "";

    },
    sendLossToJava(loss){
      fetch(this.lossApiUrl,  {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(loss)
      })
      .then(response => {
        if (response.ok) {
            alert("Crop loss has been recorded.");
          }
        })
        .catch(err => {
          console.error(err);
      });
    },
    showLossInput(event) {
      if (event.target.value) {
        this.lossCropSelected = true;
      } else {
        this.lossCropSelected = false;
      }
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
#log {
        display: flex;
        flex-flow: column;
        width: 70%;
        flex-wrap: wrap;
        margin-left: auto;
        margin-right: auto;
    }
    
    #log>.logEntry>select {
        width: 40vw;
        margin-left: 8%;
        margin-right: 8%;
        margin-bottom: 8%;
    }
    
    div.logEntry {
        border-radius: 30px;
        display: flex;
        color: black;
        flex-direction: column;
        justify-content: center;
        width: 70%;
        height: 100%;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 30px;
        background: transparent;
        box-shadow: 20px 20px 50px rgb(180, 180, 180), -30px -30px 60px rgb(255, 255, 255);
    }
    
    div.logEntry>input {
        width: 80%;
    }
    #field {
      width:20vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .8rem;
    }
    #crop {
      width:20vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .8rem;
    }
    #log > div:nth-child(1) > div.farmLogContent > div > div > div:nth-child(1) > label {
      color:  #130f40;
      margin-right: 5px;
      margin-left:0;
      font-family: "Lato", sans-serif;
      font-size: 1rem;
      font-weight: bold;
      
    }
    #harvestWeight{
      width: 14.2vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .7rem;
    }
    
    #log > div:nth-child(1) > div.farmLogContent > div > div > div:nth-child(2) > label {
      color:  #130f40;
      margin-right: 13px;
      margin-left:0;
      font-family: "Lato", sans-serif;
      font-size: 1rem;
      font-weight: bold;
    }
    #harvestCount {
      width: 14.2vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .7rem;
    }
    #log > div:nth-child(1) > div.farmLogContent > div > div > button {
      margin-left: 10%;
      margin-right: auto;
      width: 14vw;
      color:  #130f40;
      background-color: #f7b254;
      font-family: "Lato", sans-serif;
      font-weight: bold;
      font-size: 1rem;
      border-radius: 10px;
    }

    #log > div:nth-child(1) > div.farmLogContent > div > div > button:hover{
      color:white;
    }
    #salesCrop {
      width:20vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .8rem;
    }
    #salesType{
      width:20vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .8rem;
    }
    #log > div> div.farmLogContent > div > div > label {
      color:  #130f40;
      margin-right: 8px;
      margin-left:0;
      font-family: "Lato", sans-serif;
      font-size: 1rem;
      font-weight: bold;
    }
    #salesAmount {
      width: 14.3vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .7rem;
    }
    #log > div:nth-child(2) > div.farmLogContent > div > button{
      margin-left: 10%;
      margin-right: auto;
      width: 14vw;
      color:  #130f40;
      background-color: #f7b254;
      font-family: "Lato", sans-serif;
      font-weight: bold;
      font-size: 1rem;
      border-radius: 10px;
    }
    #log > div:nth-child(2) > div.farmLogContent > div > button:hover{
      color:white;
    }
    #lossCrop {
      width:20vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .8rem;
    }
    #lossType {
      width:20vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .8rem;
    }
    #lossAmount {
      width: 14.8vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .7rem;

    }
    #weightAmount {
      width: 13.7vw;
      margin-bottom: 15px;
      font-family: "Lato", sans-serif;
      font-size: .7rem;
    }
    #log > div:nth-child(3) > div.farmLogContent > div > button{
      margin-left: 10%;
      margin-right: auto;
      width: 14vw;
      color:  #130f40;
      background-color: #f7b254;
      font-family: "Lato", sans-serif;
      font-weight: bold;
      font-size: 1rem;
      border-radius: 10px;
    }
    #log > div:nth-child(3) > div.farmLogContent > div > button:hover{
      color:white;
    }
    
</style>