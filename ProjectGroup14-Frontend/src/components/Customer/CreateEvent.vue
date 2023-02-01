<template>
    <div id="CreateEvent">
        <h1>Create Event</h1>
        <div>
            <span>Date: </span>
            <input style="margin-top:20px" type="date" id="dateInput" placeholder="Date" required>
        </div>
        <div>
          <span>Start time: </span>
            <input style="margin-top:15px; margin-left:auto; margin-right:auto" type="time" id="startTimeInput" placeholder="Start Time" required>
        </div>
        <div>
            <span>End time: </span>
            <input style="margin-top:15px" type="time" id="endTimeInput" placeholder="End Time" required>
        </div>
        <div>
          <span v-if="errorEvent" style="color:red"> {{errorEvent}} </span>
        </div>
        <div>
          <span v-if="successEvent" style="color:green"> {{successEvent}} </span>
        </div>
        <div style="margin-top:20px">
            <button  style ="margin-left:auto; margin-right:auto" @click="CreateEvent()" class="btn btn-primary"> Create Event </button>
        </div>
        <div>
            <button style ="margin-left:auto; margin-right:auto; margin-top:20px" @click="BackToEvents()" class="btn btn-primary"> Back </button>
        </div>
    </div>
</template>

<script src="./CreateEvent.js">
</script>

<style>

  #CreateEvent {
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
