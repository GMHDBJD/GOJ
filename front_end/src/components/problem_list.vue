<template>
  <div id="problem_list">
    <v-card>
      <v-card-title>
        Problem
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
        :items="problems"
        :search="search"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:item.success="{ item }">
          <v-simple-checkbox v-model="item.submit" disabled></v-simple-checkbox>
        </template>
        <template v-slot:item.problemId="{ item }">
          <router-link
            :to="
              isContest
                ? contestId + '/problems/' + item.problemId
                : 'problems/' + item.problemId
            "
          >
            {{ item.problemId }}
          </router-link>
        </template>
      </v-data-table>
      <v-pagination v-model="page" :length="totalPages"></v-pagination>
    </v-card>
  </div>
</template>

<script>
import axios from '@/axios'

export default {
  name: 'problem_list',
  props: {
    isContest: { type: Boolean, default: false }
  },
  data() {
    return {
      headers: [
        { text: '', value: 'success', align: 'right' },
        { text: 'ID', value: 'problemId', align: 'left' },
        { text: 'Title', value: 'title', align: 'left' },
        { text: 'Ratio', value: 'ratio', filterable: false, align: 'left' },
        { text: 'submit', value: 'submit', filterable: false, align: 'left' },
        {
          text: 'accepted',
          value: 'accepted',
          filterable: false,
          align: 'left'
        }
      ],
      problems: [],
      search: '',
      itemsPerPage: 30,
      page: 1,
      totalPages: 0,
      contestId: this.$route.params.contestId
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      let url = ''
      if (this.isContest)
        url = `contests/${this.$route.params.contestId}/problems`
      else url = 'problems'
      axios
        .get(url)
        .then(response => {
          this.problems = response.data.content
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
