<template>
  <div class="UpdateCustomerPersonal">
    <h1>Personal Information</h1>
    <div>
      <strong>Firstname: {{ firstname }}</strong>
      <input style="margin-top:15px; margin-left:25px" type="text" v-model="newFirstname" placeholder=" New Firstname" required>
      <button :disabled="!newFirstname" @click="UpdateFirstname(newFirstname)"  class="btn btn-primary">Update Firstname</button>
    </div>
     <div>
      <strong>Lastname: {{ lastname }}</strong>
      <input style="margin-top:15px; margin-left:25px" type="text" v-model="newLastname" placeholder=" New Lastname" required>
      <button :disabled="!newLastname" @click="UpdateLastname(newLastname)"  class="btn btn-primary">Update Lastname</button>
    </div>
    <div>
      <strong>Address: {{ address }} </strong>
      <input style="margin-top:15px; margin-left:10px" type="text" v-model="newAddress" placeholder=" New Address" required>
      <button :disabled="!newAddress" @click="UpdateAddress(newAddress)" class="btn btn-primary">Update Address</button>
    </div>
    <div>
      <span v-if="errorUpdateCustomer" style="color:red"> {{errorUpdateCustomer}} </span>
    </div>
    <div>
      <span v-if="successUpdateCustomer" style="color:green"> {{successUpdateCustomer}} </span>
    </div>
    <div style="margin-top:20px">
        <div>
            <a><router-link to="/CustomerUpdateAccount">Update Account Info</router-link></a>
        </div>
        <div>
            <a><router-link to="/CustomerMainPage">Back</router-link></a>
        </div>
    </div>
  </div>
</template>

<script src="./CustomerUpdatePersonalInfo.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.UpdateCustomerPersonal {
  font-family: 'Varela Round', Helvetica, Arial, sans-serif;
  background: #9fdfcb;
  height: 100vh;
  margin: auto;
  width: 95vw;
  border: 3px solid rgb(78, 184, 78);
  padding: 10px;
}
.btn {
  min-width: 50px;
}
.btn-primary{
  min-width: 160px;
}
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
input {
  border-radius:5px;
  border-width:0px;
}
a {
  font-size: 20px;
  color: #1d42b9;
}
</style>
