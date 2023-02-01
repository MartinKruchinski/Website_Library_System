<template>
  <div id="LoginCustomer">
    <h2>Library Group 14</h2>
    <div>
        <input style="margin-top:15px" type="text" v-model="username"  placeholder=" Username" required>
    </div>
        <input  style="margin-top:15px" type="password"  v-model="password"  placeholder=" Password" required>
    <div>
    </div>
    <div>
        <button style="margin-top:15px" @click="LoginCustomer(username,password)" class="btn btn-primary">Log in</button>
    </div>
    <div>
      <span v-if="errorCustomer" style="color:red">Error: {{errorCustomer}} </span>
    </div>
    <div div style="margin-top:20px">
      <strong> Don't have an account? Sign up!</strong>
    </div>
    <div style="margin-top:20px">
      <!-- <button @click="SignUpCustomer()" style="margin:25px; margin-left: 550px;" class="btn btn-primary"> Sign up </button> -->
      <a style="margin-left:10px"><router-link to="/">Main Page</router-link></a>
      <a style="margin-left:10px" @click="SignUpCustomer()"><router-link to="/">Sign Up</router-link></a>

    </div>
  </div>
</template>

<script src="./LoginCustomer.js">
</script>

<style>
  #LoginCustomer {
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
