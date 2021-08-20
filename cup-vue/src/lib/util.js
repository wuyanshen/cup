import moment from 'moment'
import JsEcrypt from 'jsencrypt'

export const setToken = token => {
    window.sessionStorage.setItem('token', token)
}

export const getToken = (key = 'token') => {
    return window.sessionStorage.getItem(key)
}

export const removeToken = (key = 'token') => {
    window.sessionStorage.removeItem('token')
}

export const setTenantId = tenantId => {
    return window.localStorage.setItem('tenantId', tenantId)
}

export const getTenantId = (key = 'tenantId') => {
    return window.localStorage.getItem(key)
}

export const removeTenantId = (key = 'tenantId') => {
    window.localStorage.removeItem(key)
}

// 格式化时间
export const formatDate = (row, column) => {
    let date = row[column.property]
    return moment(date).format('YYYY-MM-DD HH:mm:ss')
}

// 加密
export const encode = content => {
    // 使用jsencrypt加密
    const encryptor = new JsEcrypt()
    // 之前ssl生成的公钥
    const pubKey =
        '-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiOn6nGu8Y2+hjro9eK/7JKoUh8JUTPwgT9nALay1wBM7fvOKCZy6wrcgFsOvmyC176e+jLJYTDODErgO3V9V2M+n0QAeo5kDb4oWJhhcWLiAVWdfdisnXJ51bKoKWp/bb4yDX6IHXVZe6rCDxopflwS8G1FKsukEfBBbP8PjfDQIDAQAB-----END PUBLIC KEY-----'
    encryptor.setPublicKey(pubKey) //设置公钥
    let encodeResult = encryptor.encrypt(content) // 对内容进行加密
    return encodeResult
}

// 解密
export const decode = content => {
    // 使用jsencrypt加密
    const encryptor = new JsEcrypt()
    // 之前ssl生成的私钥
    const priKey =
        '-----BEGIN RSA PRIVATE KEY-----MIICXAIBAAKBgQCiOn6nGu8Y2+hjro9eK/7JKoUh8JUTPwgT9nALay1wBM7fvOKCZy6wrcgFsOvmyC176e+jLJYTDODErgO3V9V2M+n0QAeo5kDb4oWJhhcWLiAVWdfdisnXJ51bKoKWp/bb4yDX6IHXVZe6rCDxopflwS8G1FKsukEfBBbP8PjfDQIDAQABAoGAM+YOuprtPsCrWahwiSn9+pE1wiPbLSsPBIPkWGLTSHDhVcZxmFI2J6OQx/FPpqcHlgrC9SrAYBvQlFsvKSpaSVHPzeIhZzJ1Ka7alFCj9MceJGFxt4RGfzcE5HsOMe0Tf4/IlxaPU/akurkxFgtcWvMOKz1wM53bdOtKaApK6IECQQDVPnkLhziPQ1UXwWkStUdz6fZ0dIbLvxHhzeey0z6ZrrVKllwetSZoZQvVGD1ZynEI0+dCnY4bFzPIPpDma8r9AkEAwsF2o6Xod0UTrHUDt6JPJEIkRgsMC0hdhBTPtFARUYr7Wx8e1HEbnrn672hxH2FqeIHTgQ8+Jdqj9zT6oYHJUQJAdZvEBMCqSBE5sCVivweuBdcGr0nJQjv6L9BxNmZdg0MhB6cP3XvJWBBKy1dYFtqZJuZACLR+uKA+VfVz0zGmPQJAaNCEEEjvFpmXn/4N9RumakYqjYPOhJf6tGYa7tkUqQUaiAz0o7MIAWHoekaEczYTfi2o7dGNSgQksJvTs25NMQJBALmmG5shFjTnyrkBcW27jPZIBkzZgQDlqQ3sce/iSm+rYE8R4Dd5WKdh8hXJRpnJEEn2yq/Nl5Qma+vLU8hsxio=-----END RSA PRIVATE KEY-----'
    encryptor.setPublicKey(priKey) //设置私钥
    let decodeResult = encryptor.decrypt(content) // 对内容进行加密
    return decodeResult
}
