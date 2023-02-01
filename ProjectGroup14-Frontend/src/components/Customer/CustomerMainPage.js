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
    name: 'LoginCustomer',

    data() {
        return {
          username: '',
          errorMainPage: ''
        }
    },
    methods: {
        getCustomerName: function(){
            AXIOS.get('/login/customer/username')
            .then(response =>{
                this.username = response.data
            })
            return "Welcome " + this.username
        },
        logOut: function(){
            AXIOS.post('/logout/customer')
            .catch(e => {
                this.errorMainPage = e
            })
        }
    }
}