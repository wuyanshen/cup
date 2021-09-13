import Vue from 'vue'
import axios from './lib/axios'
import vueAxios from 'vue-axios'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/global.css'
if (process.env.NODE_ENV === 'development') require('./mock')
// require('./mock')
// import Mock from './mock'
import api from '@/api/api'
import { resetForm } from "./lib/util";


Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(vueAxios, axios);
//api接口
Vue.prototype.$api = api;
Vue.prototype.resetForm = resetForm;



new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
