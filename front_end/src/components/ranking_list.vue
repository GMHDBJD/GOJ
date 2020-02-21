<template>
  <div id="ranking_list">
    <v-card>
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
        :items="rankedItems"
        :search="search"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        class="elevation-1"
        hide-default-footer
      >
      </v-data-table>
      <v-pagination v-model="page" :length="totalPages"></v-pagination>
    </v-card>
  </div>
</template>

<script>
import axios from '@/axios'

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
        }
      ],
      users: [],
      search: '',
      itemsPerPage: 30,
      page: 1,
      totalPages: 0,
      sortBy: 'accepted',
      sortDesc: true
    }
  },
  mounted() {
    this.getData()
  },
  computed: {
    rankedItems() {
      const items = []
      if (this.users.length > 0) {
        items[0] = this.users[0]
        items[0].rank = 1
        for (let index = 1; index < this.users.length; index++) {
          items[index] = this.users[index]
          items[index].rank = index + 1
        }
      }
      return items
    }
  },
  methods: {
    getData() {
      axios
        .get('users')
        .then(response => {
          this.users = response.data.content
          this.totalPages = Math.floor(
            (response.data.totalElements + this.itemsPerPage - 1) /
              this.itemsPerPage
          )
        })
        .catch(error => console.log(error))
    }
  }
}
</script>

<style></style>
