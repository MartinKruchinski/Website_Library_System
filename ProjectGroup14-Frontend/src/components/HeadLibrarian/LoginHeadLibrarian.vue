<template>
  <div id="LoginCustomer">
    <h2>Login Head Librarian</h2>
    <div>
        <input style="margin-top:15px" type="text" v-model="username"  placeholder=" Username" required>
    </div>
        <input  style="margin-top:15px" type="password"  v-model="password"  placeholder=" Password" required>
    <div style="margin-top:10px;">
        <span v-if="errorLibrarian" style="color:red"> {{errorLibrarian}} </span>
    </div>
    <div>
        <button style="margin-top:15px" @click="LoginLibrarian(username,password)" class="btn btn-primary">Log in</button>
    </div>
    <div style="margin-top:40px; margin-right:5px">
      <a style="margin-left:10px"><router-link to="/">Main Page</router-link></a>
      <a style="margin-left:10px"><router-link to="/LoginLibrarian">Login as Librarian</router-link></a>
    </div>
  </div>
</template>

<script src="./LoginHeadLibrarian.js">
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
    border-radius:5px;
    border-width:0px;
  }

</style>
