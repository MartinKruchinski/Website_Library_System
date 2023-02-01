<template>
  <div class="UpdateLibrary">
    <h1>{{ name }}</h1>
    <h3 style="padding-top:10px">Information</h3>
    <div>
      <strong>Name: {{ name }}</strong>
      <input style="margin-top:15px; margin-left:25px" type="text" v-model="newName" placeholder=" New name" required>
      <button :disabled="!newName" @click="UpdateName(newName, address, phone_number, email)"  class="btn btn-primary">Update Name</button>
    </div>
    <div>
      <strong>Address: {{ address }} </strong>
      <input style="margin-top:15px; margin-left:10px" type="text" v-model="newAddress" placeholder=" New address" required>
      <button :disabled="!newAddress" @click="UpdateAddress(name, newAddress, phone_number, email)" class="btn btn-primary">Update Address</button>
    </div>
    <div>
      <strong>Number: {{ phone_number }}</strong>
      <input type="text" style="margin-top:15px; border-radius:5px; border-width:0px; margin-left:10px" v-model="newNum" placeholder=" New number" required>
      <button :disabled="!newNum" @click="UpdatePhone(name, address, newNum, email)" class="btn btn-primary">Update Number</button>
    </div>
    <div>
      <strong>Email: {{ email }}</strong>
      <input style="margin-top:15px; margin-left:15px" type="text" v-model="newEmail" placeholder=" New email" required>
      <button :disabled="!newEmail"  @click="UpdateEmail(name, address, phone_number, newEmail)" class="btn btn-primary">Update Email</button>
    </div>
    <div>
      <span v-if="errorUpdateLibrary" style="color:red"> {{errorUpdateLibrary}} </span>
    </div>
    <div style="margin-bottom:40px; margin-top:20px">
         <a><router-link to="/HeadLibrarianMainPage">Back</router-link></a>
    </div>
  </div>
</template>

<script src="./UpdateLibrary.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.UpdateLibrary {
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
