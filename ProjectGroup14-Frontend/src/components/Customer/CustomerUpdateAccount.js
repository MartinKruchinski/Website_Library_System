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
    name: 'CustomerUpdateAccount',
    data() {
        return {
          name: '',
          username: '',
          password: '',
          email: '',
          customerId: '',
          errorUpdateCustomer: '',
          loginId: '',
          newName: '',
          realPassword: '',
          newPassword: '',
          newEmail: '',
          newPassword: '',
          newUsername: '',
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
           this.username = response.data.loginCredential.username
           this.realPassword = response.data.loginCredential.password
           this.password = "*******"
           this.email = response.data.loginCredential.email
        })
    },
    methods: {
        UpdateUsername: function(newUsername){
            AXIOS.patch('edit_username/logincredentials/'.concat(this.loginId), {}, {
                params: {
                    username: newUsername,
                }
            })
            .then(response1 => {
                this.errorUpdateCustomer = ""
                this.username = newUsername
                this.newUsername = '',
                this.successUpdateCustomer = "Your username was updated succesfully!"
            })
            .then(response1 => {
                AXIOS.post('logout/customer')
            })
            .then(response1 =>{
                AXIOS.post('login/customer/'.concat(newUsername), {}, {
                    params: {
                        password: this.realPassword,
                    }
            })})
            .catch(e =>{
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorUpdateCustomer = errorMsg.response.data
                this.successUpdateCustomer = ""
        })},

        UpdatePassword: function(newPassword){
            AXIOS.patch('edit_password/logincredentials/'.concat(this.loginId), {}, {
                params: {
                    password: this.newPassword,
                }
            })
            .then(response1 => {
                this.errorUpdateCustomer = ""
                this.realPassword = newPassword
                this.successUpdateCustomer = "Your password was updated succesfully!"
            })
            .then(response1 => {
                AXIOS.post('logout/customer')
            })
            .then(response1 =>{
                AXIOS.post('login/customer/'.concat(this.username), {}, {
                    params: {
                        password: this.newPassword,
                    }
            })
            this.newPassword = ''
            })
            .catch(e =>{
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorUpdateCustomer = errorMsg.response.data
                this.successUpdateCustomer = ""
        })},

        UpdateEmail: function(newEmail){
            AXIOS.patch('edit_email/logincredentials/'.concat(this.loginId), {}, {
                params: {
                    email: this.newEmail,
                }
            })
            .then(response1 => {
                this.errorUpdateCustomer = ""
                this.email = newEmail
                this.successUpdateCustomer = "Your email was updated succesfully!"
            })
            .then(response1 => {
                AXIOS.post('logout/customer')
            })
            .then(response1 =>{
                AXIOS.post('login/customer/'.concat(this.username), {}, {
                    params: {
                        password: this.realPassword,
                    }
            })
            this.newEmail = ''
            })
            .catch(e =>{
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorUpdateCustomer = errorMsg.response.data
                this.successUpdateCustomer = ""
        })},
    }
}
