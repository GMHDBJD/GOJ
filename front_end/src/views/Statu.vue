<template>
  <div id="statu">
    <v-card>
      <template>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">submissionId</th>
                <th class="text-left">problemId</th>
                <th class="text-left">username</th>
                <th class="text-left">time</th>
                <th class="text-left">memory</th>
                <th class="text-left">result</th>
                <th class="text-left">language</th>
                <th class="text-left">submitDateTime</th>
                <th class="text-left">codeLength</th>
                <th class="text-left">judgeTime</th>
              </tr>
            </thead>
            <tbody>
              <td>{{ submissionId }}</td>
              <td>{{ problemId }}</td>
              <td>{{ username }}</td>
              <td>{{ time }}</td>
              <td>{{ memory }}</td>
              <td>{{ result }}</td>
              <td>{{ language }}</td>
              <td>{{ submitDateTime }}</td>
              <td>{{ codeLength }}</td>
              <td>{{ judgeTime }}</td>
            </tbody>
          </template>
        </v-simple-table>
      </template>
      <v-divider class="my-2"></v-divider>
      <v-form>
        <v-container fluid>
          <v-textarea
            disabled
            filled
            height="80vh"
            label="Code"
            v-model="code"
          ></v-textarea>
          <v-btn large color="secondary" disabled>Submit </v-btn>
        </v-container>
      </v-form>
    </v-card>
  </div>
</template>

<script>
import axios from '@/axios'

export default {
  name: '',
  data() {
    return {
      submissionId: null,
      problemId: null,
      username: null,
      time: null,
      memory: null,
      result: null,
      language: null,
      submitDateTime: null,
      codeLength: null,
      judgeTime: null,
      code: null
    }
  },
  mounted() {
    axios.get(`submissions/${this.$route.params.statuId}`).then(reponse => {
      this.submissionId = reponse.data.submissionId
      this.problemId = reponse.data.problemId
      this.username = reponse.data.username
      this.time = reponse.data.time
      this.memory = reponse.data.memory
      this.result = reponse.data.result
      this.language = reponse.data.language
      this.submitDateTime = reponse.data.submitDateTime
      this.codeLength = reponse.data.codeLength
      this.judgeTime = reponse.data.judgeTime
      this.code = reponse.data.code
    })
  }
}
</script>

<style></style>
