<template>
  <div id="problem">
    <v-btn
      class="ma-2"
      color="secondary"
      @click.stop="update = true"
      v-if="this.$store.getters.isAdmin && !this.$route.params.contestId"
      dark
    >
      <v-icon left>mdi-update</v-icon>Update
    </v-btn>
    <v-btn
      class="ma-2"
      color="secondary"
      @click.stop="remove = true"
      v-if="this.$store.getters.isAdmin"
      dark
    >
      <v-icon left>mdi-delete</v-icon>Delete
    </v-btn>

    <v-dialog v-model="update" max-width="500px">
      <v-card>
        <v-card-title>Update Problem</v-card-title>
        <v-card-text>
          <v-form ref="updateProblemForm">
            <v-text-field label="title" v-model="title"></v-text-field>
            <v-text-field label="source" v-model="source"></v-text-field>
            <v-textarea label="description" v-model="description"></v-textarea>
            <v-textarea label="input" v-model="input"></v-textarea>
            <v-textarea label="output" v-model="output"></v-textarea>
            <v-textarea label="sampleInput" v-model="sampleInput"></v-textarea>
            <v-textarea
              label="sampleOutput"
              v-model="sampleOutput"
            ></v-textarea>
            <v-textarea label="hint" v-model="hint"></v-textarea>
            <v-text-field label="timeLimit" v-model="timeLimit"></v-text-field>
            <v-text-field
              label="memoryLimit"
              v-model="memoryLimit"
            ></v-text-field>
            <v-btn block color="secondary" dark class="mr-4" @click="updateOne">
              update
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-dialog v-model="remove" max-width="500px">
      <v-card>
        <v-card-title>Delete Problem</v-card-title>
        <v-card-text>
          <v-form ref="DeleteProblemForm">
            <v-btn block color="secondary" dark class="mr-4" @click="deleteOne">
              Delete
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-card>
      <v-tabs color="secondary" v-model="tabs">
        <v-tab>Problem</v-tab>
        <v-tab>Submit</v-tab>
      </v-tabs>
      <v-tabs-items v-model="tabs">
        <v-tab-item transition="false" reverse-transition="false">
          <v-card>
            <v-card-title class="display-2">
              {{ title }}
            </v-card-title>
            <v-card-subtitle>
              Time Limit: {{ timeLimit }} Memory Limit: {{ memoryLimit }}<br />
              Submit: {{ submit }} Accepted: {{ accepted }}<br />
              Source: {{ source }} CreateDate:{{ createDate }}
            </v-card-subtitle>
            <v-card-title>
              Description
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              {{ description }}
            </v-card-text>
            <v-card-title>
              Input
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              {{ input }}
            </v-card-text>
            <v-card-title>
              Output
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              {{ output }}
            </v-card-text>
            <v-card-title>
              sampleInput
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              {{ sampleInput }}
            </v-card-text>
            <v-card-title>
              sampleOutput
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              {{ sampleOutput }}
            </v-card-text>
            <v-card-title>
              Hint
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
              {{ hint }}
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item transition="false" reverse-transition="false">
          <v-form>
            <v-container fluid>
              <v-select :items="languages" v-model="language" label="language">
              </v-select>
              <v-textarea filled height="80vh" v-model="code"></v-textarea>
              <v-btn large color="secondary" @click="submitCode">Submit </v-btn>
            </v-container>
          </v-form>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </div>
</template>

<script>
import axios from '@/axios'
import { EventBus } from '@/eventbus'

export default {
  data() {
    return {
      problemId: this.$route.params.problemId,
      contestId: this.$route.params.contestId,
      title: null,
      source: null,
      description: null,
      input: null,
      output: null,
      sampleInput: null,
      sampleOutput: null,
      hint: null,
      createDate: null,
      timeLimit: null,
      memoryLimit: null,
      accepted: null,
      submit: null,
      ratio: null,
      code: null,
      tabs: 0,
      update: false,
      remove: false,
      languages: [1, 2, 3, 4],
      language: 1
    }
  },
  methods: {
    updateOne() {
      axios
        .put(`problems/${this.problemId}`, {
          title: this.title,
          source: this.source,
          description: this.description,
          input: this.input,
          output: this.output,
          sampleInput: this.sampleInput,
          sampleOutput: this.sampleOutput,
          hint: this.hint,
          timeLimit: this.timeLimit,
          memoryLimit: this.memoryLimit
        })
        .then(() => {
          this.update = false
        })
        .catch(() => {
          EventBus.$emit('callLogin')
        })
    },
    deleteOne() {
      let url = ''
      if (this.$route.params.contestId) {
        url = `contests/${this.$route.params.contestId}/problems/${this.problemId}`
      } else {
        url = `problems/${this.problemId}`
      }
      axios
        .delete(url)
        .then(() => {
          this.remove = false
        })
        .catch(() => {
          EventBus.$emit('callLogin')
        })
    },
    submitCode() {
      if (!this.$store.state.isLogin) {
        EventBus.$emit('callLogin')
        return
      }
      axios
        .post(`submissions`, {
          problemId: this.problemId,
          contestId: this.contestId,
          code: this.code,
          language: this.language
        })
        .then()
        .catch(error => EventBus.$emit('callAlert', error))
    }
  },
  mounted() {
    axios
      .get(`problems/${this.problemId}`)
      .then(response => {
        this.title = response.data.title
        this.source = response.data.source
        this.description = response.data.description
        this.input = response.data.input
        this.output = response.data.output
        this.sampleInput = response.data.sampleInput
        this.sampleOutput = response.data.sampleOutput
        this.hint = response.data.hint
        this.createDate = response.data.createDate
        this.timeLimit = response.data.timeLimit
        this.memoryLimit = response.data.memoryLimit
        this.submit = response.data.submit
        this.accepted = response.data.accepted
        this.ratio = response.data.ratio
      })
      .catch(error => EventBus.$emit('callAlert', error))
  }
}
</script>

<style></style>
