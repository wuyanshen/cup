<template>
  <div>
    <el-card>
      <!-- 表格区 -->
      <el-table size="mini" stripe :data="page.records">
        <el-table-column label="序号" type="index" align="center" width="50px"></el-table-column>
        <el-table-column label="任务id" align="center" prop="id"></el-table-column>
        <el-table-column label="任务名称" align="center" prop="name"></el-table-column>
        <el-table-column label="流程实例id" align="center" prop="processInstanceId"></el-table-column>
        <el-table-column label="流程定义id" align="center" prop="processDefinitionId"></el-table-column>
        <el-table-column label="任务执行id" align="center" prop="executionId"></el-table-column>
        <el-table-column label="处理人" align="center" prop="assignee"></el-table-column>
        <el-table-column label="任务创建时间" align="center" :formatter="formatDate" prop="createTime"></el-table-column>
        <el-table-column label="任务完成时间" align="center" :formatter="formatDate" prop="endTime"></el-table-column>
      </el-table>
      <!-- 分页区 -->
      <el-pagination
        style="margin-top: 10px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page.current"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="page.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
import moment from 'moment';
export default {
  data() {
    return {
      page: {
        records: [],
        size: 5,
        current: 1,
        total: 0
      },
    };
  },
  created() {

  },
  mounted() {
    this.handleQueryHistoryTaskList();
  },
  methods: {
    // 查询历史任务列表
    handleQueryHistoryTaskList() {
      let username = this.$store.state.user.userInfo.username;
      let params = Object.assign({ username: username }, { size: this.page.size, current: this.page.current })

      this.$api.activiti.getHistoryTaskList(params).then(res => {
        this.page.size = res.data.size;
        this.page.current = res.data.current;
        this.page.total = res.data.total;
        this.page.records = res.data.records;
      })
    },
    //修改每页显示条数
    handleSizeChange(size) {
      this.page.size = size;
      this.handleQueryHistoryTaskList();
    },
    //修改当前第几页
    handleCurrentChange(current) {
      this.page.current = current;
      this.handleQueryHistoryTaskList();
    },
  }
};
</script>

<style scoped lang="less">
</style>
