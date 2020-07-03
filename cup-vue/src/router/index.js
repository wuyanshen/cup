import Vue from 'vue'
import VueRouter from 'vue-router'
import {
    setToken,
    getToken
} from '@/lib/util'
import store from '@/store'
import menuRouter from './menuRouter.js'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Home',
        redirect: '/welcome',
        component: () => import('../views/Home.vue'),
        children: [
            {
                path: 'info',
                name: 'Info',
                component: () => import('../views/Info.vue')
            },
            ...menuRouter
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login/Login.vue'),
    },
    {
        path: '*',
        component: () => import('../views/NotFound.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

//全局路由守卫
router.beforeEach(async (to, from, next) => {
    NProgress.start();
    const token = getToken()
    if (token) { //如果有token
        // store.dispatch('checkAndRefreshToken').then(
        //     //如果没过期
        //     () => {
        //         if (to.path === '/login') next('/')
        //         else next()

        //         //如果token过期
        //     }).catch(error => {
        //     //清空token
        //     setToken('')
        //     next('/login')
        // })
        try {
            await store.dispatch('user/checkToken');
            if (to.path === '/login') next('/')
            else next()
        } catch (e) {
            // 清空token
            setToken('')
            next('/login')
        }
    } else { //没有登录
        if (to.path === '/login') next()
        else next('/login')
    }
})

router.afterEach((to,from)=>{
    if(to.path !== '/login'){
        store.dispatch("tabs/addTab", to.path)
    }
    NProgress.done();
})

export default router
