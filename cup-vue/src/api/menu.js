import axios from './index'

//获取当前登录用户的菜单树
const menuTree = () => {
    return axios.get('/menus/tree')
}
//新增菜单
const addMenu = params => {
    return axios.post('/menus',params)
}
//修改菜单
const updateMenu = params => {
    return axios.put('/menus',params)
}
//删除菜单
const deleteMenu = id => {
    return axios.delete('/menus',id)
}

export default {
    menuTree,
    addMenu,
    updateMenu,
    deleteMenu
}
