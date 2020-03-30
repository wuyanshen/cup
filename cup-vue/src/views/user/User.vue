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
          <el-input clearable v-model="queryInfo.username" placeholder="请输入要查询的用户名">
            <el-button slot="append" icon="el-icon-search" @click="handleSearch(username)"></el-button>
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
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)"
            >修改</el-button>
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="handleDeleteUser(scope.row.id)"
            >删除</el-button>
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
        <el-button type="primary" @click="handleUserAdd">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户对话框 -->
    <el-dialog title="修改用户" :visible.sync="userEditDialog" width="50%">
      <el-form label-width="80px" :model="userEditForm">
        <el-form-item label="id">
          <el-input v-model="userEditForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="userEditForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="userEditForm.password"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="userEditForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userEditForm.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userEditDialog = false">取 消</el-button>
        <el-button type="primary" @click="handleUserUpdate">确 定</el-button>
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
      userEditDialog: false,
      userAddForm: {
        username: "",
        password: "",
        phone: "",
        email: ""
      },
      userEditForm: {
        id: 0,
        username: "",
        password: "",
        phone: "",
        email: ""
      },
      pageSize: 10,
      total: 0,
      currentPage: 1,
      queryInfo: {
        size: 10,
        current: 1,
        username: null
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
    ...mapActions(["userPage", "addUser", "updateUser", "deleteUser"]),
    //修改每页显示条数
    async handleSizeChange(size) {
      this.queryInfo.size = size;
      let res = await this.userPage(this.queryInfo);
      this.tableData = res.data.records;
    },
    async handleCurrentChange(current) {
      this.queryInfo.current = current;
      let res = await this.userPage(this.queryInfo);
      this.tableData = res.data.records;
    },
    handleEdit(row) {
      this.userEditForm.id = row.id;
      this.userEditForm.username = row.username;
      this.userEditForm.password = row.password;
      this.userEditForm.phone = row.phone;
      this.userEditForm.email = row.email;
      this.userEditDialog = true;
    },
    //新增用户
    async handleUserAdd() {
      let res = await this.addUser(this.userAddForm);
      if (res.code == 0) {
        this.$message.success("添加用户成功");
      }
      //刷新
      this.flushUserPage();
      //关闭dialog
      this.userAddDialog = false;
    },
    //更新用户
    async handleUserUpdate() {
      let res = await this.updateUser(this.userEditForm);
      if (res.code == 0) {
        this.$message.success("修改用户成功");
      }
      //刷新
      this.flushUserPage();
      //关闭dialog
      this.userEditDialog = false;
    },
    //删除用户
    handleDeleteUser(id) {
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          console.log(`id是：${id}`);
          let res = await this.deleteUser(id);
          if (res.code == 0) {
            this.$message.success("删除用户成功");
          }
          //刷新
          this.flushUserPage();
        })
        .catch(() => {});
    },
    async handleSearch(username) {
      let res = await this.userPage(this.queryInfo);
      this.tableData = res.data.records;
    },
    //刷新用户列表
    async flushUserPage() {
      let res = await this.userPage();
      this.tableData = res.data.records;
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