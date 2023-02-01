<template>
  <div class="LibrarianCheckoutItem">
    <h3 style="padding-top:10px">Checkout Item for Customer</h3>
    <div class="select">
        <select v-model="selectedCustomer" @change="GetCustomerOptions( selectedCustomer )" class="form-control sl">
            <option value="null" disabled>Select Customer</option>
            <option v-for="customer in customers" :key="customer.id">{{ customer.firstName }} {{ customer.lastName }} {{ customer.id }}</option>
        </select>
    </div>
    <div>
      <strong>Customer Id: {{ customerId }}</strong>
    </div>
    <div>
      <strong>Name: {{ firstname }}</strong>
    </div>
    <div>
        <strong>Lastname: {{ lastname }}</strong>
    </div>
    <div>
      <strong>Address: {{ address }}</strong>
    </div>
    <div class="select">
        <select v-model="selectedItem" @change="GetItemOptions( selectedItem )" class="form-control sl">
            <option value="null" disabled>Select Item</option>
            <option v-for="item in items" :key="item.id">{{ item.title }} {{ item.author }} {{ item.id }}</option>
        </select>
    </div>
        <div>
      <strong>Item Id: {{ itemId }}</strong>
    </div>
    <div>
      <strong>Item Title: {{ itemTitle }}</strong>
    </div>
    <div>
        <strong>Item Author: {{ itemAuthor }}</strong>
    </div>
    <div>
      <strong>Item Type: {{ itemType }}</strong>
    </div>
    <div>
      <span v-if="checkedOutItem" style="color:green"> {{ checkedOutItem }} </span>
    </div>
    <div>
      <span v-if="errorCheckoutItem" style="color:red"> {{ errorCheckoutItem }} </span>
    </div>
    <div>
        <button :disabled="selectedItem=='null'||selectedCustomer=='null'" style="margin-top:20px" @click="CheckoutItem()" class="btn btn-primary"> Checkout Item </button>
    </div>
    <div style="margin-bottom:40px; margin-top:20px">
         <a><router-link to="/ListOfBookings">Back</router-link></a>
    </div>
  </div>
</template>

<script src="./LibrarianCheckoutItem.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.LibrarianCheckoutItem {
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
strong {
    color: rgb(35, 88, 14);
}
a {
    font-size: 20px;
    color: #1d42b9;
}
select {
    max-width: max-content;
}
</style>
