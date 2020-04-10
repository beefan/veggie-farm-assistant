<template>
<body>
  <div class="newFieldContainer">
    <div class="newFieldCreate">
      <h2>Add New Field</h2>
      <button @click="addField($event)">Create</button>
      <input type="text" value="Field Name" />
    </div>
    </div>
    <div class="field" v-if="fieldsLoaded">
    <div  v-for="field in fields" v-bind:key="field['id']">
      <input type="text" :value="field['name']" />
      <table class="fieldTable">
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
        <thead>
          <tr>
            <th>Crop Name</th>
            <th>Transplant Date</th>
            <th>Planting Date</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tr v-for="st in beds[field.id]" v-bind:key="st['bedId']">
          <td>
            <input
              class="fieldTable colName"
              type="text"
              :value="st['cropName']"
              @change="updateDatabase($event, st)"
              @click="highLightRow($event)"
              @blur="unhighlight($event)"
            />
          </td>
          <td>
            <input
              class="fieldTable td"
              type="date"
              :value="st['transplantDate']"
              @change="updateDatabase($event, st)"
              @click="highLightRow($event)"
              @blur="unhighlight($event)"
            />
          </td>
          <td>
            <input
              class="fieldTable pd"
              type="date"
              :value="st['plantingDate']"
              @change="updateDatabase($event, st)"
              @click="highLightRow($event)"
              @blur="unhighlight($event)"
            />
          </td>
          <td class="trash">
            <a href="cropplanner">
              <img :src="trashUrl" @click="deleteEntry(st['cropName']);" />
            </a>
          </td>
        </tr>
      </table>

      <upload
        :verifyUploadFormat="uploadVerify"
        :uploadDocument="uploadBeds"
        title
        @uploadSuccess="onUploadSuccess($event)"
      ></upload>
    </div>
    </div>
    <div></div>
  </div>
  </body>
</template>

<script>
import Upload from "../components/csvUpload.vue";
export default {
  components: { Upload: Upload },

  data() {
    return {
      fields: [],
      beds: [],
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
          !item["cropName"].match(/[a-z]/i) ||
          !item["transplantDate"].match(
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
    uploadBeds() {
      fetch(this.apiUrl, {
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
    getBedsByField(id) {
      fetch(this.apiUrl + "/beds/" + id)
        .then(response => {
          return response.json();
        })
        .then(data => {
          //console.log(data);
          this.beds[id]=data;
          console.log(this.beds[id]);
        })
        .catch(err => {
          console.error(err);
        });
    },
    getFields() {
      fetch(this.apiUrl + "/user")
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.fields = data;
          this.fields.forEach(field => {
            this.getBedsByField(field["id"]);
          });
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
        name: event.target.nextSibling.value
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
      console.log(jsonDate.year + '-' + jsonDate.monthValue + '-' + jsonDate.dayOfMonth);
      return String(jsonDate.year + '-' + jsonDate.monthValue + '-' + jsonDate.dayOfMonth);
    }
  },
  created() {
    this.getFields();
  },
  computed: {
   
  }
};
</script>

<style>
.field {
  border: 2px solid black;
}
.field > img {
  width: 10%;
}
.hide {
  visibility: hidden;
}
</style>