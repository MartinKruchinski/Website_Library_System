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
    name: 'LibrarianCatalogue',
    data () {
      return {
        itemType: '',
        author: '',
        title: '',
        isAvailable: '',
        items: [],
        isHead: ''
      }
    },
      created() {
        AXIOS.get('/login/librarian')
            .then(response =>{
                console.log(response)
                this.isHead = response.data.head
            })
        AXIOS.get('/items')
        .then(response => {
            this.items = response.data
        })
      },
      methods: {
          GetItem: function(itemId){
            AXIOS.patch('items/currentItem/set/'.concat(itemId))
          },
          back(){
            if(this.isHead == true){
              this.$router.push('/HeadLibrarianMainPage')
          }
          else this.$router.push('/LibrarianMainPage')
          },
          DeleteItem: function(itemId){
            AXIOS.delete('items/delete/'.concat(itemId))
            window.location.reload()
          }
      },
}
