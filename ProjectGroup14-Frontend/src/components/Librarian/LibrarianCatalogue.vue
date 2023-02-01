<template>
  <div id="LibrarianCatalogue">
     <h1>Catalog</h1>
     <div style="margin-right:20px">
         <table style="margin-right:auto; margin-left:auto">
             <tr>
                 <th style="color: #0961b9;padding-left:20px">Id</th>
                 <th style="color: #0961b9;padding-left:20px">Item type</th>
                 <th style="color: #0961b9; padding-left:20px">Title</th>
                 <th style="color: #0961b9; padding-left:20px">Author</th>
                 <th style="color: #0961b9; padding-left:20px">Available</th>
             </tr>
             <tr v-for="item in items" :key="item.itemId">
                <td style="padding-left:20px">{{ item.id }}</td>
                <td style="padding-left:20px">{{ item.itemType}}</td>
                <td style="padding-left:20px">{{item.title }}</td>
                <td style="padding-left:20px">{{item.author }}</td>
                <td style="padding-left:20px">{{item.isAvailable }}</td>
                <a style="padding-left:20px" @click="GetItem(item.id)"> <router-link to="/LibrarianUpdateItem">Update</router-link></a>
                <a style="padding-left:20px" @click="DeleteItem(item.id)"> <router-link to="/LibrarianCatalogue">Delete</router-link></a>
             </tr>
         </table>
     </div>
     <div style="margin-top:15px" >
       <a style="margin-left:10px"><router-link to="/LibrarianCreateItem">Create Item</router-link></a>
     </div>
     <div>
        <button style="margin-top:50px" @click="back()" class="btn btn-primary"> Back </button>

     </div>
  </div>
</template>

<script src="./LibrarianCatalogue.js">
</script>

<style>
  #LibrarianCatalogue {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #010a13;
    background: #f2ece8;
    margin: 20px;
    padding: 40px;
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
