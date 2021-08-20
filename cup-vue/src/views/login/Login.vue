<template>
  <div class="login-container">
    <div class="login-div">
      <div class="login-title">
        <h3>{{ appName }}</h3>
      </div>
      <!--      <div class="tenant">-->
      <!--          <el-select class="tenant-list" v-model="tenantId" placeholder="请选择租户" @change="getTenantId">-->
      <!--              <el-option-->
      <!--              v-for="tenant in tenants"-->
      <!--              :key="tenant.value"-->
      <!--              :label="tenant.label"-->
      <!--              :value="tenant.value"-->
      <!--              ></el-option>-->
      <!--          </el-select>-->
      <!--      </div>-->
      <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="loginForm">
        <el-form-item label prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="请输入用户名" v-model="loginForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label prop="password">
          <el-input show-password prefix-icon="el-icon-key" placeholder="请输入密码" type="password" v-model="loginForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label prop="captcha">
          <el-input
            class="imgCodeInput"
            prefix-icon="el-icon-lock"
            placeholder="请输入验证码"
            type="text"
            v-model="loginForm.captcha"
            @keyup.enter.native="submitForm('loginForm')"
            autocomplete="off"
          ></el-input>
          <el-image class="imgCode" :src="imgUrl" @click="getCaptchaImage"></el-image>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="rememberMe" label="记住我"></el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginForm')" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--  底部  -->
    <div class="login-footer">
      <div class="txt">
        <span>Copyright © 2018-2021 cup.lvcoding.com All Rights Reserved.</span>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from 'vuex'
import { removeTenantId, decode, encode } from '@/lib/util.js'
import Cookies from 'js-cookie'

export default {
  data() {
    var validateUsername = (rule, value, callback) => {
      if (value === '' || value === undefined || value === null) {
        callback(new Error('请输用户名'))
      } else {
        if (this.loginForm.checkPass !== '') {
          this.$refs.loginForm.validateField('password')
        }
        callback()
      }
    }
    var validatePassword = (rule, value, callback) => {
      if (value === '' || value === undefined || value === null) {
        callback(new Error('请输入密码'))
      } else if (value.length < 6) {
        callback(new Error('密码至少为6位'))
      } else {
        callback()
      }
    }
    return {
      tenantId: '',
      tenants: [],
      imgUrl: '',
      loginForm: {
        username: '',
        password: '',
        captcha: '',
        uuid: ''
      },
      rememberMe: false,
      rules: {
        username: [{ validator: validateUsername, trigger: 'blur' }],
        password: [{ validator: validatePassword, trigger: 'blur' }],
        captcha: [{ required: true, message: '请填写验证码' }]
      }
    }
  },
  mounted() {
    // 初始化登录
    this.initLoginForm()

    // 获取租户列表
    this.getTenantList()
    // 获取验证码
    this.getCaptchaImage()
  },
  computed: {
    ...mapState(['appName']),
    ...mapState('user', ['username', 'password'])
  },
  methods: {
    ...mapActions('user', ['login', 'userInfoAction']),
    ...mapActions('tenant', ['tenantList']),
    ...mapMutations('user', ['ADD_USERINFO']),
    // 初始化登录
    initLoginForm() {
      const username = Cookies.get('username')
      const password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm.username = username == undefined ? this.loginForm.username : username
      this.loginForm.password = password == undefined ? this.loginForm.password : decode(password)
      this.rememberMe = undefined ? false : Boolean(rememberMe)
    },
    // 记住我
    handleRememberMe() {
      if (this.rememberMe) {
        Cookies.set('username', this.loginForm.username, { expires: 7 })
        Cookies.set('password', encode(this.loginForm.password), { expires: 7 })
        Cookies.set('rememberMe', this.rememberMe, { expires: 7 })
      } else {
        Cookies.remove('username')
        Cookies.remove('password')
        Cookies.remove('rememberMe')
      }
    },
    // 登录
    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          // 刷新验证码
          this.getCaptchaImage()
          // 记住我
          this.handleRememberMe()

          if (this.tenantId) {
            let tenantInfo = {
              dataType: 'number',
              content: parseInt(this.tenantId),
              datetime: new Date().getTime()
            }
            localStorage.setItem('tenantId', JSON.stringify(tenantInfo))
          } else {
          }

          let params = {
            username: this.loginForm.username,
            password: encode(this.loginForm.password),
            captcha: this.loginForm.captcha,
            uuid: this.loginForm.uuid
          }

          let res = await this.login(params)
          if (res.code === 0) {
            // 查询用户信息
            let res = await this.userInfoAction()
            // 将用户信息放到vuex中
            this.ADD_USERINFO(res.data)
            this.$message.success('登录成功~')
            this.$router.push('/').catch(err => {
              err
            })
          }
        } else {
          return false
        }
      })
    },
    // 获取租户id，并存储到localStorage
    getTenantId() {
      if (this.tenantId) {
        let tenantInfo = {
          dataType: 'number',
          content: parseInt(this.tenantId),
          datetime: new Date().getTime()
        }
        localStorage.setItem('tenantId', JSON.stringify(tenantInfo))
      } else {
        removeTenantId()
      }
    },
    // 获取租户列表
    async getTenantList() {
      let res = await this.tenantList()
      let arr = []
      if (res.code === 0) {
        for (let tenant of res.data) {
          let item = {
            label: tenant.tenantName,
            value: tenant.tenantId
          }
          arr.push(item)
        }
      }
      this.tenants = arr
    },
    // 验证码
    getCaptchaImage() {
      this.$api.captcha.captchaImage().then(res => {
        this.imgUrl = 'data:image/jpg;base64,' + res.data.img
        this.loginForm.uuid = res.data.uuid
      })
    }
  }
}
</script>

<style lang="less" scoped>
el-button {
  width: 100%;
}

.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  background: url('../../assets/images/bg.jpg') no-repeat;
  background-size: 100% 100%;

  .login-footer {
    color: white;
    font-size: 12px;
    align-self: center;
    margin-top: 20px;
    letter-spacing: 1px;
  }
}

.login-title {
  font-size: 18px;
  color: #777777;
  padding: 20px;
}

.login-div {
  display: flex;
  align-items: center;
  align-self: center;
  justify-content: center;
  flex-direction: column;
  width: 22rem;
  height: 25rem;
  background-color: white;
  box-shadow: 0 0 20px rgb(34, 16, 16);
  border-radius: 8px;
}
.loginForm {
  width: 90%;
}

.tenant {
  display: flex;
  justify-content: center;
  margin: 20px auto;
  .tenant-list {
    width: 150px;
  }
}

.imgCodeInput {
  width: 246px;
}

.imgCode {
  cursor: pointer;
  position: absolute;
  width: 100px;
  height: 40px;
  right: 0;
}
</style>
