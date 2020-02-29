<template>
  <div id="queue">
    <v-card min-height="100vh">
      <v-card-title>
        Queue
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="status"
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
        <template v-slot:item.result="{ item }">
          {{ results[item.result] }}
        </template>
        <template v-slot:item.username="{ item }">
          <router-link :to="'/users/' + item.username">
            {{ item.username }}
          </router-link>
        </template>
        <template v-slot:item.language="{ item }">
          <router-link
            :to="'/status/' + item.submissionId"
            v-if="
              $store.getters.isAdmin ||
                ($store.state.isLogin && item.username == $store.state.username)
            "
          >
            {{ lgs[item.language] }}
          </router-link>
          <td v-else>
            {{ lgs[item.language] }}
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
  name: 'queue',
  data() {
    return {
      status: [],
      page: 1,
      perPage: 30,
      totalPages: 0,
      itemsPerPage: 30,
      sortBy: 'submissionId',
      sortDesc: false,
      lgs: ['c', 'c++', 'python', 'python3', 'java'],
      results: [
        'Judging',
        'Complie Error',
        'Runtime Error',
        'Time Limit Error',
        'Accept',
        'All'
      ],
      headers: [
        {
          text: 'ID',
          value: 'submissionId',
          align: 'center',
          sortable: false,
          filterable: false
        },
        {
          text: 'User',
          value: 'username',
          align: 'left',
          sortable: false,
          filterable: false
        },
        {
          text: 'Problem',
          value: 'problemId',
          align: 'left',
          sortable: false,
          filterable: false
        },
        {
          text: 'Result',
          value: 'result',
          align: 'left',
          sortable: false,
          filter: value => {
            return value == 0
          }
        },
        {
          text: 'Memory',
          value: 'memory',
          align: 'left',
          sortable: false,
          filterable: false
        },
        {
          text: 'Time',
          value: 'time',
          align: 'left',
          sortable: false,
          filterable: false
        },
        {
          text: 'Language',
          value: 'language',
          align: 'left',
          sortable: false,
          filterable: false
        },
        {
          text: 'Code Length',
          value: 'codeLength',
          align: 'left',
          sortable: false,
          filterable: false
        },
        {
          text: 'Submit Time',
          value: 'submitDateTime',
          filterable: false,
          sortable: false,
          align: 'left'
        }
      ]
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      let url = 'submissions'

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
