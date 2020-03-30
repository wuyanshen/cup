<template>
  <div>
    <!-- 面包屑区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片区 -->
    <el-card>
      <!-- 查询区 -->
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input placeholder="请输入要查询的内容">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" icon="el-icon-edit" @click="userAddDialog=true">新增</el-button>
        </el-col>
      </el-row>

      <!-- 表格区 -->
      <el-table :data="tableData" border stripe class="user_table">
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="username" label="姓名" width="180"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="status" label="是否启用">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status" type="success">启用</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            <el-button type="danger" size="mini" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 新增用户对话框 -->
    <el-dialog title="新增用户" :visible.sync="userAddDialog" width="50%">
      <el-form label-width="80px" :model="userAddForm">
        <el-form-item label="用户名">
          <el-input v-model="userAddForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="userAddForm.password"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="userAddForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userAddForm.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userAddDialog = false">取 消</el-button>
        <el-button type="primary" @click="userAddDialog = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      tableData: [],
      userAddDialog: false,
      userAddForm: {},
      pageSize: 10,
      total: 0,
      currentPage: 1,
      queryInfo: {
        size: 0,
        current: 1
      }
    };
  },
  async mounted() {
    let res = await this.userPage();
    this.tableData = res.data.records;
    this.total = res.data.total;
    this.pageSize = res.data.size;
    this.currentPage = res.data.current;
    console.log(res);
  },
  methods: {
    ...mapActions(["userPage"]),
    handleSizeChange(size) {
      this.queryInfo.size = size;
    },
    handleCurrentChange() {
      //
    }
  }
};
</script>
<style lang="less" scoped>
.user_table {
  width: 100%;
  margin-top: 10px;
}

.el-pagination {
  margin-top: 10px;
}
</style>