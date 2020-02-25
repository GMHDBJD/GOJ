import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import DatetimePicker from 'vuetify-datetime-picker'

Vue.prototype.$http = axios
store.subscribe((mutation, state) => {
  localStorage.setItem('store', JSON.stringify(state))
})

Vue.config.productionTip = false

Vue.use(DatetimePicker)

new Vue({
  router,
  store,
  vuetify,
  beforeCreate() {
    this.$store.commit('initialiseStore')
  },
  render: h => h(App)
}).$mount('#app')
