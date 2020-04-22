<template>
  <el-container>
    <el-aside :width="siderCollapse?'64px':'200px'">
      <!-- 侧边栏 -->
      <Sider />
    </el-aside>
    <el-container>
      <el-header>
        <!-- 头部 -->
        <Header />
      </el-header>
      <el-main>
        <!-- 主页 -->
        <Main />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import Header from "@/components/Header.vue";
import Main from "@/components/Main.vue";
import Sider from "@/components/Sider.vue";
import { mapState } from "vuex";

export default {
  data() {
    return {};
  },
  components: {
    Header,
    Main,
    Sider
  },
  computed: {
    ...mapState(["siderCollapse"])
  },
  methods: {
  }
};
</script>

<style lang="less" scoped>
.el-header {
  background-color: #409EFF;
  color: #fff;
  line-height: 60px;
}
.el-aside {
  color: #666;
  line-height: 200px;
  background-color: #fff;
}

.el-main {
  background-color: #e9eef3;
  color: #666;
  padding:0;
}

.el-container {
  height: 100%;
}
</style>
