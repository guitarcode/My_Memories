import Vue from 'vue'
import store from '@/store/store'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import axiosInst from './api/index'
import VueCookies from 'vue-cookies'

Vue.config.productionTip = false
Vue.use(VueCookies)


new Vue({
  store,
  router,
  vuetify,
  axiosInst,

  beforeCreate(){
    console.log("create 작동")
    this.$store.commit("hasToken")
  },
  render: h => h(App)
}).$mount('#app')
