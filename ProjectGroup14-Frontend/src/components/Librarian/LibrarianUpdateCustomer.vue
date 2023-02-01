<template>
  <div class="LibrarianUpdateCustomer">
    <h3 style="padding-top:10px">Update Customer</h3>
    <div class="select">
        <select v-model="selectedCustomer" @change="GetOptions( selectedCustomer )" class="form-control sl">
            <option value="null" disabled>Select Customer</option>
            <option v-for="customer in customers" :key="customer.id">{{ customer.firstName }} {{ customer.lastName }} {{ customer.id }}</option>
        </select>
    </div>
    <div>
        <strong>Local Citizen: {{ curIsLocal }}</strong>
        <input  style ="margin:10px" type="checkbox" id="isLocal" v-model="is_local" required>
        <button style="margin-left: 10px" :disabled="selectedCustomer=='null'" @click="UpdateIsLocal(curId)" class="btn btn-primary">Update Citizenship</button>
    </div>
    <div>
      <strong>Name: {{ curFirstname }}</strong>
      <input style="margin-top:15px; margin-left:40px" type="text" v-model="firstname" placeholder=" Firstname" required>
        <button style="margin-left: 10px" :disabled="!firstname||selectedCustomer=='null'" @click="UpdateFirstname(curId, firstname)" class="btn btn-primary">Update Firstname</button>
    </div>
    <div>
        <strong>Lastname: {{ curLastname }}</strong>
        <input type="text" style="margin-top:15px; border-radius:5px; border-width:0px; margin-left:10px" v-model="lastname" placeholder=" Lastname" required>
        <button style="margin-left: 10px" :disabled="!lastname||selectedCustomer=='null'" @click="UpdateLastname(curId, lastname)" class="btn btn-primary">Update Lastname</button>
    </div>
    <div>
      <strong>Address: {{ curAddress }}</strong>
      <input style="margin-top:15px; margin-left:25px; margin-bottom: 15px" type="text" v-model="address" placeholder=" Address" required>
      <button style="margin-left: 10px" :disabled="!address||selectedCustomer=='null'" @click="UpdateAddress(curId, address)" class="btn btn-primary">Update Address</button>
    </div>

    <div>
      <span v-if="updatedCustomer" style="color:green"> {{ updatedCustomer }} </span>
    </div>
    <div>
      <span v-if="errorUpdateCustomer" style="color:red"> {{ errorUpdateCustomer }} </span>
    </div>
    <div style="margin-bottom:40px; margin-top:20px">
         <a><router-link to="/ListOfCustomers">Back</router-link></a>
    </div>
  </div>
</template>

<script src="./LibrarianUpdateCustomer.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.LibrarianUpdateCustomer {
    font-family: 'Varela Round', Helvetica, Arial, sans-serif;
    background: #9fdfcb;
    height: 100vh;
    margin: auto;
    width: 95vw;
    border: 3px solid rgb(78, 184, 78);
    padding: 10px;
}
.select {
    margin: auto;
    width: max-content;
    padding: 10px;
    text-align: center;
    align-items: center;
    border-radius:5px;
    border-width:0px;
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
select {
    max-width: max-content;
}
</style>
