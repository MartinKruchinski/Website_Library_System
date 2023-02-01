
<template>
  <div id="Librarians">
        <h1>Create Librarian</h1>
        <div id="cols">
            <div  style="width: 50%; float:left">

                <div style="width: 50%; float:left">
                    <p>Firstname</p>
                    <p>Username</p>
                    <p>Email</p>
                    <p>
                        <span v-if="errorCreateLibrarian" style="color:red">Error: {{errorCreateLibrarian}} </span>
                        <span v-if="createdLibrarian" style="color:green">{{createdLibrarian}} </span>
                    </p>
                    <div>
                    <a style="margin-top:50px; float:right"><router-link to="/ListOfLibrarians">Back</router-link></a>
                  </div>
                </div>

                <div style="width: 50%; float:right">
                    <input type="text" v-model="firstname" class="form-control" placeholder="Firstname" required>
                    <input type="text" v-model="username" class="form-control" placeholder="Username" required>
                    <input type="text" v-model="email" class="form-control" placeholder="Email" required>
                </div>

            </div>

            <div id="col" style="width: 50%; float:right">

                <div style="width: 20%; float:left">
                    <p>Lastname</p>
                    <p>Temporary Password</p>
                </div>

                <div style="width: 80%; float:left">
                    <input type="text"  v-model="lastname" class="form-control" placeholder="Lastname" required>
                    <input type="password"  v-model="password" class="form-control" placeholder="Temporary Password" required>
                </div>

                <div style="width: 100%; float:left">
                    <button :disabled="!firstname||!lastname||!username||!password||!email" @click="CreateLibrarian(firstname, lastname, username, password, email)" class="btn btn-primary">Create Librarian</button>
                </div>

            </div>
        </div>
    </div>
</template>

<script src="./CreateLibrarian.js">
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Varela+Round');

    #Librarians {
        font-family: 'Varela Round', sans-serif;
        color: #000000;
        background: #9fdfcb;
        margin: 20px;
        height: 100vh;
        margin: auto;
        width: 95vw;
        border: 3px solid #5bc2a1;
        padding: 10px;
        font-size: 28px;
    }
    p {
        text-align: right;
        margin: 20px;
        font-size: 18px;
    }
    input {
        text-align: center;
        margin: 10px;
        max-width: 35%;
        }

</style>
