import api from '@/api/api'

const actions = {
    //查询组织机构树
    orgTree({commit}){
        return new Promise((resolve,reject) => {
            api.org.orgTree().then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //新增组织机构
    addOrg({commit}, params){
        return new Promise((resolve, reject) => {
            api.org.addOrg(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //更新组织机构
    updateOrg({commit}, params){
        return new Promise((resolve, reject) => {
            api.org.updateOrg(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //删除组织机构
    deleteOrg({commit}, id){
        return new Promise((resolve, reject) => {
            api.org.deleteOrg(id).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    }
}

export default{
    namespaced: true,
    actions,
}