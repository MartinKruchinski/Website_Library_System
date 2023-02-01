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
    name: 'LibrarianCheckoutItem',
    data() {
        return {
            customers: [],
            items: [],
            customerId: '',
            firstname: '',
            lastname: '',
            address: '',
            itemTitle: '',
            id: '',
            itemAuthor: '',
            itemType: '',
            itemId: '',
            errorCheckoutItem: '',
            checkedOutItem: '',
            selectedCustomer: 'null',
            selectedItem: 'null',
        }
    },

    created() {
        AXIOS.get('/customers/')
        .then(response => {
            this.customers = response.data
        }),
        AXIOS.get('/availableItems/')
        .then(response => {
            this.items = response.data
        })
    },

    methods: {
        GetCustomerOptions: function(customerId){
            const arr = customerId.split(" ");
            AXIOS.get("/customers/".concat(arr[2]))
            .then(response => {
                this.customerId = response.data.id
                this.firstname = response.data.firstName
                this.lastname = response.data.lastName
                this.address = response.data.address
            })
            .catch(e => {
                var errorMsg = e
                console.log(errorMsg.response.data)
                this.errorCheckoutItem = errorMsg.response.data
                this.updatedOpeningHour = ''
            })
        },

        GetItemOptions: function(itemId){
          const arr = itemId.split(" ");
          AXIOS.get("/items/".concat(arr[2]))
          .then(response => {
              this.itemId = response.data.id
              this.itemAuthor = response.data.author
              this.itemTitle = response.data.title
              this.itemType = response.data.itemType
          })
          .catch(e => {
              var errorMsg = e
              console.log(errorMsg.response.data)
              this.errorCheckoutItem = errorMsg.response.data
              this.updatedOpeningHour = ''
          })},

          CheckoutItem: function(){
            AXIOS.post('/booking/'.concat(this.customerId), {}, {
              params: {
                  bookingType: "Checkout",
                  item: this.itemId,
              },
            })
            .then(response1 => {
              this.checkedOutItem = "Item was successfully checked out!",
              this.errorCheckoutItem
              this.selectedCustomer = 'null',
              this.selectedItem = 'null',
              this.customerId = '',
              this.firstname = '',
              this.lastname = '',
              this.address = '',
              this.itemTitle = '',
              this.id = '',
              this.itemAuthor = '',
              this.itemType = '',
              this.itemId = ''
            })
            .catch(e => {
              var errorMsg = e
              console.log(errorMsg.response.data)
              this.errorCheckoutItem = errorMsg.response.data
              this.checkedOutItem = ''
            })
            .then(
              setTimeout(function(){
                location.reload();
              }, 300)
            )
         }
    }
}
