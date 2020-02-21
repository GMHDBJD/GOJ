<template>
  <div id="login_register">
    <v-card>
      <v-card-text>
        <v-btn class="float-right" icon @click="$emit('close')">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-tabs centered v-model="tabs">
          <v-tab>Login</v-tab>
          <v-tab>Register</v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabs">
          <v-tab-item transition="false" reverse-transition="false">
            <v-form ref="login_form">
              <v-text-field
                label="Username"
                v-model="loginUsername"
              ></v-text-field>
              <v-text-field
                label="Password"
                type="password"
                v-model="loginPassword"
              ></v-text-field>
              <v-btn block color="primary" class="mr-4" @click="login">
                Login
              </v-btn>
            </v-form>
          </v-tab-item>
          <v-tab-item transition="false" reverse-transition="false">
            <v-form ref="register_form">
              <v-text-field
                label="Username"
                v-model="registerUsername"
              ></v-text-field>
              <v-text-field
                label="Password"
                type="password"
                v-model="registerPassword"
              ></v-text-field>
              <v-text-field
                label="Password again"
                type="password"
                v-model="confirmPassword"
              ></v-text-field>
              <v-text-field label="Email" v-model="email"></v-text-field>
              <v-btn block color="primary" class="mr-4" @click="register">
                Register
              </v-btn>
            </v-form>
          </v-tab-item>
        </v-tabs-items>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import axios from '@/axios'

export default {
  name: 'login_register',
  data: () => ({
    tabs: null,
    loginUsername: '',
    loginPassword: '',
    registerUsername: '',
    registerPassword: '',
    email: '',
    confirmPassword: '',
    errorMessage: ''
  }),
  methods: {
    login(e) {
      e.preventDefault()
      axios
        .post('auth/signin', {
          username: this.loginUsername,
          password: this.loginPassword
        })
        .then(response => {
          this.$store.commit('login', response.data)
        })
        .catch(error => {
          console.log(error)
        })
      this.$emit('close')
    },
    register(e) {
      e.preventDefault()
      axios
        .post('auth/signup', {
          username: this.registerUsername,
          password: this.registerPassword,
          confirmPassword: this.registerPassword,
          email: this.email
        })
        .then(() => {
          this.tabs = 0
          this.loginUsername = this.registerUsername
          this.loginPassword = ''
        })
        .catch(error => console.log(error))
    }
  }
}
</script>

<style></style>
