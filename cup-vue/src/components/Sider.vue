<template>
  <div class="sider">
    <!--logo-->
    <div class="logo">
        <el-avatar shape="square" :size="64" :src="avatar"></el-avatar>
        <div class="title" v-if="!siderCollapse">{{appName}}</div>
    </div>
    <!-- 左侧菜单栏 -->
    <el-menu
      :default-active="activeRoute"
      :collapse="siderCollapse"
      :collapse-transition="false"
      router
    >
      <!-- 一级菜单 -->
      <el-submenu :index="it1.url" v-for="(it1,index1) of menuList" :key="index1">
        <template slot="title">
          <i :class="it1.icon"></i>
          <span>{{it1.menuName}}</span>
        </template>
        <!-- 二级菜单 -->
        <el-menu-item
          v-for="(it2,index2) of it1.children"
          :key="index2"
          :index="it2.url"
          @click="handleMenuClick(it2)"
        >
          <i :class="it2.icon"></i>
          <span slot="title">{{ it2.menuName }}</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import avatar from "@/assets/logoko.png";
import { mapState, mapMutations, mapActions } from "vuex";
export default {
  data() {
    return {
      avatar,
    };
  },
  mounted() {
    console.log(this.menuList)
  },
  computed: {
    ...mapState(["siderCollapse","appName"]),
    ...mapState("tabs",["activeRoute","menuList"])
  },
  methods: {
    handleMenuClick(item){
      // this.addTab(item)
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
  color: #666;
  text-align: center;
  cursor: pointer;
  border-bottom: 1px solid #666;
}

// 左边的logo和标题
.logo {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 60px;
  color: #fff;
  background-color: #409EFF;
  border-right: solid 1px #e6e6e6;
  box-shadow: 2px 0 3px -1px #000;

  .el-avatar {
    padding-left: 8px;
    background-color: #409EFF;
  }

  img {
    margin-top: 10px;
  }

  .title {
    font-size: 20px;
    font-weight: bold;
  }
}

// 右边的头像
.avatar {
  height: 60px;
  display: flex;
  align-items: center;

  .el-dropdown {
    height: 50px;
  }

  img {
    margin-top: 10px;
  }
}
</style>
