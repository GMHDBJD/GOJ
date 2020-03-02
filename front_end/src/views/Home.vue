<template>
  <div id="home">
    <v-row v-if="this.$store.state.isLogin">
      <v-col cols="12" md="auto" sm="auto">
        <v-card>
          <v-card-title>
            <v-icon class="mr-2">mdi-calendar-month</v-icon>
            ToDo List
          </v-card-title>
          <v-list>
            <v-list-item-group v-model="time" color="primary">
              <v-list-item>
                <v-list-item-title>Completed</v-list-item-title>
              </v-list-item>

              <v-list-group>
                <template v-slot:activator>
                  <v-list-item-title>Ongoing</v-list-item-title>
                </template>
                <v-list-item>
                  <v-list-item-title>Today</v-list-item-title>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>Three days</v-list-item-title>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>A week</v-list-item-title>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>A week Later</v-list-item-title>
                </v-list-item>
              </v-list-group>
              <v-list-item>
                <v-list-item-title>Upcoming</v-list-item-title>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col>
        <v-card min-height="100vh">
          <v-card-title>
            Contest
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
            <template v-slot:item.joined="{ item }"> </template>
          </v-data-table>
        </v-card>
        <v-dialog v-model="joinContest" max-width="500px">
          <v-card>
            <v-card-title>Delete Contest</v-card-title>
            <v-card-text>
              <v-form ref="joinContestForm">
                <v-textarea
                  label="password"
                  type="password"
                  v-model="password"
                  v-if="requirePassword"
                ></v-textarea>
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
      </v-col>
    </v-row>
    <div v-else>
      <v-container fluid>
        <v-row>
          <v-col cols="12" md="6" sm="6">
            <router-link to="/problems" tag="div">
              <v-img class="white--text" src="problem.jpg" aspect-ratio="1.7">
                <v-card-title class="display-2">
                  problem
                </v-card-title>
              </v-img>
            </router-link>
          </v-col>
          <v-col cols="12" md="6" sm="6">
            <router-link to="/contests" tag="div">
              <v-img class="white--text" src="contest.jpg" aspect-ratio="1.7">
                <v-card-title class="display-2">
                  contest
                </v-card-title>
              </v-img>
            </router-link>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="6" sm="6">
            <router-link to="/status" tag="div">
              <v-img class="white--text" src="statu.jpg" aspect-ratio="1.7">
                <v-card-title class="display-2">
                  statu
                </v-card-title>
              </v-img>
            </router-link>
          </v-col>
          <v-col cols="12" md="6" sm="6">
            <router-link to="/ranking" tag="div">
              <v-img class="white--text" src="ranking.jpg" aspect-ratio="1.7">
                <v-card-title class="display-2">
                  ranking
                </v-card-title>
              </v-img>
            </router-link>
          </v-col>
        </v-row></v-container
      >
    </div>
  </div>
</template>

<script>
import axios from '@/axios'
import { EventBus } from '@/eventbus'

export default {
  name: 'home',
  data() {
    return {
      headers: [
        {
          text: '',
          value: 'joined',
          align: 'right',
          filter: value => {
            return value
          },
          sortable: false
        },
        { text: 'contestId', value: 'contestId', align: 'left' },
        { text: 'Title', value: 'title', align: 'left', sortable: false },
        {
          text: 'startTime',
          value: 'startTime',
          align: 'left',
          filter: value => {
            let now = new Date()
            let startTime = new Date(value)
            if (this.time == 2 && startTime < now) return false
            if ((this.time == 1 || this.time > 2) && startTime > now)
              return false
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
            if (this.time == 0 && endTime > now) return false
            if (this.time == 1 || this.time > 2) {
              if (endTime < now) return false
              else {
                now.setHours(0, 0, 0, 0)
                now.setDate(now.getDate() + 1)
                if (this.time == 3 && endTime > now) return false
                now.setDate(now.getDate() + 2)
                if (this.time == 4 && endTime > now) return false
                now.setDate(now.getDate() + 4)
                if (this.time == 5 && endTime > now) return false
                if (this.time == 6 && endTime < now) return false
                return true
              }
            }
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
        return
      } else {
        if (contest.joined || this.$store.getters.isAdmin) {
          this.$router.push(`/contests/${contest.contestId}`)
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
