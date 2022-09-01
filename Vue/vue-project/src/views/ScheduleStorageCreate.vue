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
    }
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
