<template>
  <div class="login-container">
    <div class="login-div">
      <div class="login-title">
        <h3>{{appName}}</h3>
      </div>
      <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="loginForm">
        <el-form-item label prop="username">
          <el-input
            prefix-icon="el-icon-user"
            placeholder="请输入用户名"
            v-model="loginForm.username"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label prop="password">
          <el-input
            prefix-icon="el-icon-key"
            placeholder="请输入密码"
            type="password"
            v-model="loginForm.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginForm')" style="width:100%">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { mapState, mapActions } from "vuex";

export default {
  data() {
    var validateUsername = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输用户名"));
      } else {
        if (this.loginForm.checkPass !== "") {
          this.$refs.loginForm.validateField("password");
        }
        callback();
      }
    };
    var validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value.length < 6) {
        callback(new Error("密码至少为6位"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: "",
        password: ""
      },
      rules: {
        username: [{ validator: validateUsername, trigger: "blur" }],
        password: [{ validator: validatePassword, trigger: "blur" }]
      }
    };
  },
  mounted() {
    this.loginForm = {
      username: this.username,
      password: this.password
    };
  },
  computed: {
    ...mapState(["appName"]),
    ...mapState("user",["username", "password"])
  },
  methods: {
    ...mapActions("user",["login"]),
    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          let res = await this.login(this.loginForm);
          if (res.code === 0) {
            this.$message.success('登录成功~')
            this.$router.push("/");
          }
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
el-button {
  width: 100%;
}

.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  /*background-color: #2b4b6b;*/
  background: url("../../assets/images/bg.jpg") no-repeat;
  background-size:100% 100%;
}

.login-title {
  margin-bottom: 20px;
  font-size: 30px;
  color: #2b4b6b;
}

.login-div {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 400px;
  height: 350px;
  background-color: white;
  box-shadow: 0 0 20px rgb(34, 16, 16);
  border-radius: 6px;
}
.loginForm {
  width: 90%;
}
</style>
