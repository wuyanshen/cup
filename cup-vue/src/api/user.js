import axios from './index'

//测试获取用户名方法
const getUserInfo = () => {
    return axios.request({
        url: '/sysUser/info',
        method: 'get'
    })
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
    return axios.request({
        url: '/token/refresh',
        method: 'get',
    })
}

//分页查询用户
const userPage = (current, page) => {
    return axios.request({
        url: '/users/page',
        method: 'get',
        params: {
            current,
            page
        }
    })
}

export default {
    getUserInfo,
    login,
    refreshToken,
    userPage
}