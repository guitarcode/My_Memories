<template>
  <div>
    <app-header></app-header>
    <schedule-storage-create></schedule-storage-create>
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import ScheduleStorageCreate from './components/ScheduleStorageCreate.vue'
export default {
    data: function(){
      return{
        str: 'hi!!!'
      }
    },
    components: {
      AppHeader,
        ScheduleStorageCreate
    }
}
</script>

<style>

</style>