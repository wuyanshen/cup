<template>
  <div>
    <!-- 面包屑区 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb> -->

    <!-- 卡片区 -->
    <el-card>
        <el-row :gutter="20">
            <!-- 左侧树形菜单区 -->
            <el-col :span="4">
                <el-input
                  size="mini"
                  placeholder="输入关键字进行过滤"
                  v-model="filterText">
                </el-input>

                <el-tree
                  class="filter-tree"
                  :data="orgTreeData"
                  :props="defaultProps"
                  default-expand-all
                  :filter-node-method="filterNode"
                  @node-click="orgTreeClick"
                  ref="tree">
                </el-tree>
            </el-col>
            <el-col :span="20">
                <!-- 查询区 -->
                <el-row :gutter="20">
                		  <el-col :span="2">
                		    <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
                		  </el-col>
                </el-row>
                <el-row :gutter="20" style="margin-top:10px;">
                  <el-col :span="6">
                    <el-input size="mini" clearable v-model="queryInfo.username" placeholder="请输入要查询的用户名" @change="handleSearch">
                    </el-input>
                  </el-col>
                  <el-col :span="2">
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearch">查询</el-button>
                  </el-col>
                </el-row>

                <!-- 表格区 -->
                <el-table size="mini" :data="this.page.tableData" border stripe class="user_table" :header-cell-style="{background:'#F2F6FC'}">
                  <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                  <el-table-column align="center" prop="username" label="姓名" width="180"></el-table-column>
                  <el-table-column align="center" prop="phone" label="电话"></el-table-column>
                  <el-table-column align="center" prop="email" label="邮箱"></el-table-column>
                  <el-table-column align="center" prop="status" label="是否启用">
                    <template v-slot="scope">
                      <el-tag size="mini" v-if="scope.row.status" type="success">启用</el-tag>
                      <el-tag size="mini" v-else type="danger">禁用</el-tag>
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
                  :current-page="this.page.currentPage"
                  :page-sizes="[5, 10, 15, 20]"
                  :page-size="this.page.pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="this.page.total"
                ></el-pagination>
            </el-col>
        </el-row>
    </el-card>

    <!-- 新增用户对话框 -->
    <el-dialog
      title="新增用户"
      :visible.sync="userAddDialog"
      width="50%"
      :show-close="false"
      @close="userAddClose"
    >
      <el-form size="mini" label-width="100px" :rules="userAddRules" :model="userAddForm" ref="userAddForm">
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
        <el-form-item label="所属组织机构">
            <!-- <el-tree-select
              :elTreeProps="elTreeProps"
              :elTreeData="orgTreeData"
              :defaultSelectedId="userAddForm.orgId"
              :disabled="false"
              @handleTreeSelected="handleAddTreeSelected($event)"
              @validateSelectTree="validateAddSelectTree"/> -->
              <el-input placeholder="请选择组织机构" v-model="userAddForm.orgName"  @focus="orgTreeKey=true"></el-input>
              <div v-if="orgTreeKey">
                  <el-tree
                  :data="orgTreeData"
                  :props="defaultProps"
                  default-expand-all
                  @node-click="handleAddTreeSelected"
                  ></el-tree>
              </div>
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
            <el-select size="mini" v-model="userAddForm.roleIds" value-key="id" style="width: 100%;" multiple placeholder="请选择角色">
                <el-option
                v-for="item in roles"
                :key="item.id"
                :label="item.roleName"
                :value="item.id"
                ></el-option>
            </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="userAddDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleUserAdd">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户对话框 -->
    <el-dialog :show-close="false" title="修改用户" :visible.sync="userEditDialog" width="50%" @close="userEditClose">
      <el-form size="mini" label-width="100px" :model="userEditForm">
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
        <el-form-item label="所属组织机构">
            <!-- <el-tree-select
              :elTreeProps="elTreeProps"
              :elTreeData="orgTreeData"
              :defaultSelectedId="userEditForm.orgId"
              :disabled="false"
              @handleTreeSelected="handleEditTreeSelected($event)"
              @validateSelectTree="validateEditSelectTree"/> -->
              <el-input placeholder="请选择组织机构" v-model="userEditForm.orgName"  @focus="orgTreeKey=true"></el-input>
              <div v-if="orgTreeKey">
                  <el-tree
                  :data="orgTreeData"
                  :props="defaultProps"
                  default-expand-all
                  @node-click="handleEditTreeSelected"
                  ></el-tree>
              </div>
        </el-form-item>
        <el-form-item label="角色">
            <el-select size="mini" v-model="userEditForm.roleIds" value-key="id" style="width: 100%;" multiple placeholder="请选择角色">
                <el-option
                v-for="(item,index) in roles"
                :key="index"
                :label="item.roleName"
                :value="item.id"
                ></el-option>
            </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="userEditDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleUserUpdate">确 定</el-button>
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
      orgs:[],
      roles: [],
      orgTreeData: [],
      userAddDialog: false,
      userEditDialog: false,
      userAddForm: {
        username: "",
        password: "",
        phone: "",
        email: "",
        status: true,
        orgId: "",
        orgName: "",
        roleIds: ""
      },
      userEditForm: {
        id: 0,
        username: "",
        password: "",
        phone: "",
        email: "",
        status: true,
        orgId: "",
        orgName: "",
        roleIds: ""
      },
	  page:{
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
      elTreeProps:{// el-tree-select配置项（必选）
        value: 'id',
        label: 'orgName',
        children: 'children',
      },
      filterText: '',
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
        email: [{ required: true, message: "邮箱不能为空", trigger: "blur" }],
        roleIds: [{ required: true, message: "角色不能为空", trigger: "blur" }],
      }
    };
  },
  async mounted() {
    const res = await this.orgTree()
    this.orgTreeData = res.data
    this.flush()
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    ...mapActions("user",[
      "userPage",
      "addUser",
      "updateUser",
      "deleteUser",
      "usernameCheck",
      "userRoleIds",
    ]),
    ...mapActions("role",[
        "roleList"
    ]),
    ...mapActions('org',['orgTree']),
    //组织机构树过滤
    filterNode(value, data) {
        if (!value) return true;
        return data.orgName.indexOf(value) !== -1;
    },
    //点击组织机构树的节点
    orgTreeClick(data, node, obj){
        this.queryPage(data.id, this.queryInfo.username, this.page.pageSize, this.page.currentPage)
    },
    //弹出用户新增dialog
    async handleAdd(){
        this.userAddDialog = true
        //查询用户角色列表
        const res = await this.roleList()
        if(res.code === 0){
            this.roles = res.data
        }
    },
    //修改每页显示条数
    handleSizeChange(size) {
      this.queryPage(this.queryInfo.orgId, this.queryInfo.username, size, this.page.currentPage)
    },
	//修改当前第几页
    handleCurrentChange(current) {
      this.queryPage(this.queryInfo.orgId, this.queryInfo.username, this.page.pageSize, current)
    },
    //用户修改关闭dialog后处理
    userEditClose(){
        this.orgTreeKey = false
        this.userEditForm = {
            id: 0,
            username: "",
            password: "",
            phone: "",
            email: "",
            status: true,
            orgId: "",
            orgName: "",
            roleIds: ""
        }
    },
    //弹出用户修改dialog
    handleEdit(row) {
      this.userEditForm.id = row.id
      this.userEditForm.orgId = row.orgId
      this.userEditForm.status = row.status
      this.userEditForm.username = row.username
      this.userEditForm.phone = row.phone
      this.userEditForm.email = row.email
      
      
      console.log('form', this.userEditForm)
      
      //查询用户角色列表
      this.roleList().then(res => {
          this.roles = res.data
      })
      // 查询用户角色id集合
      this.userRoleIds(row.id).then(res => {
          this.userEditForm.roleIds = res.data
      })
      
      this.getOrg(this.orgTreeData)
      //回显组织机构
      for(let org of this.orgs){
          if(org.orgId === row.orgId){
              this.userEditForm.orgName = org.orgName
          }
      }
      this.userEditDialog = true;
    },
    getOrg(datas){ //递归遍历机构树，获取组织机构list
      for(var i in datas){
        this.orgs.push({orgId: datas[i].id, orgName: datas[i].orgName})
        if(datas[i].children){
          this.getOrg(datas[i].children);
        }
      }
    },
    //用户新增dialog关闭后清空值和校验
    userAddClose() {
      this.$refs["userAddForm"].resetFields();
      this.userAddForm = {
        username: "",
        password: "",
        phone: "",
        email: "",
        status: true,
        orgId: "",
        orgName: "",
        roleIds: ""
      }
      this.orgTreeKey = false
    },
    //新增用户
    handleUserAdd() {
      this.$refs.userAddForm.validate(async valid => {
        if (!valid) {
          return false;
        } else {
          let res = await this.addUser(this.userAddForm);
          if (res.code == 0) {
            this.$message.success("添加用户成功");
          }
          //刷新
          this.flush();
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
      this.flush();
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
          this.flush();
        })
        .catch(() => {});
    },
    handleAddTreeSelected(data){
      this.userAddForm.orgId = data.id
      this.userAddForm.orgName = data.orgName
      this.orgTreeKey = false
    },
    handleEditTreeSelected(data){
        this.userEditForm.orgId = data.id
        this.userEditForm.orgName = data.orgName
        this.orgTreeKey = false
    },
	//条件查询
    handleSearch() {
      this.queryPage('',this.queryInfo.username,this.page.pageSize,'')
    },

    //---------- 工具方法 -----------
	//封装分页参数
	copyPageValue(res){
		this.page.tableData = res.data.records;
		this.page.total = res.data.total;
		this.page.pageSize = res.data.size;
		this.page.currentPage = res.data.current;
	},
	//封装查询参数
	copyQueryValue(orgId,username,size,current){
		return {
			username: username?username:null,
            orgId: orgId?orgId:null,
			size: size?size:null,
			current: current?current:null,
		}
	},
    //封装分页请求通用方法
    async queryPage(orgId,username,size,current){
        let res = await this.userPage(this.copyQueryValue(orgId,username,size,current));
        this.copyPageValue(res)
    },
    //刷新界面
    flush() {
      this.queryPage('','',this.page.pageSize,'')
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
