<template>
  <v-container>
    <v-row justify="center">
      <v-col>
        <FullCalendar
          :options="calendarOptions"
        />
      </v-col>
      <v-col
        sm="2"
      >
        <v-card>
          <v-card
            v-for="storage in storageList"
            :key="storage.title"
            outlined
            width="120"
            @click="$router.push({path:'/schedule/storage/detail', query: {id: storage.id}})"
          >
            {{ storage.title }}
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import timeGridPlugin from '@fullcalendar/timegrid'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import axiosInst from '@/api'

export default {
  components: {
    FullCalendar,
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
            minute: '2-digit',
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
    storageList: [],
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
  created() {
    axiosInst.get("/schedule/storage",{})
    .then((response)=>{
      console.log(response.data.data)
      this.storageList = response.data.data
    })
    .catch((error)=>{
      console.log(error)
    })
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
