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

// 表格格式化时间
export const formatDate = (row, column) => {
    let date = row[column.property]
    return moment(date).format('YYYY-MM-DD HH:mm:ss')
}

// 格式化时间
export const castDate = data => {
    return moment(data).format('YYYY-MM-DD HH:mm:ss')
}

// 加密
export const encode = content => {
    // 使用jsencrypt加密
    const encryptor = new JsEcrypt()
    // 之前ssl生成的公钥
    const pubKey =
        '-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1+2hj9AMl8YhGX60ti4BT8B2EitkgibJ8kTQyAiwxHT9beqLDKeRPw4iTbO1erPG6TqBlulOgdQgntMoKITFk1A+cEn/v6JpzGNOerG8Feh/qdo++A4MSGM8JTaD9e+BZzi9P3QI5uJoGr8U86amyT4xHq2PyU5HkbQGUElG/1wIDAQAB-----END PUBLIC KEY-----'
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
        '-----BEGIN PRIVATE KEY-----MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALX7aGP0AyXxiEZfrS2LgFPwHYSK2SCJsnyRNDICLDEdP1t6osMp5E/DiJNs7V6s8bpOoGW6U6B1CCe0ygohMWTUD5wSf+/omnMY056sbwV6H+p2j74DgxIYzwlNoP174FnOL0/dAjm4mgavxTzpqbJPjEerY/JTkeRtAZQSUb/XAgMBAAECgYBTB1KYkANDs5B2cz6jEgvJwDrndyYbd1etB97lliItgmeeyDQskRdu4QqWINOoJ4Xed/MslreC9QJ0g0LPHlfXIPf2zSTl2QSthwLHF/xQ+f0y6F4ASRs3zQq3TeJQYf65P8qBtcJdzVAuIJYsr9LxLEaiWS4gL9eJd4+YxBhECQJBAN2DSaAEwzaSzOSFavBJCA7BU+JcMEdHH6KKInMjoaahW2XoJqZGfBdImo8fr0q8LTOYjw458o6xzoLwpUkSyWsCQQDSUJBscHJuJizYs9PFKQVjPAalLgadi420/tbfVrww5Z4GcDFAOAzYPaQupp7+Bke0IJh731+H2cgY44/mk+JFAkBEyEWOWKw9P+w7cWo5XpQP8NwZR8L9/wnFsNrtobzKPwRgamvF6dESccr8cjw+Gpx2jwKsyjWVNYUIh6zrOdgtAkBcuujy5yGNL4fWhHN7GvslJfJIImMIU9/HThWvo66WYKesbwtIJW6EaalaaFzx5BL5eOXCuFqGq59uWee44ruBAkEAthh9IvHPRZiv0R3MVqoaBMu3MaNmVN/ec3J8CF6zPCRO3snx40Y6wDE8Iri2wBR6yiEcp4kpsjQMk1OwItYXAQ==-----END PRIVATE KEY-----'
    encryptor.setPublicKey(priKey) //设置私钥
    let decodeResult = encryptor.decrypt(content) // 对内容进行加密
    return decodeResult
}
