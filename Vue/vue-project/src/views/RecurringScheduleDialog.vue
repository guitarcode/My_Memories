<template>
  <div class="text-center">
    <v-dialog
      v-model="dialog"
      width="600"
    >
      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
          Range Setting
        </v-card-title>

        <WeeklyScheduleDatePicker @rangeEmit="rangeSet" />
        <v-divider />

        <v-card-actions>
          <v-spacer />
          <v-btn
            color="
            primary"
            text
            @click="applySchedule"
          >
            Apply
          </v-btn>
          <v-btn
            color="primary"
            text
            @click="dialog = false"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>


<script>
import WeeklyScheduleDatePicker from './WeeklyScheduleDatePicker.vue';
import axiosInst from '@/api';
import { integer } from 'vee-validate/dist/rules';

export default {
    components: { WeeklyScheduleDatePicker },
    props: {
      selectedId : integer
    },
    data() {
        return {
            dialog: false,
            weeklySchedule: {
              startDay: "",
              endDay: "",
            }
        };
    },
    computed: {
        storageId() {
          return this.selectedId
      }
    },
    methods: {
      rangeSet(start, end) {
        console.log("부모:",start)
        this.weeklySchedule.startDay = start
        this.weeklySchedule.endDay = end
      },
      applySchedule(){
        const url="/schedule"
        this.weeklySchedule.storageId = this.storageId
        console.log("스토리지",this.weeklySchedule)
        axiosInst.post(url, JSON.stringify(this.weeklySchedule), {})
        .then((response) => {
          const scheduleList = response.data.data
          console.log(scheduleList)
          this.$emit("scheduleListEmit",scheduleList)
          this.dialog = false
        })
        .catch((error) => {
          console.log(error)
        })
      }
    }
}
</script>

<style>

</style>
