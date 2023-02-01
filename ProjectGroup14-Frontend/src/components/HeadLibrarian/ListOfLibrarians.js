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
    name: 'ListOfLibrarians',

    data() {
        return {
            librarians: [],
            errorDeleteLibrarian: '',

        }
    },
    created() {
        AXIOS.get('/librarians/')
        .then(response => {
            this.librarians = response.data
        })
    },
    methods: {
        DeleteLibrarian: function(librarianId){
            AXIOS.delete('/librarians/delete/'.concat(librarianId), {}, {})
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorDeleteLibrarian = errorMsg.response.data
            }).then(window.location.reload())
        }
    },
}
