<template>
    <div id="CreateEvent">
        <h1>Create Event</h1>
        <div class="select">
            <span>Customer: </span>
            <select v-model="selectedCustomer">
                <option value="null" disabled>Select Customer</option>
                <option v-for="customer in customers" :key="customer.id">{{ customer.firstName }} {{ customer.lastName }} {{ customer.id }}</option>
            </select>
        </div>
        <div>
            <span>Date: </span>
            <input style="margin-top:20px" type="date" id="dateInput"  v-model="date" placeholder="Date" required>
        </div>
        <div>
          <span>Start time: </span>
            <input style="margin-top:15px; margin-left:auto; margin-right:auto"  v-model="startTime" type="time" id="startTimeInput" placeholder="Start Time" required>
        </div>
        <div>
            <span>End time: </span>
            <input style="margin-top:15px" v-model="endTime" type="time" id="endTimeInput" placeholder="End Time" required>
        </div>
        <div>
          <span v-if="errorCreateEvent" style="color:red"> {{errorCreateEvent}} </span>
        </div>
        <div>
          <span v-if="createdEvent" style="color:green"> {{createdEvent}} </span>
        </div>
        <div style="margin-top:20px">
            <button :disabled="selectedCustomer=='null'||!endTime||!startTime||!date" style ="margin-left:auto; margin-right:auto" @click="CreateEvent(selectedCustomer, date, startTime, endTime)" class="btn btn-primary"> Create Event</button>
        </div>
        <div>
             <button style ="margin-left:auto; margin-right:auto; margin-top:20px" @click="BackToEvents()" class="btn btn-primary"> Back </button>
        </div>
    </div>
</template>

<script src="./CreateEventLibrarian.js">
</script>

<style>

  #CreateEvent {
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
