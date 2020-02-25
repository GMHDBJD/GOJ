import Vue from 'vue'
import Vuex from 'vuex'
import axios from '@/axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLogin: false,
    token: localStorage.getItem('token') || '',
    username: '',
    isMobile: false,
    roles: []
  },
  mutations: {
    login(state, data) {
      state.isLogin = true
      state.token = 'Bearer ' + data.token
      state.username = data.username
      state.roles = data.roles
      axios.defaults.headers.common['Authorization'] = state.token
    },
    logout(state) {
      state.isLogin = false
      state.token = ''
      state.username = ''
      state.roles = ''
      axios.defaults.headers.common['Authorization'] = ''
    },
    resize(state, width) {
      state.isMobile = width < 850
    },
    initialiseStore(state) {
      if (localStorage.getItem('store')) {
        this.replaceState(
          Object.assign(state, JSON.parse(localStorage.getItem('store')))
        )
        if (state.token) {
          axios.defaults.headers.common['Authorization'] = state.token
          axios
            .get('auth/validate_token')
            .then()
            .catch(() => {
              state.isLogin = false
              state.token = ''
              state.username = ''
              state.roles = ''
              axios.defaults.headers.common['Authorization'] = ''
            })
        }
      }
    }
  },
  actions: {},
  getters: {
    isAdmin(state) {
      return state.roles.includes('ROLE_ADMIN')
    }
  }
})
