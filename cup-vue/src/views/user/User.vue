<template>
  <div>
    <!-- 卡片区 -->
    <el-card>
      <el-row :gutter="20">
        <!-- 左侧树形菜单区 -->
        <el-col :span="4">
          <el-input size="mini" placeholder="输入关键字进行过滤" v-model="filterText"> </el-input>

          <el-tree class="filter-tree" :data="orgTreeData" :props="defaultProps" default-expand-all :filter-node-method="filterNode" @node-click="orgTreeClick" ref="tree"> </el-tree>
        </el-col>
        <el-col :span="20">
          <!-- 查询区 -->
          <el-row :gutter="20">
            <el-col :span="2">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 10px">
            <el-col :span="6">
              <el-input size="mini" clearable v-model="queryInfo.username" placeholder="请输入要查询的用户名" @change="handleSearch"> </el-input>
            </el-col>
            <el-col :span="6">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearch">查询</el-button>
              <el-button type="info" icon="el-icon-refresh" size="mini" @click="queryReset">重置</el-button>
            </el-col>
          </el-row>

          <!-- 表格区 -->
          <el-table size="mini" :data="this.page.tableData" stripe class="user_table">
            <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
            <el-table-column align="center" prop="username" label="用户名" width="180"></el-table-column>
            <el-table-column align="center" prop="phone" label="电话"></el-table-column>
            <el-table-column align="center" prop="email" label="邮箱"></el-table-column>
            <el-table-column align="center" prop="status" label="是否启用">
              <template v-slot="scope">
                <el-tag size="mini" v-if="scope.row.status" type="success">启用</el-tag>
                <el-tag size="mini" v-else type="danger">禁用</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="300">
              <template v-slot="scope">
                <el-button type="text" size="mini" icon="el-icon-key" @click="showReset(scope.row)">重置密码</el-button>
                <el-button type="text" size="mini" icon="el-icon-edit" @click="handleEdit(scope.row)">修改</el-button>
                <el-button type="text" size="mini" icon="el-icon-delete" @click="handleDeleteUser(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页区 -->
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="this.page.currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="this.page.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="this.page.total"
          ></el-pagination>
        </el-col>
      </el-row>
    </el-card>

    <!-- 重置密码对话框 -->
    <el-dialog title="重置密码" :visible.sync="resetDialog" width="30%" :show-close="false" :close-on-click-modal="false" @close="resetDialogClose">
      <el-input v-model="resetForm.password" type="password" show-password placeholder="请输入要重置的密码"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="resetDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleSubmitResetPwd">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新增/修改用户对话框 -->
    <el-dialog :title="title" :visible.sync="userDialog" width="30%" :show-close="false" @close="userDialogClose">
      <el-form size="mini" label-width="100px" :rules="userRules" :model="userForm" ref="userForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" :disabled="userForm.id !== undefined"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="userForm.id === undefined">
          <el-input type="password" v-model="userForm.password"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="userForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
        <el-form-item label="所属机构">
          <el-input placeholder="请选择机构" v-model="userForm.orgName" @focus="orgTreeKey = true"></el-input>
          <div v-if="orgTreeKey">
            <el-tree :data="orgTreeData" :props="defaultProps" default-expand-all @node-click="handleAddTreeSelected"></el-tree>
          </div>
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
          <el-select size="mini" v-model="userForm.roleIds" value-key="id" style="width: 100%" multiple placeholder="请选择角色">
            <el-option v-for="item in roles" :key="item.id" :label="item.roleName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="启用" prop="status">
          <el-radio-group v-model="userForm.status">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="userDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleUserEditSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    const usernameCheck = async (rule, value, callback) => {
      console.log(this.userForm.id)
      if (this.userForm.id === undefined) {
        let res = await this.usernameCheck({ username: value });
        if (res.code !== 0) {
          callback(new Error("用户名已存在"));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      resetForm: {
        newPassword: '',
        id: '',
      },
      resetDialog: false,
      title: '',
      orgs: [],
      roles: [],
      orgTreeData: [],
      userDialog: false,
      userForm: {
        status: true,
      },
      page: {
        tableData: [],
        pageSize: 10,
        total: 0,
        currentPage: 1,
      },
      queryInfo: {
        size: 0,
        current: 0,
        username: '',
        orgId: '',
      },
      orgTreeKey: false,
      defaultProps: {
        children: 'children',
        label: 'orgName'
      },
      elTreeProps: {// el-tree-select配置项（必选）
        value: 'id',
        label: 'orgName',
        children: 'children',
      },
      filterText: '',
      userRules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
          { validator: usernameCheck, message: "用户名已存在", trigger: "blur" }
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 6, message: "密码至少为6位", trigger: "blur" }
        ],
        phone: [{ required: true, message: "手机号不能为空", trigger: "blur" }],
        email: [{ required: true, message: "邮箱不能为空", trigger: "blur" }],
        roleIds: [{ required: true, message: "角色不能为空", trigger: "blur" }],
        status: [{ required: true, message: "启用不能为空", trigger: "blur" }],
      }
    };
  },
  async mounted() {
    // 查询机构树
    const orgs = await this.orgTree()
    this.orgTreeData = orgs.data

    // 查询用户列表
    this.flush()
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    ...mapActions("user", [
      "userPage",
      "addUser",
      "updateUser",
      "deleteUser",
      "usernameCheck",
      "userRoleIds",
    ]),
    ...mapActions("role", [
      "roleList"
    ]),
    ...mapActions('org', ['orgTree']),
    // 关闭重置密码对话框
    resetDialogClose() {
      this.resetForm = {
        password: '',
        id: ''
      }
    },
    // 查询重置
    queryReset() {
      this.queryInfo = {
        current: 1,
        username: '',
        orgId: '',
      }
      this.handleSearch();
    },
    // 提交重置密码
    handleSubmitResetPwd() {
      this.$api.user.resetPwd(this.resetForm).then(res => {
        this.$message.success('重置密码成功');
        this.resetDialog = false;
      })
    },
    // 显示重置密码对话框
    showReset(row) {
      this.resetDialog = true;
      this.resetForm.id = row.id;
    },
    // 组织机构树过滤
    filterNode(value, data) {
      if (!value) return true;
      return data.orgName.indexOf(value) !== -1;
    },
    // 点击组织机构树的节点
    orgTreeClick(data, node, obj) {
      this.queryPage(data.id, this.queryInfo.username, this.page.pageSize, this.page.currentPage)
    },
    // 新增按钮
    async handleAdd() {
      this.title = '新增用户'
      this.userDialog = true
      // 查询用户角色列表
      const roles = await this.roleList()
      this.roles = roles.data
    },
    // 修改每页显示条数
    handleSizeChange(size) {
      this.queryPage(this.queryInfo.orgId, this.queryInfo.username, size, this.page.currentPage)
    },
    // 修改当前第几页
    handleCurrentChange(current) {
      this.queryPage(this.queryInfo.orgId, this.queryInfo.username, this.page.pageSize, current)
    },

    // 修改按钮
    async handleEdit(row) {
      this.title = '修改用户'

      // 查询用户角色列表
      const roles = await this.roleList()
      this.roles = roles.data

      // 查询用户角色id集合
      const roleIds = await this.userRoleIds(row.id);
      this.userForm.roleIds = roleIds.data

      this.getOrg(this.orgTreeData)
      // 回显组织机构
      for (let org of this.orgs) {
        if (org.orgId === row.orgId) {
          this.userForm.orgName = org.orgName
        }
      }
      this.userForm = Object.assign({}, this.userForm, row)

      this.userDialog = true;
    },
    getOrg(datas) { //递归遍历机构树，获取组织机构list
      for (var i in datas) {
        this.orgs.push({ orgId: datas[i].id, orgName: datas[i].orgName })
        if (datas[i].children) {
          this.getOrg(datas[i].children);
        }
      }
    },

    // 用户新增dialog关闭后清空值和校验
    userDialogClose() {
      this.$refs["userForm"].resetFields();
      this.userForm = {
        status: true,
      }
      this.orgTreeKey = false
    },

    // 提交表单
    async handleUserEditSubmit() {
      this.$refs.userForm.validate(async valid => {
        if (!valid) {
          return false;
        } else {
          if (this.userForm.id === undefined) {
            let res = await this.addUser(this.userForm);
            if (res.code === 0) {
              this.$message.success("添加用户成功");
            }
            //刷新
            this.flush();
            //关闭dialog
            this.userDialog = false;
          } else {
            let res = await this.updateUser(this.userForm);
            if (res.code == 0) {
              this.$message.success("修改用户成功");
            }
            // 刷新
            this.flush();
            // 关闭dialog
            this.userDialog = false;
          }
        }
      })
    },

    // 删除用户
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
          this.flush();
        })
        .catch(() => { });
    },
    handleAddTreeSelected(data) {
      this.userForm.orgId = data.id
      this.userForm.orgName = data.orgName
      this.orgTreeKey = false
    },

    //条件查询
    handleSearch() {
      this.queryPage('', this.queryInfo.username, this.page.pageSize, '')
    },

    //---------- 工具方法 -----------
    //封装分页参数
    copyPageValue(res) {
      this.page.tableData = res.data.records;
      this.page.total = res.data.total;
      this.page.pageSize = res.data.size;
      this.page.currentPage = res.data.current;
    },
    //封装查询参数
    copyQueryValue(orgId, username, size, current) {
      return {
        username: username ? username : null,
        orgId: orgId ? orgId : null,
        size: size ? size : null,
        current: current ? current : null,
      }
    },
    //封装分页请求通用方法
    async queryPage(orgId, username, size, current) {
      let res = await this.userPage(this.copyQueryValue(orgId, username, size, current));
      this.copyPageValue(res)
    },
    //刷新界面
    flush() {
      this.queryPage('', '', this.page.pageSize, '')
    },
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
  margin-top: 4px;
}
</style>
