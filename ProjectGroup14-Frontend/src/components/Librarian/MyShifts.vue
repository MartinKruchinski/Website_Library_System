<template>
  <div id="MainPage">
     <h1>My shifts</h1>
     <div style="margin-left: auto; margin-right:auto; margin-top:30px">
         <table style="margin-left: auto; margin-right:auto; margin-top:30px">
             <tr>
                 <th style="color: #0961b9;padding-left:20px">Day</th>
                 <th style="color: #0961b9; padding-left:20px">Start time</th>
                 <th style="color: #0961b9; padding-left:20px">End time</th>
             </tr>
             <tr v-for="shift in shifts" :key="shift.shift_id">
                 <td style="padding-left:20px" > {{ shift.dayOfWeek}}</td>
                 <td style="padding-left:20px" > {{ shift.startTime}}</td>
                 <td style="padding-left:20px" > {{ shift.endTime}}</td>

             </tr>
         </table>
     </div>
     <div>
         <button style="margin-top:50px" @click="BackToMainPage()" class="btn btn-primary"> Back </button>
     </div>
  </div>
</template>

<script src="./MyShifts.js">
</script>

<style>
  #MainPage {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #010a13;
    background: #f2ece8;
    margin: 20px;
    padding: 40px;
    background: #9fdfcb;
    height: 100vh;
    margin: auto;
    width: 95vw;
    border: 3px solid rgb(78, 184, 78);
    padding: 10px;
  }

  input {
    text-align: center;
  }
</style>
