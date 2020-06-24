import api from '@/api/api'

const actions = {
    orgTree({commit}){
        return new Promise((resolve,reject) => {
            api.org.orgTree().then(res => {
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