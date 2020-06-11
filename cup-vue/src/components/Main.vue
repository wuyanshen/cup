<template>
  <div class="main_div">
  	<el-tabs :value="activeRoute" @tab-click="tabClick" @tab-remove="tabRemove">
	  <el-tab-pane
	    v-for="(item, index) in maintabs"
	    :key="item.url"
	    :label="item.title"
	    :name="item.url"
		:closable="item.closable"
	  >
	  </el-tab-pane>
	</el-tabs>
	<router-view v-if="routerViewShowHide"></router-view>
    <myiframe></myiframe>
  </div>
</template>

<script>
import {mapState,mapMutations} from 'vuex'
import myiframe from '@/components/myiframe.vue'
export default {
  data() {
    return {
    };
  },
  components:{
      myiframe
  },
  created() {},
  mounted() {},
  computed:{
  	...mapState('tabs',['maintabs','activeRoute']),
    ...mapState(["routerViewShowHide"]),
  },
  methods: {
  	...mapMutations('tabs',['removeTab','setActiveRoute']),
    ...mapMutations(["UPDATE_IFRAME_URL","UPDATE_IFRAME_STYLE","UPDATE_ROUTER_VIEW"]),
	//点击标签方法
  	tabClick(item){
        let url = item.name
        if (url.indexOf('http')!=-1 || url.indexOf('https')!=-1) {
            this.UPDATE_IFRAME_STYLE({visibility: 'visible', height: '100%'})
            this.UPDATE_IFRAME_URL(url)
            this.UPDATE_ROUTER_VIEW(false)
        }else{
            this.UPDATE_ROUTER_VIEW(true)
            this.UPDATE_IFRAME_URL("")
            this.UPDATE_IFRAME_STYLE({visibility: 'hidden', height: '0'})
            this.$router.push(item.name).catch(err => {err})
        }
		this.setActiveRoute(item.name)
  	},
	//点击删除标签方法
  	tabRemove(url){
        //如果删除的是外部链接标签
        if (url.indexOf('http')!=-1 || url.indexOf('https')!=-1) {
            this.UPDATE_IFRAME_STYLE({visibility: 'hidden', height: '0'})
            this.UPDATE_IFRAME_URL("")
            this.removeTab(url)
            this.UPDATE_ROUTER_VIEW(true)
            
            let nextUrl = this.maintabs[this.maintabs.length-1].url
            this.$router.push(nextUrl).catch(err => {err})
            console.log('url',url)
            console.log('newxUrl',nextUrl)
                
        //如果删除的是系统内标签
        }else{
            //将删除标签后显示的标签设置为最后一个
            let nextUrl = this.maintabs[this.maintabs.length-1].url
            //如果要删除的标签是最后一个，那么就将nextUrl变成倒数第二个
            if(nextUrl === url){
                nextUrl = this.maintabs[this.maintabs.length-2].url
            }
            
            //如果删除后显示的标签时外部链接标签
            if(nextUrl.indexOf('http')!=-1 || nextUrl.indexOf('https')!=-1){
                this.UPDATE_IFRAME_STYLE({visibility: 'visible', height: '100%'})
                this.UPDATE_IFRAME_URL(nextUrl)
                this.UPDATE_ROUTER_VIEW(false)
                this.removeTab(url)
                
            //如果删除后显示的标签时系统内标签
            }else{
                this.removeTab(url)
                this.$router.push(nextUrl).catch(err => {err})
            }
        }
  	}
  }
};
</script>

<style scoped lang="less">
	.main_div{
		height: 100%;
	}
</style>
