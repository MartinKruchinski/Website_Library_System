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
    name: 'CustomerCatalogue',
    data () {
      return {
        itemType: '',
        author: '',
        title: '',
        isAvailable: '',
        items: [],
        isHead: '',
        customerId: '',
        reservedItem: '',
        errorReserveItem: '',
      }
    },

    created() {
      AXIOS.get('/items')
      .then(response => {
          this.items = response.data
      }),
      AXIOS.get('/login/customer/')
      .then(response => {
          this.customerId = response.data.id
      })
    },

    methods: {
        back(){
          this.$router.push('/CustomerMainPage')
        },
        ReserveItem: async function(itemId){
          AXIOS.post('/booking/'.concat(this.customerId), {}, {
              params: {
                  bookingType: "Reservation",
                  item: itemId
              }
          })
          .then(
            AXIOS.patch('/items/edit/'.concat(itemId), {}, {
            })
          )
          .then(response => {
              this.errorReserveItem= ""
              this.reservedItem = "Reserved Item"
              setTimeout(function(){
                location.reload();
              }, 300);
          })
          .catch(e => {
              var errorMsg = e
              console.log(errorMsg.response.data)
              this.reservedItem= ""
              this.errorReserveItem = errorMsg.response.data
          })
      }
    },
}
