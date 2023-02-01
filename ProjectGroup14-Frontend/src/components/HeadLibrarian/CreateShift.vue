
<template>
    <div id="CreateShiftPage">
        <h1>Assign Shift</h1>
        <div id="cols">
            <div  style="width: 50%; float:left">

                <div style="width: 50%; float:left">
                    <p>Librarian</p>
                    <p>Day Of Week</p>
                    <p>Start Time</p>
                    <p>End Time</p>
                    <div>
                      <span v-if="errorCreateShift" style="color:red">Error: {{ errorCreateShift }} </span>
                    </div>
                    <div>
                      <span v-if="createdShift" style="color:green">{{ createdShift }} </span>
                    </div>
                    <div>
                      <a style="margin-top:50px; float:right"><router-link to="/ShiftPage">Back</router-link></a>
                  </div>
                </div>

                <div style="width: 50%; float:right">
                  <div class="select">
                        <select v-model="selectedLibrarian" @change="GetOptions(selectedLibrarian)" class="form-control sl">
                          <option value="null" disabled>Select Librarian</option>
                          <option v-for="librarian in librarians" :key="librarian.id">{{ librarian.username }} </option>
                        </select>
                  </div>
                  <div class="select">
                    <select v-model="selectedDay" class="form-control sl">
                      <option value="null" disabled>Select Day</option>
                      <option>Monday</option>
                      <option>Tuesday</option>
                      <option>Wednesday</option>
                      <option>Thursday</option>
                      <option>Friday</option>
                      <option>Saturday</option>
                      <option>Sunday</option>
                    </select>
                  </div>

                  <div class="select">
                      <select v-model="startTime" class="form-control sl">
                          <option value="null" disabled>Select Start Time</option>
                          <option value="8:00">8:00</option>
                          <option value="8:30">8:30</option>
                          <option value="9:00">9:00</option>
                          <option value="9:30">9:30</option>
                          <option value="10:00">10:00</option>
                          <option value="10:30">10:30</option>
                          <option value="11:00">11:00</option>
                          <option value="11:30">11:30</option>
                          <option value="12:00">12:00</option>
                          <option value="12:30">12:30</option>
                          <option value="13:00">13:00</option>
                          <option value="13:30">13:30</option>
                          <option value="14:00">14:00</option>
                          <option value="14:30">14:30</option>
                          <option value="15:00">15:00</option>
                          <option value="15:30">15:30</option>
                          <option value="16:00">16:00</option>
                          <option value="16:30">16:30</option>
                          <option value="17:00">17:00</option>
                          <option value="17:30">17:30</option>
                          <option value="18:00">18:00</option>
                          <option value="18:30">18:30</option>
                          <option value="19:00">19:00</option>
                          <option value="19:30">19:30</option>
                          <option value="20:00">20:00</option>
                      </select>
                  </div>

                  <div class="select">
                      <select v-model="endTime" class="form-control sl">
                          <option value="null" disabled>Select End Time</option>
                          <option value="8:00">8:00</option>
                          <option value="8:30">8:30</option>
                          <option value="9:00">9:00</option>
                          <option value="9:30">9:30</option>
                          <option value="10:00">10:00</option>
                          <option value="10:30">10:30</option>
                          <option value="11:00">11:00</option>
                          <option value="11:30">11:30</option>
                          <option value="12:00">12:00</option>
                          <option value="12:30">12:30</option>
                          <option value="13:00">13:00</option>
                          <option value="13:30">13:30</option>
                          <option value="14:00">14:00</option>
                          <option value="14:30">14:30</option>
                          <option value="15:00">15:00</option>
                          <option value="15:30">15:30</option>
                          <option value="16:00">16:00</option>
                          <option value="16:30">16:30</option>
                          <option value="17:00">17:00</option>
                          <option value="17:30">17:30</option>
                          <option value="18:00">18:00</option>
                          <option value="18:30">18:30</option>
                          <option value="19:00">19:00</option>
                          <option value="19:30">19:30</option>
                          <option value="20:00">20:00</option>
                      </select>
                  </div>

                </div>

            </div>

            <div id="col" style="width: 15%; float:left">
              <div style="width: 100%; float:left; margin-bottom: 20px">
                <button :disabled="selectedLibrarian=='null'||selectedDay=='null'||startTime=='null'||endTime=='null'" @click="CreateShift(selectedLibrarian, selectedDay, startTime, endTime)" class="btn btn-primary">Create Shift</button>
              </div>

              <h5 style="margin-top: 30px; margin-left:50px">Opening Hours</h5>
              <table style="margin-left:10px; margin-bottom:30px">
              <tr >
                  <th style="color: #0961b9; padding-left:20px">Day</th>
                  <th style="color: #0961b9;padding-left:20px">Start Time</th>
                  <th style="color: #0961b9;padding-left:20px">End Time</th>
              </tr>
              <tr v-for="hour in openingHours" :key="hour.hourId">
                  <td style="padding-left:20px">{{ hour.dayOfWeek}}</td>
                  <td style="padding-left:20px">{{ hour.startTime}}</td>
                  <td style="padding-left:20px">{{ hour.endTime}}</td>
              </tr>
              </table>
            </div>


        </div>
    </div>
</template>

<script src="./CreateShift.js">
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

    #CreateShiftPage {
      font-family: 'Varela Round', Helvetica, Arial, sans-serif;
      color: #000000;
      background: #9fdfcb;
      margin: 20px;
      height: 100vh;
      margin: auto;
      width: 95vw;
      border: 3px solid #5bc2a1;
      padding: 10px;
      font-size: 18px;
    }
    #timePickerStart {
      border-radius: 5px;
      /* max-width: 100%; */
      float: left;
    }
    #timePickerEnd {
      float: left;
      border-radius: 5px;
      /* max-width: 100%; */
    }
    .inner {
      width: 100%;
    }
    .select {
      margin-left: 20px;
      max-width: 295px;
      margin-bottom: 10px;
    }
    p {
      text-align: right;
      /* margin: 20px; */
      margin-bottom: 25px;
      font-size: 18px;
    }
    input {
      text-align: center;
      margin: 10px;
      max-width: 35%;
    }

</style>
