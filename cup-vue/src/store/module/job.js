import api from '@/api/api'

const state = {}

const actions = {
    // 任务分页查询
    jobPage({commit}, params) {
        return new Promise((resolve, reject) => {
            api.job.jobPage(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    // 新增/修改任务
    addJob({commit}, params) {
        return new Promise((resolve, reject) => {
            api.job.addJob(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    // 删除任务
    deleteJob({commit}, params) {
        return new Promise((resolve, reject) => {
            api.job.deleteJob(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    // 停止任务
    pauseJob({commit}, params) {
        return new Promise((resolve, reject) => {
            api.job.pauseJob(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    // 恢复任务
    resumeJob({commit}, params) {
        return new Promise((resolve, reject) => {
            api.job.resumeJob(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    // 手动执行一次任务
    triggerJob({commit}, params) {
        return new Promise((resolve, reject) => {
            api.job.trigger(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    // 检查cron表达式是否正确
    check({commit}, params) {
        return new Promise((resolve, reject) => {
            api.job.check(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    }
}

const getters = {}

const mutations = {}

export default {
    namespaced: true,
    state,
    actions,
    getters,
    mutations
}
