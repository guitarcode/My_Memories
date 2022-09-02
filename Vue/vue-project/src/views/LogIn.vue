<template>
  <v-row
    justify="center"
  >
    <v-col
      sm="6"
      align-self="center"
      class="py-18"
    >
      <v-card>
        <v-card-title class="blue">
          LOGIN
        </v-card-title>
        <v-container>
          <validation-observer
            ref="observer"
            v-slot="{ invalid }"
          >
            <form @submit.prevent="submit">
              <validation-provider
                v-slot="{ errors }"
                name="ID"
                rules="required|max:10"
              >
                <v-text-field
                  v-model="form.name"
                  :counter="20"
                  :error-messages="errors"
                  label="ID"
                  required
                />

                <validation-provider
                  v-slot="{ errors }"
                  name="password"
                  rules="required"
                >
                  <v-text-field
                    v-model="form.password"
                    type="password"
                    label="Password"
                    name="input-10-1"
                    :error-messages="errors"
                    required
                  />
                </validation-provider>
                <v-card-actions
                  class="justify-end"
                >
                  <v-btn
                    type="submit"
                    :disabled="invalid"
                  >
                    Log in
                  </v-btn>
                </v-card-actions>
              </validation-provider>
            </form>
          </validation-observer>
        </v-container>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import axios from 'axios'
import { extend, ValidationObserver, ValidationProvider, setInteractionMode } from 'vee-validate'
import { required, digits, email, min, max, regex } from 'vee-validate/dist/rules'

  setInteractionMode('eager')

  extend('digits', {
    ...digits,
    message: '{_field_} needs to be {length} digits. ({_value_})',
  })

  extend('required', {
    ...required,
    message: '{_field_} can not be empty',
  })

  extend('max', {
    ...max,
    message: '{_field_} may not be greater than {length} characters',
  })

  extend('regex', {
    ...regex,
    message: '{_field_} {_value_} does not match {regex}',
  })

  extend('email', {
    ...email,
    message: 'Email must be valid',
  })

  extend('password', {
  params: ['target'],
  validate(value, { target }) {
    return value === target;
  },
  message: 'Password confirmation does not match'
  });

  export default {
    components: {
      ValidationProvider,
      ValidationObserver,
    },
    data: () => ({
      form:{
        name: '',
        password: '',
      },
    }),

    methods: {
      clear() {
        this.form['password'] = ""
        this.$refs.observer.reset()
      },
      submit () {
        let url = "http://localhost:8080/login"
        let self = this

        axios.post(url,JSON.stringify(this.form),{
          headers: { "Content-Type": "application/json" }
        })
        .then(function(response){
          self.$store.dispatch('setToken',"Bearer "+response.data.data)
          self.$router.push('/')
        })
        .catch(() => {
          this.clear()
          alert("존재하지 않는 회원 정보입니다.")
        })
      },
    }
  }
</script>
<style>

</style>
