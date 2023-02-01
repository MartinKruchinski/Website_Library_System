<template>
  <div id="Shift">
     <h1>All Shifts</h1>
     <div style="margin-left:295px">
         <table style="margin-left:30px; margin-bottom:30px">
             <tr >
                 <th style="color: #0961b9; padding-left:20px">Librarian</th>
                 <th style="color: #0961b9; padding-left:20px">Day</th>
                 <th style="color: #0961b9;padding-left:20px">Start Time</th>
                 <th style="color: #0961b9;padding-left:20px">End Time</th>
             </tr>
             <tr v-for="shift in shifts" :key="shift.shiftId">
                <td style="padding-left:20px">{{ shift.librarianDto.firstName}} {{ shift.librarianDto.lastName }}</td>
                <td style="padding-left:20px">{{ shift.dayOfWeek}}</td>
                <td style="padding-left:20px">{{ shift.startTime}}</td>
                <td style="padding-left:20px">{{ shift.endTime}}</td>
                <td><button class="btn btn-primary" style="margin-left:30px; font-size:26px" @click="DeleteShift(shift.id)" >Delete Shift</button></td>
             </tr>
         </table>
     </div>
     <div>
        <div>
          <span v-if="errorDeleteShift" style="color:red"> {{errorDeleteShift}} </span>
        </div>
        <a style="margin-top:50px"><router-link to="/HeadLibrarianMainPage">Back</router-link></a>
        <a style="margin-top:50px; margin-left:30px; color: #01070e"><router-link to="/CreateShift">Assign Shift</router-link></a>
        <!-- @click="BackToMainPage()" -->
     </div>
  </div>
</template>

<script src="./ShiftPage.js">
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Varela+Round');

  #Shift {
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
