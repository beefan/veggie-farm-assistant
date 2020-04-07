<template>
  <div class="container">
    <div class="panel panel-sm">
      <div class="panel-heading">
        <h4>CSV Import</h4>
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
        <p v-if="parse_csv">{{parse_csv}}</p>
        <p v-for="item in seedingTimes" v-bind:key="item.crop_name">{{item}}</p>
        <!-- <table v-if="parse_csv">
          <thead>
            <tr>
              <th v-for="key in parse_header"
                  v-bind:key="key"
                  @click="sortBy(key)"
                  :class="{ active: sortKey == key }">
                {{ key | capitalize }}
                <span class="arrow" :class="sortOrders[key] > 0 ? 'asc' : 'dsc'">
                </span>
              </th>
            </tr>
          </thead> 
          <tr v-for="csv in parse_csv" v-bind:key="csv">
            <td v-for="key in parse_header" v-bind:key="key" >
              {{csv[key]}}
            </td>
          </tr>
          
        </table>-->
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
      var vm = this;
      vm.sortKey = key;
      vm.sortOrders[key] = vm.sortOrders[key] * -1;
    },
    csvJSON(csv) {
      var vm = this;
      var lines = csv.split("\n");
      var result = [];
      var headers = lines[0].split(",");
      vm.parse_header = lines[0].split(",");
      lines[0].split(",").forEach(function(key) {
        vm.sortOrders[key] = 1;
      });

      lines.map(function(line, indexLine) {
        if (indexLine < 1) return; // Jump header line

        var obj = {};
        var currentline = line.split(",");
        headers = headers.map( x => {
          if (x.includes('\r')){
            return x.substring(0, x.length-1);
          } else {
            return x;
          }
        });
        headers.map(function(header, indexHeader) {
          if (currentline[indexHeader].includes('\r')){
            currentline[indexHeader] = currentline[indexHeader].substring(0, currentline[indexHeader].length-1);
          }
          obj[header] = currentline[indexHeader];
        });

        result.push(obj);
      });

      result.pop(); // remove the last item because undefined values

      return result; // JavaScript object
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
        };
        reader.onerror = function(evt) {
          if (evt.target.error.name == "NotReadableError") {
            alert("Can't read file!");
          }
        };

        if (vm.verifyUploadFormat()) {
          vm.uploadSeedingTimes()
        }else {
          alert('Incorrect file format');
        }
 
      } else {
        alert("FileReader are not supported in this browser.");
      }
    },
    verifyUploadFormat() {
      let isGood = true;
      
      if (this.parse_header[0] != 'cropName' ||
          this.parse_header[1] != 'directSeedToHarvestInDays' ||
          this.parse_header[2] != 'directSeedToTransplantInDays' ||
          this.parse_header[3] != 'transplantToHarvestInDays') {
            isGood = false;
          }
          
      this.parse_csv.forEach( item => {
        if (!(item[0].match(/[a-z]/i) ||
            !(item[1].match(/[0-9]/) ||
            !(item[2].match(/[0-9]/) ||
            !(item[3].match(/[0-9]/) ){
              isGood = false;
            }
      });

      return isGood;
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
            alert('Your upload was successful.');
          }
        })
        .catch(err => {
          console.error(err)
        }
        );
        
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
</style>

