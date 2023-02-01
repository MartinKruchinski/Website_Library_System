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
var local = null
export default {
    name: 'SignUpCustomer',
    data() {
        return {
            errorCreating: '',
            firstname: '',
            lastname: '',
            address: '',
            is_local: '',
            local
        }
    },
    methods: {
        EnterLoginCredentialInfo: function(firstname, lastname, address){
            local = new Boolean(document.getElementById('isLocal').checked);
            console.log(document.getElementById('isLocal').checked)
            if(local == false){
                AXIOS.post('/customers/'.concat(firstname), {}, {
                    params: {
                        lastname: lastname,
                        address: address,
                        is_local: local
                    }
                })
                .then(response => {
                    this.errorCreating = ""
                    this.$router.push('/Payment')

                })
                .catch(e => {
                    var errorMsg = e
                    this.errorCreating = errorMsg.response.data
                    console.log(errorMsg.response.data)
                })
            }
            else{
                AXIOS.post('/customers/'.concat(firstname), {}, {
                    params: {
                        lastname: lastname,
                        address: address,
                        is_local: local
                    }
                })
                .then(response => {
                    this.errorCreating = ""
                    this.$router.push('/CreateLoginCredential')
                })
                .catch(e => {
                    var errorMsg = e
                    this.errorCreating = errorMsg.response.data
                    console.log(errorMsg.response.data)
                })
            }
        },
        BackToLogin(){
            this.$router.push('/LoginCustomer')
        }
    }
}
