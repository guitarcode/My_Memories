<template>
  <v-navigation-drawer
    v-model="drawer"
    app
    temporary
  >
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title class="text-h6">
          Our Schedule
        </v-list-item-title>
        <v-list-item-subtitle />
      </v-list-item-content>
    </v-list-item>

    <v-divider />

    <v-list
      dense
      nav
    >
      <v-list-item
        v-for="item in items"
        :key="item.title"
        link
        :to="item.to"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  data () {
    return {
    drawer: false,
    items: [
        { title: 'Home', icon: 'mdi-image', to: "/"},
        { title: 'Schedule Items', icon: 'mdi-view-dashboard', to: "/schedule/storage"},
      ],
      right: null,
    }
  },
  methods: {
  }
}
</script>

<style>

</style>
