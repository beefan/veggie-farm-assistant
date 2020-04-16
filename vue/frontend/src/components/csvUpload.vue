<template>
<div class="csvContainer">
<input type="file"
       name="file"
       :id="'file' + parentData"
       class="inputfile "
       @change="loadCSV($event)" />
       <label :for="'file' + parentData" class="neumorphic"><img class="uploadIcon" :src="upload" /><span class="uploadText">Choose a file... </span></label>
</div>
</template>

<style>
    .csvContainer {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items:baseline;
    }
    div.csvUpload {
        padding:0;
        margin: auto;
    }
    .neumorphic {
        margin: 10px; 
        box-shadow: 12px 12px 24px 0 rgba(0, 0, 0, 0.2);
        border-radius: 10px;
        overflow: hidden;
        padding: 0rem;
        display: flex;
        font-family: 'Lato', sans-serif;
        text-align:center;
    }
    .uploadIcon {
      position:relative;
      width: 10%;
      top:20%;
      bottom:20%;
      margin: 5px 5px 0px 5px;
      padding:0;
    }

    .inputfile {
        width: 0.1px;
        height: 0.1px;
        opacity: 0;
        overflow: hidden;
        position: absolute;
        z-index: -1;
        
        
    }
    .inputfile + label {
      background-color: #f7b254;
      width: 100%;
      height: 130%;
    font-size: 1.2rem;
    color: black;
    display: inline-block;
    cursor: pointer;
}


.inputfile:focus + label,
.inputfile + label:hover {
  opacity: 70%;
  box-shadow: 12px 12px 24px 0 rgba(0, 0, 0, 0.2);
}
    
</style>

<script>
export default {
    props: {
        title: String,
        verifyUploadFormat: Function,
        uploadDocument: Function,
        parentData: Number,
        
    },

data() {
    return {
      parse_header: [],
      parse_csv: [],
      sortOrders: {},
      sortKey: "",
      apiUrl: process.env.VUE_APP_REMOTE_API,
      upload: require('../images/upload.png')
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
            
            if (vm.parentData == undefined) {
            vm.uploadDocument();
            } else {
              vm.uploadDocument(vm.parentData);
            }
            
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
      e.target.value="";
    },
}
}
</script>


