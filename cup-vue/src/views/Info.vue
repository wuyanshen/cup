<template>
    <div class="info">
        <el-card class="box-card">
            <div slot="header" class="header">
                <span>个人信息</span><el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
            </div>
            <el-row  style="margin: 5px">
                <el-col :span="12">用户名：</el-col>
                <el-col :span="12">{{ user.username }}</el-col>
            </el-row>
            <el-row  style="margin: 5px">
                <el-col :span="12">电  话： </el-col>
                <el-col :span="12">{{ user.phone }}</el-col>
            </el-row>
            <el-row  style="margin: 5px">
                <el-col :span="12">email：</el-col>
                <el-col :span="12">{{ user.email }}</el-col>
            </el-row>
        </el-card>
    </div>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "Info",
        data(){
          return {
              user:{}
          }
        },
        async mounted() {
            let {data:res} = await this.userInfo();
            this.user = res
        },
        methods: {
            ...mapActions('user',['userInfo']),
        }
    }
</script>

<style lang="less" scoped>
    .box-card{
        width: 300px;
    }
    .header {
        display: flex;
        align-items: center;
        .el-avatar {
            margin-left: 10px;
        }
    }
</style>
