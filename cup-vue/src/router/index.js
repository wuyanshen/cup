import Vue from 'vue'
import VueRouter from 'vue-router'
import { setToken, getToken } from '@/lib/util'
import store from '@/store'
import menuRouter from './menuRouter.js'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        redirect: '/welcome',
        meta: { title: '首页' },
        component: () => import('../views/system/Home.vue'),
        children: [
            {
                path: 'info',
                name: 'Info',
                meta: { title: '个人信息' },
                component: () => import('../views/system/Info.vue')
            },
            ...menuRouter
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/system/login/Login.vue')
    },
    {
        path: '*',
        component: () => import('../views/system/error/NotFound.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

//全局路由守卫
router.beforeEach(async (to, from, next) => {
    NProgress.start()
    const token = getToken()
    if (token) {
        try {
            await store.dispatch('user/checkToken')
            if (to.path === '/login') next('/')
            else next()
        } catch (e) {
            // 清空token
            setToken('')
            next('/login')
        }
    } else {
        //没有登录
        if (to.path === '/login') next()
        else next('/login')
    }
})

router.afterEach((to, from) => {
    if (to.path !== '/login') {
        // 加载左侧菜单
        store.dispatch('menu/loadMenuTree')
    }
    NProgress.done()
})

export default router
