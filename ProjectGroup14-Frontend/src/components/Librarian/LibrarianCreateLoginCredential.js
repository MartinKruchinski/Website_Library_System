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
    name: 'LibrarianCreateLoginCredential',
    data() {
        return {
            customers: [],
            curId: '',
            selectedCustomer: 'null',
            curFirstname: '',
            curLastname: '',
            curAddress: '',
            curIsLocal: '',
            errorCreateLogin: '',
            createLogin: '',
            username: '',
            password: '',
            password2: '',
            email: '',
            loginCredential: ''
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
                this.loginCredential = response.data.loginCredential
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorCreateLogin = errorMsg.response.data
                this.createLogin = ''
            })
        },

        MatchPasswords: function(password, password2){
            if (password!=password2){
                return "Passwords do not match"
            } else {
                return "Passwords match"
            }
        },

        CreateLoginCredential: function(username, password, email, customerId){
                console.log(this.loginCredential)
                if(this.loginCredential == null){
                    AXIOS.post('/logincredentials/'.concat(username), {}, {
                        params: {
                            password: password,
                            email: email,
                            customer_id: customerId
                        }
                    })
                    .then(response => {
                        this.username = '',
                        this.password = '',
                        this.password2 = ''
                        this.email = '',
                        this.createLogin = "Created Login Credential"
                        this.errorCreateLogin = ""
                    })
                    .catch(e => {
                        var errorMsg = e
                        this.errorCreateLogin = errorMsg.response.data
                        console.log(errorMsg.response.data)
                    })
                }
                else this.errorCreateLogin = "The customer already has a login credential"
            },
    }
}
