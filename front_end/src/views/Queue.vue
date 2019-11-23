<template>
  <div id="queue">
    <v-card>
      <QueueList></QueueList>
    </v-card>
  </div>
</template>

<script>
import QueueList from '@/components/queue_list.vue'

export default {
  name: 'queue',
  components: {
    QueueList
  }
}
</script>

<style></style>
