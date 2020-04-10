<template>
  <div class="csvContainer">
        <h2>{{title}}</h2>
      <div class="csvUploadData">
        <br>
            <input
              type="file"
              id="csv_file"
              name="csv_file"
              class="form-control"
              @change="loadCSV($event)"
            />
            
          </div>
        </div>
      
</template>

<script>
export default {
    props: {
        title: String,
        verifyUploadFormat: Function,
        uploadDocument: Function,
        
    },

data() {
    return {
      parse_header: [],
      parse_csv: [],
      sortOrders: {},
      sortKey: "",
      apiUrl: process.env.VUE_APP_REMOTE_API,
    };
  },
  methods: {
    csvJSON(csv) {
      let vm = this;
      let lines = csv.split("\n");
      let result = [];
      let headers = lines[0].split(",");
      vm.parse_header = lines[0].split(",");
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
  
  loadCSV(e) {
      var vm = this;
      if (window.FileReader) {
        var reader = new FileReader();
        reader.readAsText(e.target.files[0]);
        // Handle errors load
        reader.onload = function(event) {
          var csv = event.target.result;
          vm.parse_csv = vm.csvJSON(csv);
          vm.$emit("uploadSuccess", {csv:vm.parse_csv, header:vm.parse_header});
          if (vm.verifyUploadFormat()) {
            vm.uploadDocument();
            
            
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
}
}
</script>

<style>

</style>