<template>
  <div id="MainPage" style="margin-left:auto; margin-right:auto">
    <h2><span v-text="getCustomerName()"></span></h2>
    <div style="margin-top:10px" class="link">
        <a> <router-link to="/LibraryCustomer">Library Info</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
       <a style="margin-left:10px"> <router-link to="/customerCatalogue">Browse catalog</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
        <a> <router-link to="/OpeningHoursCustomer">Opening Hours</router-link></a>
    </div>
    <div style="margin-top:10px" class="link">
        <a> <router-link to="/CustomerItems">My bookings</router-link></a>
    </div>
    <div class="link" style="margin-top:10px">
        <a> <router-link to="/CustomerEvents">My events</router-link></a>
    </div>
    <div style="margin-top:30px" class="link2">
        <a style="margin-left:10px"><router-link to="/CustomerUpdatePersonalInfo">Update Account</router-link></a>
    </div>
    <div  style="margin-top:10px" class="link2">
        <a style="margin-left:10px;" @click="logOut()"> <router-link to="/LoginCustomer">Log out</router-link></a>
    </div>
  </div>
</template>

<script src="./CustomerMainPage.js">
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

  .link a {
      color: #150aeb;
      text-align: left;
      font-size: 23px;
  }

  .link2 a {
      color: #10583a;
      text-align: left;
      margin-top: 50px;
      font-size: 23px;
  }

</style>
