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
                :rules="loginUsernameRules"
                required
              ></v-text-field>
              <v-text-field
                label="Password"
                type="password"
                :rules="loginPasswordRules"
                required
                v-model="loginPassword"
              ></v-text-field>
              <v-btn block color="primary" class="mr-4" @click="login">
                Login
              </v-btn>
            </v-form>
          </v-tab-item>
          <v-tab-item transition="false" reverse-transition="false">
            <v-form ref="register_form" v-model="valid">
              <v-text-field
                label="Username"
                v-model="registerUsername"
                :rules="registerUsernameRules"
                required
              ></v-text-field>
              <v-text-field
                label="Password"
                type="password"
                :rules="registerPasswordRules"
                v-model="registerPassword"
                required
              ></v-text-field>
              <v-text-field
                label="Password again"
                type="password"
                :rules="confirmPasswordRules"
                v-model="confirmPassword"
                required
              ></v-text-field>
              <v-text-field
                label="Email"
                v-model="email"
                :rules="emailRules"
                required
              ></v-text-field>
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
import { EventBus } from '@/eventbus'

export default {
  name: 'login_register',
  data() {
    return {
      tabs: null,
      loginUsername: '',
      loginPassword: '',
      registerUsername: '',
      registerPassword: '',
      email: '',
      confirmPassword: '',
      valid: true,
      registerUsernameRules: [
        v => !!v || 'username is required',
        v => (v && v.length <= 15) || 'Name must be less than 15 characters'
      ],
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid'
      ],
      registerPasswordRules: [
        v => !!v || 'password is required',
        v =>
          (v && v.length >= 5 && v.length <= 15) ||
          'password length should between 5 and 15'
      ],
      confirmPasswordRules: [
        v => !!v || 'Confirm password',
        v => v === this.registerPassword || 'Passwords do not match'
      ],
      loginUsernameRules: [v => !!v || 'username is required'],
      loginPasswordRules: [v => !!v || 'password is required']
    }
  },
  methods: {
    login(e) {
      e.preventDefault()
      if (!this.$refs.login_form.validate()) return
      axios
        .post('auth/signin', {
          username: this.loginUsername,
          password: this.loginPassword
        })
        .then(response => {
          this.$emit('close')
          this.$store.commit('login', response.data)
        })
        .catch(() => {
          EventBus.$emit('callLogin')
        })
    },
    register(e) {
      e.preventDefault()
      if (!this.$refs.register_form.validate()) return
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
        .catch(error => EventBus.$emit('callAlert', error))
    }
  }
}
</script>

<style></style>
