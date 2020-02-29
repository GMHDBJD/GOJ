<template>
  <div id="statu_list">
    <v-card min-height="100vh">
      <v-card-title>
        Status
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          label="Search"
          single-line
          append-icon="mdi-search"
        ></v-text-field>
      </v-card-title>
      <v-card-title>
        <v-row>
          <v-col cols="3">
            <v-text-field
              label="username"
              v-model="filterUsername"
            ></v-text-field>
          </v-col>

          <v-col cols="3">
            <v-text-field
              label="problemId"
              v-model="filterProblemId"
            ></v-text-field>
          </v-col>
          <v-col cols="3">
            <v-select
              :items="results"
              v-model="filterResult"
              label="result"
            ></v-select>
          </v-col>

          <v-col cols="3">
            <v-select
              :items="languages"
              v-model="filterLanguage"
              label="language"
            ></v-select>
          </v-col>
        </v-row>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="status"
        :search="search"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        class="elevation-1"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        hide-default-footer
      >
        <template v-slot:item.problemId="{ item }">
          <router-link :to="'/problems/' + item.problemId">
            {{ item.problemId }}
          </router-link>
        </template>
        <template v-slot:item.username="{ item }">
          <router-link :to="'/users/' + item.username">
            {{ item.username }}
          </router-link>
        </template>
        <template v-slot:item.result="{ item }">
          {{ results[item.result] }}
        </template>
        <template v-slot:item.language="{ item }">
          <router-link
            :to="'/status/' + item.submissionId"
            v-if="
              $store.getters.isAdmin ||
                ($store.state.isLogin && item.username == $store.state.username)
            "
          >
            {{ languages[item.language] }}
          </router-link>
          <td v-else>
            {{ languages[item.language] }}
          </td>
        </template>
      </v-data-table>
    </v-card>
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
  name: 'statu_list',
  data() {
    return {
      status: [],
      page: 1,
      perPage: 30,
      totalPages: 0,
      search: '',
      itemsPerPage: 30,
      results: [
        'Judging',
        'Complie Error',
        'Runtime Error',
        'Time Limit Error',
        'Wrong Answer',
        'Accept',
        'All'
      ],
      sortBy: 'submissionId',
      sortDesc: true,
      languages: ['c', 'c++', 'python', 'python3', 'java', 'All'],
      headers: [
        { text: 'ID', value: 'submissionId', align: 'center' },
        {
          text: 'User',
          value: 'username',
          align: 'left',
          sortable: false,
          filter: value => {
            if (!this.filterUsername) return true
            else return value == this.filterUsername
          }
        },
        {
          text: 'Problem',
          value: 'problemId',
          align: 'left',
          sortable: false,
          filter: value => {
            if (!this.filterProblemId) return true
            else return value == this.filterProblemId
          }
        },
        {
          text: 'Result',
          value: 'result',
          align: 'left',
          sortable: false,
          filter: value => {
            if (!this.filterResult || this.filterResult == 'All') return true
            else return value == this.results.indexOf(this.filterResult)
          }
        },
        { text: 'Memory', value: 'memory', align: 'left', sortable: false },
        { text: 'Time', value: 'time', align: 'left', sortable: false },
        {
          text: 'Language',
          value: 'language',
          align: 'left',
          sortable: false,
          filter: value => {
            if (!this.filterLanguage || this.filterLanguage == 'All')
              return true
            else return value == this.languages[this.filterLanguage]
          }
        },
        {
          text: 'Code Length',
          value: 'codeLength',
          align: 'left',
          sortable: false
        },
        {
          text: 'Submit Time',
          value: 'submitDateTime',
          filterable: false,
          align: 'left'
        }
      ],
      queryResult: parseInt(this.$route.query.result),
      filterUsername: this.$route.query.username,
      filterProblemId: this.$route.query.problemId,
      filterResult: this.$route.query.result,
      filterLanguage: 'All'
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      let url = ''

      if (this.$route.params.contestId)
        url = `contests/${this.$route.params.contestId}/submissions`
      else url = 'submissions'

      axios
        .get(url)
        .then(response => {
          this.status = response.data.content
          this.totalPages = response.data.totalPages
        })
        .catch(error => EventBus.$emit('callAlert', error))
    }
  }
}
</script>

<style></style>
