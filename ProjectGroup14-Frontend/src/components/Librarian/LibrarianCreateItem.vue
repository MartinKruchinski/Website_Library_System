<template>
<div id="LibrarianCreateItem">
  <h2>Create Item </h2>
  <table style="margin-left:auto; margin-right:auto">
  <tr>
  <td>Item Type</td>
  <td>Author</td>
  <td>Title</td>
  <td>ItemAvailable</td>
  </tr>
  <tr>
  <td>
    <select id="ItemType" Size="Number_of_options">
            <option> Book </option>
            <option> Newspaper</option>
            <option> Archive </option>
            <option> Video </option>
            <option> CD </option>
    </select>
  </td>
  <td> <input type="text" v-model="author" placeholder="Author"> </td>
  <td> <input type="text" v-model="title" placeholder="Title"> </td>
  <td> <input type="checkbox" id="availability" placeholder="YesorNo"> </td>
  <td>
  <button :disabled="!author||!title" class="btn btn-primary" @click="createItem(author,title)">Create</button>
  </td>
  </tr>
  </table>
  <div style="margin-top:30px">
    <span v-if="successmessage" style="color:green"> {{ successmessage }} </span>
  </div>
  <div>
    <a style="margin-left:auto; margin-right:auto"> <router-link to="/LibrarianCatalogue">Back</router-link></a>
  </div>
</div>
</template>


<script src="./LibrarianCreateItem.js">
</script>


<style>
 #LibrarianCreateItem{
    font-family: 'Varela Round', Helvetica, Arial, sans-serif;
    background: #9fdfcb;
    height: 100vh;
    margin: auto;
    width: 95vw;
    border: 3px solid rgb(78, 184, 78);
    padding: 10px;
  }
  input {
    margin-left: 10px;
    border-radius:5px;
    border-width:0px;
    max-width:max-content;
  }
</style>
