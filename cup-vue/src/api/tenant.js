import axios from './index.js'

// 获取租户列表
const tenantList = () => {
    return axios.get('/tenant/list')
}

// 租户分页查询
const tenantPage = params => {
    return axios.get('/tenant/page', params)
}

// 新增租户
const tenantAdd = params => {
    return axios.post('/tenant', params)
}

// 修改租户
const tenantUpdate = params => {
    return axios.put('/tenant', params)
}

// 删除租户
const tenantDelete = id => {
    return axios.delete(`/tenant/${id}`)
}

export default {
    tenantList,
    tenantPage,
    tenantAdd,
    tenantUpdate,
    tenantDelete
}