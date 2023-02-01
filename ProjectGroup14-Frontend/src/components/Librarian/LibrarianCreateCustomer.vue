<template>
  <div class="LibrarianCreateCustomer">
    <h3 style="padding-top:10px">Create Customer</h3>
    <div>
        <strong>Local Citizen</strong>
        <input  style ="margin:10px" type="checkbox" id="isLocal" v-model="is_local" required>
    </div>
    <div>
      <strong>Name:</strong>
      <input style="margin-top:15px; margin-left:40px" type="text" v-model="firstname" placeholder=" Firstname" required>
    </div>
    <div>
      <strong>Lastname:</strong>
      <input type="text" style="margin-top:15px; border-radius:5px; border-width:0px; margin-left:10px" v-model="lastname" placeholder=" Lastname" required>
    </div>
    <div>
      <strong>Address:</strong>
      <input style="margin-top:15px; margin-left:25px; margin-bottom: 15px" type="text" v-model="address" placeholder=" Address" required>
    </div>
    <div>
      <button :disabled="!firstname||!lastname||!address" @click="CreateCustomer(firstname, lastname, address)" class="btn btn-primary">Create Customer</button>
    </div>
    <div>
      <span v-if="createdCustomer" style="color:green"> {{ createdCustomer }} </span>
    </div>
    <div>
      <span v-if="errorCreateCustomer" style="color:red"> {{ errorCreateCustomer }} </span>
    </div>
    <div style="margin-bottom:40px; margin-top:20px">
         <a><router-link to="/ListOfCustomers">Back</router-link></a>
    </div>
  </div>
</template>

<script src="./LibrarianCreateCustomer.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.LibrarianCreateCustomer {
  font-family: 'Varela Round', Helvetica, Arial, sans-serif;
  background: #9fdfcb;
  height: 100vh;
  margin: auto;
  width: 95vw;
  border: 3px solid rgb(78, 184, 78);
  padding: 10px;
}
strong {
    margin-right: 10px;
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
