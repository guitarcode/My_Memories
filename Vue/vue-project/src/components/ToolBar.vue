<template>
  <v-toolbar
    id="header"
    color="green"
    elevation="4"
  >
    <v-app-bar-nav-icon @click.stop="showSideBar" />
  </v-toolbar>
</template>
<script>


export default {
     methods: {
        showSideBar() {
            this.$emit('drawerset')
        }
    }
}
</script>

<style>
</style>
