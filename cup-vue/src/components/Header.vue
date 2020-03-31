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
          <el-dropdown-item command="info">个人信息</el-dropdown-item>
          <el-dropdown-item command="preUpdatePwd">修改密码</el-dropdown-item>
          <el-dropdown-item command="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- 修改密码对话框 -->
    <el-dialog
      title="修改密码"
      :visible.sync="pwdDialog"
      width="50%"
      :show-close="false"
      @close="pwdClose"
    >
      <el-form label-width="180px" ref="pwdForm" :rules="pwdRules" :model="pwdForm">
        <el-form-item label="原密码" prop="old_password">
          <el-input type="password" v-model="pwdForm.old_password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="new_password">
          <el-input type="password" v-model="pwdForm.new_password"></el-input>
        </el-form-item>
        <el-form-item label="再输入一次新密码" prop="re_password">
          <el-input type="password" v-model="pwdForm.re_password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="pwdDialog = false">取 消</el-button>
        <el-button type="primary" @click="handleUpdatePwd('pwdForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import avatar from "@/assets/logo.png";
import { mapState, mapActions } from "vuex";

export default {
  data() {
    var validateRePassword = (rule, value, callback) => {
      if (value !== this.pwdForm.new_password) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    var checkPassword = async (rule, value, callback) => {
      let res = await this.pwdCheck({ password: value });
      if (res.code !== 0) {
        callback(new Error("原密码不正确"));
      } else {
        callback();
      }
    };
    return {
      avatarUrl:
        "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
      avatar,
      pwdDialog: false,
      pwdForm: {
        old_password: "",
        new_password: "",
        re_password: ""
      },
      pwdRules: {
        old_password: [
          { required: true, message: "原密码不能为空", trigger: "blur" },
          { validator: checkPassword, message: "原密码错误", trigger: "blur" }
        ],
        new_password: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, message: "密码至少6位", trigger: "blur" }
        ],
        re_password: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { validator: validateRePassword, trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    ...mapState({
      appName: state => state.appName,
      username: state => state.user.username
    })
  },
  methods: {
    ...mapActions(["updatePwd", "userInfo", "pwdCheck"]),
    handleCommand(command) {
      this[command]();
    },
    //弹出修改按钮dialog
    preUpdatePwd() {
      this.pwdDialog = true;
    },
    //提交密码修改
    handleUpdatePwd(formName) {
      this.$refs[formName].validate(async valid => {
        if (!valid) {
          return false;
        } else {
          //获取当前登录用户信息
          let {
            data: { id }
          } = await this.userInfo();

          let password = this.pwdForm.new_password;
          //更新密码
          let res = await this.updatePwd({ id, password });
          this.$message.success("密码修改成功");
          this.pwdDialog = false;
        }
      });
    },
    //关闭修改密码dialog后清空提示和数据
    pwdClose() {
      this.$refs["pwdForm"].resetFields();
      this.pwdForm = {};
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