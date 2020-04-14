<template>
<body>
  <Header></Header>
  <div class = "dashboardWrapper">
  <div class="notificationsContainer">
        <email-notify></email-notify>
        <br>
        </div>
      </div>
      
      <div class="dashboardWrapper">
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
        <br><br>
        </div>
        </div>
      
    

  <br />
  <br />
  <Footer></Footer>
</body>
</template>

<style>
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
        margin-bottom: 20px;
        margin-top: 30px;
        margin-left: auto;
        margin-right: auto;
        background: transparent;
        box-shadow: 20px 20px 50px rgb(180, 180, 180), -30px -30px 60px rgb(255, 255, 255);
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
      //let today = new Date();
      let yesterday = x => {
       return new Date(new Date().setDate(new Date().getDate() - x)).toString().substring(0, 10);
      };
      this.chartData = {
        options: {
          title: { display: true, text: "hardcoded" },
          responsive: true,
          maintainAspectRatio: false
        },
        labels: [yesterday(6), yesterday(5),yesterday(4),yesterday(3),yesterday(2), yesterday(1), "Today"],
        datasets: [
          {
            label: "SALES",
            borderColor: "#130f40",
            backgroundColor: "RGBA(19,15,64,1)",
            data: mv.cropSalesData
          },
          {
            label: "Harvest",
            borderColor: "#f7b254",
            backgroundColor: "RGBA(247,178,84,1)",
            fillOpacity: 0,
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

</script>

