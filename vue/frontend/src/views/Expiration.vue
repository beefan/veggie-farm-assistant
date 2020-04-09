<template>
  <div class="container">
    <div class="panel panel-sm">
      <div class="panel-body">
      <upload
      :verifyUploadFormat="uploadVerify"
      :uploadDocument="uploadExpirationTimes"
      title="Upload Expiration Times"
      @uploadSuccess="onUploadSuccess($event)"
    ></upload>
    <table v-if="expirationTimes">
        <thead>
            <tr>
              <th>Add New Crop</th>
              <th>Expiration Time</th> 
              <th>Submit</th>
            </tr>
          </thead> 
          <tr class="newCrop" >
            <td><input  class="harvestTable colName" type="text" value="Crop Name" /></td>
            <td><input  class="harvestTable colDSTH" type="text" /> </td>
            
            <td class="submit"><a href="wasteinfo"><img :src= "submitUrl" @click="addNewEntry($event);"/></a></td>
            </tr>
    </table>
    <table v-if="expirationTimes">
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
    </div>
  </div>
</template>

<script>
import Upload from "../components/csvUpload.vue";
export default {
  components: { Upload: Upload },

  data() {
    return {
      expirationTimes: [],
      parse_csv: [],
      apiUrl: process.env.VUE_APP_REMOTE_API_EXPIRATION,
      parse_header: [],
      trashUrl: require('../images/trash.png'),
      submitUrl: require('../images/submit.png')
    };
  },
  methods: {
    uploadVerify() {
      console.log(this.parse_header), console.log(this.parse_csv);
      let vm = this;
      if (
        vm.parse_header[0] != "cropName" ||
        vm.parse_header[1] != "daysToExpiration"
    
      ) {
        return false;
      }

      for (let item of this.parse_csv) {
        console.log(item["cropName"]);
        console.log(isNaN(item["daysToExpiration"]));
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
          console.log(data)
        })
        .catch(err => {
          console.error(err);
        });
    },
    
    onUploadSuccess(on) {
      
      this.parse_header = on.header;
      this.parse_csv = on.csv;
    },
    highLightRow(e){
      
      e.target.parentNode.classList.add("highlight");
    },
    
    unhighlight(e){
      e.target.parentNode.classList.remove("highlight");
    },
    updateDatabase(e, st) {
     let cropName = st['cropName'];
      if (e.target.classList[1] === 'colName') {
        st['cropName'] = e.target.value;
        this.deleteEntry(cropName);
      } else if (e.target.classList[1] === 'colDSTH') {
        st['daysToExpiration'] = e.target.value;
      } 
      let jasonsArray = [st]
      console.log(JSON.stringify(jasonsArray));
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
      fetch(this.apiUrl + '/' + cropName, {
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
      let x = Array.from(
      e.target.parentNode.parentNode.parentNode.children);
      newEntry["cropName"] = x[0].firstChild.value;
      newEntry["daysToExpiration"] = x[1].firstChild.value;
      this.updateDatabase(e, newEntry);
    },
    
  },
  created() {
        console.log("Test")
        this.getExpirationTimes();
    },
};
</script>

<style>
</style>