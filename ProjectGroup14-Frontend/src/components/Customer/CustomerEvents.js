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
    name: 'event',
    data() {
      return {
        customer_id: '',
        errorGetEvent: '',
        eventCustomer: '',
        noEvent: ''
      }
    },
    created: function() {
        AXIOS.get('/login/customer/')
            .then(response => {
                this.customer_id = response.data.id
            })
            .catch(e => {
                this.errorGetEvent = e
                console.log(this.errorGetEvent.message)
            })
            .finally(() => {
                AXIOS.get('/events/customer/'.concat(this.customer_id))
                    .then(response => {
                        this.eventCustomer = response.data
                        console.log(response.data)
                    })
                    .catch(e => {
                        this.errorGetEvent = e.response.data
                        this.noEvent = null
                    })
            })
    },
    methods: {
        BackToMainPage(){
            this.$router.push('/CustomerMainPage')
        },
        Cancel: function(id){
            AXIOS.delete('events/delete/'.concat(id))
            setTimeout(function(){
                location.reload();
              }, 300)
        }
    },
}