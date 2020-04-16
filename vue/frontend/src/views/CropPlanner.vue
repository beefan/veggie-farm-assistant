<template>
  <div class="cropPlanner">
    <Header></Header>

    <section class="newFieldContainer">
      <div class="newFieldCreate">
        <span class="textNew">Add New Field</span>
        <input class="input btn" type="text" placeholder="Enter New Field Name" />
        <button class="addNewField btn" @click="addField($event)">Create</button>
      </div>
    </section>

    <div class="fieldsWrapper" v-if="fieldsLoaded">
      <div class="fieldLoaded" v-for="field in fields" v-bind:key="field['id']">
        <div class="fieldName">
          <input
            class="fieldNamebtn"
            type="text"
            :value="field['name']"
            @change="updateField($event, field)"
          />
          <img :src="deleteImg" class="deleteImg" @click="deleteField(field.id);" />
        </div>
        <br />
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
              <input class="fieldTable colName" type="text" placeholder="Crop Name" />
            </td>
            <td>
              <input class="fieldTable td" type="date" value="Transplant Date" />
            </td>
            <td>
              <input class="fieldTable pd" type="date" value="Planting Date" />
            </td>
            <td class="plannerSubmit">
              <a href="cropplanner">
                <img :src="submitUrl" @click="addNewEntry($event);" />
              </a>
            </td>
          </tr>
        </table>

        <br />

        <table class="addedCropsFieldTable">
          <thead>
            <tr>
              <th>Bed #</th>
              <th>Crop Name</th>
              <th>Transplant Date</th>
              <th>Planting Date</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tr v-for="st in field.beds" v-bind:key="st['bedId']">
            <td>
              <p>{{ field.beds.indexOf(st) + 1 }}</p>
            </td>
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
        <br />
        <div class="csvUploadFields">
          <p>Upload Beds from CSV:</p>
          <upload
            :parentData="field['id']"
            :verifyUploadFormat="uploadVerify"
            @uploadSuccess="onUploadSuccess($event)"
            :uploadDocument="uploadBeds"
          ></upload>
        </div>
        <br />
      </div>
      <br />
      <br />
    </div>

    <br />
    <Footer></Footer>
  </div>
</template>

<style>
span.textNew {
  margin-top: 10px;
  margin-left: 0.5vw;
}

.deleteImg:hover {
  opacity: 60%;
}

div.fieldName > img {
  width: 1.2%;
  margin-left: 6%;
  margin-right: 1%;
  padding: 0;
}

div.fieldName {
  width: 100%;
  height: 100%;
  background-color: #130f40;
  border-radius: 30px 30px 0px 0;
  margin-right: auto;
  margin-left: auto;
}

div.fieldName > input {
  width: 80%;
  margin-left: 10%;
}

.newFieldContainer {
  position: fixed;
  top: 85%;
  border-radius: 0 30px 30px 0px;
  width: 15vw;
  margin-left: 0;
  background-color: #130f40;
  z-index: 999;
  font-family: "Lato", sans-serif;
  font-size: 2vw;
  padding: 0;
  color: white;
}

.newFieldCreate {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.fieldName {
  display: flex;
  align-items: center;
  justify-content: center;
}

.csvUploadFields {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.hide {
  visibility: hidden;
}

div.fieldLoaded {
  position: relative;
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 50px;
  width: 95%;
  background: transparent;
  box-shadow: 20px 20px 50px rgb(180, 180, 180),
    -30px -30px 60px rgb(255, 255, 255);
}

.addNewCropFieldTable {
  border-collapse: collapse;
  font-size: 1.1rem;
  width: 70%;
  color: #130f40;
  border: none;
  background-color: transparent;
  font-family: "Lato", sans-serif;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  padding: 0;
}

.addedCropsFieldTable {
  border-collapse: collapse;
  font-size: 1.1rem;
  width: 90%;
  color: #130f40;
  border: none;
  background-color: transparent;
  font-family: "Lato", sans-serif;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  padding: 0;
}

.fieldNamebtn {
  background: #130f40;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 0;
  color: White;
  font-family: "Lato", sans-serif;
  padding: 0;
  cursor: pointer;
  font-size: 3rem;
  width: 1000%;
  text-align: center;
}

.fieldNamebtn:hover {
  opacity: 70%;
}

.fieldName.btn {
  width: 400px;
  height: 1.8rem;
}

.newFieldContainer .btn:last-of-type {
  margin-right: 1rem;
  margin-left: 0.5rem;
  height: 30px;
  margin-bottom: 0.5vw;
}

#app > div > section.newFieldContainer > div > button {
  margin-left: 30%;
  margin-right: auto;
  width: 5vw;
  color: black;
  background-color: #f7b254;
}
#app > div > section.newFieldContainer > div > button:hover {
  opacity: 70%;
  box-shadow: 12px 12px 24px 0 rgba(0, 0, 0, 0.2);
}
.newFieldContainer .btn {
  font-size: 1rem;
  transition: background-color 150ms;
  padding: 0 0.6rem;
  border-radius: 0.3rem;
  height: 3rem;
  font-family: "Lato", sans-serif;
}

.addNewFieldCreate > button {
  margin-left: auto;
  margin-right: auto;
  width: 10px;
}

.fieldsWrapper {
  position: relative;
  top: 50px;
  margin: auto;
  border-radius: 30%;
}

.fieldTable {
  border-collapse: collapse;
  font-size: 1.1rem;
  width: 100%;
  color: #130f40;
  border: none;
  background-color: transparent;
  font-family: "Lato", sans-serif;
  text-align: center;
  margin: 0;
  padding: 0;
}

#app
  > div
  > div.fieldsWrapper
  > div
  > table.addedCropsFieldTable
  > thead
  > tr
  > th:nth-child(1) {
  width: 8vw;
}
#app
  > div
  > div.fieldsWrapper
  > div
  > table.addedCropsFieldTable
  > thead
  > tr
  > th:nth-child(2) {
  width: 20vw;
}
#app
  > div
  > div.fieldsWrapper
  > div
  > table.addedCropsFieldTable
  > thead
  > tr
  > th:nth-child(3) {
  width: 20vw;
}
#app
  > div
  > div.fieldsWrapper
  > div
  > table.addedCropsFieldTable
  > thead
  > tr
  > th:nth-child(4) {
  width: 20vw;
}
#app
  > div
  > div.fieldsWrapper
  > div
  > table.addedCropsFieldTable
  > thead
  > tr
  > th:nth-child(5) {
  width: 8vw;
}
table.addNewCropFieldTable > thead > tr > th:nth-child(1) {
  width: 20vw;
}
table.addNewCropFieldTable > thead > tr > th:nth-child(2) {
  width: 20vw;
}
table.addNewCropFieldTable > thead > tr > th:nth-child(3) {
  width: 20vw;
}
table.addNewCropFieldTable > thead > tr > th:nth-child(4) {
  width: 8vw;
}

td.plannerSubmit > a > img {
  display: block;
  margin-left: auto;
  margin-right: auto;
  opacity: 70%;
  width: 25%;
  padding: 8px 0 8px 0;
  cursor: pointer;
}
td.plannerSubmit > a > img:hover {
  display: block;
  margin-left: auto;
  margin-right: auto;
  opacity: 70%;
  width: 26%;
  padding: 8px 0 8px 0;
  cursor: pointer;
}

body.cropPlanner {
  white-space: nowrap;
}
body.cropPlanner {
  border: 0;
  padding: 0;
  margin: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>





<script>
import Upload from "../components/csvUpload.vue";
import Header from "../components/header.vue";
import Footer from "../components/footer.vue";
export default {
  components: { Upload: Upload, Header: Header, Footer: Footer },

  data() {
    return {
      fields: [],
      fieldsLoaded: false,
      parse_csv: [],
      apiUrl: process.env.VUE_APP_REMOTE_API_CROP,
      parse_header: [],
      trashUrl: require("../images/trash.png"),
      submitUrl: require("../images/submit.png"),
      deleteImg: require("../images/close.png")
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
        if (
          !item["cropName"].match(/[a-z]/i) ||
          (item["transplantDate"] &&
            !item["transplantDate"].match(
              /^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$/i
            )) ||
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
      fetch(this.apiUrl + "/beds/" + bedId, {
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
      fetch(this.apiUrl + "/" + fieldId, {
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
      console.log(fieldId + "<---------");
      this.parse_csv.forEach(bed => {
        bed["fieldId"] = fieldId;
      });
      console.log(this.parse_csv + "Its this one");
      fetch(this.apiUrl + "/beds", {
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
      this.fieldsLoaded = false;
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
      st.fieldId =
        e.target.parentNode.parentNode.parentNode.previousSibling.previousSibling.children[1].classList[1];
      st.transplantDate = this.getDateFromJSON(st.transplantDate);
      st.plantingDate = this.getDateFromJSON(st.plantingDate);

      console.log(st);
      console.log("look at me im right here look");
      console.log(st.fieldId);

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
        return "";
      } else if (jsonDate.year == undefined) {
        return jsonDate;
      }
      let year = jsonDate.year;
      let month =
        jsonDate.monthValue < 10
          ? "0" + jsonDate.monthValue
          : jsonDate.monthValue;
      let day =
        jsonDate.dayOfMonth < 10
          ? "0" + jsonDate.dayOfMonth
          : jsonDate.dayOfMonth;
      return year + "-" + month + "-" + day;
    }
  },
  created() {
    this.getFields();
  },
  computed: {}
};
</script>

