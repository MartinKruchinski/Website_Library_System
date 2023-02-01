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

function CustomerDto (id, firstName, lastName, address, isLocal){
    this.id = id
    this.firstName = firstName
    this.lastName = lastName
    this.address = address
    this.isLocal = isLocal
}

function LoginCredentialDto(id, username, password, email){
    this.id = id
    this.username = username
    this.password = password
    this.email = email
}

export default {
    name: 'LoginCustomer',

    data() {
        return {
          customer: '',
          username: '',
          password: '',
          errorCustomer: ""
        }
    },
    methods: {
        LoginCustomer: function(username, password){
            AXIOS.post('/login/customer/'.concat(username), {}, {
                params: {
                    password: password
                }
            })
            .then(response => {
                this.errorCustomer = ''
                this.$router.push('/CustomerMainPage')
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorCustomer = errorMsg.response.data
            })
        },
        SignUpCustomer(){
            this.$router.push('/SignUpCustomer')
        }
    }
}