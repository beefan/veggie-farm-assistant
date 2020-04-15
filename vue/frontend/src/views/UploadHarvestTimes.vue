<template>
<body class="harvestTimesBody">
  <Header></Header>
  <br />
  <br />
  <div class="harvestTimesContainer">
    <div class="havestTimesUpload">
      <div class="sectionHeader">Upload Harvest Times</div>
      <div class="csvUpload">
        <upload
          :verifyUploadFormat="uploadVerify"
          :uploadDocument="uploadSeedingTimes"
          @uploadSuccess="onUploadSuccess($event)"
        ></upload>
      </div>
      <p
        class="getStarted"
      >To get started, upload a .csv file above by clicking "Choose File" and select your .csv file with each Crop Name, the direct seed to harvest time, the direct seed to transplant time, and the transplant to harvest time, or you can use the Add New Crop field below to add them each individually.</p>
      <br />

      <div class="harvestTables">
        <table v-if="seedingTimes" class="addNewCrop">
          <thead>
            <tr>
              <th>Add New Crop</th>
              <th>Direct Seed to Harvest Time</th>
              <th>Direct Seed to Transplant Time</th>
              <th>Transplant to Harvest Time</th>
              <th>Submit</th>
            </tr>
          </thead>
          <tr class="newCrop">
            <td>
              <input class="harvestTable colName" type="text" placeholder="Crop Name" />
            </td>
            <td>
              <input class="harvestTable colDSTH" type="text" />
            </td>
            <td>
              <input class="harvestTable colDSTT" type="text" />
            </td>
            <td>
              <input class="harvestTable colTTH" type="text" />
            </td>
            <td class="submit">
              <a href="harvesttimes">
                <img :src="submitUrl" @click="addNewEntry($event);" />
              </a>
            </td>
          </tr>
        </table>
        <br />
        <table class="existingCrops">
          <thead>
            <tr>
              <th>Crop Name</th>
              <th>Direct Seed to Harvest Time</th>
              <th>Direct Seed to Transplant Time</th>
              <th>Transplant to Harvest Time</th>
              <th>Delete Entry</th>
            </tr>
          </thead>
        </table>
        <div class="scrollTable">
          <table class="existingCrops1">
            <tr v-for="st in seedingTimes" v-bind:key="st['cropName']">
              <td>
                <input
                  class="harvestTable colName"
                  type="text"
                  :value="st['cropName']"
                  @change="updateDatabase($event, st)"
                  @click="highLightRow($event)"
                  @blur="unhighlight($event)"
                />
              </td>
              <td>
                <input
                  class="harvestTable colDSTH"
                  type="text"
                  :value="st['directSeedToHarvestInDays'] "
                  @change="updateDatabase($event, st)"
                  @click="highLightRow($event)"
                  @blur="unhighlight($event)"
                />
              </td>
              <td>
                <input
                  class="harvestTable colDSTT"
                  type="text"
                  :value="st['directSeedToTransplantInDays']"
                  @change="updateDatabase($event, st)"
                  @click="highLightRow($event)"
                  @blur="unhighlight($event)"
                />
              </td>
              <td>
                <input
                  class="harvestTable colTTH"
                  type="text"
                  :value="st['transplantToHarvestInDays']"
                  @change="updateDatabase($event, st)"
                  @click="highLightRow($event)"
                  @blur="unhighlight($event)"
                />
              </td>
              <td class="trash">
                <a href="harvesttimes">
                  <img :src="trashUrl" @click="deleteEntry(st['cropName']);" />
                </a>
              </td>
            </tr>
          </table>
          <br />
          
        </div>
      </div>
      <br />
      <br />
    </div>
  </div>
  <br />
  <br />
  <br />
  <Footer></Footer>
</body>
</template>

<script>
import Upload from "../components/csvUpload.vue";
import Header from "../components/header.vue";
import Footer from "../components/footer.vue";

export default {
  props: {
    // apiUrl: String
  },
  components: { Upload: Upload, Header: Header, Footer: Footer },
  data() {
    return {
      parse_header: [],
      parse_csv: [],
      seedingTimes: [],
      apiUrl: process.env.VUE_APP_REMOTE_API,
      trashUrl: require("../images/trash.png"),
      submitUrl: require("../images/submit.png")
    };
  },
  methods: {
    highLightRow(e) {
      e.target.parentNode.classList.add("highlight");
    },

    unhighlight(e) {
      e.target.parentNode.classList.remove("highlight");
    },
    onUploadSuccess(on) {
      this.parse_header = on.header;
      this.parse_csv = on.csv;
    },

    addNewEntry(e) {
      let newEntry = {};
      let x = Array.from(e.target.parentNode.parentNode.parentNode.children);
      newEntry["cropName"] = x[0].firstChild.value;
      newEntry["directSeedToHarvestInDays"] = x[1].firstChild.value;
      newEntry["directSeedToTransplantInDays"] = x[2].firstChild.value;
      newEntry["transplantToHarvestInDays"] = x[3].firstChild.value;
      this.updateDatabase(e, newEntry);
    },

    deleteEntry(cropName) {
      fetch(this.apiUrl + "/" + cropName, {
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

    updateDatabase(e, st) {
      let cropName = st["cropName"];
      if (e.target.classList[1] === "colName") {
        st["cropName"] = e.target.value;
        this.deleteEntry(cropName);
      } else if (e.target.classList[1] === "colDSTH") {
        st["directSeedToHarvestInDays"] = e.target.value;
      } else if (e.target.classList[1] === "colDSTT") {
        st["directSeedToTransplantInDays"] = e.target.value;
      } else if (e.target.classList[1] === "colTTH") {
        st["transplantToHarvestInDays"] = e.target.value;
      }
      let jasonsArray = [st];
      fetch(this.apiUrl, {
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
    uploadVerify() {
      let vm = this;
      if (
        vm.parse_header[0] != "cropName" ||
        vm.parse_header[1] != "directSeedToHarvestInDays" ||
        vm.parse_header[2] != "directSeedToTransplantInDays" ||
        vm.parse_header[3] != "transplantToHarvestInDays"
      ) {
        return false;
      }

      for (let item of this.parse_csv) {
        if (
          !item["cropName"].match(/[a-z]/i) ||
          isNaN(item["directSeedToHarvestInDays"]) ||
          isNaN(item["directSeedToTransplantInDays"]) ||
          isNaN(item["transplantToHarvestInDays"])
        ) {
          return false;
        }
      }

      return true;
    },
    uploadSeedingTimes() {
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
            this.getSeedingTimes();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    getSeedingTimes() {
      fetch(this.apiUrl)
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.seedingTimes = data;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    this.getSeedingTimes();
  }
};
</script>

<style>
body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.addNewCrop
  > tr
  > td.submit
  > a
  > img {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 18%;
  padding: 8px 0 8px 0;
  cursor: pointer;
}
body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.addNewCrop
  > tr
  > td.submit
  > a
  > img:hover {
  display: block;
  margin-left: auto;
  margin-right: auto;
  opacity: 70%;
  width: 19%;
  padding: 8px 0 8px 0;
  cursor: pointer;
}

div.havestTimesUpload {
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 95%;
  height: auto;
  margin-left: auto;
  margin-right: auto;
  background: transparent;
  box-shadow: 20px 20px 50px rgb(180, 180, 180),
    -30px -30px 60px rgb(255, 255, 255);
}
div.harvestTables {
  width: 99%;
  margin-left: auto;
  margin-right: auto;
}
.harvestTable {
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
.addNewCrop {
  border-collapse: collapse;
  font-size: 1.1rem;
  width: 100%;
  color: #130f40;
  border: none;
  font-family: "Lato", sans-serif;
  margin: 0;
  padding: 0;
}
.existingCrops {
  border-collapse: collapse;
  font-size: 1.1rem;
  width: 100%;
  color: #130f40;
  font-family: "Lato", sans-serif;
  margin: 0;
  padding: 0;
}
.scrollTable {
  margin: 0;
  padding: 0;
  
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(1) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(2) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(3) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(4) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(5) {
  width: 12%;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > div
  > table
  > tr:nth-child(1)
  > td:nth-child(1) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > div
  > table
  > tr:nth-child(1)
  > td:nth-child(2) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > div
  > table
  > tr:nth-child(1)
  > td:nth-child(3) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > div
  > table
  > tr:nth-child(1)
  > td:nth-child(4) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > div
  > table
  > tr:nth-child(1)
  > td:nth-child(5) {
  width: 10vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(1) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(2) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(3) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(4) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(5) {
  width: 12%;
}

body
  > div.harvestTimesContainer
  > div
  > div.addNewCrop
  > div
  > table
  > tr
  > td:nth-child(1) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.addNewCrop
  > div
  > table
  > tr
  > td:nth-child(2) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.addNewCrop
  > div
  > table
  > tr
  > td:nth-child(3) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.addNewCrop
  > div
  > table
  > tr
  > td:nth-child(4) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.addNewCrop
  > div
  > table
  > tr
  > td:nth-child(5) {
  width: 10vw;
}
body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(1) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(2) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(3) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(4) {
  width: 20vw;
}

body
  > div.harvestTimesContainer
  > div
  > div.harvestTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(5) {
  width: 10vw;
}

body > div.harvestTimesContainer .existingCrops1 {
  border-collapse: collapse;
  font-size: 1.1rem;
  width: 100%;
  color: #130f40;
  font-family: "Lato", sans-serif;
  margin: 0;
  padding: 0;
  
}
tr.newCrop {
  background-color: #e9e9ed;
}

.addNewCrop {
  font-size: 1.1rem;
  color: #130f40;
  border: none;
  background-color: transparent;
  font-family: "Lato", sans-serif;
}
</style>

