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
    name: 'CreateOpeningHour',

    components: {
        Dropdown,
        VueTimepicker
    },

    data () {
        return {
            openingHours: [],
            day: '',
            start_time: '',
            end_time: '',
            selected:'null',
            errorCreateOpeningHour: '',
            createdOpeningHour: '',
            start_time: 'null',
            end_time: 'null'
        }
    },

    methods: {
        CreateOpeningHour: function(day, start_time, end_time){
            AXIOS.post('/openingHour/'.concat(day), {}, {
                params: {
                    start_time: start_time.concat(":00"),
                    end_time: end_time.concat(":00")
                }
            })
            .then(
                day= '',
                start_time= '',
                end_time= '',
                this.day= '',
                this.start_time= '',
                this.end_time= '',
                this.selected='null',
                this.errorCreateOpeningHour= '',
                this.createdOpeningHour= '',
                this.errorCreateOpeningHour= '',
                this.start_time= 'null',
                this.end_time= 'null',
                this.createdOpeningHour= 'Created Opening Hour'
            )
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorCreateOpeningHour = errorMsg.response.data
                this.createdOpeningHour= ''
            })
        }
    }
}
