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
	//点击标签方法
  	tabClick(item){
		this.$router.push(item.name).catch(err=>err)
		this.setActiveRoute(item.name)
  	},
	//点击删除标签方法
  	tabRemove(url){
  		this.removeTab(url)
		this.$router.push(this.maintabs[this.maintabs.length-1].url)
  	}
  }
};
</script>

<style scoped lang="less">
	.main_div{
		height: 100%;
	}
</style>
