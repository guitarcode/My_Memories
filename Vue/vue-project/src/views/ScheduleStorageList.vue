<template>
  <div>
    <v-btn
      color="primary"
      link
      to="/schedule/storage/create"
    >
      Create it!
    </v-btn>
    <v-card>
      <v-card
        v-for="storage in storageList"
        :key="storage.title"
        outlined
        width="auto"
        @click="$router.push({path:'/schedule/storage/detail', query: {id: storage.id}})"
      >
        <v-card-title>
          {{ storage.title }}
        </v-card-title>
      </v-card>
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
