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
var itemId = null
export default {
    name: 'LibrarianUpdateItem',
    data () {
      return {
        itemType: '',
        author: '',
        title: '',
        isAvailable: '',
        items: [],
        successmessage: '',
        errormessage: '',
        newauthor: '',
        newtitle: '',
        item: '',
        item_id: '',
      }
    },
      created() {
        AXIOS.get('/items/currentItem')
        .then(response => {
          console.log(response.data)
          this.item_id = response.data.id
        })
        .finally(() => {
          AXIOS.get('/items/'.concat(this.item_id))
          .then(response => {
            console.log(response.data)
            this.author = response.data.author
            this.itemType = response.data.itemType
            this.title = response.data.title
            this.isAvailable = response.data.isAvailable
          })
        })
      },
      methods: {
        createItem: function (newauthor,newtitle) {
            availability = new Boolean(document.getElementById('availability').checked);
            select = document.getElementById('ItemType');
            type = select.options[select.selectedIndex].text;
            console.log(type)
            AXIOS.post('/items/'.concat(type), {}, {
                params: {
                    author: newauthor,
                    title: newtitle,
                    isAvailable: availability
                }
            })
              .then ( response => {
                this.successmessage = "Item updated successfully",
                this.author = response.data.author
                this.itemType = response.data.itemType
                this.title = response.data.title
                this.isAvailable = response.data.isAvailable
                AXIOS.get('/items/currentItem')
                .then(response => {
                  console.log(response.data)
                  this.item_id = response.data.id
                })
                .finally(() => {
                  AXIOS.delete('/items/delete/'.concat(this.item_id))
                })
              })
              .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errormessage = errorMsg.response.data
              })
          },
      }
  }
