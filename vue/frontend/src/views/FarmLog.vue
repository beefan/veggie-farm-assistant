<template>
<body>
  <Header></Header>
  <div id="log">
    <div class="logEntry">
      <h1>Log Harvest</h1>
      <select id="field" name="fields" @change="showCrops($event)">
        <option value="">-------- select a field --------</option>
        <option
          v-for="field in fields"
          :value="field.id"
          v-bind:key="field.id"
        >{{ field.name }}</option>
      </select>
      <select v-if="fieldSelected" id="crop" name="crops" @change="showInput($event)">
        <option value>-------- select a bed/crop --------</option>
        <option
          v-for="bed in beds"
          :value="bed"
          v-bind:key="bed.bedId"
        >{{ '(' + String(beds.indexOf(bed) + 1) + ')  ' + bed.cropName }}</option>
      </select>
      <div v-if="cropSelected">
    <label for="harvestWeight" >Weight: </label>
      <input type="text" id="harvestWeight" placeholder="harvest weight (lbs)"/>
      </div>
      <div v-if="cropSelected">
          <label for="harvestCount">Count: </label>
      <input type="text" id="harvestCount" placeholder="          (optional)" />
      </div>
      <button v-if="cropSelected" @click="submitHarvest" >Submit</button>
    </div>
    <div class="logEntry">
      <h1>Log Sales</h1>
    </div>
  </div>
</body>
</template>

<script>
import Header from "../components/header.vue";
export default {
  components: { Header: Header },
  data() {
    return {
      beds: [],
      fields: [],
      fieldSelected: false,
      cropSelected: false,
      cropApiUrl: process.env.VUE_APP_REMOTE_API_CROP
    };
  },
  methods: {
    showCrops(event) {
        let fieldSelection = event.target.value;
        if (fieldSelection != "") {
            this.fieldSelected = true;
        }else {
            this.fieldSelected = false;
            fieldSelection = "";
        }
      this.beds = this.fields.filter( field => {
          return field.id === Number(fieldSelection)
      })[0].beds;
    },
    showInput(event) {
        let bed = event.target.value;
        console.log(bed)
        if (bed) {
            this.cropSelected = true;
        }else {
            this.cropSelected = "";
        }
    },
    getFields() {
      fetch(this.cropApiUrl + "/user")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.fields = data;
          console.log(data);
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    this.getFields();
  }
};
</script>

<style>
</style>