<template>
<body>
  <Header></Header>
  <div class = "dashboardWrapper">
  <div class="notificationsContainer">
        <email-notify></email-notify>
      </div>
</div>
      <div class="chartWrapper">
        <div class="sectionHeader">Harvest and Sales Data</div>
        <div class="reports">
          <br>
          <div class="chartSmall" >
            <select
              id="field"
              name="cropChartDropdown"
              @change="refreshChart($event)"
              v-model="selectedCrop"
            >
              <option value></option>
              <option v-for="crop in cropsWithChartData" v-bind:key="crop">{{crop}}</option>
            </select>
            <line-chart :chart-data="chartData" />
          </div>
          <div class="weeklyTotals" v-if="showCharOptions">
            <div class="weeklyTotalsHeader"><h3 class="wTHeader">Weekly Totals</h3></div>
            <ul>
              <li>Sales: ${{Math.round(this.cropSalesData.reduce(sum))}} </li>
              <li>Loss: ${{Math.round(this.cropLossData.reduce(sum))}}</li>
              <li>Harvest: {{Math.round(this.cropHarvestData.reduce(sum))}} lbs </li>
              
            </ul>
            
           </div> 
          <br><br>
        
        </div>
      </div>
    

  <br />
  <br />
  <Footer></Footer>
</body>
</template>

<style>
.weeklyTotals{
  margin-top: 6.5%;
  margin-right: auto;
  margin-left:0;
  width:20%;
  height:100%;
  font-family: 'Lato', sans-serif;
        font-size: 1.5vw;
        background: transparent;
        border-radius:30px;
        box-shadow: 1px 1px 3px rgb(180, 180, 180), -1px -1px 5px rgb(255, 255, 255);
        color: #130f40;
        align-self: left;

}
.weeklyTotalsHeader {
  background-color: #f7b254;
  text-align:center;
  padding: 10px 0 10px 0;
  width:100%;
  height:auto;
  
  margin:0;
  
  border-radius: 30px 30px 0 0;
}
.weeklyTotalsHeader h3{
  margin: 0;
  padding:0;
  font-size: 1.5rem;
  font-weight: bold;

}

/* background-color: #130f40;
        width: 100%;
        color: white;
        text-align: center;
        font-size: 2.5rem;
        font-family: 'Lato', sans-serif;
        margin: 0;
        padding: 10px 0 10px 0;
        height: 10%;
        border-radius: 30px 30px 0px 0px; */
.chartSmall #field{
  width:60%;
  margin-left:auto;
  margin-right:auto;
  font-size:1rem;
}

.notificationsContainer{
  margin:0;
  padding:0;
}
.dashboardWrapper {
  border-radius: 30px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 95%;
        margin-bottom: 40px;
        margin-top: 30px;
        margin-left: auto;
        margin-right: auto;
        background: transparent;
        box-shadow: 20px 20px 50px rgb(180, 180, 180), -30px -30px 60px rgb(255, 255, 255);
    }

.chartWrapper{
  
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  flex-wrap:nowrap;
  justify-content: center;
  width: 95%;
  height: auto;
  margin-left: auto;
  margin-right: auto;
  background: transparent;
  box-shadow: 20px 20px 50px rgb(180, 180, 180),
    -30px -30px 60px rgb(255, 255, 255);
}
.chartSmall {
  padding:20px;
  display:flex;
  flex-direction:column;
  width: 40%;
  height:auto;
  margin-left: 20%;
  margin-right: 10px;
}
#bar-chart{
  width:60%;
  height:60%;
}
.dashboardContent {
  width: 90%;
  height: 100%;
  background-color: gainsboro;
  border: 0.05rem solid #130f40;
  margin-left: auto;
  margin-right: auto;
}
.reports {
  display:flex;
  flex-direction:row;
  justify-content: space-evenly;
  align-content:center;
  margin-left:auto;
  margin-right:auto;
  padding:0;
  width:100%;
  
}
.reports h3 {
  text-align:center;
}
.reports li{
  list-style:none;
  margin-bottom:10px;
  font-weight: bold;
  font-size: 1.3vw;
}


</style>

<script>
import Header from "../components/header.vue";
import Notify from "../components/notify.vue";
import Footer from "../components/footer.vue";
import LineChart from "../LineChart.js";

export default {
  components: {
    Header,
    Footer,
    "email-notify": Notify,
    LineChart
  },

  data() {
    return {
      datacollection: null,
      apiUrl: process.env.VUE_APP_REMOTE_API_CHART,
      cropsWithChartData: {},
      wasteData: [],
      lossData: [],
      chartData: null,
      selectedCrop: null,
      cropSalesData: [0],
      cropHarvestData: [0],
      cropLossData: [0],
      showCharOptions: false
    };
  },

  mounted() {
    this.fillData();
  },

  created() {
    this.getSevenDayData();
  },
  methods: {
    getSevenDayData() {
      fetch(this.apiUrl)
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.cropsWithChartData = data;
          this.showCharOptions = true;
          console.log(data)
        })
        .catch(err => {
          console.error(err);
        });
    },
    sum(acc, current) {
        return acc+current;
      
    },
    refreshChart() {
      let selectedId = null;
      for (let [key, value] of Object.entries(this.cropsWithChartData)) {
        console.log(`${key}: ${value}`);
        if (value === this.selectedCrop) {
          selectedId = key;
        }
      }
      if (selectedId) {
        this.getSevenDayHarvestData(selectedId);
        this.getSevenDaySalesData(selectedId);
        this.getSevenDayLossData(selectedId);
      }
    },

    getSevenDayLossData(cropId) {
      fetch(this.apiUrl + "/loss/" + cropId)
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.cropLossData = data;
          console.log("check crop");
          console.log(this.cropLossData);
          this.fillData();
        })
        .catch(err => {
          console.error(err);
        });
    },

    getSevenDayHarvestData(cropId) {
      fetch(this.apiUrl + "/harvest/" + cropId)
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.cropHarvestData = data;
          console.log("check yoself");
          console.log(this.cropHarvestData);
          this.fillData();
        })
        .catch(err => {
          console.error(err);
        });
    },

    getSevenDaySalesData(cropId) {
      fetch(this.apiUrl + "/sales/" + cropId)
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.cropSalesData = data;
          this.fillData();

          console.log("schmeck yoself");
          console.log(this.cropSalesData);
          console.log(this.cropSalesData[6] + "<------------------------");
        })
        .catch(err => {
          console.error(err);
        });
    },

    fillData() {
      let mv = this;
      //let today = new Date();
      let yesterday = x => {
        return new Date(new Date().setDate(new Date().getDate() - x))
          .toString()
          .substring(0, 10);
      };
      this.chartData = {
        options: {
          title: { display: true, text: "hardcoded" },
          responsive: true,
          maintainAspectRatio: false
        },
        labels: [
          yesterday(6),
          yesterday(5),
          yesterday(4),
          yesterday(3),
          yesterday(2),
          yesterday(1),
          "Today"
        ],
        datasets: [
          {
            label: "Sales ($)",
            borderColor: "#130f40",
            backgroundColor: "RGBA(19,15,64,1)",
            data: mv.cropSalesData
          },
          {
            label: "Harvest (lbs)",
            borderColor: "#f7b254",
            backgroundColor: "RGBA(247,178,84,1)",
            fillOpacity: 0,
            data: mv.cropHarvestData
          },
          {
            label: "Loss (-$)",
            borderColor: 'red',
            backgroundColor: 'red',
            data: mv.cropLossData

          }
        ]
      };
    }
  }
};

</script>

