import api from '@/api/api'

const state = {
	maintabs:[{url:'/welcome',title:'首页',closable:false}],
	activeRoute:'/welcome',
	menuList:[],
}

const mutations = {
	//添加tab
	addTabMutation(state, url){
		let isAreadyIn = state.maintabs.some(item => item.url === url);
		if(!isAreadyIn){
			if(state.menuList.length>=1){
				let route = state.menuList[0].children.filter(item => item.url === url)
				state.maintabs.push({url:url,title:route[0].menuName, closable:true})
			}
		}
		state.activeRoute = url
	},
	//删除tab
	removeTab(state, url){
		state.maintabs = state.maintabs.filter(item=>item.url !== url)
		state.activeRoute = state.maintabs[state.maintabs.length-1].url
	},
	//设置当前激活的tab的url
	setActiveRoute(state, url){
		state.activeRoute = url
	},
	//加载菜单树
	loadMenuTree(state,menuTree){
		state.menuList = menuTree
	},
	//退出登录后初始化tab数组
	initMainTabs(state){
		state.maintabs = [{url:'/welcome',title:'首页',closable:false}]
	}
}

const actions = {
	//加载菜单树并添加tab
	addTab({state, commit}, url){
		api.menu.menuTree().then(res => {
			commit('loadMenuTree',res.data)
			commit('addTabMutation',url)
		})
	},
}

export default {
	namespaced: true,
	state,
	mutations,
	actions,
}
