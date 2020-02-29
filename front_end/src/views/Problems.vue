<template>
  <div id="problems">
    <v-btn
      class="ma-2"
      color="secondary"
      @click.stop="add = true"
      v-if="this.$store.getters.isAdmin"
      dark
    >
      <v-icon left>mdi-plus</v-icon>Add
    </v-btn>
    <v-dialog v-model="add" max-width="500px">
      <v-card>
        <v-card-title>Add Problem</v-card-title>
        <v-card-text>
          <v-form ref="addForm">
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
            <v-btn block color="secondary" dark class="mr-4" @click="addOne">
              Add
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
    <ProblemList></ProblemList>
  </div>
</template>

<script>
import ProblemList from '@/components/problem_list.vue'
import axios from '@/axios'
import { EventBus } from '@/eventbus'

export default {
  name: 'problems',
  components: {
    ProblemList
  },
  data() {
    return {
      add: false,
      title: null,
      source: null,
      description: null,
      input: null,
      output: null,
      sampleInput: null,
      sampleOutput: null,
      hint: null,
      timeLimit: null,
      memoryLimit: null
    }
  },
  methods: {
    addOne() {
      axios
        .post('problems', {
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
          this.add = false
        })
        .catch(error => {
          EventBus.$emit('callAlert', error)
        })
    }
  }
}
</script>

<style></style>
