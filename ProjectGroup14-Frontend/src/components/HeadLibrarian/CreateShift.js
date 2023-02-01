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
    name: 'CreateShift',

    components: {
        Dropdown,
        VueTimepicker
    },

    data () {
        return {
            librarians: [],
            day: '',
            curId: '',
            startTime: 'null',
            endTime: 'null',
            errorCreateShift: "",
            createdShift: '',
            selectedLibrarian: "null",
            selectedDay: 'null',
            openingHours: []
        }
    },

    created() {
        AXIOS.get('/librarians/')
        .then(response => {
            this.librarians = response.data
        }),
        AXIOS.get('/openingHours/')
        .then(response => {
            this.openingHours = response.data
            console.log(this.openingHours)
        })
    },

    methods: {
        GetOptions: function(username){
            AXIOS.get("/librarian/".concat(username))
            .then(response => {
                this.curId = response.data.id
            })
        },
        CreateShift: function(username, day, startTime, endTime){
            AXIOS.post('/shift/'.concat(this.curId), {}, {
                params: {
                    day: day,
                    start_time: startTime.concat(":00"),
                    end_time: endTime.concat(":00"),
                }
            })
            .then(response => {
                this.day= '',
                this.startTime= 'null',
                this.curId= '',
                this.endTime= 'null',
                this.errorCreateShift= "",
                this.createdShift= "Created Shift",
                this.selectedLibrarian= "null",
                this.selectedDay= 'null'
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.createdShift= ""
                this.errorCreateShift = errorMsg.response.data

            })
        }
    }
}
