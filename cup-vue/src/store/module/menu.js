import api from '@/api/api'

const state = {
    activeRoute: '/welcome',
    menuList: []
}

const mutations = {
    //加载菜单树
    loadMenuTree(state, menuTree) {
        state.menuList = menuTree
    }
}

const actions = {
    //加载菜单树并添加tab
    loadMenuTree({ commit }) {
        api.menu.menuTree().then(res => {
            commit('loadMenuTree', res.data)
        })
    },
    //菜单树
    menuTree({ commit }) {
        return new Promise((resolve, reject) => {
            api.menu
                .menuTree()
                .then(res => {
                    resolve(res)
                })
                .catch(error => {
                    reject(error)
                })
        })
    },
    //查询菜单
    menuTreePage({ commit }) {
        return new Promise((resolve, reject) => {
            api.menu
                .menuTreePage()
                .then(res => {
                    resolve(res)
                })
                .catch(error => {
                    reject(error)
                })
        })
    },
    //新增菜单
    addMenu({ commit }, params) {
        return new Promise((resolve, reject) => {
            api.menu
                .addMenu(params)
                .then(res => {
                    resolve(res)
                })
                .catch(error => {
                    reject(error)
                })
        })
    },
    //修改菜单
    updateMenu({ commit }, params) {
        return new Promise((resolve, reject) => {
            api.menu
                .updateMenu(params)
                .then(res => {
                    resolve(res)
                })
                .catch(error => {
                    reject(error)
                })
        })
    },
    //删除菜单
    deleteMenu({ commit }, id) {
        return new Promise((resolve, reject) => {
            api.menu
                .deleteMenu(id)
                .then(res => {
                    resolve(res)
                })
                .catch(error => {
                    reject(error)
                })
        })
    }
}

const getters = {}

export default {
    namespaced: true,
    state,
    actions,
    getters,
    mutations
}
