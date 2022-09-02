<template>
  <div id="main">
    <v-container class="py-14">
      <v-row justify="center">
        <v-col>
          <FullCalendar :options="calendarOptions" />
        </v-col>
      </v-row>
    </v-container>

    <!-- dialog 창 부분 -->
    <schedule-item-dialog
      ref="itemDialog"
      @createEvent="createEvent"
    />
    <schedule-storage-dialog @saveItems="saveItems" />
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
        plugins: [ timeGridPlugin, interactionPlugin ],
        initialView: 'timeGridWeek',
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
    selectInfo: {}
    }
  },

    methods: {
        handleDateSelect(selectInfo) {
          this.selectInfo = selectInfo
          console.log(this.selectInfo)
          this.$refs.itemDialog.dialogActivate()
        },
        createEvent(title, importance) {
            let calendarApi = this.selectInfo.view.calendar;

            console.log(calendarApi)
            calendarApi.unselect()

            if(title) {
                calendarApi.addEvent({
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
            item.startDay = startInfo[0];
            item.startTime = startInfo[4];
            item.endDay = endInfo[0];
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

            const url = "schedule/storage"

            axiosInst.post(url, JSON.stringify(scheduleStorageItem), {
            })
            .then((response) => {
                console.log(response)
                this.$router.push("/schedule/storage")
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
