import * as types from './type/mutations_type'

const mutations = {
    //修改后台名称
    [types.UPDATE_APPNAME](state, value) {
        state.appName = value
    },
    //控制左侧菜单栏缩放
    [types.UPDATE_COLLAPSE](state, value) {
        state.siderCollapse = value
    },
    //修改iframeUrl
    [types.UPDATE_IFRAME_URL](state, value) {
       state.iframeUrl = value 
    },
    //修改iframeStyle
    [types.UPDATE_IFRAME_STYLE](state, value) {
       state.iframeStyle = value 
    },
    //修改router-view的显示隐藏
    [types.UPDATE_ROUTER_VIEW](state, value) {
       state.routerViewShowHide = value 
    },
}

export default mutations