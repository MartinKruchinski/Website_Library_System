<template>
  <div id="CustomerCatalogue">
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
                <td><button :disabled="!item.isAvailable" class="btn btn-primary" style="margin-left:30px; font-size:14px" @click="ReserveItem(item.id)" >Reserve Item</button></td>
             </tr>
         </table>
          <div>
            <span v-if="errorReserveItem" style="color:red"> {{errorReserveItem}} </span>
          </div>
          <div>
            <span v-if="reservedItem" style="color:green"> {{reservedItem}} </span>
          </div>
     </div>
     <div>
        <button style="margin-top:50px" @click="back()" class="btn btn-primary"> Back </button>

     </div>
  </div>
</template>

<script src="./CustomerCatalogue.js">
</script>

<style>
  #CustomerCatalogue {
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
