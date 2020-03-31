<template>
  <div>
    <h1>欢迎来到{{this.$store.state.appName}}</h1>
    <h1>{{appName}}</h1>
    <h1>{{appNameWithVersion}}</h1>
    <h1>{{usernameWithVersion}}</h1>
    <br />
    <el-button type="warning" @click="uInfo">获取用户信息</el-button>
    <el-button type="warning" @click="handleAppName">修改系统名称</el-button>
    <el-button type="warning" @click="handleUsername">修改用户名</el-button>
  </div>
</template>

<script>
import { mapMutations, mapActions, mapGetters, mapState } from "vuex";

export default {
  data() {
    return {
      //
    };
  },
  components: {
    ...mapState(["username"])
  },
  watch: {
    //
  },
  computed: {
    ...mapGetters(["appNameWithVersion", "usernameWithVersion"]),
    appName: {
      set(value) {
        this.UPDATE_APPNAME(value);
      },
      get() {
        return this.$store.state.appName;
      }
    }
  },
  methods: {
    ...mapMutations(["UPDATE_APPNAME", "SET_USERNAME"]),
    ...mapActions(["editAppName", "updateUsername"]),
    async uInfo() {
      const { data: res } = await this.$api.user.userInfo();
    },
    handleAppName() {
      this.editAppName();
    },
    handleUsername() {
      this.updateUsername();
    }
  }
};
</script>

<style scoped lang="less">
</style>
