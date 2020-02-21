<template>
  <div id="statu_list">
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th>ID</th>
            <th>User</th>
            <th>Problem</th>
            <th>Result</th>
            <th>Memory</th>
            <th>Time</th>
            <th>Language</th>
            <th>Code Length</th>
            <th>Submit Time</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="statu in status" :key="statu.submissionId">
            <td>
              <router-link :to="'submission/' + statu.submissionId">
                {{ statu.submissionId }}</router-link
              >
            </td>
            <td>{{ statu.username }}</td>
            <td>{{ statu.problemId }}</td>
            <td>{{ statu.result }}</td>
            <td>{{ statu.memory }}</td>
            <td>{{ statu.time }}</td>
            <td>{{ statu.language }}</td>
            <td>{{ statu.codeLength }}</td>
            <td>{{ statu.submitDateTime }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>

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
  name: 'statu_list',
  components: {
    Pagination
  },
  data() {
    return {
      status: [],
      page: 1,
      perPage: 30,
      totalPages: 0
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      axios
        .get(`submissions?page=${this.page}&per_page=${this.perPage}`)
        .then(response => {
          this.status = response.data.content
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
