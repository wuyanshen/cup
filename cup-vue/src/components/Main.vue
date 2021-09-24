<template>
  <div class="main_div">
  	<!--<el-tabs :value="activeRoute" @tab-click="tabClick" @tab-remove="tabRemove">-->
	<!--  <el-tab-pane-->
	<!--    v-for="(item, index) in maintabs"-->
	<!--    :key="item.url"-->
	<!--    :label="item.title"-->
	<!--    :name="item.url"-->
	<!--	:closable="item.closable"-->
	<!--  >-->
	<!--  </el-tab-pane>-->
	<!--</el-tabs>-->
    <v-tags></v-tags>
      <transition name="move" mode="out-in">
          <keep-alive :include="tagsList">
              <router-view v-if="routerViewShowHide"></router-view>
          </keep-alive>
      </transition>
    <myiframe></myiframe>
  </div>
</template>

<script>
import {mapState,mapMutations} from 'vuex'
import myiframe from '@/components/myiframe.vue'
import vTags from '@/components/Tags.vue';
import bus from '@/components/bus.js';

export default {
  data() {
    return {
        tagsList: [],
    };
  },
  components:{
      myiframe,
      vTags
  },
    created() {
        // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
        bus.$on('tags', msg => {
            let arr = [];
            for (let i = 0, len = msg.length; i < len; i++) {
                msg[i].name && arr.push(msg[i].name);
            }
            this.tagsList = arr;
        });
    },
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
  	// tabRemove(url){
    //     //如果删除的是外部链接标签
    //     if (url.indexOf('http')!=-1 || url.indexOf('https')!=-1) {
    //         this.UPDATE_IFRAME_STYLE({visibility: 'hidden', height: '0'})
    //         this.UPDATE_IFRAME_URL("")
    //         this.removeTab(url)
    //         this.UPDATE_ROUTER_VIEW(true)
    //
    //         let nextUrl = this.maintabs[this.maintabs.length-1].url
    //         console.log('url',url)
    //         console.log('newxUrl',nextUrl)
    //         //如果删除后显示的标签时外部链接标签
    //         if(nextUrl.indexOf('http')!=-1 || nextUrl.indexOf('https')!=-1){
    //             this.UPDATE_IFRAME_STYLE({visibility: 'visible', height: '100%'})
    //             this.UPDATE_IFRAME_URL(nextUrl)
    //             this.UPDATE_ROUTER_VIEW(false)
    //             this.removeTab(url)
    //
    //         //如果删除后显示的标签时系统内标签
    //         }else{
    //             this.removeTab(url)
    //             this.$router.push(nextUrl).catch(err => {err})
    //         }
    //
    //     //如果删除的是系统内标签
    //     }else{
    //         //将删除标签后显示的标签设置为最后一个
    //         let nextUrl = this.maintabs[this.maintabs.length-1].url
    //         //如果要删除的标签是最后一个，那么就将nextUrl变成倒数第二个
    //         if(nextUrl === url){
    //             nextUrl = this.maintabs[this.maintabs.length-2].url
    //         }
    //
    //         //如果删除后显示的标签时外部链接标签
    //         if(nextUrl.indexOf('http')!=-1 || nextUrl.indexOf('https')!=-1){
    //             this.UPDATE_IFRAME_STYLE({visibility: 'visible', height: '100%'})
    //             this.UPDATE_IFRAME_URL(nextUrl)
    //             this.UPDATE_ROUTER_VIEW(false)
    //             this.removeTab(url)
    //
    //         //如果删除后显示的标签时系统内标签
    //         }else{
    //             this.removeTab(url)
    //             this.$router.push(nextUrl).catch(err => {err})
    //         }
    //     }
  	// }
  }
};
</script>

<style lang="less">
	.main_div{
		height: 100%;
	}

    // 定义进入\离开过渡生效时的状态
    .move-enter-active,
    .move-leave-active {
        transition: 0.5s;
    }

    // 定义进入\离开过渡的开始状态
    .move-enter,
    .move-leave {
        opacity: 0;
    }
</style>
