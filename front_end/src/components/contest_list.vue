<template>
  <div id="contest_list">
    <v-card
      class="my-3"
      v-for="contest in contests"
      :key="contest.contestId"
      :to="'/contests/' + contest.contestId"
    >
      <v-card-title>{{ contest.title }}</v-card-title>
      <v-card-text>
        startTime: {{ contest.startTime }}<br />
        endTime:{{ contest.endTime }}
      </v-card-text>
    </v-card>
    <Pagination
      :page="page"
      :totalPages="totalPages"
      @pageChange="pageChange"
    ></Pagination>
  </div>
</template>

<script>
import axios from '@/axios'
import Pagination from '@/components/pagination.vue'

export default {
  name: 'contest_list',
  components: {
    Pagination
  },
  data() {
    return {
      contests: null,
      page: 1,
      totalPages: null
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
        .catch(error => console.log(error))
    },
    pageChange(pageNum) {
      this.page = pageNum
      this.getData()
    }
  }
}
</script>

<style></style>
