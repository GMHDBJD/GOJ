<template>
  <v-app id="app">
    <v-app-bar app dark hide-on-scroll color="primary">
      <v-menu bottom offset-y v-if="is_mobile">
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-icon>mdi-menu</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item to="/">
            <v-list-item-title>GOJ</v-list-item-title>
          </v-list-item>
          <v-list-item to="/problem">
            <v-list-item-title>Problem</v-list-item-title>
          </v-list-item>
          <v-list-item to="/contest">
            <v-list-item-title>Contest</v-list-item-title>
          </v-list-item>
          <v-list-item to="/status">
            <v-list-item-title>Status</v-list-item-title>
          </v-list-item>
          <v-list-item to="/ranking">
            <v-list-item-title>Ranking</v-list-item-title>
          </v-list-item>
          <v-list-item to="/discuss">
            <v-list-item-title>Discuss</v-list-item-title>
          </v-list-item>
          <v-list-item to="/queue">
            <v-list-item-title>Queue</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <router-link to="/" tag="h2">GOJ</router-link>
      <v-divider vertical class="ml-4"></v-divider>
      <v-toolbar-items v-if="!is_mobile">
        <v-btn text to="/problem">Problem</v-btn>
        <v-btn text to="/contest">Contest</v-btn>
        <v-btn text to="/status">Status</v-btn>
        <v-btn text to="/ranking">Ranking</v-btn>
        <v-btn text to="/discuss">Discuss</v-btn>
        <v-btn text to="/queue">Queue</v-btn>
      </v-toolbar-items>
      <v-spacer></v-spacer>

      <v-btn class="ma-2" dark outlined @click.stop="dialog_login = true">
        <v-icon dark left>mdi-account</v-icon>Login
      </v-btn>
    </v-app-bar>

    <!-- Sizes your content based upon application components -->
    <v-content app>
      <v-dialog v-model="dialog_login" max-width="500px">
        <LoginRegister @close="dialog_login = false"></LoginRegister>
      </v-dialog>
      <div style="height:100vh;">
        <router-view></router-view>
      </div>
      <!-- Provides the application the proper gutter -->
    </v-content>
  </v-app>
</template>

<script>
import LoginRegister from '@/components/login_register.vue'

export default {
  name: 'app',
  components: {
    LoginRegister
  },
  data() {
    return {
      dialog_login: false,
      is_mobile: false,
      menu: false
    }
  },
  beforeDestroy() {
    if (typeof window !== 'undefined') {
      window.removeEventListener('resize', this.onResize, { passive: true })
    }
  },
  mounted() {
    this.onResize()
    window.addEventListener('resize', this.onResize, { passive: true })
  },
  methods: {
    onResize() {
      this.is_mobile = window.innerWidth < 850
    }
  }
}
</script>

<style scoped>
.v-btn {
  text-transform: none !important;
}
</style>
