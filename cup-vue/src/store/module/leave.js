import api from '@/api/api'

const state = {
	
}

const actions = {
	// 新增请假
	leaveAdd({commit}, params){
		return new Promise((resolve,reject) => {
			api.leave.leaveAdd(params).then(res => {
				resolve(res)
			}).catch(error => {
				reject(error)
			})
		})
    },
    // 查询请假列表
	leavePage({commit}, params){
		return new Promise((resolve,reject) => {
			api.leave.leavePage(params).then(res => {
				resolve(res)
			}).catch(error => {
				reject(error)
			})
		})
    },
    // 修改请假
	leaveUpdate({commit}, params){
		return new Promise((resolve,reject) => {
			api.leave.leaveUpdate(params).then(res => {
				resolve(res)
			}).catch(error => {
				reject(error)
			})
		})
    },
    // 删除请假列表
	leaveDelete({commit}, params){
		return new Promise((resolve,reject) => {
			api.leave.leaveDelete(params).then(res => {
				resolve(res)
			}).catch(error => {
				reject(error)
			})
		})
	},
}

const getters = {
	
}

const mutations = {
	
}

export default{
	namespaced: true,
	state,
	actions,
	getters,
	mutations
}