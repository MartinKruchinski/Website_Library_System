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
          events: [],
          id: '',
        }
    },
    created() {
        AXIOS.get('/events/')
        .then(response => {
            this.events = response.data
            console.log(this.events)
        })
        AXIOS.get('/login/librarian')
            .then(response =>{
                console.log(response)
                this.isHead = response.data.head
            })
    },
    methods: {
        BackToMainPage(){
            if(this.isHead == true){
                this.$router.push('/HeadLibrarianMainPage')
            }
            else this.$router.push('/LibrarianMainPage')
        },
        Cancel: function(id){
            AXIOS.delete('events/delete/'.concat(id))
            setTimeout(function(){
                location.reload();
              }, 300)
        }
    },
}
