import Vue from 'vue'
import store from '@/store/store'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'


Vue.config.productionTip = false

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
