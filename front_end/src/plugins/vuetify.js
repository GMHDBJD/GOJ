import Vue from 'vue'
import Vuetify from 'vuetify/lib'

import colors from 'vuetify/lib/util/colors'

Vue.use(Vuetify)

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: colors.teal.base,
        secondary: colors.blue.base,
        accent: colors.indigo.base
      },
      dark: {
        primary: colors.teal.base,
        secondary: colors.blue.base,
        accent: colors.indigo.base
      }
    }
  }
})
