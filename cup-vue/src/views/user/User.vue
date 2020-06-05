<template>
  <div>
    <!-- 面包屑区 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb> -->

    <!-- 卡片区 -->
    <el-card>
      <!-- 查询区 -->
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input size="small" clearable v-model="queryInfo.username" placeholder="请输入要查询的用户名">
            <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" icon="el-icon-plus" size="small" @click="userAddDialog=true">新增</el-button>
        </el-col>
      </el-row>

      <!-- 表格区 -->
      <el-table size="small" :data="tableData" border stripe class="user_table">
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="username" label="姓名" width="180"></el-table-column>
        <el-table-column align="center" prop="phone" label="电话"></el-table-column>
        <el-table-column align="center" prop="email" label="邮箱"></el-table-column>
        <el-table-column align="center" prop="status" label="是否启用">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status" type="success">启用</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
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
    <el-dialog
      title="新增用户"
      :visible.sync="userAddDialog"
      width="50%"
      :show-close="false"
      @close="userAddClose"
    >
      <el-form label-width="80px" :rules="userAddRules" :model="userAddForm" ref="userAddForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userAddForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="userAddForm.password"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="userAddForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userAddForm.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="userAddDialog = false">取 消</el-button>
        <el-button size="small" type="primary" @click="handleUserAdd">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户对话框 -->
    <el-dialog :show-close="false" title="修改用户" :visible.sync="userEditDialog" width="50%">
      <el-form size="small" label-width="80px" :model="userEditForm">
        <el-form-item label="id">
          <el-input v-model="userEditForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="userEditForm.username"></el-input>
        </el-form-item>
        <el-form-item label="是否启用">
          <el-switch
            style="display: block"
            v-model="userEditForm.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="启用"
            inactive-text="禁用"
          ></el-switch>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="userEditForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userEditForm.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="userEditDialog = false">取 消</el-button>
        <el-button size="small" type="primary" @click="handleUserUpdate">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    var usernameCheck = async (rule, value, callback) => {
      let res = await this.usernameCheck({ username: value });
      if (res.code !== 0) {
        callback(new Error("用户名已存在"));
      } else {
        callback();
      }
    };
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
        email: "",
        status: true
      },
      pageSize: 10,
      total: 0,
      currentPage: 1,
      queryInfo: {
        size: 10,
        current: 1,
        username: ""
      },
      userAddRules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
          { validator: usernameCheck, message: "用户名已存在", trigger: "blur" }
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 6, message: "密码至少为6位", trigger: "blur" }
        ],
        phone: [{ required: true, message: "手机号不能为空", trigger: "blur" }],
        email: [{ required: true, message: "邮箱不能为空", trigger: "blur" }]
      }
    };
  },
  async mounted() {
    let res = await this.userPage();
    if (res.code == 0) {
      this.tableData = res.data.records;
      this.total = res.data.total;
      this.pageSize = res.data.size;
      this.currentPage = res.data.current;
    }
  },
  methods: {
    ...mapActions("user",[
      "userPage",
      "addUser",
      "updateUser",
      "deleteUser",
      "usernameCheck"
    ]),
    //修改每页显示条数
    async handleSizeChange(size) {
      this.queryInfo.size = size;
      let res = await this.userPage(this.queryInfo);
      this.tableData = res.data.records;
    },
	//修改当前第几页
    async handleCurrentChange(current) {
      this.queryInfo.current = current;
      let res = await this.userPage(this.queryInfo);
      this.tableData = res.data.records;
    },
    handleEdit(row) {
      this.userEditForm.id = row.id;
      this.userEditForm.username = row.username;
      this.userEditForm.phone = row.phone;
      this.userEditForm.email = row.email;
      this.userEditForm.status = row.status;
      this.userEditDialog = true;
    },
    //用户新增dialog关闭后清空值和校验
    userAddClose() {
      this.$refs["userAddForm"].resetFields();
      this.userAddForm = {};
    },
    //新增用户
    handleUserAdd(formName) {
      this.$refs[formName].validate(async valid => {
        if (!valid) {
          return false;
        } else {
          let res = await this.addUser(this.userAddForm);
          if (res.code == 0) {
            this.$message.success("添加用户成功");
          }
          //刷新
          this.flushUserPage();
          //关闭dialog
          this.userAddDialog = false;
        }
      });
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
    async handleSearch() {
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

.el-switch {
  margin-top: 10px;
}
</style>
