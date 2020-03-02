<template>
  <div id="user">
    <v-card class="mx-auto" max-width="450">
      <v-img src="home.jpg" :aspect-ratio="16 / 9"> </v-img>
      <v-card-title class="display-2 mb-2">
        {{ username }}
      </v-card-title>

      <v-divider class="mt-6 mx-4"></v-divider>
      <v-card-text>
        <v-text-field
          v-model="nickname"
          label="nickname"
          disabled
        ></v-text-field>
        <v-text-field v-model="email" label="email" disabled></v-text-field>
        <v-row>
          <v-spacer></v-spacer>
          <div>
            submit:
            <v-chip
              color="green"
              text-color="white"
              :to="'/status?username=' + username"
            >
              {{ submit }}
            </v-chip>
          </div>
          <v-spacer></v-spacer>
          <div>
            <v-chip
              color="red"
              text-color="white"
              :to="'/status?username=' + username + '&result=Accept'"
            >
              {{ accepted }}
            </v-chip>
            accepted
          </div>
          <v-spacer></v-spacer>
        </v-row>
        <v-divider class="my-2"></v-divider>
        <v-row>
          <v-spacer></v-spacer>
          <div>
            Rank
            <v-chip color="orange" text-color="white">
              {{ rank }}
            </v-chip>
          </div>
          <v-spacer></v-spacer>
          <div>
            <v-chip color="orange" text-color="white">
              {{ solved }}
            </v-chip>
            Solved
          </div>
          <v-spacer></v-spacer>
        </v-row>

        <v-divider class="my-3"></v-divider>
        <v-btn
          color="secondary"
          dark
          text
          v-if="this.$store.state.username == this.$route.params.username"
          @click="passwordChange = true"
        >
          reset password
        </v-btn>
        <v-btn
          color="secondary"
          dark
          text
          v-if="this.$store.state.username == this.$route.params.username"
          @click="nicknameChange = true"
        >
          reset nickname
        </v-btn>
      </v-card-text>
    </v-card>
    <v-dialog v-model="passwordChange" max-width="500px">
      <v-card>
        <v-card-title>Change Password</v-card-title>
        <v-card-text>
          <v-form ref="changePasswordForm">
            <v-text-field
              label="oldPassword"
              v-model="oldPassword"
              type="password"
            ></v-text-field>
            <v-text-field
              label="newPassword"
              v-model="newPassword"
              type="password"
            ></v-text-field>
            <v-text-field
              label="confirmPassword"
              v-model="confirmPassword"
              type="password"
            ></v-text-field>
            <v-btn
              block
              color="secondary"
              dark
              class="mr-4"
              @click="changePassword"
            >
              Change
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-dialog v-model="nicknameChange" max-width="500px">
      <v-card>
        <v-card-title>Change nickname</v-card-title>
        <v-card-text>
          <v-form ref="changeNicknameForm">
            <v-text-field label="nickname" v-model="nickname"></v-text-field>
            <v-btn
              block
              color="secondary"
              dark
              class="mr-4"
              @click="changeNickname"
            >
              change
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from '@/axios'
import { EventBus } from '@/eventbus'

export default {
  data() {
    return {
      username: null,
      nickname: null,
      email: null,
      registerTime: null,
      submit: null,
      accepted: null,
      ratio: null,
      rank: null,
      passwordChange: false,
      nicknameChange: false,
      oldPassword: null,
      newPassword: null,
      confirmPassword: null,
      solved: null
    }
  },
  methods: {
    changePassword() {
      axios
        .post(`users/${this.username}/reset_password`, {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword,
          confirmPassword: this.confirmPassword
        })
        .then(() => {
          this.passwordChange = false
          this.$store.commit('logout')
        })
        .catch(error => EventBus.$emit('callAlert', error))
    },
    changeNickname() {
      axios
        .post(`users/${this.username}/reset_nickname`, {
          newNickname: this.nickname
        })
        .then(() => {
          this.nicknameChange = false
        })
        .catch(error => EventBus.$emit('callAlert', error))
    }
  },
  mounted() {
    axios
      .get(`users/${this.$route.params.username}`)
      .then(response => {
        this.username = response.data.username
        this.nickname = response.data.nickname
        this.email = response.data.email
        this.registerTime = response.data.registerTime
        this.submit = response.data.submit
        this.accepted = response.data.accepted
        this.ratio = (response.data.submit
          ? response.data.accepted / response.data.submit
          : 0.0
        ).toFixed(2)
        this.rank = response.data.rank
        this.solved = response.data.solved
      })
      .catch(error => EventBus.$emit('callAlert', error))
  }
}
</script>

<style></style>
