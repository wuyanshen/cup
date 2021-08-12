import axios from './index'

//获取当前登录用户信息方法
const userInfo = () => {
    return axios.get('/users/info')
}

//登录方法
const login = params => {
    // return axios.request({
    //     url: '/certification',
    //     method: 'post',
    //     data: params
    // })
    return axios.post('/certification', params)
}

//登出
const logout = () => {
    return axios.get('/signout')
}

//检查token方法
const checkToken = () => {
    return axios.get('/token/check')
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

//修改用户密码
const updatePwd = user => {
    return axios.put(`/users/pwd`, user)
}

//校验原密码是否正确
const pwdCheck = params => {
    return axios.get('/users/pwd/check', params)
}

//校验用户名是否已存在
const usernameCheck = params => {
    return axios.get('/users/name/check', params)
}

//获取用户角色id集合
const userRoleIds = id => {
    return axios.get(`/users/roleIds/${id}`)
}

export default {
    userInfo,
    login,
    logout,
    checkToken,
    userPage,
    addUser,
    updateUser,
    deleteUser,
    updatePwd,
    pwdCheck,
    usernameCheck,
    userRoleIds
}
