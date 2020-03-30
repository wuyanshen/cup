//封装axios
import axios from 'axios'
import { baseURL } from '@/config'
import { getToken } from '@/lib/util'
import { Message } from 'element-ui'
import qs from 'qs'


//设置请求超时
axios.defaults.timeout = 10000
//设置请求头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
//将post请求参数转成get参数形式 例如：/user?id=1&age=22&name=jack的形式
axios.defaults.transformRequest = data => qs.stringify(data)
//跨域请求是否允许携带cookie
axios.defaults.withCredentials = true

class HttpRequest {

    constructor(baseUrl = baseURL) {
        // this.baseUrl = baseUrl || baseURL //这个相当于参数中的baseUrl=baseURL
        this.baseUrl = baseURL
        //队列
        this.queue = {}
    }

    //全局配置
    getInsideConfig() {
        const config = {
            baseUrl: this.baseUrl,
            headers: {

            }
        }
        return config
    }

    //拦截器
    interceptors(instance) {
        //请求拦截器
        instance.interceptors.request.use(
            config => {
                //添加全局loading...
                //Spin->iView组件
                let token = getToken()
                token && (config.headers['token'] = token)
                return config
            },
            error => {
                return Promise.reject(error)
            }
        )

        //响应拦截器
        instance.interceptors.response.use(
            response => {
                let { data: { code } } = response

                switch (code) {
                    case 401:
                        Message.error('登录超时，请重新登录')
                        break;

                    case 403:
                        Message.error('您没有权限访问该资源，请联系管理员哦~')
                        break;

                    case 404:
                        Message.error('没有找到您要访问的资源哦~')
                        break;
                }
                return response.data
            },
            error => {

                let { response } = error
                //服务器有返回结果
                if (response) {
                    switch (response.status) {

                        case 401:
                            Message.error('未登录，请重新登录')
                            break;

                        case 403:
                            Message.error('您没有权限访问该资源，请联系管理员哦~')
                            break;

                        case 404:
                            Message.error('没有找到您要访问的资源哦~')
                            break;

                        case 500:
                            Message.error('服务器出错啦~')
                            break;
                    }
                    return Promise.reject(error.response)

                    //服务器没返回结果
                } else {
                    //断网
                    if (!window.navigator.onLine) {
                        //跳转到断网页面
                        Message.error('断网了~~~')
                        return;
                    }

                    //服务器崩了
                    return Promise.reject(error)
                }

            }
        )
    }

    //定义方法
    request(options) {
        const instance = axios.create()
        //es6 对配置进行合并,把两个对象进行合并,相同的key合并时默认取后面的对象覆盖前面的对象
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance)
        return instance(options)
    }
}

export default HttpRequest