import * as types from './type/mutations_type'

const mutations = {
    //修改后台名称
    [types.UPDATE_APPNAME](state, value) {
        state.appName = value
    },
    //控制左侧菜单栏缩放
    [types.UPDATE_COLLAPSE](state, value) {
        state.siderCollapse = value
    }
}

export default mutations