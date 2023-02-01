<template>
  <div class="UpdateLibrary">
    <h1>Account Information</h1>
    <div>
      <strong>First name: {{ name }}</strong>
      <input style="margin-top:15px; margin-left:25px" type="text" v-model="newName" placeholder=" New first name" required>
      <button :disabled="!newName" @click="UpdateFirstName(newName, lastName)"  class="btn btn-primary">Update First name</button>
    </div>
     <div>
      <strong>Last name: {{ lastName }}</strong>
      <input style="margin-top:15px; margin-left:25px" type="text" v-model="newLastName" placeholder=" New last name" required>
      <button :disabled="!newLastName" @click="UpdateLastName(name, newLastName)"  class="btn btn-primary">Update Name</button>
    </div>
    <div>
      <strong>Username: {{ username }} </strong>
      <input style="margin-top:15px; margin-left:10px" type="text" v-model="newUsername" placeholder=" New username" required>
      <button :disabled="!newUsername" @click="UpdateUsername(newUsername)" class="btn btn-primary">Update Username</button>
    </div>
    <div>
      <strong>Password: {{ password }}</strong>
      <input style="margin-top:15px; margin-left:15px" type="password" v-model="newPassword" placeholder=" New password" required>
      <button :disabled="!newPassword" @click="UpdatePassword(newPassword)" class="btn btn-primary">Update Password</button>
    </div>
    <div>
      <strong>Email: {{ email }}</strong>
      <input  style="margin-top:15px; border-radius:5px; border-width:0px; margin-left:40px" type="text" v-model="newEmail" placeholder=" New email" required>
      <button :disabled="!newEmail" @click="UpdateEmail(newEmail)" class="btn btn-primary">Update Email</button>
    </div>
    <div>
      <span v-if="errorUpdateLibrarian" style="color:red"> {{errorUpdateLibrarian}} </span>
    </div>
    <div>
      <span v-if="successUpdateLibrarian" style="color:green"> {{successUpdateLibrarian}} </span>
    </div>
    <div style="margin-bottom:40px; margin-top:40px">
         <button @click="back()" class="btn btn-primary" style="width: 60px">Back</button>
    </div>
  </div>
</template>

<script src="./UpdateLibrarianAccount.js">
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
