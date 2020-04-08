<template>
  <div class="container">
    <div class="panel panel-sm">
      <div class="panel-heading">
        <h4>Harvest Times File Import</h4>
      </div>
      <div class="panel-body">
        <div class="form-group">
          <label for="csv_file" class="control-label col-sm-3 text-right">CSV file to import</label>
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
              <th>Crop Name</th>
              <th>Direct Seed to Harvest</th> 
              <th>Direct Seed to Transplant</th>
              <th>Transplant to Harvest</th>
            </tr>
          </thead> 
          <tr v-for="st in seedingTimes" v-bind:key="st['cropName']">
            <td><input id="col1" class="dong" type="text" :value="st['cropName']" @change="updateDatabase($event, st)"/></td>
            <td><input id="col2" class="dong" type="text" :value="st['directSeedToHarvestInDays']" @change="updateDatabase($event, st)"/></td>
            <td><input id="col3" class="dong" type="text" :value="st['directSeedToTransplantInDays']" @change="updateDatabase($event, st)"/></td>
            <td><input id="col4" class="dong" type="text" :value="st['transplantToHarvestInDays']" @change="updateDatabase($event, st)"/></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    apiUrl: String
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
      seedingTimes: []
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
      if (e.target.id === 'col1') {
        st['cropName'] = e.target.value;
        this.deleteEntry(cropName);
      } else if (e.target.id === 'col2') {
        st['directSeedToHarvestInDays'] = e.target.value;
      } else if (e.target.id === 'col3') {
        st['directSeedToTransplantInDays'] = e.target.value;
      } else if (e.target.id === 'col4') {
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
html,
body {
  margin: 0;
  padding: 0;
}
body {
  margin: 32px auto;
}
.panel {
  border: 2px solid #dfdfdf;
  box-shadow: rgba(0, 0, 0, 0.15) 0 1px 0 0;
  margin: 10px;
}
.panel.panel-sm {
  max-width: 700px;
  margin: 10px auto;
}
.panel-heading {
  border-bottom: 2px solid #dfdfdf;
}
.panel-heading h1,
.panel-heading h2,
.panel-heading h3,
.panel-heading h4,
.panel-heading h5,
.panel-heading h6 {
  margin: 0;
  padding: 0;
}
.panel-body .checkbox-inline {
  padding: 15px 20px;
}
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td,
th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}

.dong {
  width: 100%;
  border: none;
  background-color: transparent;
  font-size: 1rem;
}
</style>

