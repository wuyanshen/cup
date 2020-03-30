<template>
  <el-container>
    <el-header>
      <!-- 头部 -->
      <Header />
    </el-header>
    <el-container>
      <el-aside :width="siderCollapse?'64px':'200px'">
        <!-- 侧边栏 -->
        <Sider />
      </el-aside>
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
  methods: {}
};
</script>

<style lang="less" scoped>
.el-header {
  background-color: #2b4b6b;
  color: #fff;
  text-align: center;
  line-height: 60px;
}
.el-aside {
  color: #333;
  // text-align: center;
  line-height: 200px;
  background-color: #325272;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
}

.el-container {
  height: 100%;
}
</style>