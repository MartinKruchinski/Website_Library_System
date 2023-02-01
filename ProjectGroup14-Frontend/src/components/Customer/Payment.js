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

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
export default {
    name: 'booking',
    data() {
      return {
          number:'',
          name:'',
          date:'',
          errorPayment:'',
          successPayment:''
      }
    },
    methods: {
        pay: async function(number, name, date){
            if(isNaN(number) || number.length < 9){
                this.errorPayment = "The card number is invalid"
            }
            else if(name.length < 1){
                this.errorPayment = "The name is empty"
            }
            else if(date.length > 7 || date.length < 7){
                this.errorPayment = "Invalid date"
            }
            else{
                this.errorPayment = ""
                this.successPayment = "Payment completed!"
                await sleep(2000);
                this.$router.push('/CreateLoginCredential')
            }
            
        },
        BackToSignUp: function(){
            this.$router.push('/SignUpCustomer')
        }
    }
}