import axios from 'axios'
import Dropdown from 'vue-simple-search-dropdown';
import VueTimepicker from 'vue2-timepicker'


var config = require('../../../config')

var backendConfigurer = function () {
    switch (process.env.NODE_ENV) {
      case 'development':
        return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
      case 'production':
        return 'https://' + config.build.backendHost + ':' + config.build.backendPort
    }
  }
  
  var frontendConfigurer = function () {
    switch (process.env.NODE_ENV) {
      case 'development':
        return 'http://' + config.dev.host + ':' + config.dev.port
      case 'production':
        return 'https://' + config.build.host + ':' + config.build.port
    }
  }
  
  var backendUrl = backendConfigurer()
  var frontendUrl = frontendConfigurer()

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'UpdateOpeningHour',

    components: {
        Dropdown,
        VueTimepicker
    },

    data () {
        return {
            openingHourId: '',
            day: '',
            curId: '',
            curDay: '',
            curStart: '',
            curEnd: '',
            start_time: 'null',
            startTime: '',
            endTime: '',
            end_time: 'null',
            selectedHour: 'null',
            selectedDay: 'null',
            errorUpdateOpeningHour: '',
            updatedOpeningHour: '',
            openingHours: []
        }
    },

    created() {
        AXIOS.get('/openingHours/')
        .then(response => {
            this.openingHours = response.data
        })
    },


    methods: {
        GetOptions: function(id){
            const arr = id.split(" ");
            AXIOS.get("/openingHours/".concat(arr[1]))
            .then(response => {
                this.curId = response.data.id
                this.curDay = response.data.dayOfWeek
                this.curStart = response.data.startTime
                this.curEnd = response.data.endTime
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorUpdateOpeningHour = errorMsg.response.data
                this.updatedOpeningHour = ''
            })
        },
        UpdateDay: function(openingHourId, newDay, start_time, end_time){
            AXIOS.patch('/openingHours/edit/'.concat(openingHourId), {}, {
                params: {
                    day: newDay,
                    start_time: start_time,
                    end_time: end_time,
                }
            })
            .then( response => {
                this.day='',
                this.selectedHour='',
                this.selectedDay='null',
                this.startTime='',
                this.curId= '',
                this.endTime='',
                this.curDay='',
                this.curStart='',
                this.curEnd='',
                this.errorUpdateOpeningHour = '',
                this.updatedOpeningHour = "Updated Opening Hour"
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.updatedOpeningHour = ""
                this.errorUpdateOpeningHour = errorMsg.response.data
            })
        },
        UpdateStartTime: function(openingHourId, day, newStart, end_time){
            AXIOS.patch('/openingHours/edit/'.concat(openingHourId), {}, {
                params: {
                    day: day,
                    start_time: newStart.concat(":00"),
                    end_time: end_time,
                }
            })
            .then( response => {
                this.day='',
                this.selectedHour='',
                this.selectedDay='',
                this.startTime='',
                this.curId= '',
                this.endTime='',
                this.curDay='',
                this.curStart='',
                this.curEnd='',
                this.errorUpdateOpeningHour = "",
                this.updatedOpeningHour = 'Updated Opening Hour'
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorUpdateOpeningHour = errorMsg.response.data
                this.updatedOpeningHour = ''
            })
        },
        UpdateEndTime: function(openingHourId, day, start_time, newEnd){
            AXIOS.patch('/openingHours/edit/'.concat(openingHourId), {}, {
                params: {
                    day: day,
                    start_time: start_time,
                    end_time: newEnd.concat(":00"),
                }
            })
            .then( response => {
                this.day='',
                this.selectedHour='',
                this.selectedDay='',
                this.startTime='',
                this.endTime='',
                this.curDay='',
                this.curStart='',
                this.curEnd='',
                this.curId= '',
                this.errorUpdateOpeningHour = '',
                this.updatedOpeningHour = 'Updated Opening Hour'
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorUpdateOpeningHour = errorMsg.response.data
                this.updatedOpeningHour = ''
            })
        },
    }
}
