import {
    setToken
} from '@/lib/util'
import api from '@/api/api'

const state = {
    username: 'admin',
    password: '123456',
}

const actions = {

    //修改系统名称
    editAppName({commit}) {
        commit('UPDATE_APPNAME', '全局actions修改')
    },
    //登录方法
    login({commit}, params) {
        return new Promise((resolve, reject) => {
            api.user.login(params).then(res => {
                if (res.code !== 0) reject(new Error('登录出错了'))
                else {
                    setToken(res.data)
                    resolve(res)
                }
            }).catch(error => {
                reject(error)
            })
        })
    },
    //校验并刷新token
    checkAndRefreshToken({commit}) {
        return new Promise((resolve, reject) => {
            api.user.refreshToken().then(res => {
                if (res.code !== 0) reject(new Error('token已过期'))
                else {
                    //刷新token，继续延长token的有效期
                    setToken(res.data)
                    resolve(res)
                }
            }).catch(error => {
                reject(error)
            })
        })
    },
    //用户分页查询
    userPage({commit}, params) {
        return new Promise((resolve, reject) => {
            api.user.userPage(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //新增用户
    addUser({commit}, user) {
        return new Promise((resolve, reject) => {
            api.user.addUser(user).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //修改用户
    updateUser({commit}, user) {
        return new Promise((resolve, reject) => {
            api.user.updateUser(user).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //删除用户
    deleteUser({commit}, id) {
        return new Promise((resolve, reject) => {
            api.user.deleteUser(id).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //修改密码
    updatePwd({commit}, user) {
        return new Promise((resolve, reject) => {
            api.user.updatePwd(user).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //获取当前登录用户信息
    userInfo({commit}, user) {
        return new Promise((resolve, reject) => {
            api.user.userInfo(user).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //校验原密码是否正确
    pwdCheck({commit}, params) {
        return new Promise((resolve, reject) => {
            api.user.pwdCheck(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //校验用户名是否存在
    usernameCheck({commit}, params) {
        return new Promise((resolve, reject) => {
            api.user.usernameCheck(params).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
    //获取用户角色id集合
    userRoleIds({commit}, id) {
        return new Promise((resolve, reject) => {
            api.user.userRoleIds(id).then(res => {
                resolve(res)
            }).catch(error => {
                reject(error)
            })
        })
    },
}

const getters = {

}

const mutations = {

}

export default {
    namespaced: true,
    getters,
    state,
    actions,
    mutations
}
