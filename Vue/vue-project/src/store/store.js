import Vue from "vue"
import Vuex from "vuex"
import VueCookies from "vue-cookies"

Vue.use(Vuex);

export default new Vuex.Store({
  state:{
    accessToken: null,
  },
  getters: {
    isAuth(state){
      return state.accessToken == null ? false : true ;
    },
    getToken(state){
      return state.accessToken;
    },
    removeToken(state){
      VueCookies.remove('accessToken')
      accessToken = null;
    }
  },
  mutations: {

    setToken(state, newToken){
      VueCookies.set('accessToken', newToken, '86400s');
      state.token = newToken;
    }
  },
  actions: {
    setToken:({commit}, newToken) => {
      commit('setToken', newToken);
    }
  }
})
