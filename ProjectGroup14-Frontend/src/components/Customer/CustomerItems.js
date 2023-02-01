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
    name: 'booking',
    data() {
      return {
        bookings: [],
        customer: '',
        customer_id: '',
        errorGetBooking: '',
        errorDeleteReservation: '',
        deletedReservation: ''
      }
    },
    created: function() {
        AXIOS.get('/login/customer/')
            .then(response => {
                this.customer_id = response.data.id
            })
            .catch(e => {
                this.errorGetBooking = e
                console.log(this.errorGetBooking.message)
            })
            .finally(() => {
                AXIOS.get('/bookings/customer/'.concat(this.customer_id))
                    .then(response => {
                        this.bookings = response.data
                        this.bookingId = this.booking.Id
                    })
                    .catch(e => {
                        this.errorGetBooking = e
                    })
            })
    },

    methods: {
        BackToMainPage(){
            this.$router.push('/CustomerMainPage')
        },

        DeleteReservation: async function(bookingId, itemId){
            AXIOS.delete('/bookings/delete/'.concat(bookingId), {}, {})
            .then(
              AXIOS.patch('/items/edit/'.concat(itemId), {}, {
              })
            )
            .then(
                this.errorReserveItem= "",
                this.deletedReservation = "Deleted Reservation",
                setTimeout(function(){
                  location.reload();
                }, 300)
            )
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.deletedReservation= ""
                this.errorDeleteReservation = errorMsg.response.data
            })
        }
    }
}
