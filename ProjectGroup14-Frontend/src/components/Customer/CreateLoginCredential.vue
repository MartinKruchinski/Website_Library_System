<template>
  <div id="SignUpCustomer" class="form-group">
    <h2>Sign Up</h2>
    <h4  style="margin:30px"> User Information </h4>
    <div>
        <input style ="margin:10px" type="text" v-model="email" placeholder=" Email" required>
    </div>
    <div>
      <input style ="margin:10px" type="text" v-model="username" placeholder=" Username" required>
    </div>
    <div>
      <input type= "password" style ="margin:10px" v-model="password" placeholder=" Password" required>
    </div>
    <div>
      <span v-if="errorCreateLogin" style="color:red"> {{errorCreateLogin}} </span>
    </div>
    <div style="margin-top:20px">
      <button  style ="" @click="EnterLoginCredentialInfo(username,password,email)" class="btn btn-primary"> Sign Up </button>
    </div>
    <div>
      <button style ="margin-top:30px" @click="BackToSignUp()" class="btn btn-primary"> Back </button>
    </div>
  </div>
</template>

<script src="./CreateLoginCredential.js">
</script>

<style>
  #SignUpCustomer {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
    margin: 10px;
    padding: 20px;
  }
  input {
    text-align: center;
  }

</style>
