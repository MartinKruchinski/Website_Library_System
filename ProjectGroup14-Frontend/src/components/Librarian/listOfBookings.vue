<template>
  <div id="MainPage">
     <h1>Bookings</h1>
     <div>
         <h3>Reservations</h3>
         <table style="margin-left:auto; margin-right:auto">
             <tr>
                <th style="color: #0961b9; padding-left:20px">Customer</th>
                <th style="color: #0961b9;padding-left:20px">Item Type</th>
                 <th style="color: #0961b9; padding-left:20px">Title</th>
                 <th style="color: #0961b9; padding-left:20px">Author</th>
                 <th style="color: #0961b9; padding-left:20px">Last day</th>

             </tr>
             <tr v-for="booking in bookings" :key="booking.bookingId">
                 <td style="padding-left:20px" > {{ booking.customer.loginCredential.username}}</td>
                 <td style="padding-left:20px" > {{ booking.item.itemType}}</td>
                 <td style="padding-left:20px" > {{ booking.item.title}}</td>
                 <td style="padding-left:20px" > {{ booking.item.author}}</td>
                 <td style="padding-left:20px" > {{ booking.endDate}}</td>
                  <td><button class="btn btn-primary" style="margin-left:30px; font-size:14px" @click="Checkout(booking.id)">Checkout Item</button></td>
                 <td><button class="btn btn-primary" style="margin-left:30px; font-size:14px" @click="DeleteBooking(booking.id, booking.item.id)">Delete Reservation</button></td>
             </tr>
         </table>
          <div>
            <span v-if="errorDeleteReservation" style="color:red"> {{errorDeleteReservation}} </span>
          </div>
          <div>
            <span v-if="deletedReservation" style="color:green"> {{deletedReservation}} </span>
          </div>
     </div>
     <div style="margin-top:60px">
          <h3>Checkouts</h3>
         <table style="margin-left:auto; margin-right:auto">
             <tr>
                <th style="color: #0961b9; padding-left:20px">Customer</th>
                <th style="color: #0961b9;padding-left:20px">Item Type</th>
                 <th style="color: #0961b9; padding-left:20px">Title</th>
                 <th style="color: #0961b9; padding-left:20px">Author</th>
                 <th style="color: #0961b9; padding-left:20px">Start Day</th>
                 <th style="color: #0961b9; padding-left:20px">Last day</th>

             </tr>
             <tr v-for="booking in bookings_checkout" :key="booking.bookingId">
                 <td style="padding-left:20px" > {{ booking.customer.firstName}} {{booking.customer.lastName}}</td>
                 <td style="padding-left:20px" > {{ booking.item.itemType}}</td>
                 <td style="padding-left:20px" > {{ booking.item.title}}</td>
                 <td style="padding-left:20px" > {{ booking.item.author}}</td>
                 <td style="padding-left:20px" > {{ booking.startDate}}</td>
                 <td style="padding-left:20px" > {{ booking.endDate}}</td>
                 <td><button class="btn btn-primary" style="margin-left:30px; font-size:14px" @click="DeleteBooking(booking.id, booking.item.id)">Return Item</button></td>
             </tr>
         </table>
     </div>
     <div style="margin-top:40px">
       <a style="margin-top:30px"><router-link to="/LibrarianCheckoutItem">Checkout Item for customer</router-link></a>
     </div>
     <div>
         <button style="margin-top:50px" @click="BackToMainPage()" class="btn btn-primary"> Back </button>
     </div>
  </div>
</template>

<script src="./listOfBookings.js">
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
