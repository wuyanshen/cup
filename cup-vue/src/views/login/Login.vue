<template>
  <div class="login-container">
    <div class="login-div">
      <div class="login-title">
        <h3>{{appName}}</h3>
      </div>
      <div class="tenant">
          <el-select class="tenant-list" v-model="tenantId" placeholder="请选择租户" @change="getTenantId">
              <el-option
              v-for="tenant in tenants"
              :key="tenant.value"
              :label="tenant.label"
              :value="tenant.value"
              ></el-option>
          </el-select>
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
import {removeTenantId} from '@/lib/util.js'

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
      tenantId:'',
      tenants: [],
      loginForm: {
        username: "",
        password: "",
       
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
    
    // 获取租户列表
    this.getTenantList();
  },
  computed: {
    ...mapState(["appName"]),
    ...mapState("user",["username", "password"])
  },
  methods: {
    ...mapActions("user",["login"]),
    ...mapActions("tenant",["tenantList"]),
    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
            if(this.tenantId){
                let tenantInfo = {
                    dataType: 'number',
                    content: parseInt(this.tenantId),
                    datetime: new Date().getTime(),
                };
                localStorage.setItem('tenantId', JSON.stringify(tenantInfo))
            }else{
                
            }
            
            
          let res = await this.login(this.loginForm);
          if (res.code === 0) {
            this.$message.success('登录成功~')
            this.$router.push("/").catch(err => {err})
          }
        } else {
          return false;
        }
      });
    },
    // 获取租户id，并存储到localStorage
    getTenantId(){
        if(this.tenantId){
            let tenantInfo = {
                dataType: 'number',
                content: parseInt(this.tenantId),
                datetime: new Date().getTime(),
            };
            localStorage.setItem('tenantId', JSON.stringify(tenantInfo))
        }else{
            removeTenantId();
        }
    },
    // 获取租户列表
    async getTenantList(){
        let res = await this.tenantList();
        let arr = []
        if(res.code === 0){
            for(let tenant of res.data){
                let item = {
                    label: tenant.tenantName,
                    value: tenant.tenantId
                }
                arr.push(item)
            }
        }
        this.tenants = arr;
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

.tenant {
    display: flex;
    justify-content: center;
    margin: 20px auto;
    .tenant-list {
        width: 150px;
    }
}
</style>
