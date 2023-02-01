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
var dateInput = null
var startTimeInput = null
var endTimeInput = null
export default {

    name: 'CreateEvent',

    data() {
        return {
          cid: '',
          startTime: '',
          endTime: '',
          errorEvent: '',
          successEvent: ''
        }
    },

    methods: {

        CreateEvent: async function () {
            AXIOS.get('/login/customer/')
            .then(response => {
                this.cid = response.data.id
            })
            .finally(() => {

                dateInput = document.getElementById("dateInput").value.toString()
                startTimeInput = document.getElementById("startTimeInput").value.toString().concat(':00')
                endTimeInput = document.getElementById("endTimeInput").value.toString().concat(':00')
                AXIOS.post('/event/'.concat(this.cid), {}, {
                    params: {
                        startTime: startTimeInput,
                        endTime: endTimeInput,
                        date: dateInput
                    },
                })
                .then ( response => {
                    this.cid = '',
                    this.day = '',
                    this.startTime = '',
                    this.endTime = ''
                    this.successEvent = "The event was successfully created!"
                    this.errorEvent = "",
                    setTimeout(function(){
                        location.reload();
                    }, 300)
                })
                .catch (e => {
                    this.successEvent = ""
                    this.errorEvent = e.response.data
                })
            })
        },

        BackToEvents(){
            this.$router.push('/CustomerEvents')
        }

    }
}
