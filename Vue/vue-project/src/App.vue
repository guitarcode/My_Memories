<template>
  <div id="app">
    <v-app dark>
      <tool-bar @drawerset="drawerset" />
      <side-navi-bar ref="sideNavi" />
      <v-main>
        <v-container>
          <router-view />
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import SideNaviBar from './components/SideNaviBar.vue';
import ToolBar from './components/ToolBar.vue';

  export default {
    name: "App",
    components: { ToolBar , SideNaviBar},

    data (){
      return {
        // drawer: false,
      }
    },
    methods: {
      drawerset: function() {
        this.$refs.sideNavi.drawer = !this.$refs.sideNavi.drawer
      }
    }
  }
</script>

<style>

</style>
