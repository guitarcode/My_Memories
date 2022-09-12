<template>
  <v-container>
    <v-row justify="center">
      <v-col>
        <FullCalendar
          :options="calendarOptions"
        />
      </v-col>
      <v-col cols="2">
        <v-card>
          <v-card-actions
            v-for="storage in storageList"
            :key="storage.id"
            outlined
            width="120"
            @click="openDialog(storage.id)"
          >
            {{ storage.title }}
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <RecurringScheduleDialog
      ref="weeklyDialog"
      :selected-id="selected"
    />
  </v-container>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import timeGridPlugin from '@fullcalendar/timegrid'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import axiosInst from '@/api'
import RecurringScheduleDialog from './RecurringScheduleDialog.vue'

export default {
  components: {
    FullCalendar,
    RecurringScheduleDialog
},

  data() {
    return {
    calendarOptions: {
      plugins: [
        dayGridPlugin,
        timeGridPlugin,
        interactionPlugin // needed for dateClick
      ],
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      initialView: 'timeGridWeek',
      allDaySlot: false,
      eventOverlap: false,
      selectOverlap: false,
      dayHeaderFormat:{
        weekday: 'short'
      },
      eventTimeFormat: { // like '14:30:00'
          hour: "2-digit",
          hour12: true
      },
      dateClick: this.handleDateclick,
      events: [],
      editable: true,
      droppable: true,
      selectable: true,
      select: this.handleDateSelect,
      eventsSet: this.handleEvents
    },
    storageList: [],
    currentEvents: [],
    selectInfo: {},
    selected:0
    }
  },

  beforeCreate() {
    axiosInst.get("/schedule/storage",{})
    .then((response)=>{
      this.storageList = response.data.data
    })
    .catch((error)=>{
      console.log(error)
    })
      const url = "/schedule"
      axiosInst.get(url)
      .then((response) => {
        if(response.data.result == "success")
          this.calendarOptions.events = this.parseSchedule(response.data.data)
        else
          alert(response.data.message)
      })
      .catch((error)=>{
        console.log(error)
      })
  },

  beforeUpdate() {
      const url = "/schedule"
      axiosInst.get(url)
      .then((response) => {
        if(response.data.result == "success")
          this.title = response.data.data.title,
          this.calendarOptions.events = this.parseSchedule(response.data.data)
        else
          alert(response.data.message)
      })
      .catch((error)=>{
        console.log(error)
      })
  },

  methods: {
    openDialog(id){
      this.$refs.weeklyDialog.dialog = true
      this.selected=id
    },
    handleDateSelect(selectInfo) {
      let calendarApi = selectInfo.view.calendar;
      calendarApi.unselect()

      this.selectInfo = selectInfo
      console.log(this.selectInfo)
      this.$refs.itemDialog.dialogActivate()
    },
    createEvent(title, importance) {

        if(title) {
            this.selectInfo.view.calendar.addEvent({
                title: title,
                start: this.selectInfo.startStr,
                end: this.selectInfo.endStr,
                allDay: this.selectInfo.allDay,
                extendedProps: {
                  importance: importance
                }
            })
        }

        console.log()
        this.selectInfo = {}
    },
    handleEvents(events) {
        this.currentEvents = events
    },
    parseSchedule(schedules) {
      const events = schedules.map(schedule => {
        let event = {
          title: "",
          start: "",
          end: "",
          extendedProps: {
            importance: "",
            subtitle: ""
          }
        }
        event.title = schedule.title
        event.start = schedule.start + ":00-06:00"
        event.end = schedule.end + ":00-06:00"
        event.extendedProps.importance = schedule.importance
        event.extendedProps.subtitle = schedule.subtitle
        console.log(event)
        return event
      })
      return events
    },
    parseEvent(){
      const items = this.currentEvents.map(event=>{
        let item = {};

        const startInfo = event._instance.range.start.toString().split(" ");
        const endInfo = event._instance.range.end.toString().split(" ");

        item.title = event._def.title;
        item.startDay = this.dayOfWeekParse[startInfo[0]];
        item.startTime = startInfo[4];
        item.endDay = this.dayOfWeekParse[endInfo[0]];
        item.endTime = endInfo[4];
        item.importance = event._def.extendedProps.importance
        return item;
        })

        return items;
    },

  }
}

</script>
<style>

</style>
