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
    name: 'LibraryCustomer',
    data() {
        return {
          name: '',
          number: '',
          address: '',
          email: '',
          errorUpdateLibrary: '',
        }
    },
    created() {
        AXIOS.get('library')
        .then(response => {
            console.log(response.data)
            this.name = response.data.name
            this.number = response.data.phoneNumber
            this.address = response.data.address
            this.email = response.data.email
        })
    },
    methods: {
    }
}
