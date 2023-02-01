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
    name: 'CreateLibrarian',

    data () {
        return {
            firstname: '',
            lastname: '',
            username: '',
            password: '',
            email: '',
            isHead: false,
            errorCreateLibrarian: '',
            createdLibrarian: ''
        }
    },

    methods: {
        CreateLibrarian: function(firstname, lastname, username, password, email){
            AXIOS.post('/librarian/'.concat(username), {}, {
                params: {
                    firstname: firstname,
                    lastname: lastname,
                    password: password,
                    isHead: false,
                    email: email
                },
            })
            .then(
                this.firstname='',
                this.lastname='',
                this.username='',
                this.password='',
                this.email='',
                this.errorCreateLibrarian = '',
                this.createdLibrarian = 'Created Librarian'
            )
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorCreateLibrarian = errorMsg.response.data
                this.createdLibrarian = ''
            })
        }
    }
}
