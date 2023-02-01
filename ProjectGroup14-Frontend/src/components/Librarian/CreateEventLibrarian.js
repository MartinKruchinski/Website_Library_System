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

    name: 'CreateEvent',

    data() {
        return {
          customerId: '',
          day: '',
          startTime: '',
          endTime: '',
          errorCreateEvent: '',
          createdEvent: '',
          selectedCustomer: 'null',
          customers: [],
          date: ''
        }
    },

    created() {
        AXIOS.get('/customers/')
        .then(response => {
            this.customers = response.data
            console.log(this.customers)
        })
    },

    methods: {

        CreateEvent: function (customerId, date, startTime, endTime) {
            const arr = customerId.split(" ");
            AXIOS.post('/event/'.concat(arr[2]), {}, {
                params: {
                    date: date,
                    startTime: startTime.concat(":00"),
                    endTime: endTime.concat(":00")
                },
            })
            .then (response => {
                this.customerId = '',
                this.date = '',
                this.startTime = '',
                this.endTime = '',
                this.createdEvent = 'Created Event',
                this.errorCreateEvent = '',
                this.selectedCustomer = 'null'
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorCreateEvent = errorMsg.response.data
                this.createdEvent = ''
            })
        },

        BackToEvents(){
            this.$router.push('/ListOfEvents')
        }

    }
}
