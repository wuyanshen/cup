import Vue from 'vue'
import Vuex from 'vuex'
import state from './state'
import mutations from './mutations'
import actions from './actions'
import user from './module/user'
import getters from './getters'
import menu from './module/menu'
import log from './module/log'
import role from './module/role'
import org from './module/org'
import job from './module/job'
import tenant from './module/tenant'
import leave from './module/leave'
import { saveInLocal } from './plugin/saveInLocal'

Vue.use(Vuex)

export default new Vuex.Store({
    strict: process.env.NODE_ENV === 'development',
    state,
    mutations,
    actions,
    getters,
    modules: {
        user,
        menu,
		log,
        role,
        org,
        job,
        leave,
        tenant
    },
    plugins: [saveInLocal]
})
