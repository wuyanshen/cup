import Vue from 'vue'
import axios from './lib/axios'
import vueAxios from 'vue-axios'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import Bus from './lib/bus'
import './assets/css/global.css'
if (process.env.NODE_ENV === 'development') require('./mock')
// require('./mock')
// import Mock from './mock'
import api from '@/api/api'


Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(vueAxios, axios)
// Vue.prototype.$bus = Bus
//api接口
Vue.prototype.$api = api



new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
