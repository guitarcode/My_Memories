<template>
  <div>
    <v-btn
      color="primary"
      link
      to="/schedule/storage/create"
    >
      Create it!
    </v-btn>
    <v-card
      v-for="storage in storageList"
      :key="storage.title"
    >
      {{ storage.title }}
    </v-card>
  </div>
</template>

<script>
import axiosInst from '@/api'
export default {
  data() {
    return{
    storageList: []
    }
  },
  created() {
    axiosInst.get("schedule/storage",{})
    .then((response)=>{
      this.storageList = response.data
    })
    .catch((error)=>{
      console.log(error)
    })
  },
  methods: {

  }
}

</script>

<style>

</style>
