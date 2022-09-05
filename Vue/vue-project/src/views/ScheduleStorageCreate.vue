<template>
  <div id="main">
    <v-container>
      <v-row justify="center">
        <v-col>
          <FullCalendar
            :options="calendarOptions"
          />
        </v-col>
      </v-row>
      <schedule-storage-dialog @saveItems="saveItems" />
    </v-container>

    <!-- dialog 창 부분 -->
    <schedule-item-dialog
      ref="itemDialog"
      @createEvent="createEvent"
    />
  </div>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import ScheduleStorageDialog from './ScheduleStorageDialog.vue'
import ScheduleItemDialog from './ScheduleItemDialog.vue'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import axiosInst from '@/api'

export default {
  components: {
    FullCalendar,
    ScheduleItemDialog,
    ScheduleStorageDialog// make the <FullCalendar> tag available
  },

  data() {
    return {
      calendarOptions: {
        timeZone: 'UTC',
        plugins: [ timeGridPlugin, interactionPlugin ],
        initialView: 'timeGridWeek',
        timeZone: 'UTC',
        headerToolbar: false,
        allDaySlot: false,
        eventOverlap: false,
        selectOverlap: false,
        dayHeaderFormat:{
            weekday: 'short'
        },
        eventTimeFormat: { // like '14:30:00'
            hour: "2-digit",
            minute: '2-digit',
            second: '2-digit',
            hour12: true
        },
        dateClick: this.handleDateclick,
      editable: true,
      droppable: true,
      selectable: true,
    //   eventsSet:
      select: this.handleDateSelect,
      eventsSet: this.handleEvents
    },
    currentEvents: [],
    title: "",
    selectInfo: {},
    dayOfWeekParse: {
      Mon: "MONDAY",
      Tue: "TUESDAY",
      Wed: "WEDNESDAY",
      Thu: "THURSDAY",
      Fri: "FRIDAY",
      Sat: "SATURDAY",
      Sun: "SUNDAY"
    }
    }
  },

    methods: {
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

        saveItems(title){
            const items = this.parseEvent();

            const scheduleStorageItem = {
                "title": title,
                "scheduleItems": items
            }

            const url = "/schedule/storage"

            axiosInst.post(url, JSON.stringify(scheduleStorageItem), {
            })
            .then((response) => {
              if(response.data.result == "success")
                this.$router.push("/schedule/storage")
              else{
                alert(response.data.message)
              }
            })
            .catch(function(error) {
                console.log(error)
            })
        }
    }
}

</script>
<style>

</style>
