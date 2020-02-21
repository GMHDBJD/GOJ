<template>
  <div id="pagination">
    <v-pagination
      v-model="pageNum"
      class="my-4"
      :length="totalPages"
    ></v-pagination>
  </div>
</template>

<script>
export default {
  name: 'pagination',
  props: {
    page: Number,
    totalPages: Number
  },
  data() {
    return {
      pageNum: this.page
    }
  },
  watch: {
    pageNum: function(newPageNum) {
      this.$emit('pageChange', newPageNum)
    }
  }
}
</script>

<style></style>
