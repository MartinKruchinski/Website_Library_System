
<template>
    <div id="UpdateOpeningHourPage">
        <h1>Update Opening Hour</h1>
        <div id="cols">
            <div  style="width: 50%; float:left">

                <div style="width: 50%; float:left; margin-top:20px">
                    <p>Opening Hour: {{ curId }}</p>
                    <p>Day Of Week: {{ curDay }}</p>
                    <p>Start Time: {{ curStart }}</p>
                    <p>End Time: {{ curEnd }}</p>
                    <div>
                      <p>
                        <span v-if="errorUpdateOpeningHour" style="color:red">Error: {{ errorUpdateOpeningHour }} </span>
                        <span v-if="updatedOpeningHour" style="color:green">{{ updatedOpeningHour }} </span>
                      </p>
                      <a style="float:right"><router-link to="/OpeningHoursHead">Back</router-link></a>
                  </div>
                </div>

                <div style="width: 50%; float:left; margin-top:18px">
                  <div>

                      <select v-model="selectedHour" @change="GetOptions(selectedHour)" class="form-control sl">
                        <option value="null" disabled>Select Opening Hour</option>
                        <option v-for="hour in openingHours" :key="hour.id">{{ hour.dayOfWeek }} {{ hour.id }}</option>
                      </select>

                      <select v-model="selectedDay" class="form-control sl">
                          <option value="null" disabled>Select New Day</option>
                          <option value="Monday">Monday</option>
                          <option value="Tuesday">Tuesday</option>
                          <option value="Wednesday">Wednesday</option>
                          <option value="Thursday">Thursday</option>
                          <option value="Friday">Friday</option>
                          <option value="Saturday">Saturday</option>
                          <option value="Sunday">Sunday</option>
                      </select>
                  </div>

                  <div class="select">

                      <select v-model="start_time" class="form-control sl">
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

                      <select v-model="end_time" class="form-control sl">
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

            <div id="col" style="width: 20%; float:left; margin-top:60px">
              <div style="width: 100%; float:left">
                <button :disabled="selectedHour=='null'||selectedDay=='null'" @click="UpdateDay(curId, selectedDay, curStart, curEnd)" class="btn btn-primary">Update Day</button>
              </div>
              <div style="width: 100%; float:left">
                <button :disabled="selectedHour=='null'||start_time=='null'" @click="UpdateStartTime(curId, curDay, start_time, curEnd)" class="btn btn-primary">Update Start Time</button>
              </div>
              <div style="width: 100%; float:left">
                <button :disabled="selectedHour=='null'||end_time=='null'" @click="UpdateEndTime(curId, curDay, curStart, end_time)" class="btn btn-primary">Update End Time</button>
              </div>
            </div>


        </div>

    </div>
</template>

<script src="./UpdateOpeningHours.js">
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

    #UpdateOpeningHourPage {
        font-family: 'Varela Round', Helvetica, Arial, sans-serif;
        color: #000000;
        background: #9fdfcb;
        margin: 20px;
        height: 100vh;
        margin: auto;
        width: 95vw;
        border: 3px solid #5bc2a1;
        padding: 10px;
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
      /* margin: 10px; */
    }
    p {
      text-align: right;
      margin-bottom: 25px;
      font-size: 18px;
    }
    button {
      margin: 10px;
      min-width: 200px;
    }
    select {
      margin-right: 20px;
      margin-left: 20px;
      margin-bottom: 15px;
      /* max-width: 20px; */
    }
    input {
      text-align: center;
      /* margin: 10px; */
      max-width: 35%;
    }

</style>
