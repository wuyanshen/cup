//封装axios
import axios from 'axios'
import { baseURL } from '@/config'
import { getToken } from '@/lib/util'
import { Message } from 'element-ui'
import { showLoading,hideLoading } from '@/lib/loading'
import qs from 'qs'


// //设置请求超时
// axios.defaults.timeout = 10000
// //设置请求头
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// //将post请求参数转成get参数形式 例如：/user?id=1&age=22&name=jack的形式
// axios.defaults.transformRequest = data => qs.stringify(data)
// //跨域请求是否允许携带cookie
// axios.defaults.withCredentials = true

class HttpRequest {

    constructor(baseUrl=baseURL) {
        // this.baseUrl = baseUrl || baseURL //这个相当于参数中的baseUrl=baseURL
        this.baseUrl = baseUrl
        //队列
        this.queue = {}
    }

    //全局配置
    getInsideConfig() {
        const config = {
            //跨域请求是否允许携带cookie
            withCredentials: true,
            baseURL: this.baseUrl,
            //将post请求参数转成get参数形式 例如：/user?id=1&age=22&name=jack的形式
            transformRequest: data => qs.stringify(data),
            //设置请求超时
            timeout: 10000,
            headers: {
                //设置默认请求头
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            }
        }
        return config
    }

    //拦截器
    interceptors(instance, url) {
        
        //请求拦截器
        instance.interceptors.request.use(
        
            config => {
                //添加全局loading...
                //Spin->iView组件
                if(!Object.keys(this.queue).length)showLoading()
                
                //在所有请求头设置token
                let token = getToken()
                token && (config.headers['token'] = token)
                return config
            },
            error => {
                // hideLoading()
                return Promise.reject(error)
            }
        )

        //响应拦截器
        instance.interceptors.response.use(
            response => {
                delete this.queue[url]
                hideLoading()
                let { data: { code } } = response

                switch (code) {
                    case 401:
                        Message.error('登录超时，请重新登录')
                        break;

                    case 403:
                        Message.error('您没有权限执行此操作，请联系管理员哦~')
                        break;

                    case 404:
                        Message.error('没有找到您要访问的资源哦~')
                        break;

                    case 420:
                        Message.error('用户名或密码错误~')
                        break;

                    case 433:
                        Message.error('您的账户已被禁用，请联系管理员哦~')
                        break;
                }
                setTimeout(() => {
                    
                }, 5000);
                return response.data
            },
            error => {
                delete this.queue[url]
                hideLoading()
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

                        case 420:
                            Message.error('用户名或密码错误~')
                            break;

                        case 433:
                            Message.error('您的账户已被禁用，请联系管理员哦~')
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

    //自定义方法
    request(options) {
        const instance = axios.create()
        //es6 对配置进行合并,把两个对象进行合并,相同的key合并时默认取后面的对象覆盖前面的对象
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance, options.url)
        return instance(options)
    }

    //封装post方法
    post(url, data) {
        let options = {
            url: url,
            method: 'post',
            data,
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            transformRequest: data => JSON.stringify(data)
        }
        const instance = axios.create()
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance)
        return instance(options, options.url)
    }

    //封装put方法
    put(url, data) {
        let options = {
            url: url,
            method: 'put',
            data,
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            transformRequest: data => JSON.stringify(data)
        }
        const instance = axios.create()
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance, options.url)
        return instance(options)
    }

    //封装get方法
    get(url, params) {
        let options = {
            url,
            method: 'get',
            params
        }
        const instance = axios.create()
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance, options.url)
        console.log(this.queue)
        return instance(options)
    }

    //封装delete方法
    delete(url, data) {
        let options = {
            url,
            method: 'delete',
            data
        }
        const instance = axios.create()
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance, options.url)
        return instance(options)
    }

}

export default HttpRequest
