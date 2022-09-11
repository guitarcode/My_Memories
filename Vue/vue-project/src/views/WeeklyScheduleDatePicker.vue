<template>
  <v-row>
    <v-col
      cols="12"
      sm="6"
    >
      <v-date-picker
        v-model="dates"
        range
        @change="emitRange"
      />
    </v-col>
    <v-col
      cols="12"
      sm="6"
    >
      <v-text-field
        v-model="dateRangeText"
        label="Date range"
        prepend-icon="mdi-calendar"
      />
    </v-col>
  </v-row>
</template>

<script>
export default {
  data: () => ({
    today: "",
      // today: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toLocaleDateString(),
    dates: []
    }),
  computed: {
    dateRangeText () {
      return this.dates.join(' ~ ')
    },
  },
  created() {
    this.today = this.getToday()
    this.dates = [this.today, this.today]
  },
  methods: {
    getToday() {
      const date = new Date();
      const year = date.getFullYear();
      const month = ("0" + (1 + date.getMonth())).slice(-2);
      const day = ("0" + date.getDate()).slice(-2);

      return year + "-" + month + "-" + day;
    },
    emitRange() {
      const start = this.dates[0]
      const end = this.dates[1]
      console.log(start,end)
      this.$emit("rangeEmit", start, end)
    }
  }
}
</script>

<style>

</style>
