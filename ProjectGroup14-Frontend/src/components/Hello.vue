<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h3 style="margin-top:20px">Are you a librarian?</h3>
    <div>
      <a style="margin-left:10px; font-size:20px"><router-link to="/LoginLibrarian">Log in as a librarian</router-link></a>
    </div>
    <h3 style="margin-top:20px" >Are you a customer? </h3>
    <div>
      <a style="margin-left:10px; font-size:20px"><router-link to="/LoginCustomer">Log in as a customer</router-link></a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Library of Montreal'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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

a {
  color: #42b983;
}
</style>
