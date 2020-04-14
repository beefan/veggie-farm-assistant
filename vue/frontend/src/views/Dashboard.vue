
<template>
<body>
  <Header></Header>
  <div class="harvestTimesContainer">
    <div class="havestTimesUpload">
      <div>
        <br>
        <email-notify></email-notify>
        
      </div>
    </div>
  </div>
  <br>
  <br>
  <Footer></Footer>
</body>

</template>


<script>
import Header from "../components/header.vue";
import Notify from "../components/notify.vue";
import Footer from "../components/footer.vue";

export default {
  components: {
    Header,
    Footer,
    "email-notify": Notify
  }
};
</script>

<style>
.dashboardWrapper {
  width: 80%;
  height: 100%;
  margin-right: auto;
  margin-left: auto;
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

// <html lang="en">
// <head>
//   <meta charset="UTF-8">
//   <meta name="viewport" content="width=device-width, initial-scale=1.0">
//   <meta http-equiv="X-UA-Compatible" content="ie=edge">
//   <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
//   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
//   <title>My Chart.js Chart</title>
// </head>
// <body>
//   <div class="container">
//     <canvas id="myChart"></canvas>
//   </div>

//   <script>
//     let myChart = document.getElementById('myChart').getContext('2d');

//     // Global Options
//     Chart.defaults.global.defaultFontFamily = 'Lato';
//     Chart.defaults.global.defaultFontSize = 18;
//     Chart.defaults.global.defaultFontColor = '#777';

//     let massPopChart = new Chart(myChart, {
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
//   </script>
// </body>
// </html>