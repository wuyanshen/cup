<template>
  <div class="header-container">
    <div class="logo">
      <el-avatar shape="square" :size="50" :src="avatar"></el-avatar>
      <div class="title">{{appName}}</div>
    </div>
    <div class="avatar">
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          <el-avatar v-if="avatarUrl" :size="50" :src="avatarUrl"></el-avatar>
          <el-avatar v-if="!avatarUrl" :size="50" icon="el-icon-user-solid"></el-avatar>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="info">{{username}}个人信息</el-dropdown-item>
          <el-dropdown-item command="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
import avatar from "@/assets/logo.png";
// import { createNamespacedHelpers } from "vuex";
// const { mapState } = createNamespacedHelpers("user");
import { mapState } from "vuex";

export default {
  data() {
    return {
      avatarUrl:
        "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
      avatar
    };
  },
  computed: {
    ...mapState({
      appName: state => state.appName,
      username: state => state.user.username
    })
  },
  methods: {
    handleCommand(command) {
      this[command]();
    },
    info() {
      this.$message.success("个人信息");
    },
    logout() {
      this.$confirm("此操作将退出系统, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          window.sessionStorage.clear();
          this.$router.push("/login");
          this.$message({
            type: "success",
            message: "退出成功!"
          });
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="less" scoped>
.header-container {
  display: flex;
  justify-content: space-between;

  // 左边的logo和标题
  .logo {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 60px;

    .el-avatar {
      background-color: #2b4b6b;
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
}
</style>