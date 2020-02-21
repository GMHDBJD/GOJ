<template>
  <div id="contest">
    <v-card>
      <v-card-title>{{ title }}</v-card-title>
      <v-card-subtitle>
        StartTime: {{ startTime }} endTime: {{ endTime }}<br />
        createUser: {{ createUser }}
      </v-card-subtitle>
    </v-card>
    <v-btn
      class="ma-2"
      color="secondary"
      @click.stop="add = true"
      v-if="this.$store.getters.isAdmin"
      dark
    >
      <v-icon left>mdi-plus</v-icon>Add
    </v-btn>
    <v-btn
      class="ma-2"
      color="secondary"
      @click.stop="update = true"
      v-if="this.$store.getters.isAdmin"
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

    <v-dialog v-model="add" max-width="500px">
      <v-card>
        <v-card-title>Add Contest Problem</v-card-title>
        <v-card-text>
          <v-form ref="addContestProblemForm">
            <v-text-field label="problemId" v-model="problemId"></v-text-field>
            <v-btn block color="secondary" dark class="mr-4" @click="addOne">
              Add
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>

    <v-dialog v-model="update" max-width="500px">
      <v-card>
        <v-card-title>Update Contest</v-card-title>
        <v-card-text>
          <v-form ref="updateContestForm">
            <v-text-field label="title" v-model="title"></v-text-field>
            <v-textarea label="description" v-model="description"></v-textarea>
            <v-text-field label="startTime" v-model="startTime"></v-text-field>
            <v-text-field label="endTime" v-model="endTime"></v-text-field>
            <v-text-field
              label="password"
              type="password"
              v-model="password"
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
        <v-card-title>Delete Contest</v-card-title>
        <v-card-text>
          <v-form ref="DeleteContestForm">
            <v-btn block color="secondary" dark class="mr-4" @click="deleteOne">
              Delete
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
    <ProblemList :isContest="true"></ProblemList>
  </div>
</template>

<script>
import ProblemList from '@/components/problem_list.vue'
import axios from '@/axios'

export default {
  name: 'contest',
  components: {
    ProblemList
  },
  data() {
    return {
      add: false,
      update: false,
      remove: false,
      problemId: null,
      contestId: null,
      title: null,
      description: null,
      startTime: null,
      endTime: null,
      createUser: null,
      password: null
    }
  },
  methods: {
    addOne() {
      axios
        .post(`contests/${this.$route.params.contestId}/problems`, {
          problemId: this.problemId
        })
        .then(() => {
          this.add = false
          this.getData()
        })
        .catch(error => {
          console.log(error)
        })
    },
    updateOne() {
      axios
        .put(`contests/${this.$route.params.contestId}`, {
          title: this.title,
          description: this.description,
          startTime: this.startTime,
          endTime: this.endTime,
          password: this.password
        })
        .then(() => {
          this.update = false
        })
        .catch(error => {
          console.log(error)
        })
    },
    deleteOne() {
      axios
        .delete(`contests/${this.$route.params.contestId}`)
        .then(() => {
          this.remove = false
        })
        .catch(error => {
          console.log(error)
        })
    },
    getData() {
      axios
        .get(`contests/${this.$route.params.contestId}`)
        .then(response => {
          this.contestId = response.data.contestId
          this.title = response.data.title
          this.description = response.data.description
          this.createUser = response.data.createUser
          this.startTime = response.data.startTime
          this.endTime = response.data.endTime
        })
        .catch(error => console.log(error))
    }
  },
  mounted() {
    this.getData()
  }
}
</script>

<style></style>
