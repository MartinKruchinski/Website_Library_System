<template>
<div id="LibrarianUpdateItem">
  <h2>Update Item </h2>
  <table style="margin-left:auto; margin-right:auto">
  <tr>Item Type: {{itemType}}
     <select style="margin-top:10px" id="ItemType" Size="Number_of_options">
            <option> Book </option>
            <option> Newspaper</option>
            <option> Archive </option>
            <option> Video </option>
            <option> CD </option>
    </select>
  </tr>
  <tr>Author: {{author}} <input style="margin-top:10px" type="text" v-model="newauthor" placeholder="New author"></tr>
  <tr>Title: {{title}} <input style="margin-top:10px" type="text" v-model="newtitle" placeholder="New title"></tr>
  <tr >Available: {{isAvailable}} <input style="margin-top:10px" type="checkbox" id="availability" placeholder="YesorNo"></tr>

  <tr style="margin-top:30px">
  <button style="margin-top:30px" :disabled="!newauthor||!newtitle" class="btn btn-primary" @click="createItem(newauthor,newtitle)">Update</button>
  </tr>
  </table>
  <div style="margin-top:30px">
    <span v-if="successmessage" style="color:green"> {{ successmessage }} </span>
  </div>
  <div>
    <a style="margin-left:10px;"> <router-link to="/LibrarianCatalogue">Back</router-link></a>
  </div>
</div>
</template>


<script src="./LibrarianUpdateItem.js">
</script>


<style>
 #LibrarianUpdateItem{
    font-family: 'Varela Round', Helvetica, Arial, sans-serif;
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
select {
    border-radius:5px;
    border-width:0px;
}
input {
    border-radius:5px;
    border-width:0px;
}
</style>
