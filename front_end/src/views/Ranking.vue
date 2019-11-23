<template>
  <div id="ranking">
    <v-card>
      <RankingList></RankingList>
    </v-card>
  </div>
</template>

<script>
import RankingList from '@/components/ranking_list.vue'

export default {
  name: 'ranking',
  components: {
    RankingList
  }
}
</script>

<style></style>
