<template>
  <v-app-bar
    app
  >
    <v-app-bar-nav-icon @click.stop="showSideBar" />
    <v-spacer />

    <div v-if="!isAuth">
      <v-btn
        color="accent"
        elevation="4"
        outlined
        to="/signup"
        class="mx-2"
      >
        Sign up
      </v-btn>

      <v-btn
        color="accent"
        elevation="4"
        outlined
        to="/login"
        class="mx-2"
      >
        Log in
      </v-btn>
    </div>
    <div v-else>
      Welcome

      <v-btn
        color="accent"
        elevation="4"
        outlined
        class="mx-2"
        @click="logout"
      >
        Logout
      </v-btn>
    </div>
  </v-app-bar>
</template>
<script>


export default {
  computed: {
    isAuth(){
      return this.$store.getters.getToken
    }
  },
  methods: {
    showSideBar() {
      this.$emit('drawerset')
    },
    logout(){
      this.$store.dispatch('removeToken')
      this.$router.push('/')
    }
  }
}
</script>

<style>

</style>
