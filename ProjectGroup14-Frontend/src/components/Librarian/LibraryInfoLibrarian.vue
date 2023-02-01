<template>
  <div class="LibraryInfoLibrarian">
    <h1>{{ name }}</h1>
    <h3 style="padding-top:10px;">Information</h3>
    <div>
      <strong>Name: {{ name }}</strong>
    </div>
    <div>
      <strong>Address: {{ address }} </strong>
    </div>
    <div>
      <strong>Number: {{ number }}</strong>
    </div>
    <div>
      <strong>Email: {{ email }}</strong>
    </div>
    <div style="margin-bottom:40px; margin-top:20px">
         <a><router-link to="/LibrarianMainPage">Back</router-link></a>
    </div>
  </div>
</template>

<script src="./LibraryInfoLibrarian.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

.LibraryInfoLibrarian {
  font-family: 'Varela Round', Helvetica, Arial, sans-serif;
  background: #9fdfcb;
  height: 100vh;
  margin: auto;
  width: 95vw;
  border: 3px solid rgb(78, 184, 78);
  padding: 10px;
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
h3 {
    color: #1d42b9;
}
a {
  font-size: 20px;
  color: #1d42b9;
}
</style>
