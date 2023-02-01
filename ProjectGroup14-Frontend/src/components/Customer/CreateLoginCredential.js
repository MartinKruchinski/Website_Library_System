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
    name: 'SignUpCustomer',

    data() {
        return {
          username: '',
          password: '',
          email: '',
          customer: '',
          customer_id: '',
          errorCreateLogin: '',
          isLocal: ''
        }
    },
    methods: {
        EnterLoginCredentialInfo: function(username, password, email){
            AXIOS.get('/customers/last')
            .then(response => {
                this.customer = response.data
                console.log(this.customer)
                this.customer_id = this.customer.id
                this.errorCreateLogin = ""
            })
            .catch(e => {
                var errorMsg = e
                this.errorCreateLogin = errorMsg.response.data
                console.log(errorMsg.response.data)
            })
            .finally(() => {
                AXIOS.post('/logincredentials/'.concat(username), {}, {
                    params: {
                        password: password,
                        email: email,
                        customer_id: this.customer_id
                    }
                })
                .then(response => {
                    this.$router.push('/LoginCustomer')
                    this.errorCreateLogin = "" 
                })
                .catch(e => {
                    var errorMsg = e
                    this.errorCreateLogin = errorMsg.response.data
                    console.log(errorMsg.response.data)
                });
            })
        },
        BackToSignUp(){
            this.$router.push('/SignUpCustomer')
        }
    }
}