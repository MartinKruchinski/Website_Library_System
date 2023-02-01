<template>
  <div id="HeadLibrarianPage">
    <h2><span v-text="getLibrarianName()"></span></h2>
    <div style="" class="link">
        <a style="margin-left:10px"> <router-link to="/UpdateLibrary">Library</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
        <a> <router-link to="/OpeningHoursHead">Opening Hours</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
        <a> <router-link to="/ShiftPage">Shifts</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
        <a> <router-link to="/LibrarianCatalogue">Catalog</router-link></a>
    </div>
    <div class="link" style="margin-top:10px">
        <a> <router-link to="/ListOfBookings">Bookings</router-link></a>
    </div>
    <div class="link" style="margin-top:10px">
        <a> <router-link to="/ListOfEvents">Events</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
        <a style="margin-left:10px"> <router-link to="/ListOfCustomers">Customers</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
        <a style="margin-left:10px"> <router-link to="/ListOfLibrarians">Librarians</router-link></a>
    </div>
    <div style="margin-top: 40px" class="link2">
        <a style="margin-left:10px"><router-link to="/UpdateLibrarianAccount">Update account</router-link></a>
    </div>
    <div  class="link2">
        <a style="margin-left:10px;"> <router-link to="/MyShifts">My shifts</router-link></a>
    </div>
    <div class="link2" >
        <a style="margin-left:10px;" @click="logOut()"> <router-link to="/LoginLibrarian">Log out</router-link></a>
    </div>
  </div>
</template>

<script src="./HeadLibrarianMainPage.js">
</script>

<style>
  #HeadLibrarianPage {
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

  .link a {
      color: #150aeb;
      text-align: left;
      font-size: 23px;
  }

  .link2 a {
      color: #10583a;
      text-align: left;
      font-size: 23px;
  }

</style>
