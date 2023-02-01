
<template>
    <div id="OpeningHourPage">
        <h1>Create Opening Hour</h1>
        <div id="cols">
            <div  style="width: 50%; float:left">
                    <p>Day Of Week</p>
                    <p>Start Time</p>
                    <p>End Time</p>

                <div style="width: 50%; float:left">

                    <div>
                    <p>
                      <span v-if="errorCreateOpeningHour" style="color:red">Error: {{ errorCreateOpeningHour }} </span>
                      <span v-if="createdOpeningHour" style="color:green">{{ createdOpeningHour }} </span>
                    </p>
                    </div>
                </div>

                <div style="width: 50%; float:left">



                </div>

            </div>

            <div id="col" style="width: 20%; float:left">
                                <div class="inner">

                     <select v-model="selected" class="form-control sl">
                            <option value="null" disabled>Select Day</option>
                            <option value="Monday">Monday</option>
                            <option value="Tuesday">Tuesday</option>
                            <option value="Wednesday">Wednesday</option>
                            <option value="Thursday">Thursday</option>
                            <option value="Friday">Friday</option>
                            <option value="Saturday">Saturday</option>
                            <option value="Sunday">Sunday</option>
                        </select>

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

                  <div class="inner">

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
              <div style="width: 100%; float:left">
                <button :disabled="selected=='null'||start_time=='null'||end_time=='null'" @click="CreateOpeningHour(selected, start_time, end_time)" class="btn btn-primary">Create Opening Hour</button>
              </div>
                <a style="margin-top:20px; float:left"><router-link to="/OpeningHoursHead">Back</router-link></a>
            </div>
        </div>
    </div>
</template>

<script src="./CreateOpeningHour.js">
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

    #OpeningHourPage {
      font-family: 'Varela Round', sans-serif;
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
    select {
      margin-left: 10px;
      margin-bottom: 10px;
      max-width: 180px;
      float: right;
    }
    #timePickerStart {
      border-radius: 5px;
      float: left;
    }
    #timePickerEnd {
      float: left;
      border-radius: 5px;
    }
    .inner {
      width: 100%;
    }
    p {
      text-align: right;
      margin: 20px;
      font-size: 18px;
    }
    input {
      text-align: center;
      margin: 10px;
      max-width: 35%;
    }
    button {
      margin-top: 20px;
    }

</style>
