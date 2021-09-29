const menuRouter = [
    {
        path: 'welcome',
        name: 'welcome',
        meta: { title: '首页' },
        component: () => import('../views/system/Welcome.vue')
    },
    {
        path: 'user',
        name: 'user',
        meta: { title: '用户管理' },
        component: () => import('../views/system/user/Index.vue')
    },
    {
        path: 'menu',
        name: 'menu',
        meta: { title: '菜单管理' },
        component: () => import('../views/system/menu/Index.vue')
    },
    {
        path: 'role',
        name: 'role',
        meta: { title: '角色管理' },
        component: () => import('../views/system/role/Index.vue')
    },
    {
        path: 'dict',
        name: 'dict',
        meta: { title: '字典管理' },
        component: () => import('../views/system/dict/Index.vue')
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
        meta: { title: '字典数据' },
        component: () => import('../views/system/dict/DictData.vue')
    },
    {
        path: 'log',
        name: 'log',
        meta: { title: '系统日志' },
        component: () => import('../views/system/log/Index.vue')
    },
    {
        path: 'org',
        name: 'org',
        meta: { title: '部门管理' },
        component: () => import('../views/system/org/Index.vue')
    },
    {
        path: 'job',
        name: 'job',
        meta: { title: '任务管理' },
        component: () => import('../views/system/job/Index.vue')
    },
    {
        path: 'tenant',
        name: 'tenant',
        meta: { title: '租户管理' },
        component: () => import('../views/system/tenant/Index.vue')
    },
    {
        path: 'leaveBill',
        name: 'leaveBill',
        meta: { title: '请假' },
        component: () => import('../views/system/activiti/LeaveBill.vue')
    },
    {
        path: 'myTask',
        name: 'myTask',
        meta: { title: '我的待办' },
        component: () => import('../views/system/activiti/MyTask.vue')
    },
    {
        path: 'historyTask',
        name: 'HistoryTask',
        meta: { title: '历史任务' },
        component: () => import('../views/system/activiti/HistoryTask.vue')
    },
    {
        path: 'actManage',
        name: 'actManage',
        meta: { title: '流程管理' },
        component: () => import('../views/system/activiti/actManage.vue')
    },
    {
        path: 'monitor/druid',
        name: 'druid',
        meta: { title: '数据源监控' },
        component: () => import('../views/system/monitor/druid/index.vue')
    },
    {
        path: 'websocket',
        name: 'websocket',
        component: () => import('../views/system/websocket/Index.vue')
    }
]

export default menuRouter
