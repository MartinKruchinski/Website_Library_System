<template>
  <div id="MainPage">
     <h1>All events</h1>
     <div>
         <table style="margin-left:auto; margin-right:auto">
             <tr>
                 <th style="color: #0961b9; padding-left:20px">Customer</th>
                 <th style="color: #0961b9;padding-left:20px">Date</th>
                 <th style="color: #0961b9; padding-left:20px">Start time</th>
                 <th style="color: #0961b9; padding-left:20px">End time</th>
             </tr>
             <tr v-for="event in events" :key="event.eventId">
                <td style="padding-left:20px">{{ event.customerDto.firstName}}</td>
                <td style="padding-left:20px">{{ event.date}}</td>
                <td style="padding-left:20px">{{ event.startTime}}</td>
                <td style="padding-left:20px">{{ event.endTime}}</td>
                <td><button style="font-size:14px; margin-left:10px" @click="Cancel(event.id)" class="btn btn-primary">Cancel Event</button></td>
             </tr>
         </table>
     </div>
    <div style="margin-top:30px">
        <a><router-link to="/CreateEventLibrarian">Create Event For Customer</router-link></a>
    </div>
     <div>
        <button style="margin-top:20px" @click="BackToMainPage()" class="btn btn-primary"> Back </button>
     </div>
  </div>
</template>

<script src="./ListOfEvents.js">
</script>

<style>
  #MainPage {
    font-family: 'Varela Round', Helvetica, Arial, sans-serif;
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
