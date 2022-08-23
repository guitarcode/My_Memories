<template>
    <div id="createitems">
        <button v-on:click="saveItems">Save</button>
        
        <FullCalendar :options="calendarOptions">
           
            
        </FullCalendar>
    </div>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import axios from 'axios'

export default {

  components: {
    FullCalendar// make the <FullCalendar> tag available
  },

  data: function() {
    return {
      calendarOptions: {
        plugins: [ timeGridPlugin, interactionPlugin ],
        initialView: 'timeGridWeek',
        headerToolbar: false,
        dayHeaderFormat:{
            weekday: 'short'
        },
        eventTimeFormat:{
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        },
        dateClick: this.handleDateclick,
      editable: true,
      droppable: true,
      selectable: true,
    //   eventsSet:
      select: this.handleDateSelect,
      eventsSet: this.handleEvents
    },
    currentEvents: []
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
            var items = []
            for(let event of events){
                items.push({
                    "name": event._def.title,
                    "start": event._instance.range.start,
                    "end":event._instance.range.end,
                })
            }
            // }
            var ScheduleStorageDto= {
                "name": "test",
                "items": items
            }
            console.log(JSON.stringify(ScheduleStorageDto))
            let url = "http://localhost:8080/schedule/storage"
            
            axios.post(url, JSON.stringify(ScheduleStorageDto), {
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