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
    }
  },
  mutations: {
    setToken(state, newToken){
      VueCookies.set('accessToken', newToken, '86400s');
      state.accessToken = newToken;
    },
    removeToken(state){
      VueCookies.remove('accessToken')
      state.accessToken = null;
    }
  },
  actions: {
    setToken:({commit}, newToken) => {
      commit('setToken', newToken);
    },
    removeToken:({commit}) => {
      commit('removeToken');
    }
  }
})
