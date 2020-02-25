<template>
  <div id="contest_list">
    <v-card min-height="100vh">
      <v-card-title>
        Contest
        <v-spacer></v-spacer>
        <v-select :items="times" v-model="time" label="Time"></v-select>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          label="Search"
          single-line
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="contests"
        :search="search"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        @click:row="toContest"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:item.joined="{ item }">
          <v-simple-checkbox v-model="item.joined" disabled></v-simple-checkbox>
        </template>
      </v-data-table>
    </v-card>
    <v-dialog v-model="joinContest" max-width="500px">
      <v-card>
        <v-card-title>Join Contest</v-card-title>
        <v-card-text>
          <v-form ref="joinContestForm">
            <v-text-field
              label="password"
              type="password"
              v-model="password"
              v-if="requirePassword"
            ></v-text-field>
            <v-btn block color="secondary" dark class="mr-4" @click="join">
              Join
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-pagination
      class="mt-5"
      v-model="page"
      :length="totalPages"
    ></v-pagination>
  </div>
</template>

<script>
import axios from '@/axios'
import { EventBus } from '@/eventbus'

export default {
  name: 'contest_list',
  data() {
    return {
      headers: [
        { text: '', value: 'joined', align: 'right', filterable: false },
        { text: 'contestId', value: 'contestId', align: 'left' },
        { text: 'Title', value: 'title', align: 'left', sortable: false },
        {
          text: 'startTime',
          value: 'startTime',
          align: 'left',
          filter: value => {
            let now = new Date()
            let startTime = new Date(value)
            if (this.time == 'ongoing' && startTime > now) return false
            if (this.time == 'upcoming' && startTime < now) return false
            return true
          }
        },
        {
          text: 'endTime',
          value: 'endTime',
          align: 'left',
          filter: value => {
            let now = new Date()
            let endTime = new Date(value)
            if (this.time == 'completed' && endTime > now) return false
            if (this.time == 'ongoing' && endTime < now) return false
            return true
          }
        }
      ],
      contests: [],
      search: '',
      itemsPerPage: 30,
      page: 1,
      totalPages: 0,
      joinContest: false,
      password: null,
      requirePassword: false,
      contestId: null,
      times: ['all', 'completed', 'ongoing', 'upcoming'],
      time: null
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      axios
        .get(`contests?page=${this.page}`)
        .then(response => {
          this.contests = response.data.content
          this.totalPages = response.data.totalPages
        })
        .catch(error => EventBus.$emit('callAlert', error))
    },
    pageChange(pageNum) {
      this.page = pageNum
      this.getData()
    },
    toContest(contest) {
      if (!this.$store.state.isLogin) {
        EventBus.$emit('callLogin')
      } else {
        if (contest.joined || this.$store.getters.isAdmin) {
          this.$router.push(`/contests/${contest.contestId}`)
        } else if (new Date(contest.startTime) < new Date()) {
          console.log('Contest has begun')
        } else {
          this.requirePassword = contest.requirePassword
          this.contestId = contest.contestId
          this.joinContest = true
        }
      }
    },
    join() {
      axios
        .post(`contests/${this.contestId}/users`, {
          password: this.password
        })
        .then(() => {
          this.$router.push(`contests/${this.contestId}`)
        })
        .catch(error => EventBus.$emit('callAlert', error))
    }
  }
}
</script>

<style></style>
