<template>
<body>
  <Header></Header>
  <div class="harvestTimesContainer">
    <div class="havestTimesUpload">
      <div>
        <br />
        <email-notify></email-notify>

      </div>
       <div class="sectionHeader">Harvest and Sales Data</div>
    <div class="dailies">
      <div class="chartSmall" v-if="showCharOptions">
        <select
          id="field"
          name="cropChartDropdown"
          @change="refreshChart($event)"
          v-model="selectedCrop"
        >
          <option value></option>
          <option v-for="crop in harvestData" v-bind:key="crop">{{crop}}</option>
        </select>
        <line-chart :chart-data="chartData" />
      </div>
      </div>
    </div>
  </div>
  <br />
  <br />
  <Footer></Footer>
</body>
</template>


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
      salesData: [],
      harvestData: {},
      wasteData: [],
      lossData: [],
      chartData: null,
      selectedCrop: null,
      cropSalesData: [],
      cropHarvestData: [],
      showCharOptions: false
    };
  },

  mounted() {
    this.fillData();
    this.renderChart(this.chartdata, this.options);
  },

  created() {
    this.getSevenDayData();
  },
  methods: {
    getSevenDayData() {
      fetch(this.apiUrl + "/harvest")
        .then(response => {
          return response.json();
        })
        .then(data => {
      
          this.harvestData = data;
          fetch(this.apiUrl + "/sales")
            .then(response => {
              return response.json();
            })

            .then(data => {
              this.harvestData = Object.assign(this.harvestData, data);
              this.showCharOptions = true;
            })
            .catch(err => {
              console.error(err);
            });
        })
        .catch(err => {
          console.error(err);
        });
    },

    refreshChart() {
      let selectedId = null;
      for (let [key, value] of Object.entries(this.harvestData)) {
        console.log(`${key}: ${value}`);
        if (value === this.selectedCrop) {
          selectedId = key;
        }
      }
      if (selectedId) {
      this.getSevenDayHarvestData(selectedId);
      this.getSevenDaySalesData(selectedId);
      }
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
      this.chartData = {
        options: {
          title: {display: true, text: 'hardcoded'},
          responsive: true,
          maintainAspectRatio: false
        },
        labels: [1, 2, 3, 4, 5, 6, 7],
        datasets: [
          {
            label: "SALES",
            borderColor: '#130f40',
            data: mv.cropSalesData
          },
          {
            label: "Harvest",
            borderColor: "#f7b254",
            backgroundColor: "#f7b254",
            background: 'transparent',
            data: mv.cropHarvestData
          }
       
        ]
      };
    },

    getRandomInt() {
      this.cropSalesData[0];
    }
  }
};
// let myChart = document.getElementById('myChart').getContext('2d');

//     // Global Options
//     Chart.defaults.global.defaultFontFamily = 'Lato';
//     Chart.defaults.global.defaultFontSize = 18;
//     Chart.defaults.global.defaultFontColor = '#777';

//     new Chart(myChart, {
//       type:'bar', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
//       data:{
//         labels:['Boston', 'Worcester', 'Springfield', 'Lowell', 'Cambridge', 'New Bedford'],
//         datasets:[{
//           label:'Population',
//           data:[
//             617594,
//             181045,
//             153060,
//             106519,
//             105162,
//             95072
//           ],
//           //backgroundColor:'green',
//           backgroundColor:[
//             'rgba(255, 99, 132, 0.6)',
//             'rgba(54, 162, 235, 0.6)',
//             'rgba(255, 206, 86, 0.6)',
//             'rgba(75, 192, 192, 0.6)',
//             'rgba(153, 102, 255, 0.6)',
//             'rgba(255, 159, 64, 0.6)',
//             'rgba(255, 99, 132, 0.6)'
//           ],
//           borderWidth:1,
//           borderColor:'#777',
//           hoverBorderWidth:3,
//           hoverBorderColor:'#000'
//         }]
//       },
//       options:{
//         title:{
//           display:true,
//           text:'Largest Cities In Massachusetts',
//           fontSize:25
//         },
//         legend:{
//           display:true,
//           position:'right',
//           labels:{
//             fontColor:'#000'
//           }
//         },
//         layout:{
//           padding:{
//             left:50,
//             right:0,
//             bottom:0,
//             top:0
//           }
//         },
//         tooltips:{
//           enabled:true
//         }
//       }
//     });
</script>

<style>
.dashboardWrapper {
  width: 80%;
  height: 100%;
  margin-right: auto;
  margin-left: auto;
}

.chartSmall {
  width: 40%;
  margin-left: auto;
  margin-right: auto;
}

.dashboardContent {
  width: 90%;
  height: 100%;
  background-color: gainsboro;
  border: 0.05rem solid #130f40;
  margin-left: auto;
  margin-right: auto;
}
</style>