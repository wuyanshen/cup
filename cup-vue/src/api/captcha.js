import axios from './index.js'

const captchaImage = params => {
    return axios.get('/captcha/captchaImage')
}

export default {
    captchaImage
}
