<template>
  <div id="MainPage">
     <h1>Customers</h1>
     <div>
         <table style="margin-left:auto; margin-right:auto">
             <tr >
                 <th style="color: #0961b9; padding-left:20px">First name</th>
                 <th style="color: #0961b9;padding-left:20px">Last name</th>
                 <th style="color: #0961b9; padding-left:20px">Address</th>
                 <th style="color: #0961b9; padding-left:20px">Local Resident</th>
                 <th style="color: #0961b9; padding-left:20px">Id</th>
             </tr>
             <tr v-for="customer in customers" :key="customer.id">
                <td style="padding-left:20px">{{ customer.firstName }}</td>
                <td style="padding-left:20px">{{ customer.lastName }}</td>
                <td style="padding-left:20px">{{ customer.address }}</td>
                <td style="padding-left:20px">{{ customer.isLocal }}</td>
                <td style="padding-left:20px">{{ customer.id }}</td>
                <td><button class="btn btn-primary" style="margin-left:30px; font-size:16px" @click="DeleteCustomer(customer.id)" >Delete Customer</button></td>
             </tr>
         </table>
      </div>
      <div style= "margin-top: 20px; margin-bottom:10px">
          <a><router-link to="/LibrarianCreateCustomer">New Customer</router-link></a>
      </div>
      <div style= "margin-bottom:10px">
        <a><router-link to="/LibrarianUpdateCustomer">Update Customer</router-link></a>
      </div>
      <div style= "margin-bottom:10px">
          <a><router-link to="/LibrarianCreateLoginCredential">Create Login Credential For Customer</router-link></a>
      </div>

      <div>
        <button @click="BackToMainPage()" class="btn btn-primary"> Back </button>
      </div>
  </div>
</template>

<script src="./ListOfCustomers.js">
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

    #MainPage {
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
</style>
