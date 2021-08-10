<template>
  <div>
    <el-card>
      <!-- 表格区 -->
      <el-table border stripe size="mini" :data="taskList">
        <el-table-column align="center" label="流程实例ID" prop="processInstanceId"></el-table-column>
        <el-table-column align="center" label="任务名称" prop="taskName"></el-table-column>
        <el-table-column align="center" label="任务ID" prop="taskId"></el-table-column>
        <el-table-column align="center" label="负责人" prop="assignee"></el-table-column>
        <el-table-column align="center" label="操作"></el-table-column>
      </el-table>
      <!-- 分页区 -->
      <el-pagination :total="page.total" :current-page="page.current" :page-size="page.size" @size-change="handleSizeChange" @current-change="handleCurrentChange" layout=""></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      taskList: [],
      page: {
        size: 5,
        current: 1,
        total: 0,
      },
    };
  },
  created() { },
  mounted() { },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
  },
};
</script>

<style scoped lang="less">
</style>
