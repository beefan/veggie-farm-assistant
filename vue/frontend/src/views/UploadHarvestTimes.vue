<template>
  <div class="container">
    <div class="panel panel-sm">
      <div class="panel-heading">
        <h4>Upload and Edit Harvest Times</h4>
      </div>
      <div class="panel-body">
        <div class="form-group">
          <label for="csv_file" class="control-label col-sm-3 text-right">Import Your CSV File</label>
          <div class="col-sm-9">
            <input
              type="file"
              id="csv_file"
              name="csv_file"
              class="form-control"
              @change="loadCSV($event)"
            />
            
          </div>
        </div>
        <table v-if="seedingTimes">
          <thead>
            <tr>
              <th>Add New Crop</th>
              <th>Direct Seed to Harvest Time</th> 
              <th>Direct Seed to Transplant Time</th>
              <th>Transplant to Harvest Time</th>
              <th>Submit</th>
            </tr>
          </thead> 
          <tr class="newCrop" >
            <td><input  class="harvestTable colName" type="text" value="Crop Name" /></td>
            <td><input  class="harvestTable colDSTH" type="text" /> </td>
            <td><input  class="harvestTable colDSTT" type="text" /></td>
            <td><input  class="harvestTable colTTH" type="text" /></td>
            <td class="submit"><a href="harvesttimes"><img :src= "submitUrl" @click="addNewEntry($event);"/></a></td>
            </tr>
            <thead>
            <tr>
              <th>Crop Name</th>
              <th>Direct Seed to Harvest Time</th> 
              <th>Direct Seed to Transplant Time</th>
              <th>Transplant to Harvest Time</th>
              <th>Delete Entry</th>
            </tr>
          </thead>
          <tr v-for="st in seedingTimes" v-bind:key="st['cropName']">
            <td><input  class="harvestTable colName" type="text" :value="st['cropName']" @change="updateDatabase($event, st)"/></td>
            <td><input  class="harvestTable colDSTH" type="text" :value="st['directSeedToHarvestInDays'] " @change="updateDatabase($event, st)"/></td>
            <td><input  class="harvestTable colDSTT" type="text" :value="st['directSeedToTransplantInDays']" @change="updateDatabase($event, st)"/></td>
            <td><input  class="harvestTable colTTH" type="text" :value="st['transplantToHarvestInDays']" @change="updateDatabase($event, st)"/></td>
            <td class="trash"><a href="harvesttimes"><img :src= "trashUrl" @click="deleteEntry(st['cropName']);"/></a></td>
          </tr>
          
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    //apiUrl: String
  },
  data() {
    return {
      channel_name: "",
      channel_fields: [],
      channel_entries: [],
      parse_header: [],
      parse_csv: [],
      sortOrders: {},
      sortKey: "",
      seedingTimes: [],
      apiUrl: process.env.VUE_APP_REMOTE_API,
      trashUrl: require('../images/trash.png'),
      submitUrl: require('../images/submit.png')
    };
  },
  filters: {
    capitalize: function(str) {
      return str.charAt(0).toUpperCase() + str.slice(1);
    }
  },
  methods: {
    sortBy: function(key) {
      let vm = this;
      vm.sortKey = key;
      vm.sortOrders[key] = vm.sortOrders[key] * -1;
    },
    csvJSON(csv) {
      let vm = this;
      let lines = csv.split("\n");
      let result = [];
      let headers = lines[0].split(",");
      vm.parse_header = lines[0].split(",");
      lines[0].split(",").forEach((key) => {
        vm.sortOrders[key] = 1;
      });

      lines.map((line, indexLine) => {
        if (indexLine < 1) return; // Jump header line

        let obj = {};
        let currentline = line.split(",");
        headers = headers.map(x => {
          if (x.includes("\r")) {
            return x.substring(0, x.length - 1).trim();
          } else {
            return x.trim();
          }
        });
        headers.map((header, indexHeader) => {
          if (currentline[indexHeader].includes("\r")) {
            currentline[indexHeader] = currentline[indexHeader]
              .substring(0, currentline[indexHeader].length - 1)
              .trim();
          }
          obj[header] = currentline[indexHeader].trim();
        });
        result.push(obj);
      });

      vm.parse_header = headers;
      return result; // JavaScript object
    },
    
    
    addNewEntry(e) {
      let newEntry = {};
      let x = Array.from(
      e.target.parentNode.parentNode.parentNode.children);
      newEntry["cropName"] = x[0].firstChild.value;
      newEntry["directSeedToHarvestInDays"] = x[1].firstChild.value;
      newEntry["directSeedToTransplantInDays"] = x[2].firstChild.value;
      newEntry["transplantToHarvestInDays"] = x[3].firstChild.value;
      this.updateDatabase(e, newEntry);
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

    updateDatabase(e, st) {
     let cropName = st['cropName'];
      if (e.target.classList[1] === 'colName') {
        st['cropName'] = e.target.value;
        this.deleteEntry(cropName);
      } else if (e.target.classList[1] === 'colDSTH') {
        st['directSeedToHarvestInDays'] = e.target.value;
      } else if (e.target.classList[1] === 'colDSTT') {
        st['directSeedToTransplantInDays'] = e.target.value;
      } else if (e.target.classList[1] === 'colTTH') {
        st['transplantToHarvestInDays'] = e.target.value;
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
    loadCSV(e) {
      var vm = this;
      if (window.FileReader) {
        var reader = new FileReader();
        reader.readAsText(e.target.files[0]);
        // Handle errors load
        reader.onload = function(event) {
          var csv = event.target.result;
          vm.parse_csv = vm.csvJSON(csv);
          if (vm.verifyUploadFormat()) {
            vm.uploadSeedingTimes();
          } else {
            alert("Incorrect file format");
          }
        };
        reader.onerror = function(evt) {
          if (evt.target.error.name == "NotReadableError") {
            alert("Can't read file!");
          }
        };
      } else {
        alert("FileReader are not supported in this browser.");
      }
    },
    verifyUploadFormat() {
      let vm = this;
      if (
        vm.parse_header[0] != "cropName" ||
        vm.parse_header[1] != "directSeedToHarvestInDays" ||
        vm.parse_header[2] != "directSeedToTransplantInDays" ||
        vm.parse_header[3] != "transplantToHarvestInDays"
      ) {
        return false;
      }

     
      for (let item of this.parse_csv){
        console.log(item["cropName"])
        console.log(isNaN(item["transplantToHarvestInDays"]))
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

</style>

