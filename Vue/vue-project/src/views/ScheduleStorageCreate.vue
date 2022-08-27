<template>
  <div id="createitems">
    <button @click="saveItems">
      Save
    </button>
    <FullCalendar :options="calendarOptions" />
  </div>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import axios from 'axios'

export default {

    // name: "ScheduleStorageCreate",
  components: {
    FullCalendar// make the <FullCalendar> tag available
  },

  data: function() {
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
        dateClick: this.handleDateclick,
      editable: true,
      droppable: true,
      selectable: true,
    //   eventsSet:
      select: this.handleDateSelect,
      eventsSet: this.handleEvents
    },
    currentEvents: [

    ]
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

        saveItems(){
            var events = this.currentEvents
            const items = events.map(function(event){
                var item = {};

                const startInfo = event._instance.range.start.toString().split(" ");
                const endInfo = event._instance.range.end.toString().split(" ");

                item.name = event._def.title;
                item.startDay = startInfo[0];
                item.startTime = startInfo[4];
                item.endDay = endInfo[0];
                item.endTime = endInfo[4];

                return item;
            })

            console.log(items)

            var scheduleStorageData = {
                "name": "test",
                "scheduleItems": items
            }

            let url = "http://localhost:8080/schedule/storage"
            
            axios.post(url, JSON.stringify(scheduleStorageData), {
                headers: { "Content-Type": "application/json" }
            }) 
            .then(function(response){
                console.log(response)
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