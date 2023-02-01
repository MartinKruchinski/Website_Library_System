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
    name: 'OpeningHours',

    data() {
        return {
            openingHours: [],
            errorDeleteOpeningHour: ''
        }
    },
    created() {
        AXIOS.get('/openingHours/')
        .then(response => {
            this.openingHours = response.data
            console.log(this.openingHours)
        })
    },
    methods: {
        DeleteOpeningHour: function(openingHourId){
            AXIOS.delete('/openingHours/delete/'.concat(openingHourId), {}, {})
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorDeleteOpeningHour = errorMsg.response.data
            }).then(window.location.reload())
        }
    },
}
