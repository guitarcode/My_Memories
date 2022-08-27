import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeVue from '@/views/HomeVue'
import ScheduleStorageCreate from '@/views/ScheduleStorageCreate'

Vue.use(VueRouter)

const routes = [
    {
        path: '/schedule/storage',
        name: 'ScheduleStorageCreate',
        component: ScheduleStorageCreate
    },
    {
        path: '/',
        name: "HomeVue",
        component: HomeVue
    }
]

const router = new VueRouter({
    mode: "history",
    routes
})

export default router