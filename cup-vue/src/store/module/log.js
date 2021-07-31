import api from '@/api/api'

const state = {
	
}

const actions = {
	//日志分页查询
	logPage({commit}, params){
		return new Promise((resolve,reject) => {
			api.log.logPage(params).then(res => {
				resolve(res)
			}).catch(error => {
				reject(error)
			})
		})
	},
	// 删除日志
	deleteLogs({ commit }, params) {
		return new Promise((resolve, reject) => {
			api.log.deleteLogs(params).then(res => {
				resolve(res)
			}).catch(error => {
				reject(error)
			})
		})
	}
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