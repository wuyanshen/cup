<template>
  <div>
    <el-card>
      <!-- 表格区 -->
      <el-table :data="taskList">
        <el-table-column label=""></el-table-column>
      </el-table>
      <!-- 分页区 -->
      <el-pagination
        :total="page.total"
        :current-page="page.current"
        :page-size="page.size"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout=""
      ></el-pagination>
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
  created() {},
  mounted() {},
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
