const menuRouter = [{
                path: 'about',
                name: 'About',
                component: () => import('../views/About.vue')
            }, {
                path: 'welcome',
                name: 'Welcome',
                component: () => import('../views/Welcome.vue')
            }, {
                path: 'users**',
                name: 'User',
                component: () => import('../views/user/User.vue')
            }, {
                path: 'menus**',
                name: 'Menu',
                component: () => import('../views/menu/Menu.vue')
            },
            {
                path: 'roles**',
                name: 'Role',
                component: () => import('../views/role/Role.vue')
            },
            {
                path: 'logs**',
                name: 'Log',
                component: () => import('../views/log/Log.vue')
            },
            {
                path: 'nextTickDemo',
                name: 'nextTickDemo',
                component: () => import('../views/nextTickDemo.vue')
            }]

export default menuRouter