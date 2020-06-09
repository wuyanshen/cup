import api from '@/api/api'

const actions = {
    //保存权限
    permission({commit},params){
        return new Promise((resolve,reject) => {
            api.role.permission(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //通过角色id查询菜单id集合
    roleMenuIds({commit},id){
        return new Promise((resolve,reject) => {
            api.role.roleMenuIds(id).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //角色列表
    roleList({commit}){
        return new Promise((resolve,reject) => {
            api.role.roleList().then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //分页查询角色
    rolePage({commit},params){
        return new Promise((resolve,reject) => {
            api.role.rolePage(params).then(res =>{
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //新增角色
    roleAdd({commit}, params){
        return new Promise((resolve,reject) => {
            api.role.roleAdd(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //修改角色
    roleUpdate({commit}, params){
        return new Promise((resolve,reject) => {
            api.role.roleUpdate(params).then(res=>{
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //删除角色
    roleDelete({commit},id){
        return new Promise((resolve,reject) => {
            api.role.roleDelete(id).then(res=>{
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
}

const getters = {
    
}

const state = {
    
}

const mutations = {
    
}


export default {
    namespaced: true,
    actions,
    state,
    getters,
    mutations
}