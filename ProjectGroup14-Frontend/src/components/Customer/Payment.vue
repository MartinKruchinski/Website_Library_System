<template>
  <div id="MainPage">
     <h1>Payment</h1>
     <h5>A small one-time fee of 30$ needs to be paid when you are not a local customer! </h5>
     <div style="margin-top:30px">
          <strong style="color:#0a0e92">Select your card</strong>  
          <select Name="Name_of_list_box" Size="Number_of_options">  
          <option> Visa </option>  
          <option> MasterCard</option>  
          <option> American Express </option>  
        </select>  
     </div>
     <div style="margin-top:30px">
         <strong style="color:#0a0e92">Card number</strong>
         <input type="text" v-model="number" placeholder="**** **** ****" required>
     </div>
     <div style="margin-top:30px">
         <strong style="color:#0a0e92">Name on card</strong>
         <input type="text" v-model="name" placeholder="John Smith" required>
     </div>
     <div style="margin-top:30px">
         <strong style="color:#0a0e92">Expiration date</strong>
         <input type="text" v-model="date" placeholder="Month/Year" required>
     </div>
     <div style="margin-top:15px">
       <span v-if="errorPayment" style="color:red">Error: {{errorPayment}} </span>
     </div>
      <div style="margin-top:15px">
       <span v-if="successPayment" style="color:green">{{successPayment}} </span>
     </div>
     <div>
         <button @click="pay(number,name,date)" style="margin-top:40px;" class="btn btn-primary"> Pay </button>
     </div>
     <div>
       <button style ="margin-top:60px;" @click="BackToSignUp()" class="btn btn-primary"> Back </button>
     </div>
  </div>
</template>

<script src="./Payment.js">
</script>

<style>
  #MainPage {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #000005;
    background: #eae8f2;
    margin: 20px;
    padding: 40px;
  }
  
  input {
    text-align: bottom;
  }
</style>
