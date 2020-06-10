import moment from 'moment'

export const setToken = (token) => {
    window.sessionStorage.setItem('token', token);
}

export const getToken = (key = 'token') => {
    return window.sessionStorage.getItem(key)
}

//格式化时间
export const formatDate = (row,column) => {
    let date = row[column.property]
    return moment(date).format("YYYY-MM-DD HH:mm:ss")
}