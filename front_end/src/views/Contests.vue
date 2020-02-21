<template>
  <div id="contests">
    <v-btn
      class="ma-2"
      color="red"
      @click.stop="add = true"
      v-if="this.$store.getters.isAdmin"
      dark
    >
      <v-icon left>mdi-plus</v-icon>Add
    </v-btn>
    <v-dialog v-model="add" max-width="500px">
      <v-card>
        <v-card-title>Add Contest</v-card-title>
        <v-card-text>
          <v-form ref="addContestForm">
            <v-text-field label="title" v-model="title"></v-text-field>
            <v-textarea label="description" v-model="description"></v-textarea>
            <v-text-field label="startTime" v-model="startTime"></v-text-field>
            <v-text-field label="endTime" v-model="endTime"></v-text-field>
            <v-text-field
              label="password"
              type="password"
              v-model="password"
            ></v-text-field>
            <v-btn block color="red" dark class="mr-4" @click="addOne">
              Add
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
    <ContestList></ContestList>
  </div>
</template>

<script>
import ContestList from '@/components/contest_list.vue'
import axios from '@/axios'

export default {
  name: 'contests',
  components: {
    ContestList
  },
  data() {
    return {
      add: false,
      title: null,
      description: null,
      startTime: null,
      endTime: null,
      password: null
    }
  },
  methods: {
    addOne() {
      axios
        .post('contests', {
          title: this.title,
          description: this.description,
          startTime: this.startTime,
          endTime: this.endTime,
          password: this.password
        })
        .then(() => {
          this.add = false
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>

<style></style>
