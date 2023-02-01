import axios from 'axios'
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
    name: 'ShiftPage',

    data() {
        return {
            shifts: [],
            errorDeleteShift: ''
        }
    },
    created() {
        AXIOS.get('/shifts/')
        .then(response => {
            this.shifts = response.data
        })
    },
    methods: {
        DeleteShift: function(shiftId){
            AXIOS.delete('/shifts/delete/'.concat(shiftId), {}, {})
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorDeleteShift = errorMsg.response.data
            }).then(window.location.reload())
        }
    },
}
