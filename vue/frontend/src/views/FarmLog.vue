<template>
<body>
  <Header></Header>
  <div id="log">
    <div class="logEntry">
      <h1>Log Harvest</h1>
      <select id="field" name="fields" @change="showCrops($event)">
        <option value>-------- select a field --------</option>
        <option
          v-for="field in fields"
          :value="field.id"
          v-bind:key="field.id"
        >{{ field.name }}</option>
      </select>
      <select v-if="fieldSelected" id="crop" name="crops">
        <option value>-------- select a bed/crop --------</option>
        <option
          v-for="bed in beds"
          :value="bed.cropName"
          v-bind:key="bed.bedId"
        >{{ bed.cropName }}</option>
      </select>
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
      cropApiUrl: process.env.VUE_APP_REMOTE_API_CROP
    };
  },
  methods: {
    showCrops(event) {
        let fieldId = event.target.value;
        console.error(fieldId)
        if (fieldId != "") {
            this.fieldSelected = true;
        }else if (this.fieldSelected){
            this.fieldSelected = false;
            fieldId = "";
        }
      console.log(this.fields.filter(x=>x.id=fieldId))
      this.beds = this.fields.filter( field => {
          return field.id = fieldId
      })[0].beds;

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