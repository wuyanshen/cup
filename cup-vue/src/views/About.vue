<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>关于我们</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>关于我们</el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      msg: "hello world"
    };
  }
};
</script>
