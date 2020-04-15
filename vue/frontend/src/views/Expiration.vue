<template>
<body class="wasteTimesBody">
  <Header></Header>
  <br />
  <br />
  <div class="wasteContainer">
    <div class="wasteTimesUpload">
      <div class="sectionHeader">Upload Expiration Times</div>
      <div class="csvUpload">
        <upload
          class="csvUploadClickBox"
          :verifyUploadFormat="uploadVerify"
          :uploadDocument="uploadExpirationTimes"
          @uploadSuccess="onUploadSuccess($event)"
        ></upload>
      </div>
      <p
        class="getStarted"
      >To get started, upload a .csv file with each Crop Name and it's Expiration Time, or you can use the Add New Crop field below to add them each individually.</p>
      <br />
      <div class="wasteTables">
        <table v-if="expirationTimes" class="addNewCrop">
          <thead>
            <tr>
              <th>Add New Crop</th>
              <th>Expiration Time</th>
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
            <td class="submit wasteSubmit">
              <a href="wasteinfo">
                <img :src="submitUrl" @click="addNewEntry($event);" />
              </a>
            </td>
          </tr>
        </table>
        <br />
        <table class="existingCrops" v-if="expirationTimes">
          <thead>
            <tr>
              <th>Crop Name</th>
              <th>Expiration Time</th>
              <th>Delete</th>
            </tr>
          </thead>

          <tr v-for="st in expirationTimes" v-bind:key="st['cropName']">
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
                :value="st['daysToExpiration'] "
                @change="updateDatabase($event, st)"
                @click="highLightRow($event)"
                @blur="unhighlight($event)"
              />
            </td>
            <td class="trash">
              <a href="wasteinfo">
                <img :src="trashUrl" @click="deleteEntry(st['cropName']);" />
              </a>
            </td>
          </tr>
        </table>
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
  components: {
    Upload: Upload,
    Header: Header,
    Footer: Footer
  },

  data() {
    return {
      expirationTimes: [],
      parse_csv: [],
      apiUrl: process.env.VUE_APP_REMOTE_API_EXPIRATION,
      parse_header: [],
      trashUrl: require("../images/trash.png"),
      submitUrl: require("../images/submit.png")
    };
  },
  methods: {
    uploadVerify() {
      let vm = this;
      if (
        vm.parse_header[0] != "cropName" ||
        vm.parse_header[1] != "daysToExpiration"
      ) {
        return false;
      }

      for (let item of this.parse_csv) {
        if (
          !item["cropName"].match(/[a-z]/i) ||
          isNaN(item["daysToExpiration"])
        ) {
          return false;
        }
      }

      return true;
    },

    uploadExpirationTimes() {
      console.error("uploading these expiration times V");
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
            this.getExpirationTimes();
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    getExpirationTimes() {
      fetch(this.apiUrl)
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.expirationTimes = data;
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
    updateDatabase(e, st) {
      let cropName = st["cropName"];
      if (e.target.classList[1] === "colName") {
        st["cropName"] = e.target.value;
        this.deleteEntry(cropName);
      } else if (e.target.classList[1] === "colDSTH") {
        st["daysToExpiration"] = e.target.value;
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
    addNewEntry(e) {
      let newEntry = {};
      let x = Array.from(e.target.parentNode.parentNode.parentNode.children);
      newEntry["cropName"] = x[0].firstChild.value;
      newEntry["daysToExpiration"] = x[1].firstChild.value;
      this.updateDatabase(e, newEntry);
    }
  },
  created() {
    this.getExpirationTimes();
  }
};
</script>

<style>
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(1) {
  width: 35vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(2) {
  width: 35vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.existingCrops
  > thead
  > tr
  > th:nth-child(3) {
  width: 1vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(1) {
  width: 35vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(2) {
  width: 35vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.addNewCrop
  > thead
  > tr
  > th:nth-child(3) {
  width: 1vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.addNewCrop
  > thead
  > tr
  > tr:nth-child(1) {
  width: 35vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.addNewCrop
  > thead
  > tr
  > tr:nth-child(2) {
  width: 35vw;
}
body
  > div.wasteContainer
  > div
  > div.wasteTables
  > table.addNewCrop
  > thead
  > tr
  > tr:nth-child(3) {
  width: 1vw;
}

div.wasteTables {
  width: 99%;
  margin-left: auto;
  margin-right: auto;
}
td.wasteSubmit {
  margin-left: auto;
  margin-right: auto;
}

td.wasteSubmit > a > img {
  display: block;
  margin-left: auto;
  margin-right: auto;
  opacity: 70%;
  width: 19%;
  padding: 8px 0 8px 0;
  cursor: pointer;
}
td.wasteSubmit > a > img:hover {
  display: block;
  margin-left: auto;
  margin-right: auto;
  opacity: 70%;
  width: 20%;
  padding: 8px 0 8px 0;
  cursor: pointer;
}
div.wasteTimesUpload {
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 95%;
  margin-left: auto;
  margin-right: auto;
  background: transparent;
  box-shadow: 20px 20px 50px rgb(180, 180, 180),
    -30px -30px 60px rgb(255, 255, 255);
}
</style>