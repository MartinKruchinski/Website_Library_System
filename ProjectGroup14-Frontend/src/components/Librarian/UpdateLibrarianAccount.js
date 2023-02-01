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
    name: 'UpdateLibrarianAccount',
    data() {
        return {
          name: '',
          lastName: '',
          username: '',
          password: '',
          email: '',
          librarianId: '',
          errorUpdateLibrarian: '',
          newName: '',
          newLastName: '',
          newEmail: '',
          newPassword: '',
          newUsername: '',
          successUpdateLibrarian: '',
          head: ''
        }
    },
    created() {
        AXIOS.get('login/librarian')
        .then(response => {
           console.log(response.data)
           this.librarianId = response.data.id
           this.name = response.data.firstName
           this.lastName = response.data.lastName
           this.username = response.data.username
           this.password = "*******"
           this.email = response.data.email
           this.head = response.data.head
        })
    },
    methods: {
        UpdateFirstName: function(newName, lastName){
            AXIOS.patch('librarians/edit/fullname/'.concat(this.librarianId), {}, {
                params: {
                    firstname: newName,
                    lastname: lastName
                }
            })
            .then(response1 => {
                console.log(response1.data)
                this.name = response1.data.firstName
                this.errorUpdateLibrarian = ""
                this.successUpdateLibrarian = "Your first name was updated succesfully!"
            })
            .catch(e =>{
                var errorMsg = e
                // this.errorUpdateLibrarian = errorMsg.response1.data
                this.successUpdateLibrarian = ""
            })

        },
        UpdateLastName: function(name, newLastName){
            AXIOS.patch('librarians/edit/fullname/'.concat(this.librarianId), {}, {
                params: {
                    firstname: name,
                    lastname: newLastName
                }
            })
            .then(response2 => {
                console.log(response2.data)
                this.lastName = response2.data.lastName
                this.errorUpdateLibrarian = ""
                this.successUpdateLibrarian = "Your last name was updated succesfully!"
            })
            .catch(e =>{
                this.errorUpdateLibrarian = e.response.data
                this.successUpdateLibrarian = ""
            })

        },
        UpdateUsername: function(newUsername){
            AXIOS.patch('librarians/edit/username/'.concat(this.librarianId), {}, {
                params: {
                    username: newUsername
                }
            })
            .then(response3 => {
                console.log(response3.data)
                this.username = response3.data.username
                this.errorUpdateLibrarian = ""
                this.successUpdateLibrarian = "Your username was updated succesfully!"
            })
            .catch(e =>{
                this.errorUpdateLibrarian = e.response.data
                this.successUpdateLibrarian = ""
            })

        },
        UpdatePassword: function(newPassword){
            AXIOS.patch('librarians/edit/password/'.concat(this.librarianId), {}, {
                params: {
                    password: newPassword
                }
            })
            .then(response4 => {
                console.log(response4.data)
                this.errorUpdateLibrarian = ""
                this.successUpdateLibrarian = "Your password was updated succesfully!"
            })
            .catch(e =>{
                this.errorUpdateLibrarian = e.response.data
                this.successUpdateLibrarian = ""
            })

        }
        ,
        UpdateEmail: function(newEmail){
            AXIOS.patch('librarians/edit/email/'.concat(this.librarianId), {}, {
                params: {
                    email: newEmail
                }
            })
            .then(response4 => {
                console.log(response4.data)
                this.email = response4.data.email
                this.errorUpdateLibrarian = ""
                this.successUpdateLibrarian = "Your email was updated succesfully!"
            })
            .catch(e =>{
                this.errorUpdateLibrarian = e.response.data
                this.successUpdateLibrarian = ""
            })

        }
        ,
        back: function(){
            if(!this.head){
                this.$router.push('/LibrarianMainPage')
            }
            else this.$router.push('/HeadLibrarianMainPage')
        }
    }
}
