import axios from './index'

//测试获取用户名方法
const getUserInfo = () => {
    return axios.get('/sysUser/info')
}

//登录方法
const login = params => {
    return axios.request({
        url: '/login',
        method: 'post',
        data: params
    })
}

//刷新token方法
const refreshToken = () => {
    return axios.get('/token/refresh')
}

//分页查询用户
const userPage = params => {
    return axios.get('/users/page', params)
}

//新增用户
const addUser = user => {
    return axios.post('/users', user)
}

//更新用户
const updateUser = user => {
    return axios.put('/users', user)
}

//删除用户
const deleteUser = id => {
    return axios.delete(`/users/${id}`)
}


export default {
    getUserInfo,
    login,
    refreshToken,
    userPage,
    addUser,
    updateUser,
    deleteUser,
}