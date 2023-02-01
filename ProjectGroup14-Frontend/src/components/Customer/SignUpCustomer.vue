<template>
  <div id="SignUpCustomer" class="form-group">
    <h2>Sign Up</h2>
    <h4  style="margin:30px"> Personal information </h4>
    <div>
      <label for="isLocal">
        Local Citizen <input  style ="margin:10px" id="isLocal" type="checkbox" v-model="local" required>
      </label>
    </div>
    <div>
        <input style ="margin:10px" type="text" v-model="firstname" placeholder=" Name" required>
    </div>
    <div>
      <input style ="margin:10px" type="text" v-model="lastname" placeholder=" Last Name" required>
    </div>
    <div>
      <input style ="margin:10px" type="text" v-model="address" placeholder=" Address" required>
    </div>
    <div>
        <span v-if="errorCreating" style="color:red" > Error: {{errorCreating}} </span>
    </div>
    <div>
      <button style ="margin-top:10px"  @click="EnterLoginCredentialInfo(firstname,lastname,address)" class="btn btn-primary"> Continue </button>
    </div>
    <div>
      <button style =" margin-top:10px"  @click="BackToLogin()" class="btn btn-primary"> Back </button>
    </div>
  </div>
</template>

<script src="./SignUpCustomer.js">
</script>

<style>
  #SignUpCustomer {
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
