<template>
  <div id="MainPage">
     <h1>My events</h1>
     <div style="margin-right:20px">
         <table style="margin-right:auto; margin-left:auto">
             <tr>
                 <th style="color: #0961b9;padding-left:20px">Date</th>
                 <th style="color: #0961b9; padding-left:20px">Start time</th>
                 <th style="color: #0961b9; padding-left:20px">End time</th>
             </tr>
             <tr v-bind="eventCustomer" :disabled="eventCustomer==null">
                <td :disabled="eventCustomer==null" style="padding-left:20px">{{ eventCustomer.date}}</td>
                <td :disabled="eventCustomer==null" style="padding-left:20px">{{ eventCustomer.startTime}}</td>
                <td :disabled="eventCustomer==null" style="padding-left:20px">{{ eventCustomer.endTime}}</td>
                <td><button :hidden="noEvent==null" class="btn btn-primary" style="margin-left:30px; font-size:14px" @click="Cancel(eventCustomer.id)">Cancel</button></td>
             </tr>
         </table>
     </div>
     <div style="margin-top:15px" >
       <a style="margin-left:10px"><router-link to="/CreateEvent">Create Event</router-link></a>
     </div>
     <div>
        <button style="margin-top:20px" @click="BackToMainPage()" class="btn btn-primary"> Back </button>
     </div>
  </div>
</template>

<script src="./CustomerEvents.js">
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
