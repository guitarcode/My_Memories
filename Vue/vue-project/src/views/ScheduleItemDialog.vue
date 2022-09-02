<template>
  <div class="text-center">
    <v-dialog
      v-model="dialog"
      transition="dialog-top-transition"
      width="400"
      light
    >
      <v-card-title class="text-h5 grey lighten-2">
        Item Detail
      </v-card-title>
      <v-row
        justify="center"
      >
        <v-col
          sm="6"
          class="pt-3 pb-0"
        >
          <v-text-field
            v-model="title"
            label="Title"
          />
        </v-col>
      </v-row>
      <v-row
        justify="center"
      >
        <v-col
          sm="6"
          class="pt-0 pb-3"
        >
          <v-select
            v-model="importance"
            :items="importanceItems"
            label="Importance"
          />
        </v-col>
      </v-row>
      <v-divider />

      <v-card-actions>
        <v-spacer />
        <v-btn
          color="primary"
          text
          @click="createEvent"
        >
          Complete
        </v-btn>
      </v-card-actions>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data () {
      return {
        dialog: false,
        title: "",
        importanceItems: ['IMPORTANT', 'ADJUSTABLE', 'FREE', 'PROMISE'],
        importance: '',
      }
    },
  methods: {
    dialogActivate(){
      this.dialog=true
    },
    createEvent() {
      this.$emit("createEvent",this.title, this.importance)
      this.dataClear()
    },
    dataClear(){
      this.dialog=false
      this.title=""
      this.importance=""
    }
  }
}
</script>

<style>

</style>
