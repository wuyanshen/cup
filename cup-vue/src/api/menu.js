import axios from './index'

//查询菜单树
const menuTree = () => {
    return axios.get('/menus/tree')
}

//查询菜单
const menuTreePage = () => {
    return axios.get('/menus/treePage')
}

// 查询菜单列表
const menuList = params => {
    return axios.get('/menus/list', params)
}

//新增菜单
const addMenu = params => {
    console.log(params)
    return axios.post('/menus', params)
}
//修改菜单
const updateMenu = params => {
    return axios.put('/menus', params)
}
//删除菜单
const deleteMenu = id => {
    return axios.delete(`/menus/${id}`)
}

export default {
    menuTree,
    menuTreePage,
    addMenu,
    updateMenu,
    deleteMenu,
    menuList
}
