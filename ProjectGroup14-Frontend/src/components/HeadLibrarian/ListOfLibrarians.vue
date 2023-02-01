<template>
  <div id="MainPage">
     <h1>Librarian</h1>
     <div style="margin-left:295px">
         <table style="margin-left:30px; margin-bottom:30px">
             <tr >
                 <th style="color: #0961b9; padding-left:20px">First name</th>
                 <th style="color: #0961b9;padding-left:20px">Last name</th>
                 <th style="color: #0961b9;padding-left:20px">Username</th>
                 <th style="color: #0961b9;padding-left:20px">Email</th>
                 <th style="color: #0961b9; padding-left:20px">Id</th>
             </tr>
             <tr v-for="librarian in librarians" :key="librarian.id">
                <td style="padding-left:20px">{{ librarian.firstName}}</td>
                <td style="padding-left:20px">{{ librarian.lastName}}</td>
                <td style="padding-left:20px">{{ librarian.username}}</td>
                <td style="padding-left:20px">{{ librarian.email}}</td>
                <td style="padding-left:20px">{{ librarian.id }}</td>
                <td><button class="btn btn-primary" style="margin-left:30px; font-size:26px" @click="DeleteLibrarian(librarian.id)" >Fire librarian</button></td>
             </tr>
         </table>
     </div>
     <div>
        <div>
          <span v-if="errorDeleteLibrarian" style="color:red"> {{errorDeleteLibrarian}} </span>
        </div>
        <a style="margin-top:50px"><router-link to="/HeadLibrarianMainPage">Back</router-link></a>
        <a style="margin-top:50px; margin-left:30px; color: #01070e"><router-link to="/CreateLibrarian">Hire new librarian</router-link></a>
     </div>
  </div>
</template>

<script src="./ListOfLibrarians.js">
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Varela+Round');

  #MainPage {
    font-family: 'Varela Round', sans-serif;
    color: #000000;
    background: #9fdfcb;
    margin: 20px;
    height: 100vh;
    margin: auto;
    width: 95vw;
    border: 3px solid #5bc2a1;
    padding: 10px;
    font-size: 28px;
  }

  input {
    text-align: center;
  }
</style>
