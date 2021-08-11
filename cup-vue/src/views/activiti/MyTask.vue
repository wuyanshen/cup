<template>
  <div>
    <el-card>
      <!-- 表格区 -->
      <el-table border stripe size="mini" :data="taskList">
        <el-table-column align="center" label="流程实例ID" prop="processInstanceId"></el-table-column>
        <el-table-column align="center" label="任务名称" prop="name"></el-table-column>
        <el-table-column align="center" label="任务ID" prop="id"></el-table-column>
        <el-table-column align="center" label="负责人" prop="assignee"></el-table-column>
        <el-table-column align="center" label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="primary" @click="handleComplete(scope.row)">办理</el-button>
          </template>
        </el-table-column>
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
  mounted() {
    this.getTask();
  },
  methods: {
    // 完成任务
    handleComplete(item) {
      this.$api.activiti.completeTask({ id: item.id }).then(res => {
        if (res.code === 0) {
          this.$message.success('完成任务')
        } else {
          this.$message.error(res.msg)
        }
        this.getTask();
      })
    },
    // 查询待办任务
    getTask() {
      // 当前用户名
      let username = this.$store.state.user.userInfo.username;
      this.$api.activiti.getTask({ username }).then(res => {
        this.taskList = res.data
      })
    },
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
