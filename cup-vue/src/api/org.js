import axios from './index.js'

//组织机构树
const orgTree = params => {
    return axios.get('/orgs/tree', params)
}

// 查询机构列表
const orgList = params => {
    return axios.get('/orgs/list', params)
}

//更新组织机构
const updateOrg = params => {
    return axios.put('/orgs', params)
}

//新增组织机构
const addOrg = params => {
    return axios.post('/orgs', params)
}

//删除组织机构
const deleteOrg = id => {
    return axios.delete(`/orgs/${id}`)
}

export default {
    orgTree,
    updateOrg,
    addOrg,
    orgList,
    deleteOrg
}
