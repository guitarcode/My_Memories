<template>
  <v-app-bar
    app
  >
    <v-app-bar-nav-icon @click.stop="showSideBar" />
    <v-spacer />
    <v-btn
      color="accent"
      elevation="4"
      outlined
      to="/signup"
    >
      Sign up
    </v-btn>

    <v-btn
      color="accent"
      elevation="4"
      outlined
      to="/login"
    >
      Log in
    </v-btn>
  </v-app-bar>
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
