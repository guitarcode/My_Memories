<template>
  <div id="app">
    <tool-bar @drawerset="drawerset" />
    <side-navi-bar ref="sideNavi" />
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
        console.log("event")
        this.$refs.sideNavi.drawer = !this.$refs.sideNavi.drawer
      }
    }
  }
</script>

<style>

</style>
