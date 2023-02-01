<template>
  <div id="MainPage">
     <h1>My bookings</h1>
     <div>
         <table style="margin-left:auto; margin-right:auto">
             <tr>
                 <th style="color: #0961b9;padding-left:20px">Type</th>
                 <th style="color: #0961b9; padding-left:20px">Title</th>
                 <th style="color: #0961b9; padding-left:20px">Author</th>
                 <th style="color: #0961b9; padding-left:20px">Last day</th>
                 <th style="color: #0961b9; padding-left:20px;">Booking Type</th>

             </tr>
             <tr v-for="booking in bookings" :key="booking.bookingId">
                 <td style="padding-left:20px" > {{ booking.item.itemType}}</td>
                 <td style="padding-left:20px" > {{ booking.item.title}}</td>
                 <td style="padding-left:20px" > {{ booking.item.author}}</td>
                 <td style="padding-left:20px" > {{ booking.endDate}}</td>
                 <td style="padding-left:20px" > {{ booking.bookingType}}</td>
                 <td><button :disabled="booking.bookingType=='Checkout'" class="btn btn-primary" style="margin-left:30px; font-size:14px" @click="DeleteReservation(booking.id, booking.item.id)">Cancel Rervation</button></td>
             </tr>
         </table>
          <div>
            <span v-if="errorDeleteReservation" style="color:red"> {{errorDeleteReservation}} </span>
          </div>
          <div>
            <span v-if="deletedReservation" style="color:green"> {{deletedReservation}} </span>
          </div>
     </div>
     <div>
         <button style="margin-top:50px" @click="BackToMainPage()" class="btn btn-primary"> Back </button>
     </div>
  </div>
</template>

<script src="./CustomerItems.js">
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Varela+Round');

  #MainPage {
    font-family: 'Varela Round', Helvetica, Arial, sans-serif;
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
