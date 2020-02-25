<template>
  <div id="problem_list">
    <v-card min-height="100vh">
      <v-card-title>
        Problem
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          label="Search"
          single-line
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="problems"
        :search="search"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        @click:row="toProblem"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:item.solved="{ item }">
          <v-simple-checkbox v-model="item.solved" disabled></v-simple-checkbox>
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
  name: 'problem_list',
  data() {
    return {
      headers: [
        { text: '', value: 'solved', align: 'right', filterable: false },
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
      totalPages: 0
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      let url = ''
      if (this.$route.params.contestId)
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
        .catch(error => EventBus.$emit('callAlert', error))
    },
    toProblem(problem) {
      let url = ''
      if (this.$route.params.contestId)
        url = `/contests/${this.$route.params.contestId}/problems/${problem.problemId}`
      else url = `/problems/${problem.problemId}`
      this.$router.push(url)
    }
  }
}
</script>

<style></style>
