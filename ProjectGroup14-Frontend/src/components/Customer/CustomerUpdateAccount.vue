<template>
  <div class="UpdateCustomer">
    <h1>Account Information</h1>
    <div>
      <strong>Username: {{ username }}</strong>
      <input style="margin-top:15px; margin-left:25px" type="text" v-model="newUsername" placeholder=" New Username" required>
      <button :disabled="!newUsername" @click="UpdateUsername(newUsername)"  class="btn btn-primary">Update Username</button>
    </div>
     <div>
      <strong>Password: {{ password }}</strong>
      <input style="margin-top:15px; margin-left:25px" type="password" v-model="newPassword" placeholder=" New Password" required>
      <button :disabled="!newPassword" @click="UpdatePassword(newPassword)"  class="btn btn-primary">Update Password</button>
    </div>
    <div>
      <strong>Email: {{ email }} </strong>
      <input style="margin-top:15px; margin-left:10px" type="text" v-model="newEmail" placeholder=" New Email" required>
      <button :disabled="!newEmail" @click="UpdateEmail(newEmail)" class="btn btn-primary">Update Email</button>
    </div>
    <div>
      <span v-if="errorUpdateCustomer" style="color:red"> {{errorUpdateCustomer}} </span>
    </div>
    <div>
      <span v-if="successUpdateCustomer" style="color:green"> {{successUpdateCustomer}} </span>
    </div>
    <div style="margin-top:20px">
      <a><router-link to="/CustomerUpdatePersonalInfo">Back</router-link></a>
    </div>
  </div>
</template>

<script src="./CustomerUpdateAccount.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.UpdateCustomer {
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
