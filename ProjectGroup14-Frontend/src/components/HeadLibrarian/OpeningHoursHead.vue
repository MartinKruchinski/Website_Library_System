<template>
  <div id="OpeningHours">
     <h1>Our Opening Hours</h1>
     <div style="margin-left:295px">
         <table style="margin-left:30px; margin-bottom:30px">
             <tr >
                 <th style="color: #0961b9; padding-left:20px">Day</th>
                 <th style="color: #0961b9;padding-left:20px">Start Time</th>
                 <th style="color: #0961b9;padding-left:20px">End Time</th>
             </tr>
             <tr v-for="hour in openingHours" :key="hour.hourId">
                <td style="padding-left:20px">{{ hour.dayOfWeek}}</td>
                <td style="padding-left:20px">{{ hour.startTime}}</td>
                <td style="padding-left:20px">{{ hour.endTime}}</td>
                <td><button class="btn btn-primary" style="margin-left:30px; font-size:26px" @click="DeleteOpeningHour(hour.id)">Delete Opening Hour</button></td>
             </tr>
         </table>
     </div>
     <div>
        <a style="margin-top:50px"><router-link to="/HeadLibrarianMainPage">Back</router-link></a>
        <a style="margin-top:50px; margin-left:30px; color: #01070e"><router-link to="/UpdateOpeningHours">Update Opening Hours</router-link></a>
        <a style="margin-top:50px; margin-left:30px; color: #01070e"><router-link to="/CreateOpeningHour">Create Opening Hour</router-link></a>
        <!-- @click="BackToMainPage()" -->
     </div>
  </div>
</template>

<script src="./OpeningHoursHead.js">
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Varela+Round');

  #OpeningHours {
    font-family: 'Varela Round', Helvetica, Arial, sans-serif;
    color: #000000;
    background: #9fdfcb;
    margin: 20px;
    height: 100vh;
    margin: auto;
    width: 95vw;
    border: 3px solid #5bc2a1;
    padding: 10px;
    font-size: 28px;
  }

  input {
    text-align: center;
  }
</style>
