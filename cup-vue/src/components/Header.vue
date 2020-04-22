<template>
  <div class="header-container">
    <!-- 缩放按钮 -->
    <div class="collapse_btn_div" @click="handleCollapse"><i :class="handleClass()"></i></div>
    <!-- 头像 -->
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
import {mapState, mapActions, mapMutations} from "vuex";

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
    ...mapState(["siderCollapse"]),
    ...mapState({
      username: state => state.user.username
    })
  },
  methods: {
    ...mapActions(["updatePwd", "userInfo", "pwdCheck"]),
    ...mapActions("tabs",["addTab"]),
    ...mapMutations(["UPDATE_COLLAPSE"]),
    ...mapMutations("tabs",["initMainTabs","setActiveRoute"]),
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
      this.addTab('/info')
      this.setActiveRoute('/info')
      this.$router.push({name:"Info"});
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
          this.initMainTabs()
          this.$message({
            type: "success",
            message: "退出成功!"
          });
        })
        .catch(() => {});
    },
    handleCollapse() {
      this.UPDATE_COLLAPSE(!this.siderCollapse);
    },
    handleClass(){
      return {
        "el-icon-s-fold":!this.siderCollapse,
        "el-icon-s-unfold":this.siderCollapse
      }
    },
  }
};
</script>
<style lang="less" scoped>
.header-container {
  display: flex;
  justify-content: space-between;

  //左边缩放按钮
  .collapse_btn_div {
    font-size: 25px;
    i:hover{
      cursor:pointer;
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
