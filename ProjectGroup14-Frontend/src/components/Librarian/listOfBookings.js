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
        bookings_checkout: [],
        customer: '',
        customer_id: '',
        errorGetBooking: '',
        errorDeleteReservation: '',
        deletedReservation: '',
        librarian_head: '',
        itemId: '',
      }
    },
    created: function() {
        AXIOS.get('/bookings/'.concat("Reservation"))
            .then(response => {
                console.log(response.data)
                this.bookings = response.data
                this.bookingId = this.booking.Id
            })
            .catch(e => {
                this.errorGetBooking = e
            })
        AXIOS.get('/bookings/'.concat("Checkout"))
        .then(response => {
            console.log(response.data)
            this.bookings_checkout = response.data
        })
        .catch(e => {
            this.errorGetBooking = e
        })


    },

    methods: {
        BackToMainPage(){
            AXIOS.get('/login/librarian')
            .then(response => {
                this.librarian_head = response.data.head
            })
            .finally(() => {
                if(this.librarian_head == true){
                    this.$router.push('/HeadLibrarianMainPage')
                }
                else this.$router.push('/LibrarianMainPage')
            })
        },

        DeleteBooking: async function(bookingId, itemId){
            AXIOS.delete('/bookings/delete/'.concat(bookingId), {}, {})
            .then(
              AXIOS.patch('/items/edit/'.concat(itemId), {}, {
              })
            )
            .then(AXIOS.patch('/items/edit/'.concat(itemId), {}, {}
            ))
            .then(
                this.errorReserveItem= "",
                this.deletedReservation = "Deleted Booking",
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
        },
        Checkout: function(bookingId){
            AXIOS.patch('bookings/checkout/'.concat(bookingId))
            this.deletedReservation = "Item was successfully checked out!",
            setTimeout(function(){
                location.reload();
              }, 300)
        }
    }
}
