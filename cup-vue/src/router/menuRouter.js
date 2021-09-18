const menuRouter = [ {
                path: 'welcome',
                name: 'welcome',
                component: () => import('../views/system/Welcome.vue')
            }, {
                path: 'user',
                name: 'user',
                component: () => import('../views/system/user/Index.vue')
            }, {
                path: 'menu',
                name: 'menu',
                component: () => import('../views/system/menu/Index.vue')
            },
            {
                path: 'role',
                name: 'role',
                component: () => import('../views/system/role/Index.vue')
            },
            {
                path: 'dict',
                name: 'dict',
                component: () => import('../views/system/dict/Index.vue'),
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
                component: () => import('../views/system/dict/DictData.vue')
            },
            {
                path: 'log',
                name: 'log',
                component: () => import('../views/system/log/Index.vue')
            },
            {
                path: 'org',
                name: 'org',
                component: () => import('../views/system/org/Index.vue')
            },
            {
                path: 'job',
                name: 'job',
                component: () => import('../views/system/job/Index.vue')
            },
            {
                path: 'tenant',
                name: 'tenant',
                component: () => import('../views/system/tenant/Index.vue')
            },
            {
                path: 'leaveBill',
                name: 'leaveBill',
                component: () => import('../views/system/activiti/LeaveBill.vue')
            },
            {
                path: 'myTask',
                name: 'myTask',
                component: () => import('../views/system/activiti/MyTask.vue')
            },
            {
                path: 'historyTask',
                name: 'HistoryTask',
                component: () => import('../views/system/activiti/HistoryTask.vue')
            },
            {
                path: 'actManage',
                name: 'actManage',
                component: () => import('../views/system/activiti/actManage.vue')
            },
            {
                path: 'websocket',
                name: 'websocket',
                component: () => import('../views/system/websocket/Index.vue')
            }]

export default menuRouter
