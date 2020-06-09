import axios from './index.js'

//保存权限
const permission = params => {
    return axios.put(`/roles/permission`,params)
}

//通过角色id查询菜单id集合
const roleMenuIds = id => {
    return axios.get(`/roles/menuIds/${id}`)
}

//角色列表
const roleList = () => {
    return axios.get('/roles/list')
}

//分页查询角色
const rolePage = params => {
    return axios.get('/roles/page',params)
}

//新增角色
const roleAdd = params => {
    return axios.post('/roles',params)
}

//修改角色
const roleUpdate = params => {
    return axios.put('/roles',params)
}

//删除角色
const roleDelete = id => {
    return axios.delete(`/roles/${id}`)
}

export default {
    rolePage,
    roleAdd,
    roleUpdate,
    roleDelete,
    roleList,
    roleMenuIds,
    permission,
}
