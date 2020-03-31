import axios from './index'

//获取当前登录用户的菜单树
const menuTree = () => {
    return axios.get('/menus/tree')
}

export default {
    menuTree,
}