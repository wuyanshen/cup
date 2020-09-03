import moment from 'moment'

export const setToken = (token) => {
    window.sessionStorage.setItem('token', token);
}

export const getToken = (key = 'token') => {
    return window.sessionStorage.getItem(key)
}

export const removeToken = (key = 'token') => {
    window.sessionStorage.removeItem('token')
}

export const setTenantId = (tenantId) => {
    return window.localStorage.setItem('tenantId', tenantId)
}

export const getTenantId = (key = 'tenantId') => {
    return window.localStorage.getItem(key)
}

export const removeTenantId = (key = 'tenantId') => {
    window.localStorage.removeItem(key)
}

//格式化时间
export const formatDate = (row,column) => {
    let date = row[column.property]
    return moment(date).format("YYYY-MM-DD HH:mm:ss")
}