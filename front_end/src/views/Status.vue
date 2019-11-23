<template>
  <div id="status">
    <v-card>
      <StatusList></StatusList>
    </v-card>
  </div>
</template>

<script>
import StatusList from '@/components/status_list.vue'

export default {
  name: 'status',
  components: {
    StatusList
  }
}
</script>

<style></style>
