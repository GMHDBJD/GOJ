<template>
  <div id="problem">
    <v-card>
      <ProblemList></ProblemList>
    </v-card>
  </div>
</template>

<script>
import ProblemList from '@/components/problem_list.vue'

export default {
  name: 'problem',
  components: {
    ProblemList
  }
}
</script>

<style></style>
