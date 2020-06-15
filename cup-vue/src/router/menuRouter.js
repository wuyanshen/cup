const menuRouter = [{
                path: 'about',
                name: 'about',
                component: () => import('../views/About.vue')
            }, {
                path: 'welcome',
                name: 'welcome',
                component: () => import('../views/Welcome.vue')
            }, {
                path: 'user',
                name: 'user',
                component: () => import('../views/user/User.vue')
            }, {
                path: 'menu',
                name: 'menu',
                component: () => import('../views/menu/Menu.vue')
            },
            {
                path: 'role',
                name: 'role',
                component: () => import('../views/role/Role.vue')
            },
            {
                path: 'log',
                name: 'log',
                component: () => import('../views/log/Log.vue')
            },
            {
                path: 'nextTickDemo',
                name: 'nextTickDemo',
                component: () => import('../views/nextTickDemo.vue')
            },
            {
                path: 'websocket',
                name: 'websocket',
                component: () => import('../views/websocket/websocket.vue')
            }]

export default menuRouter