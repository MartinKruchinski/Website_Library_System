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
    name: 'LibrarianCreateCustomer',
    data() {
        return {
            errorCreateCustomer: '',
            createdCustomer: '',
            firstname: '',
            lastname: '',
            address: '',
            is_local: '',
        }
    },

    methods: {
        CreateCustomer: function(firstname, lastname, address){
            var local = new Boolean(document.getElementById('isLocal').checked);
            AXIOS.post('/customers/'.concat(firstname), {}, {
                params: {
                    lastname: lastname,
                    address: address,
                    is_local: local
                }
            })
            .then(
                this.errorCreateCustomer= '',
                this.createdCustomer= 'Created Customer',
                this.firstname= '',
                this.is_local='',
                this.lastname= '',
                this.address= '',
            )
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorCreateCustomer = errorMsg.response.data
                this.createdCustomer= ''
            })

        }
    }
}
