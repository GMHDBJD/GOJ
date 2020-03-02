<template>
  <v-app id="app">
    <v-app-bar app dark hide-on-scroll color="primary">
      <v-menu bottom offset-y v-if="this.$store.state.isMobile">
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-icon>mdi-menu</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item to="/">
            <v-list-item-title>GOJ</v-list-item-title>
          </v-list-item>
          <v-list-item to="/problems">
            <v-list-item-title>Problem</v-list-item-title>
          </v-list-item>
          <v-list-item to="/contests">
            <v-list-item-title>Contest</v-list-item-title>
          </v-list-item>
          <v-list-item to="/status">
            <v-list-item-title>Statu</v-list-item-title>
          </v-list-item>
          <v-list-item to="/ranking">
            <v-list-item-title>Ranking</v-list-item-title>
          </v-list-item>
          <v-list-item to="/queue">
            <v-list-item-title>Queue</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <router-link to="/" tag="h2">GOJ</router-link>
      <v-divider vertical class="ml-4"></v-divider>
      <v-toolbar-items v-if="!this.$store.state.isMobile">
        <v-btn text to="/problems">Problem</v-btn>
        <v-btn text to="/contests">Contest</v-btn>
        <v-btn text to="/status">Statu</v-btn>
        <v-btn text to="/ranking">Ranking</v-btn>
        <v-btn text to="/queue">Queue</v-btn>
      </v-toolbar-items>
      <v-spacer></v-spacer>

      <v-btn
        class="ma-2"
        dark
        outlined
        @click.stop="dialog_login = true"
        v-if="!this.$store.state.isLogin"
      >
        <v-icon dark left>mdi-account</v-icon>Login
      </v-btn>
      <v-menu bottom offset-y v-else>
        <template v-slot:activator="{ on }">
          <v-btn class="ma-2" dark outlined v-on="on">
            <v-icon dark left>mdi-account</v-icon>
            {{ username }}
          </v-btn>
        </template>
        <v-list>
          <v-list-item :to="'/users/' + username">
            <v-list-item-title>profile</v-list-item-title>
          </v-list-item>
          <v-list-item to="/" @click="logout">
            <v-list-item-title>Logout</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

    <v-content app>
      <v-alert
        v-if="this.$store.state.isMobile"
        dense
        border="left"
        type="warning"
        color="red"
        dismissible
      >
        You'd better use <strong>desktop</strong> version.
      </v-alert>
      <v-dialog v-model="showAlert" max-width="500px">
        <v-alert type="error">
          <div class="display-2">{{ status }}</div>
          <br />
          {{ errors }}
        </v-alert>
      </v-dialog>

      <v-dialog v-model="dialog_login" max-width="500px">
        <LoginRegister @close="dialog_login = false"></LoginRegister>
      </v-dialog>
      <v-container fluid style="min-height:100vh;">
        <router-view></router-view>
      </v-container>
      <!-- Provides the application the proper gutter -->
    </v-content>

    <v-footer v-if="$route.meta.show_footer" padless>
      <v-card flat tile width="100%" class="primary white--text text-center">
        <v-card-text>
          <v-btn
            icon
            class="mx-4 white--text"
            href="https://github.com/gmhdbjd"
          >
            <v-icon size="24px">mdi-github-circle</v-icon>
          </v-btn>
          <v-btn
            icon
            class="mx-4 white--text"
            href="mailto:gmhdbjd@gmial.com?subject=contact me"
          >
            <v-icon size="24px">mdi-email-outline</v-icon>
          </v-btn>
          <v-btn
            icon
            class="mx-4 white--text"
            href="http://www.sysu.edu.cn/2012/cn/index.htm"
          >
            <v-icon size="24px">mdi-school-outline</v-icon>
          </v-btn>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="white--text">
          {{ new Date().getFullYear() }} — <strong>GOJ</strong>
        </v-card-text>
      </v-card>
    </v-footer>
  </v-app>
</template>

<script>
import LoginRegister from '@/components/login_register.vue'
import { EventBus } from '@/eventbus'

export default {
  name: 'app',
  components: {
    LoginRegister
  },
  data() {
    return {
      dialog_login: false,
      menu: false,
      showAlert: false,
      status: null,
      errors: null
    }
  },
  created() {
    EventBus.$on('callLogin', () => {
      this.dialog_login = true
    })
    EventBus.$on('callAlert', error => {
      if (error) {
        this.showAlert = true
        this.status = error.response.data.status
        this.errors = error.response.data.errors
      }
    })
  },
  computed: {
    username() {
      return this.$store.state.username
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
      this.$store.commit('resize', window.innerWidth)
    },
    logout() {
      this.$store.commit('logout')
    }
  }
}
</script>

<style scoped>
.v-btn {
  text-transform: none !important;
}
</style>
