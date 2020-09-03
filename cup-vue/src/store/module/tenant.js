import api from '@/api/api'

const actions = {
    // 租户列表
    tenantList(){
        return api.tenant.tenantList()
    },
    
    // 租户分页查询
    tenantPage({commit}, params){
        return api.tenant.tenantPage(params)
    },
    
    // 新增租户
    tenantAdd({commit}, params) {
        return api.tenant.tenantAdd(params)
    },
    
    // 修改租户
    tenantUpdate({commit}, params) {
        return api.tenant.tenantUpdate(params)
    },
    
    // 删除租户
    tenantDelete({commit}, id) {
        return api.tenant.tenantDelete(id)
    }
}

export default {
    namespaced: true,
    actions
}