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
    name: 'ListOfCustomers',

    data() {
        return {
          customers: [],
          isHead: '',
          id: '',
        }
    },

    created() {
        AXIOS.get('/customers/')
        .then(response => {
            this.customers = response.data
            console.log(this.customers)
        })
        .then (
            AXIOS.get('/login/librarian/')
                .then(response => {
                    console.log(response.data)
                    this.isHead = response.data.head
                })
                .catch(e => {
                })
        )
        console.log(this.isHead)
    },

    methods: {
        DeleteCustomer: function(customerId){
            AXIOS.delete('/customers/delete/'.concat(customerId), {}, {})
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorDeleteCustomer = errorMsg.response.data
            }).then(
                setTimeout(function(){
                    location.reload();
                }, 300));
        },

        BackToMainPage(){
            if(this.isHead){
                this.$router.push('/HeadLibrarianMainPage')
            }
            else this.$router.push('/LibrarianMainPage')
        }
    },
}
