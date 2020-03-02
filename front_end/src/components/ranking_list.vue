<template>
  <div id="ranking_list">
    <v-card min-height="100vh">
      <v-card-title>
        Ranking
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          label="Search"
          single-line
          append-icon="mdi-search"
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="users"
        :search="search"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:item.username="{ item }">
          <router-link :to="'/users/' + item.username">
            {{ item.username }}
          </router-link>
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
  name: 'ranking_list',
  data() {
    return {
      headers: [
        { text: 'Rank', value: 'rank' },
        { text: 'username', value: 'username' },
        { text: 'nickname', value: 'nickname', sortable: false },
        { text: 'Ratio', value: 'ratio', filterable: false },
        { text: 'submit', value: 'submit', filterable: false },
        {
          text: 'accepted',
          value: 'accepted',
          filterable: false
        },
        {
          text: 'solved',
          value: 'solved',
          filterable: false
        }
      ],
      users: [],
      search: '',
      itemsPerPage: 30,
      page: 1,
      totalPages: 0,
      sortBy: 'solved',
      sortDesc: true
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      let url = ''

      if (this.$route.params.contestId)
        url = `contests/${this.$route.params.contestId}/users`
      else url = 'users'
      axios
        .get(url)
        .then(response => {
          this.users = response.data.content
          this.users.forEach(element => {
            element.ratio = (element.submit
              ? element.accepted / element.submit
              : 0.0
            ).toFixed(2)
          })
          this.totalPages = Math.floor(
            (response.data.totalElements + this.itemsPerPage - 1) /
              this.itemsPerPage
          )
        })
        .catch(error => EventBus.$emit('callAlert', error))
    }
  }
}
</script>

<style></style>
