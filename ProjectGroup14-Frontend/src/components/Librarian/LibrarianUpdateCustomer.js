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
    name: 'LibrarianUpdateCustomer',
    data() {
        return {
            customers: [],
            firstname: '',
            lastname: '',
            address: '',
            is_local: '',
            id: '',
            curId: '',
            errorUpdateCustomer: '',
            updatedCustomer: '',
            selectedCustomer: 'null',
            curFirstname: '',
            curLastname: '',
            curAddress: '',
            curIsLocal: '',
            newFirstname: '',
            newLastname: '',
            newAddress: '',
            newIsLocal: '',
            customer: '',
        }
    },

    created() {
        AXIOS.get('/customers/')
        .then(response => {
            this.customers = response.data
        })
    },

    methods: {
        GetOptions: function(id){
            const arr = id.split(" ");
            AXIOS.get("/customers/".concat(arr[2]))
            .then(response => {
                this.curId = response.data.id
                this.curFirstname = response.data.firstName
                this.curLastname = response.data.lastName
                this.curAddress = response.data.address
                this.curIsLocal = response.data.isLocal
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorUpdateCustomer = errorMsg.response.data
                this.updatedOpeningHour = ''
            })

        },

        UpdateFirstname: function(id, newFirstname){
            AXIOS.patch('customers/edit_firstname/'.concat(id), {}, {
              params:
              {
                firstname: newFirstname
              }
          })
          .then(
            this.curName = newFirstname,
            this.firstName = '',
            this.newFirstname = '',
            this.newLastname = '',
            this.lastname = '',
            this.selectedCustomer = 'null',
            this.curFirstname= '',
            this.curLastname= '',
            this.curAddress= '',
            this.curIsLocal= '',
          )
          .catch(e => {
            var errorMsg = e
            this.errorUpdateCustomer = errorMsg.response.data
            console.log(errorMsg.response.data)
          })
          .then(
            window.location.reload()
        )},

        UpdateLastname: function(id, newLastname){
            AXIOS.patch('customers/edit_lastname/'.concat(id), {}, {
              params:
              {
                lastname: newLastname
              }
          })
          .then(
            this.curLastname = '',
            this.firstName = '',
            this.newLastname = '',
            this.lastname = '',
            this.selectedCustomer = 'null',
            this.curFirstname= '',
            this.curLastname= '',
            this.curAddress= '',
            this.curIsLocal= '',
          )
          .catch(e => {
            var errorMsg = e
            this.errorUpdateCustomer = errorMsg.response.data
            console.log(errorMsg.response.data)
          })
          .then(
            window.location.reload()
        )},

        UpdateIsLocal: function(id){
            var local = new Boolean(document.getElementById('isLocal').checked);
            AXIOS.patch('edit_local/customers/'.concat(id), {}, {
              params:
              {
                is_local: local
              }
          })
          .then(
            this.curLastname = '',
            this.firstName = '',
            this.isLocal = '',
            this.lastname = '',
            this.selectedCustomer = 'null',
            this.curFirstname= '',
            this.curLastname= '',
            this.curAddress= '',
            this.curIsLocal= '',
          )
          .catch(e => {
            var errorMsg = e
            this.errorUpdateCustomer = errorMsg.response.data
            console.log(errorMsg.response.data)
          })
          .then(
            window.location.reload()
        )},

        UpdateAddress: function(id, newAddress){
            AXIOS.patch('customers/edit_address/'.concat(id), {}, {
              params:
              {
                address: newAddress
              }
          })
          .then(
            this.curLastname = '',
            this.firstName = '',
            this.isLocal = '',
            this.lastname = '',
            this.selectedCustomer = 'null',
            this.curFirstname= '',
            this.curLastname= '',
            this.curAddress= '',
            this.address= '',
            this.curIsLocal= '',
          )
          .catch(e => {
            var errorMsg = e
            this.errorUpdateCustomer = errorMsg.response.data
            console.log(errorMsg.response.data)
          })
          .then(
            window.location.reload()
        )},
    }
}
