<template>
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
            :counter="10"
            :error-messages="errors"
            label="ID"
            required
          />

          <ValidationObserver>
            <validation-provider
              v-slot="{ errors }"
              name="password"
              rules="required|password:@confirm"
            >
              <v-text-field
                v-model="form.password"
                counter="20"
                type="password"
                label="Password"
                name="input-10-1"
                :error-messages="errors"
                required
              />
            </validation-provider>

            <validation-provider
              v-slot="{ errors }"
              name="confirm"
              rules="required"
            >
              <v-text-field
                v-model="passwordConfirm"
                :error-messages="errors"
                type="password"
                label="PassWord Confirm"
                required
              />
            </validation-provider>
          </ValidationObserver>
          <validation-provider
            v-slot="{ errors }"
            name="email"
            rules="email"
          >
            <v-text-field
              v-model="form.email"
              :error-messages="errors"
              label="E-mail"
              required
            />
          </validation-provider>

          <v-btn
            class="mr-4"
            type="submit"
            :disabled="invalid"
          >
            submit
          </v-btn>
          <v-btn @click="clear">
            clear
          </v-btn>
        </validation-provider>
      </form>
    </validation-observer>
  </v-container>
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
        email: '',
      },
      passwordConfirm: '',
    }),

    methods: {
      submit () {
        let url = "http://localhost:8080/member"
        let self = this

        axios.post(url,JSON.stringify(this.form),{
          headers: { "Content-Type": "application/json" }
        })
        .then(function(response){
          console.log(response)
          self.$router.push('/')
        })
        .catch(function(error){
          console.log(error)
        })
      },
      clear () {
        this.name = ''
        this.password = ''
        this.passwordCheck = ''
        this.email = ''
        this.$refs.observer.reset()
      },
    }
  }
</script>
<style>

</style>
