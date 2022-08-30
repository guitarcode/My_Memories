import Vue from 'vue'
import VueRouter from 'vue-router'
import VueCookies from 'vue-cookies'
import HomeVue from '@/views/HomeVue'
import ScheduleStorageList from '@/views/ScheduleStorageList'
import ScheduleStorageCreate from '@/views/ScheduleStorageCreate'
import SignUp from '@/views/SignUp'
import LogIn from '@/views/LogIn'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: "HomeVue",
        component: HomeVue,
        meta : {unauthorized : true}
    },
    {
      path: '/signup',
      name: "SignUp",
      component: SignUp,
      meta : {unauthorized : true}
    },
    {
      path: '/login',
      name: "LogIn",
      component: LogIn,
      meta : {unauthorized : true}
    },
    {
      path: '/schedule/storage/create',
      name: 'ScheduleStorageCreate',
      component: ScheduleStorageCreate
    },
    {
      path: '/schedule/storage',
      name: 'ScheduleStorageList',
      component: ScheduleStorageList
    },
]

const router = new VueRouter({
    mode: "history",
    routes
})

router.beforeEach( async(to, from, next) => {
  if (to.matched.some(record => record.meta.unauthorized || VueCookies.isKey('accessToken'))){
    return next();
  }
  else{
    alert("로그인이 필요한 서비스 입니다.");
    return next('/login');
  }
})

export default router
