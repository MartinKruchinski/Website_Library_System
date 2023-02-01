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
    name: 'LoginHeadLibrarian',

    data() {
        return {
          librarian: '',
          username: '',
          password: '',
          errorLibrarian: ''
        }
    },
    methods: {
        LoginLibrarian: function(username, password){
            AXIOS.post('/login/librarian/'.concat(username), {}, {
                params: {
                    password: password
                }
            })
            .then(response => {
                AXIOS.get('/login/librarian')
                .then(response2 =>{
                    if(response2.data.head == true){
                        this.errorLibrarian = ""
                        this.$router.push('/HeadLibrarianMainPage')
                    }
                    else {
                        console.log(response2.data)
                        this.errorLibrarian = "A librarian cannot log in as the head-librarian, please log in as a librarian"
                        AXIOS.post('/logout/librarian')
                    }
                })
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorLibrarian = errorMsg.response.data
            })
        }
    }
}