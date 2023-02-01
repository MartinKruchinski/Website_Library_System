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
    name: 'LoginLibrarian',

    data() {
        return {
          username: '',
          events: []
        }
    },
    methods: {
        getLibrarianName: function(){
            AXIOS.get('/login/librarian')
            .then(response =>{
                this.username = response.data.username
            })
            return "Welcome " + this.username
        },
        logOut: function(){
            AXIOS.post('/logout/librarian')
            this.$router.push('/LoginLibrarian')           
        }
    }
}