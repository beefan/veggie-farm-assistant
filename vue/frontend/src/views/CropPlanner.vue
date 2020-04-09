<template>
    <div class="container">
        <div>
        <button @click="addField($event)">Add New Field</button>
        <input type="text" value="Field Name"/>
        </div>
  <div class="field" v-for="field in fields" v-bind:key="field['id']">
      <input type="text" :value="field['name']"/>
        <table>
        <thead>
            <tr>
              <th>Add New Bed</th> 
              <th>Transplant Date</th>
              <th>Planting Date</th>
            </tr>
          </thead> 
          <tr class="newCrop" >
            <td><input  class="harvestTable colName" type="text" value="Crop Name" /></td>
            <td><input  class="harvestTable td" type="text" value="Transplant Date"/> </td>
            <td><input  class="harvestTable pd" type="text" value="Planting Date" /></td>
            <td class="submit"><a href="cropplanner"><img :src= "submitUrl" @click="addNewEntry($event);"/></a></td>
            </tr>
            <thead> 
            <tr>
              <th>Crop Name</th>
              <th>Transplant Date</th> 
              <th>Planting Date</th>
            </tr>
            </thead>
            <tr v-for="st in getBedsByField(field['id'])" v-bind:key="st['id']">
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
            class="harvestTable td"
            type="text"
            :value="st['transplantDate'] "
            @change="updateDatabase($event, st)"
            @click="highLightRow($event)"
            @blur="unhighlight($event)"
          />
        </td>
        <td>
        <input
            class="harvestTable pd"
            type="text"
            :value="st['plantingDate'] "
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
      :uploadDocument="uploadExpirationTimes"
      title="Upload Expiration Times"
      @uploadSuccess="onUploadSuccess($event)"
      ></upload>
  </div>
      <div></div>
  

      </div>
</template>

<script>
import Upload from "../components/csvUpload.vue";
export default {
    components: {Upload: Upload},

    data() {
    return {
      fields: [],
      parse_csv: [],
      apiUrl: process.env.VUE_APP_REMOTE_API_CROP,
      parse_header: [],
      trashUrl: require('../images/trash.png'),
      submitUrl: require('../images/submit.png')
    };
  },
  methods:{
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
          !item["transplantDate"].match(/^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$/i) ||
          !item["plantingDate"].match(/^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$/i)
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
    highLightRow(e){
      
      e.target.parentNode.classList.add("highlight");
    },
    
    unhighlight(e){
      e.target.parentNode.classList.remove("highlight");
    },
    getFields(){
        fetch(this.apiUrl +'/user')
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.fields = data;
          console.log(data)
        })
        .catch(err => {
          console.error(err);
        });
    },
    getBedsByField(id){
        fetch(this.apiUrl +'/beds/' + id)
        .then(response => {
          return response.json();
        })
        .then(data => {
            console.log(data)
          return data;
          
        })
        .catch(err => {
          console.error(err);
        });
    },
    addField(event){
        let field= {
            username: 'user',
            name: event.target.nextSibling.value
        }
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
    
  },
  created(){
      this.getFields()
      
  }

}

</script>

<style>
.field{
   width: 30%;
   
    
}
</style>