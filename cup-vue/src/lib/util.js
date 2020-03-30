export const setToken = (token) => {
    window.sessionStorage.setItem('token', token);
}

export const getToken = (key = 'token') => {
    return window.sessionStorage.getItem(key)
}