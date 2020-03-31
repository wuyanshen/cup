<template>
  <div class="sider">
    <!-- 缩放按钮 -->
    <div class="collapse_btn_div" @click="handleCollapse">|||</div>
    <!-- 左侧菜单栏 -->
    <el-menu
      default-active="2"
      background-color="#325272"
      text-color="#fff"
      :collapse="siderCollapse"
      :collapse-transition="false"
      router
    >
      <!-- <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>权限管理</span>
        </template>
        <el-menu-item index="/about">关于我们</el-menu-item>
        <el-menu-item index="/user">用户管理</el-menu-item>
      </el-submenu>-->

      <el-submenu index="1" v-for="(it1,index1) of menuTrees" :key="index1">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>{{it1.menuName}}</span>
        </template>
        <el-menu-item
          v-for="(it2,index2) of it1.children"
          :key="index2"
          :index="it2.url"
        >{{ it2.menuName }}</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
export default {
  data() {
    return {
      menuTrees: []
    };
  },
  async mounted() {
    let res = await this.menuTree();
    if (res.code === 0) {
      this.menuTrees = res.data;
    }
  },
  computed: {
    ...mapState(["siderCollapse"])
  },
  methods: {
    ...mapActions(["menuTree"]),
    ...mapMutations(["UPDATE_COLLAPSE"]),
    handleCollapse() {
      this.UPDATE_COLLAPSE(!this.siderCollapse);
    }
  }
};
</script>

<style scoped lang="less">
.el-menu {
  border: none;
}

.collapse_btn_div {
  line-height: 20px;
  color: #fff;
  text-align: center;
  cursor: pointer;
  border-bottom: 1px solid #2b4b6b;
}
</style>
