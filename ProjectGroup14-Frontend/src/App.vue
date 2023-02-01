<template>
  <div id="app">
    <img style="width: 100vw; height:40vh" src="./assets/logo.png">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

#app {

  font-family: 'Varela Round', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
