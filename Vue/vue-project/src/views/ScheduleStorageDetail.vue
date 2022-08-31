<template>
  <div id="createitems">
    <FullCalendar :options="calendarOptions" />
    <!-- dialog 창 부분 -->
    <v-row justify="space-around">
      <v-col cols="auto">
        <v-dialog
          transition="dialog-bottom-transition"
          max-width="400"
        >
          <template #activator="{ on, attrs }">
            <v-btn
              color="primary"
              v-bind="attrs"
              v-on="on"
            >
              Create it!
            </v-btn>
          </template>
          <template #default="dialog">
            <v-card>
              <v-toolbar
                color="primary"
                dark
              >
                Create Schedule Storage
              </v-toolbar>
              <v-col
                cols="12"
                sm="6"
                md="9"
              >
                <v-text-field
                  :value="title"
                  :counter="20"
                  label="Title"
                  required
                  @blur="title=$event.target.value"
                />
              </v-col>
              <v-card-actions class="justify-end">
                <v-btn
                  text
                  @click="saveItems"
                >
                  Save
                </v-btn>
              </v-card-actions>
              <v-card-actions class="justify-end">
                <v-btn
                  text
                  @click="dialog.value = false"
                >
                  Close
                </v-btn>
              </v-card-actions>
            </v-card>
          </template>
        </v-dialog>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import axiosInst from '@/api'

export default {

    // name: "ScheduleStorageCreate",
  components: {
    FullCalendar// make the <FullCalendar> tag available
  },

  data() {
    return {
      calendarOptions: {
        plugins: [ timeGridPlugin, interactionPlugin ],
        initialView: 'timeGridWeek',
        headerToolbar: false,
        allDaySlot : false,
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
    }
  },
    beforeCreate() {
      const id = this.$route.query.id
      const url = "schedule/storage/"+id
      axiosInst.get(url)
      .then((response) => {
        const resData = response.data
        const items = resData.items

        console.log(this.calendar)

        let events = this.parseItem(items)
        this.calendarOptions.events = events
      })
      .catch((error)=>{
        console.log(error)
      })
    },

    methods: {
        handleDateSelect(selectInfo) {
            let title = prompt("hello");
            let calendarApi = selectInfo.view.calendar;

            calendarApi.unselect()

            if(title) {
                calendarApi.addEvent({
                    title,
                    start: selectInfo.startStr,
                    end: selectInfo.endStr,
                    allDay: selectInfo.allDay
                })
            }
        },
        handleEvents(events) {
          this.currentEvents = events
        },
        parseItem(items){
          let events = []
          console.log(typeof(items))
            items.map((item) => {
              let start
              let end
              if(item.startDay == "Mon"){
                start = "2022-08-22"
              }
              else if (item.startDay == "Tue"){
                start = "2022-08-23"
              }
              else if (item.startDay == "Wed"){
                start = "2022-08-24"
              }
              else if (item.startDay == "Thu"){
                start = "2022-08-25"
              }
              else if (item.startDay == "Fri"){
                start = "2022-08-26"
              }
              else if (item.startDay == "Sat"){
                start = "2022-08-27"
              }
              else {
                start = "2022-08-21"
              }

              if(item.endDay == "Mon"){
                end = "2022-08-22"
              }
              else if (item.endDay == "Tue"){
                end = "2022-08-23"
              }
              else if (item.endDay == "Wed"){
                end = "2022-08-24"
              }
              else if (item.endDay == "Thu"){
                end = "2022-08-25"
              }
              else if (item.endDay == "Fri"){
                end = "2022-08-26"
              }
              else if (item.endDay == "Sat"){
                end = "2022-08-27"
              }
              else {
                end = "2022-08-21"
              }
              events.push({
                title: item.title,
                start: start+"T"+item.startTime+"-06:00",
                end: end+"T"+item.endTime,
                allDay: false
              })
            })

          return events;
        },
        parseEvent(){
          const items = this.currentEvents.map(event=>{
            let item = {};

            const startInfo = event._instance.range.start.toString().split(" ");
            const endInfo = event._instance.range.end.toString().split(" ");

            item.name = event._def.title;
            item.startDay = startInfo[0];
            item.startTime = startInfo[4];
            item.endDay = endInfo[0];
            item.endTime = endInfo[4];
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
        }
    }
}

</script>
<style>
    #createitems {
        margin: 100px
    }
</style>
