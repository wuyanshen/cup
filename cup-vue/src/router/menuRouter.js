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
                path: 'org',
                name: 'org',
                component: () => import('../views/org/Org.vue')
            },
            {
                path: 'job',
                name: 'job',
                component: () => import('../views/job/Job.vue')
            },
            {
                path: 'tenant',
                name: 'tenant',
                component: () => import('../views/tenant/Tenant.vue')
            },
            {
                path: 'leaveBill',
                name: 'leaveBill',
                component: () => import('../views/activiti/LeaveBill.vue')
            },
            {
                path: 'myTask',
                name: 'myTask',
                component: () => import('../views/activiti/MyTask.vue')
            },
            {
                path: 'websocket',
                name: 'websocket',
                component: () => import('../views/websocket/websocket.vue')
            }]

export default menuRouter
