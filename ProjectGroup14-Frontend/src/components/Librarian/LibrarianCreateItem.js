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
var availability = null
var type = null
var select = null;
export default {
    name: 'LibrarianCreateItem',
    data () {
      return {
        itemType: '',
        author: '',
        title: '',
        isAvailable: '',
        items: [],
        successmessage: '',
        errormessage: ''
      }
    },

      methods: {
        createItem: function (author,title) {
            availability = new Boolean(document.getElementById('availability').checked);
            select = document.getElementById('ItemType');
            type = select.options[select.selectedIndex].text;
            console.log(type)
            AXIOS.post('/items/'.concat(type), {}, {
                params: {
                    author: author,
                    title: title,
                    isAvailable: availability
                }
            })
              .then (

                this.successmessage = "Item created successfully",
                this.itemType = '',
                this.author = '',
                this.title = '',
                this.isAvailable = ''
              )
              .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errormessage = errorMsg.response.data
              })
          }
      }
  }
