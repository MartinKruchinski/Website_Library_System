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
    name: 'shifts',
    data() {
      return {
        shifts: [],
        startTime: '',
        endTime: '',
        librarian_id: '',
        head: ''
      }
    },
    created: function() {
        AXIOS.get('/login/librarian/')
            .then(response => {
                console.log(response.data)
                this.head = response.data.head
                this.librarian_id = response.data.id
            })
            .catch(e => {
            })
            .finally(() => {
                AXIOS.get('/shifts/librarian/'.concat(this.librarian_id))
                    .then(response => {
                        this.shifts = response.data
                        console.log(response.data)
                    })
            })
    },

    methods: {
        BackToMainPage(){
            if(this.head == true){
                this.$router.push('/HeadLibrarianMainPage')
            }
            else {
                this.$router.push('/LibrarianMainPage')
            }
        }
    }    
}