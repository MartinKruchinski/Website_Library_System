<template>
  <div class="LibrarianCreateLoginCredential">
    <h3 style="padding-top:10px">Create Login Credential For Customer</h3>
    <div class="select">
        <select v-model="selectedCustomer" @change="GetOptions( selectedCustomer )" class="form-control sl">
            <option value="null" disabled>Select Customer</option>
            <option v-for="customer in customers" :key="customer.id">{{ customer.firstName }} {{ customer.lastName }} {{ customer.id }}</option>
        </select>
    </div>
    <div>
        <strong>Local Citizen: {{ curIsLocal }}</strong>
        <strong>Name: {{ curFirstname }}</strong>
        <strong>Lastname: {{ curLastname }}</strong>
        <strong>Address: {{ curAddress }}</strong>
    </div>
    <div>
        <strong>Username</strong>
        <input style="margin-top:15px; margin-left:40px" type="text" v-model="username" placeholder=" Username" required>
    </div>
    <div>
        <strong>Password</strong>
        <input type="password" style="margin-top:15px; border-radius:5px; border-width:0px; margin-left:10px" v-model="password" placeholder=" Password" required>
        <input type="password" style="margin-top:15px; border-radius:5px; border-width:0px; margin-left:10px" v-model="password2" placeholder=" Verify Password" required>
    </div>
    <div v-if="password" style="margin-top: 10px">
        {{ MatchPasswords(password, password2) }}
    </div>
    <div>
        <strong>Email</strong>
      <input style="margin-top:15px; margin-left:25px; margin-bottom: 15px" type="text" v-model="email" placeholder=" Email" required>
    </div>

    <div>
      <span v-if="createLogin" style="color:green"> {{ createLogin }} </span>
    </div>
    <div>
      <span v-if="errorCreateLogin" style="color:red"> {{ errorCreateLogin }} </span>
    </div>
    <div>
        <button style="margin-left: 10px" :disabled="!username||password!=password2||!email||selectedCustomer=='null'" @click="CreateLoginCredential(username, password, email, curId)" class="btn btn-primary">Create Login Credential</button>
    </div>
    <div style="margin-bottom:40px; margin-top:20px">
        <a><router-link to="/ListOfCustomers">Back</router-link></a>
    </div>
  </div>
</template>

<script src="./LibrarianCreateLoginCredential.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.LibrarianCreateLoginCredential {
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
