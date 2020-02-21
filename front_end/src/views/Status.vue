<template>
  <div id="statu">
    <v-card>
      <StatusList></StatusList>
    </v-card>
  </div>
</template>

<script>
import StatusList from '@/components/status_list.vue'

export default {
  name: 'statu',
  components: {
    StatusList
  }
}
</script>

<style></style>
