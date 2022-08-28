import axios from 'axios'
import VueCookies from 'vue-cookies';

const axiosInst= axios.create({
  baseURL: process.env.VUE_APP_API_URL,
  timeout: 1000,
  headers: {'Content-Type': 'application/json' }
})

axiosInst.interceptors.request.use(
    function(config) {
      config.headers['Authorization'] = VueCookies.get('accessToken');
      console.log('인터셉터 작용')
      return config;
    },
  function(error){
    return Promise.reject(error);
  }
  )
axiosInst.interceptors.response.use(
  function(response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  function(error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  },
);

export default axiosInst;
