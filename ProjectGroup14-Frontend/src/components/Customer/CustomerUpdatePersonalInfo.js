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
    name: 'CustomerUpdatePersInfo',
    data() {
        return {
          firstname: '',
          lastname: '',
          username: '',
          password: '',
          address: '',
          email: '',
          customerId: '',
          errorUpdateCustomer: '',
          loginId: '',
          newName: '',
          realPassword: '',
          newFirstname: '',
          newLastname: '',
          newAddress: '',
          successUpdateCustomer: '',
          head: ''
        }
    },
    created() {
        AXIOS.get('login/customer')
        .then(response => {
           console.log(response.data)
           this.customerId = response.data.id
        //    this.username = response.data.username
           this.loginId = response.data.loginCredential.id
           this.customerId = response.data.id
           this.firstname = response.data.firstName
           this.lastname = response.data.lastName
           this.address = response.data.address
           this.username = response.data.loginCredential.username
           this.realPassword = response.data.loginCredential.password
           this.password = "*******"
           this.email = response.data.loginCredential.email
        })
    },
    methods: {
        UpdateFirstname: function(newFirstname){
            AXIOS.patch('customers/edit_firstname/'.concat(this.customerId), {}, {
                params: {
                    firstname: newFirstname,
                }
            })
            .then(response1 => {
                this.errorUpdateCustomer = ""
                this.newFirstname = '',
                this.firstname = newFirstname,
                this.successUpdateCustomer = "Your firstname was updated succesfully!"
            })
            .then(response1 => {
                AXIOS.post('logout/customer')
            })
            .then(response1 =>{
                AXIOS.post('login/customer/'.concat(this.username), {}, {
                    params: {
                        password: this.realPassword,
                    }
            })})
            .catch(e =>{
                var errorMsg = e
                console.log(errorMsg.response)
                this.errorUpdateCustomer = errorMsg.response
                this.successUpdateCustomer = ""
        })},

        UpdateLastname: function(newLastname){
            AXIOS.patch('customers/edit_lastname/'.concat(this.customerId), {}, {
                params: {
                    lastname: newLastname,
                }
            })
            .then(response1 => {
                this.errorUpdateCustomer = ""
                this.newLastname = '',
                this.lastname = newLastname,
                this.successUpdateCustomer = "Your lastname was updated succesfully!"
            })
            .then(response1 => {
                AXIOS.post('logout/customer')
            })
            .then(response1 =>{
                AXIOS.post('login/customer/'.concat(this.username), {}, {
                    params: {
                        password: this.realPassword,
                    }
            })})
            .catch(e =>{
                var errorMsg = e
                console.log(errorMsg.response)
                this.errorUpdateCustomer = errorMsg.response
                this.successUpdateCustomer = ""
        })},

        UpdateAddress: function(newAddress){
            AXIOS.patch('customers/edit_address/'.concat(this.customerId), {}, {
                params: {
                    address: newAddress,
                }
            })
            .then(response1 => {
                this.errorUpdateCustomer = ""
                this.newAddress = '',
                this.address = newAddress,
                this.successUpdateCustomer = "Your address was updated succesfully!"
            })
            .then(response1 => {
                AXIOS.post('logout/customer')
            })
            .then(response1 =>{
                AXIOS.post('login/customer/'.concat(this.username), {}, {
                    params: {
                        password: this.realPassword,
                    }
            })})
            .catch(e =>{
                var errorMsg = e
                console.log(errorMsg.response)
                this.errorUpdateCustomer = errorMsg.response
                this.successUpdateCustomer = ""
        })},
    }
}
