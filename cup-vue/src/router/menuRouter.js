const menuRouter = [ {
                path: 'welcome',
                name: 'welcome',
                component: () => import('../views/Welcome.vue')
            }, {
                path: 'user',
                name: 'user',
                component: () => import('../views/user/Index.vue')
            }, {
                path: 'menu',
                name: 'menu',
                component: () => import('../views/menu/Index.vue')
            },
            {
                path: 'role',
                name: 'role',
                component: () => import('../views/role/Index.vue')
            },
            {
                path: 'dict',
                name: 'dict',
                component: () => import('../views/dict/Index.vue'),
                // children: [
                //     {
                //         path: 'data/:typeCode',
                //         component: () => import('../views/dict/DictData.vue'),
                //         name: 'dictData',
                //     }]
            },
            {
                path: 'dict/data/:id(\\d+)',
                name: 'dictData',
                component: () => import('../views/dict/DictData.vue')
            },
            {
                path: 'log',
                name: 'log',
                component: () => import('../views/log/Index.vue')
            },
            {
                path: 'org',
                name: 'org',
                component: () => import('../views/org/Index.vue')
            },
            {
                path: 'job',
                name: 'job',
                component: () => import('../views/job/Index.vue')
            },
            {
                path: 'tenant',
                name: 'tenant',
                component: () => import('../views/tenant/Index.vue')
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
                path: 'historyTask',
                name: 'HistoryTask',
                component: () => import('../views/activiti/HistoryTask.vue')
            },
            {
                path: 'actManage',
                name: 'actManage',
                component: () => import('../views/activiti/actManage.vue')
            },
            {
                path: 'websocket',
                name: 'websocket',
                component: () => import('../views/websocket/Index.vue')
            }]

export default menuRouter
