<template>
<body class="cropPlanner">
  <Header></Header>
  <section class="newFieldContainer">
    <div class="newFieldCreate">
      <h2>Add New Field:  </h2>
      <input class="input btn" type="text" value="Enter New Field Name" />
      <button class="addNewField btn" @click="addField($event)">Create</button>
      
    </div>
    </section>


<section class="fields">

<div class ="fieldsWrapper">
    <div class="fieldLoaded" v-for="field in fields" v-bind:key="field['id']">
      <div class="fieldName">
      
      <input class="fieldNamebtn" type="text" :value="field['name']" @change="updateField($event, field)"/>
      <img :src="trashUrl" @click="deleteField(field.id);" />
      </div>
      

      <table class="addNewCropFieldTable">
        <thead>
          <tr>
            <th>Add New Bed</th>
            <th>Transplant Date</th>
            <th>Planting Date</th>
            <th>Submit</th>
          </tr>
        </thead>
        <tr :class="'newCrop ' + field['id']">
          <td>
            <input class="fieldTable colName" type="text" value="Crop Name" />
          </td>
          <td>
            <input class="fieldTable td" type="date" value="Transplant Date" />
          </td>
          <td>
            <input class="fieldTable pd" type="date" value="Planting Date" />
          </td>
          <td class="submit">
            <a href="cropplanner">
              <img :src="submitUrl" @click="addNewEntry($event);" />
            </a>
          </td>
        </tr>
      </table>

      <br>

      <table class="addedCropsFieldTable">
        <thead>
          <tr>
            <th>Crop Name</th>
            <th>Transplant Date</th>
            <th>Planting Date</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tr v-for="st in field.beds" v-bind:key="st['bedId']">
          <td>
            <input
              class="fieldTable colName"
              type="text"
              :value="st['cropName']"
              @change="updateBed($event, st)"
              @click="highLightRow($event)"
              @blur="unhighlight($event)"
            />
          </td>
          <td>
            
            <input
              class="fieldTable td"
              type="date"
              :value="getDateFromJSON(st['transplantDate'])"
              @blur="updateBed($event, st); unhighlight($event)"
              @click="highLightRow($event)"
        
            />
          </td>
          <td>
            <input
              class="fieldTable pd"
              type="date"
              :value="getDateFromJSON(st['plantingDate'])"
              @blur="updateBed($event, st); unhighlight($event)"
              @click="highLightRow($event)"
             
            />
          </td>
          <td class="trash">
            <a href="cropplanner">
              <img :src="trashUrl" @click="deleteEntry(st['bedId']);" />
            </a>
          </td>
        </tr>
        
      </table>
      <br>
      <div class ="csvUpload">
      <p>Upload CSV:</p>
      <upload
        :parentData="field['id']"
        :verifyUploadFormat="uploadVerify"
         title
        @uploadSuccess="onUploadSuccess($event)"
        :uploadDocument="uploadBeds"
      ></upload>
      </div>
      <br>
    </div>
    <br><br>
    </div>
  </section>
  <br>
  </body>
  
</template>

<script>
import Upload from "../components/csvUpload.vue";
import Header from "../components/header.vue"
export default {
  components: { Upload: Upload, Header: Header },

  data() {
    return {
      fields: [],
      fieldsLoaded: false,
      parse_csv: [],
      apiUrl: process.env.VUE_APP_REMOTE_API_CROP,
      parse_header: [],
      trashUrl: require("../images/trash.png"),
      submitUrl: require("../images/submit.png")
    };
  },
  methods: {
    uploadVerify() {
      console.log(this.parse_header), console.log(this.parse_csv);
      let vm = this;
      if (
        vm.parse_header[0] != "cropName" ||
        vm.parse_header[1] != "transplantDate" ||
        vm.parse_header[2] != "plantingDate"
      ) {
        return false;
      }

      for (let item of this.parse_csv) {
        console.log(item["cropName"]);

        if (
          !item["cropName"].match(/[a-z]/i) 
          || !item["transplantDate"].match(
            /^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$/i
          ) ||
          !item["plantingDate"].match(
            /^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$/i
          )
        ) {
          return false;
        }
      }

      return true;
    },
    deleteEntry(bedId) {
      fetch(this.apiUrl + '/beds/' + bedId, {
        method: "delete"
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    deleteField(fieldId) {
      let randomname = confirm("Are you sure you want to delete this field?");
      if (!randomname) {
        return;
      }
      fetch(this.apiUrl + '/' + fieldId, {
        method: "delete"
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
            this.getFields();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    uploadBeds(fieldId) {

      console.log(fieldId + '<---------')
      this.parse_csv.forEach(bed => {
        bed['fieldId'] = fieldId;
      })
      console.log(this.parse_csv + 'Its this one')
      fetch(this.apiUrl + '/beds', {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.parse_csv)
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
            alert("Your upload was successful.");
            this.getFields();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },

    onUploadSuccess(on) {
      this.parse_header = on.header;
      this.parse_csv = on.csv;
    },
    highLightRow(e) {
      e.target.parentNode.classList.add("highlight");
    },

    unhighlight(e) {
      e.target.parentNode.classList.remove("highlight");
    },
    getFields() {
      fetch(this.apiUrl + "/user")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.fields = data;
          this.fieldsLoaded = true;
          console.log(data);
        })
        .catch(err => {
          console.error(err);
        });
    },
    addNewEntry(e) {
      let newEntry = {};
      let parentNode = e.target.parentNode.parentNode.parentNode;

      let x = Array.from(parentNode.children);
      newEntry["cropName"] = x[0].firstChild.value;
      newEntry["transplantDate"] = x[1].firstChild.value;
      newEntry["plantingDate"] = x[2].firstChild.value;
      newEntry["fieldId"] = parentNode.classList[1];

      console.log(newEntry["fieldId"]);

      this.updateDatabase(e, newEntry);
    },
    updateBed(e, st) {
      if (e.target.classList[1] === "colName") {
        st["cropName"] = e.target.value;
      } else if (e.target.classList[1] === "td") {
        st["transplantDate"] = e.target.value;
      } else if (e.target.classList[1] === "pd") {
        st["plantingDate"] = e.target.value;
      }
      st.fieldId = e.target.parentNode.parentNode.parentNode.previousSibling.previousSibling.children[1].classList[1];
      st.transplantDate = this.getDateFromJSON(st.transplantDate);
      st.plantingDate = this.getDateFromJSON(st.plantingDate);

console.log(st)
       console.log('look at me im right here look')
       console.log(st.fieldId)

      fetch(this.apiUrl + "/beds/update", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify(st)
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    updateField(e, field) {
      field.name = e.target.value;
      field.beds = null;
      fetch(this.apiUrl + "/field/update", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify(field)
      })
        .then(response => {
          if (response.ok) {
            this.getFields();
            //this.$emit("showReviews");
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    updateDatabase(e, st) {
      if (e.target.classList[1] === "colName") {
        st["cropName"] = e.target.value;
      } else if (e.target.classList[1] === "td") {
        st["transplantDate"] = e.target.value;
      } else if (e.target.classList[2] === "pd") {
        st["plantingDate"] = e.target.value;
      }

      let jasonsArray = [st];
      console.log(JSON.stringify(jasonsArray));
      fetch(this.apiUrl + "/beds", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify(jasonsArray)
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    addField(event) {
      let field = {
        username: "user",
        name: event.target.previousSibling.value
      };
      fetch(this.apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(field)
      })
        .then(response => {
          if (response.ok) {
            //this.$emit("showReviews");
            alert("Your upload was successful.");
            this.getFields();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
     bedsByField(id) {
      return this.beds[id];
    },
    getDateFromJSON(jsonDate) {
      if (jsonDate == null) {
        return '';
      } else if (jsonDate.year == undefined){
        return jsonDate;
      }
      let year = jsonDate.year;
      let month = jsonDate.monthValue < 10 ? '0' + jsonDate.monthValue : jsonDate.monthValue;
      let day = jsonDate.dayOfMonth < 10 ? '0' + jsonDate.dayOfMonth : jsonDate.dayOfMonth;
      return year + '-' + month + '-' + day;
    },
  },
  created() {
    this.getFields();
  },
  computed: {
   
  }
};
</script>

<style>

</style>