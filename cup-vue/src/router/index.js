import Vue from 'vue'
import VueRouter from 'vue-router'
import {
    setToken,
    getToken
} from '@/lib/util'
import store from '@/store'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Home',
        redirect: '/welcome',
        component: () => import('../views/Home.vue'),
        children: [{
                path: '/about',
                name: 'About',
                component: () => import('../views/About.vue')
            }, {
                path: '/welcome',
                name: 'Welcome',
                component: () => import('../views/Welcome.vue')
            }, {
                path: '/users**',
                name: 'User',
                component: () => import('../views/user/User.vue')
            }, {
                path: '/menus**',
                name: 'Menu',
                component: () => import('../views/menu/Menu.vue')
            },
            {
                path: '/roles**',
                name: 'Role',
                component: () => import('../views/role/Role.vue')
            },
            {
                path: '/logs**',
                name: 'Log',
                component: () => import('../views/log/Log.vue')
            },
            {
                path: '/nextTickDemo',
                name: 'nextTickDemo',
                component: () => import('../views/nextTickDemo.vue')
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/login/Login.vue'),
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

//全局路由守卫
router.beforeEach(async (to, from, next) => {
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
            await store.dispatch('checkAndRefreshToken');
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

export default router