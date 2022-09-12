<template>
  <div id="createitems">
    <FullCalendar :options="calendarOptions" />
    <!-- dialog 창 부분 -->
    <v-btn
      v-if="!calendarOptions.editable"
      color="primary"
      @click="editThis"
    >
      Modifying
    </v-btn>
    <v-btn
      v-else
      @click="update"
    >
      Save
    </v-btn>
    <schedule-item-dialog
      ref="itemDialog"
      @createEvent="createEvent"
    />
  </div>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import ScheduleItemDialog from './ScheduleItemDialog.vue'
import axiosInst from '@/api'

export default {

    // name: "ScheduleStorageCreate",
  components: {
    FullCalendar, ScheduleItemDialog// make the <FullCalendar> tag available
  },

  data() {
    return {
      calendarOptions: {

        plugins: [ timeGridPlugin, interactionPlugin ],
        initialView: 'timeGridWeek',
        headerToolbar: false,
        allDaySlot : false,
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
        initialDate:'2022-08-24',
        events: [],
        dateClick: this.handleDateclick,

      editable: false,
      droppable: false,
      selectable: false,
    //   eventsSet:
      select: this.handleDateSelect,
      eventsSet: this.handleEvents
    },
    currentEvents: [],
    title: "",
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
    beforeCreate() {
      const id = this.$route.query.id
      const url = "/schedule/storage/"+id
      axiosInst.get(url)
      .then((response) => {
        if(response.data.result == "success")
          this.title = response.data.data.title,
          this.calendarOptions.events = this.parseItem(response.data.data.items)
        else
          alert(response.data.message)
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
          this.selectInfo = {}
          },
        handleEvents(events) {
          this.currentEvents = events
        },
        parseDay(date) {
          let start
          if(date == "MONDAY"){
                start = "2022-08-22"
              }
              else if (date == "TUESDAY"){
                start = "2022-08-23"
              }
              else if (date == "WEDNESDAY"){
                start = "2022-08-24"
              }
              else if (date == "THURSDAY"){
                start = "2022-08-25"
              }
              else if (date == "FRIDAY"){
                start = "2022-08-26"
              }
              else if (date == "SATURDAY"){
                start = "2022-08-27"
              }
              else {
                start = "2022-08-21"
              }
            return start
        },
        parseItem(items){
          let events = []
          console.log(typeof(items))
            items.map((item) => {
              let start = this.parseDay(item.startDay)
              let end = this.parseDay(item.endDay)
              events.push({
                id: item.id,
                title: item.title,
                start: start+"T"+item.startTime,
                end: end+"T"+item.endTime,
                allDay: false,
                extendedProps: {
                  dbId : item.id,
                  importance: item.importance
                }
              })
            })

          return events;
        },
        parseEvent(){
          const items = this.currentEvents.map(event=>{
            let item = {};

            const startInfo = event._instance.range.start.toString().split(" ");
            const endInfo = event._instance.range.end.toString().split(" ");

            if(event._def.extendedProps.dbId)
              item.itemId = event._def.extendedProps.dbId;
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

        saveItems(){

            const items = this.parseEvent();
            console.log(items);

            const scheduleStorageItem = {
                "title": this.title,
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
        },
        editThis() {
          this.calendarOptions.editable = true
          this.calendarOptions.droppable = true
          this.calendarOptions.selectable = true
        },
        update() {
          const items = this.parseEvent();

            const scheduleStorageItem = {
                "storageId": this.$route.query.id,
                "title": this.title,
                "items": items
            }

            const url = "/schedule/storage"

            axiosInst.put(url, JSON.stringify(scheduleStorageItem), {
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
    #createitems {
        margin: 100px
    }
</style>
