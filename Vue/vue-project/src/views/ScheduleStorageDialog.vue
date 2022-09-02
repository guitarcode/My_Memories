/<template>
  <v-row justify="end">
    <v-col cols="auto">
      <v-dialog
        transition="dialog-bottom-transition"
        max-width="400"
      >
        <template
          #activator="{ on, attrs }"
        >
          <v-btn
            color="primary"
            v-bind="attrs"

            v-on="on"
          >
            Save
          </v-btn>
        </template>
        <template #default="dialog">
          <v-card class="overflow-hidden">
            <v-toolbar
              color="primary"
              dark
            >
              Create Schedule Storage
            </v-toolbar>
            <v-row
              justify="center"
            >
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
            </v-row>
            <v-row
              justify="end"
            >
              <v-col>
                <v-card-actions
                  class="justify-end"
                >
                  <v-btn
                    text
                    @click="saveItems"
                  >
                    Save
                  </v-btn>
                </v-card-actions>
              </v-col>
              <v-col>
                <v-card-actions
                  class="justify-end"
                >
                  <v-btn
                    text
                    @click="dialog.value = false"
                  >
                    Close
                  </v-btn>
                </v-card-actions>
              </v-col>
            </v-row>
          </v-card>
        </template>
      </v-dialog>
    </v-col>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      title: "",
    }
  },
  methods:{
    saveItems() {
      this.$emit("saveItems", this.title)
    }
  }
}
</script>

<style>

</style>
