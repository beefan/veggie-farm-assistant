<template>
<div class="bg-cover" :style="{ backgroundImage: `url(${coverImage})` }">
    <homepageHeader />
    <br>
    <div class="contactWrapper">
        <div class="contactHeader">
            <br>
            <h5 class="contactHeaderText">Meet the team behind HarvestAssist</h5>
            <br><br>
        </div>
        <div class="teamDiv">
            <div class="team">
                <img class="headshot" :src="brandon"/>
                <p class="name">Brandon Fannin</p>
                <p>www.linkedin.com/in/brandon-fannin</p>
            </div>
            <div class="team">
                <img class="headshot" :src="noah"/>
                <p class="name">Noah Walcher</p>
                <p>www.linkedin.com/in/noahwalcher</p>
            </div>
            <div class="team">
                <img class="headshot" :src="lucas"/>
                <p class="name">Lucas Dumais</p>
                <p>www.linkedin.com/in/lucasdumais</p>
            </div>
            <div class="team">
                <img class="headshot" :src="brian"/>
                <p class="name">Brian Berryman</p>
                <p>www.linkedin.com/in/brianberryman</p>
            </div>
        </div>
        <br>
        <br>
    </div>
    
    </div>


</template>

<script>
import homepageHeader from "../components/homepageHeader.vue";
export default {
    components: {
    homepageHeader: homepageHeader,
  },
    data(){
    return {
    noah: require('../images/noah.png'),
    brian: require('../images/brian.png'),
    lucas: require('../images/lucas.png'),
    brandon: require('../images/brandon.png'),
    coverImage: require('../images/background.jpg')
        }
    }
}
</script>

<style>
h5 {
    font-size: 3rem;
    font-weight: bolder;
    text-align: center;
    padding:0;
    margin:0;
    color:#f7b254;
}
.contactWrapper{
    margin-left: auto;
    margin-right: auto;
    width: 80%;
    height: auto;
    background: rgba(12,12,12,.7);
    border: 2px solid #f7b254;
    border-radius: 6px;
    display: flex;
    flex-direction: column;
}
.teamDiv {
    display: flex;
    flex-direction:row;
    flex-wrap: nowrap;
    justify-content: space-evenly;
    width: auto;
    height: auto;

}
.team {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-left: auto;
    margin-right: auto;
    align-items: center;
}
.team > p {
    color: white;
    margin:0;
    padding: 0;
}
.name{
    font-size: 1.5rem;
}
.headshot {
    width: 70%;
}
</style>