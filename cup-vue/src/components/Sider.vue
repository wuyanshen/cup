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
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>权限管理</span>
        </template>
        <el-menu-item index="/about">关于我们</el-menu-item>
        <el-menu-item index="/user">用户管理</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  data() {
    return {};
  },
  computed: {
    ...mapState(["siderCollapse"])
  },
  methods: {
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
