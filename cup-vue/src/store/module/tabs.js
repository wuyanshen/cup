import api from '@/api/api'

const state = {
	maintabs:[{url:'/welcome',title:'首页',closable:false}],
	activeRoute:'/welcome',
	menuList:[
		{
			menuName: '非菜单路由',
			url:'',
			children:[
				{	menuName:'个人信息',url:'/info'}
			]
		}
	],
}

const mutations = {
	//添加tab
	addTabMutation(state, url){
		let isAreadyIn = state.maintabs.some(item => item.url === url);
		if(!isAreadyIn){
			if(state.menuList.length>=1){
				let route = []
				for(let i in state.menuList){
					route = state.menuList[i].children.filter(item => item.url === url)
					if(route.length>0){
						break
					}
				}
				state.maintabs.push({url:url, title:route[0].menuName, closable:true})
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
		state.menuList = [state.menuList[0], ...menuTree]
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
