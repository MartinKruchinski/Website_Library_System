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
    name: 'Library',
    data() {
        return {
          name: '',
          phone_number: '',
          address: '',
          email: '',
          errorUpdateLibrary: '',
          newName: '',
          newNum: '',
          newAddress: '',
          newEmail: '',
          temp: '',
        }
    },

    created() {
        AXIOS.get('library')
        .then(response => {
            console.log(response.data)
            this.name = response.data.name
            this.phone_number = response.data.phoneNumber
            this.address = response.data.address
            this.email = response.data.email
        })
    },

    methods: {
      UpdateName: function(newName, address, phone_number, email){
        AXIOS.patch('/library/edit/'.concat(newName), {}, {
          params:
          {
            address: address,
            phone_number: phone_number,
            email: email
          }
      })
      .then(
        this.name = newName,
        this.newName = '',
      )
      .catch(e => {
        var errorMsg = e
        this.errorUpdateLibrary = errorMsg.response.data
        console.log(errorMsg.response.data)
      })},

      UpdateAddress: function(name, newAddress, phone_number, email){
        AXIOS.patch('/library/edit/'.concat(name), {}, {
          params:
          {
            address: newAddress,
            phone_number: phone_number,
            email: email
          }
      })
      .catch(e => {
        var errorMsg = e
        this.errorUpdateLibrary = errorMsg.response.data
        console.log(errorMsg.response.data)
      })
      .then(
        this.address = newAddress,
        this.newAddress = '',
      )
      },
      UpdatePhone: function(name, address, newNum, email){
        if(isNaN(newNum) || newNum.length != 10){
          this.errorUpdateLibrary = "The phone number is invalid, must be 10 digits"
        } else {
          AXIOS.patch('/library/edit/'.concat(name), {}, {
            params:
            {
              address: address,
              phone_number: newNum,
              email: email
            }
        })
        .catch(e => {
          var errorMsg = e
          this.errorUpdateLibrary = errorMsg.response.data
          console.log(errorMsg.response.data)
        })
        .then(
          this.phone_number = newNum,
          this.newNum = '',
          this.errorUpdateLibrary = ''
      )}},

      UpdateEmail: function(name, address, phone_number, newEmail){
        AXIOS.patch('/library/edit/'.concat(name), {}, {
          params:
          {
            address: address,
            phone_number: phone_number,
            email: newEmail
          }
      })
      .then( response => {
        this.temp = this.email,
        this.email = newEmail,
        this.newEmail = ''
      })
        
      .catch(e => {
        var errorMsg = e
        this.errorUpdateLibrary = errorMsg.response.data
        // this.email = this.temp
        console.log(errorMsg.response.data)
      })
}
    }
}
