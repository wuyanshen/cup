<template>
    <div class="info">
        <el-card class="box-card">
            <div slot="header">
                <span>个人信息</span>
            </div>
            <el-row  style="margin: 5px">
                <el-col :span="12">用户名：</el-col>
                <el-col :span="12"></el-col>
            </el-row>
            <el-row  style="margin: 5px">
                <el-col :span="12">电  话： </el-col>
                <el-col :span="12"></el-col>
            </el-row>
            <el-row  style="margin: 5px">
                <el-col :span="12">email：</el-col>
                <el-col :span="12"></el-col>
            </el-row>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "Info"
    }
</script>

<style lang="less" scoped>
    .box-card{
        width: 300px;
    }
</style>
