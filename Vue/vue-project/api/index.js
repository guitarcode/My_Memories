import axios from 'axios'
import setInterceptors from './interceptors'

const instance = axios.create({
  baseURL: process.env.VUE_APP_API_URL,
  timeout: 1000
})
