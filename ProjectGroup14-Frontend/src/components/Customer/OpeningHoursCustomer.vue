<template>
  <div id="OpeningHoursCustomer">
     <h1>Our Opening Hours</h1>
     <div style="margin-right:10px; margin-top:20px">
         <table style="margin-left:auto; margin-right:auto">
             <tr >
                 <th style="color: #0961b9; padding-left:20px">Day</th>
                 <th style="color: #0961b9;padding-left:20px">Start Time</th>
                 <th style="color: #0961b9;padding-left:20px">End Time</th>
             </tr>
             <tr v-for="hour in openingHours" :key="hour.hourId">
                <td style="padding-left:20px">{{ hour.dayOfWeek}}</td>
                <td style="padding-left:20px">{{ hour.startTime}}</td>
                <td style="padding-left:20px">{{ hour.endTime}}</td>
             </tr>
         </table>
     </div>
     <div style="margin-top:50px">
        <a style="margin-top:50px"><router-link to="/CustomerMainPage">Back</router-link></a>
     </div>
  </div>
</template>

<script src="./OpeningHoursCustomer.js">
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Varela+Round');

  #OpeningHoursCustomer {
    font-family: 'Varela Round', Helvetica, Arial, sans-serif;
    color: #000000;
    background: #9fdfcb;
    margin: 20px;
    height: 100vh;
    margin: auto;
    width: 95vw;
    border: 3px solid #5bc2a1;
    padding: 10px;
    font-size: 20px;
  }

  input {
    text-align: center;
  }
</style>
