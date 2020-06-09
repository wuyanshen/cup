<template>
    <div>
        <el-card>
            <!-- 条件查询 -->
            <el-row :gutter="20">
                <el-col :span="2">
                    <el-button icon="el-icon-plus" size="small" type="primary" @click="roleAddDialog=true">新增</el-button>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input size="small" clearable v-model="queryInfo.roleName" placeholder="请输入要查询的角色名称" @change="queryRole"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-input size="small" clearable v-model="queryInfo.roleCode" placeholder="请输入要查询的角色英文名称" @change="queryRole"></el-input>
                </el-col>
                <el-col :span="2">
                    <el-button icon="el-icon-search" size="small" type="primary" @click="queryRole">查询</el-button>
                </el-col>
            </el-row>
            <!-- 表格区 -->
            <el-table size="small" border stripe :data="this.page.tableData">
                <el-table-column align="center" label="序号" type="index" width="50px"></el-table-column>
                <el-table-column align="center" label="角色名称" prop="roleName"></el-table-column>
                <el-table-column align="center" label="角色英文名称" prop="roleCode"></el-table-column>
                <el-table-column align="center" label="状态" prop="status">
                    <template v-slot="scope">
                        <el-tag size="small" v-if="scope.row.status===1">启用</el-tag>
                        <el-tag size="small" v-if="scope.row.status===0" type="danger">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="创建时间" prop="createTime" :formatter="formatDate"></el-table-column>
                <el-table-column align="center" label="更新时间" prop="updateTime" :formatter="formatDate1"></el-table-column>
                <el-table-column align="center" label="操作" width="300px">
                    <template v-slot="scope">
                        <el-button size="mini" icon="el-icon-edit" type="primary" @click="handleEidt(scope.row)">修改</el-button>
                        <el-button size="mini" icon="el-icon-delete" type="danger" @click="handleRoleDelete(scope.row.id)">删除</el-button>
                        <el-button size="mini" icon="el-icon-plus" type="warning" @click="handlePermission(scope.row.id)">权限</el-button>
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
        </el-card>
        
        <!-- 修改角色dialog -->
        <el-dialog :show-close="false" title="修改角色" :visible.sync="roleEditDialog" width="50%">
          <el-form size="small" label-width="80px" :model="roleEditForm">
            <el-form-item label="id">
              <el-input v-model="roleEditForm.id" disabled></el-input>
            </el-form-item>
            <el-form-item label="角色名称">
              <el-input v-model="roleEditForm.roleName"></el-input>
            </el-form-item>
            <el-form-item label="角色英文名称">
              <el-input v-model="roleEditForm.roleCode"></el-input>
            </el-form-item>
            <el-form-item label="是否启用">
              <el-switch
                style="display: block"
                v-model="roleEditForm.status"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="启用"
                inactive-text="禁用"
                :active-value="1"
                :inactive-value="0"
              ></el-switch>
            </el-form-item>
            <el-form-item label="排序">
              <el-input v-model="roleEditForm.sort"></el-input>
            </el-form-item>
            
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button size="small" @click="roleEditDialog = false">取 消</el-button>
            <el-button size="small" type="primary" @click="handleRoleUpdate">确 定</el-button>
          </span>
        </el-dialog>
        
        <!-- 新增角色dialog -->
        <el-dialog :show-close="false" title="新增角色" :visible.sync="roleAddDialog" width="50%" @close="roleAddClose">
          <el-form size="small" label-width="80px" :model="roleAddForm">
            <el-form-item label="角色名称">
              <el-input v-model="roleAddForm.roleName"></el-input>
            </el-form-item>
            <el-form-item label="英文名称">
              <el-input v-model="roleAddForm.roleCode"></el-input>
            </el-form-item>
            <el-form-item label="排序">
              <el-input v-model="roleAddForm.sort"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button size="small" @click="roleAddDialog = false">取 消</el-button>
            <el-button size="small" type="primary" @click="handleRoleAdd">确 定</el-button>
          </span>
        </el-dialog>
        
        <!-- 权限dialog -->
        <el-dialog :show-close="false" title="分配权限" :visible.sync="permissionDialog" width="50%">
            <el-tree
              ref="tree"
              :data="treeData"
              show-checkbox
              node-key="id"
              default-expand-all
              :props="defaultProps">
            </el-tree>
            <span slot="footer" class="dialog-footer">
              <el-button size="small" @click="permissionDialog = false">取 消</el-button>
              <el-button size="small" type="primary" @click="handlePermissionUpdate">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    import moment from 'moment'
    export default {
        data() {
            return {
                permissionForm:{
                    id: 0,
                    menuIds: []
                },
                defaultProps: {
                    children: 'children',
                    label: 'menuName'
                },
                treeData:[],
                permissionDialog:false,
                roleEditDialog:false,
                roleAddDialog:false,
                roleAddForm:{
                    roleName:'',
                    roleCode:'',
                    status: 1,
                    sort: 0,
                },
                roleEditForm:{
                    id:0,
                    roleName:'',
                    roleCode:'',
                    status: 0,
                    sort: 0,
                },
                page:{
                    tableData:[],
                    pageSize:0,
                    currentPage:0,
                    total:0
                },
                queryInfo: {
                  roleName: '',
                  roleCode: '',
                  size: 0,
                  current: 0
                },
            };
        },
        created() {},
        async mounted() {
            const res  = await this.rolePage({size:5})
            if(res.code === 0){
            	this.copyPageValue(res)
            }
        },
        methods: {
            ...mapActions("role",["rolePage","roleAdd","roleUpdate","roleDelete","roleMenuIds","permission"]),
            ...mapActions("menu",["menuTreePage"]),
            //更新权限
            async handlePermissionUpdate(){
                this.permissionForm.menuIds = this.$refs.tree.getCheckedKeys()
                console.log(this.permissionForm)
                let res = await this.permission(this.permissionForm)
                if(res.code === 0){
                    this.permissionDialog = false
                    this.$message.success('分配权限成功')
                }
            },
            //弹出权限dialog
            async handlePermission(id){
                this.permissionForm.id = id
                this.permissionDialog = true
                let res = await this.menuTreePage()
                if(res.code ===0 ){
                    this.treeData = res.data
                    //设置默认选中
                    let res1 = await this.roleMenuIds(id)
                    if(res1.code === 0){
                        this.$refs.tree.setCheckedKeys(res1.data);
                    }
                }
            },
            //关闭角色新增dialog清空数据
            roleAddClose(){
                this.roleAddForm = {}
            },
            //删除角色
            handleRoleDelete(id){
                this.$confirm("此操作将永久删除该角色, 是否继续?", "提示", {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "warning"
                })
              .then(async () => {
                let res = await this.roleDelete(id)
                if(res.code ===0){
                    this.flush()
                    this.$message.success('删除成功')
                }
              })
              .catch(() => {});
            },
            //新增角色
            async handleRoleAdd(){
               let res = await this.roleAdd(this.roleAddForm)
               this.roleAddDialog = false
               if(res.code === 0){
                   this.flush()
                   this.$message.success('新增成功')
               }
            },
            //刷新界面
            async flush(){
                let res = await this.rolePage({size:5})
                this.copyPageValue(res)
            },
            //弹出修改角色dialog
            handleEidt(row){
                this.roleEditDialog = true
                this.roleEditForm.roleCode = row.roleCode
                this.roleEditForm.roleName = row.roleName
                this.roleEditForm.id = row.id
                this.roleEditForm.sort = row.sort
                this.roleEditForm.status = row.status
            },
            //修改角色
            async handleRoleUpdate(){
                const res = await this.roleUpdate(this.roleEditForm)
                this.roleEditDialog = false
                if(res.code === 0){
                    this.flush()
                    this.$message.success('更新成功')
                }
            },
            //条件查询角色
            async queryRole(){
                const res  = await this.rolePage(this.copyQueryValue(this.queryInfo.roleName,this.queryInfo.roleCode,this.page.pageSize,''))
                if(res.code === 0){
                	this.copyPageValue(res)
                }
            },
            //格式化创建时间
            formatDate(row,column){
                let date = row[column.property]
                return moment(date).format("YYYY-MM-DD HH:mm:ss")
            },
            //格式化更新时间
            formatDate1(row,column){
                let date = row[column.property]
                return moment(date).format("YYYY-MM-DD HH:mm:ss")
            },
            //修改每页显示条数
            async handleSizeChange(size) {
              this.queryInfo.size = size;
              let res = await this.rolePage(this.copyQueryValue(this.queryInfo.roleName,this.queryInfo.roleCode,size,this.page.currentPage));
              this.copyPageValue(res)
            },
            //修改当前第几页
            async handleCurrentChange(current) {
              let res = await this.rolePage(this.copyQueryValue(this.queryInfo.roleName,this.queryInfo.roleCode,this.page.pageSize,current));
              this.copyPageValue(res)
            },
            //封装查询条件
            copyQueryValue(roleName,roleCode,size,current){
              return {
                  roleName: roleName?roleName:null,
                  roleCode: roleCode?roleCode:null,
                  size: size?size:null,
                  current: current?current:null,
              }
            },
            //封装分页属性值
            copyPageValue(res){
              this.page.tableData = res.data.records;
              this.page.total = res.data.total;
              this.page.pageSize = res.data.size;
              this.page.currentPage = res.data.current;
            }
        }
    };
</script>

<style scoped lang="less">
    .el-table{
        margin-bottom: 10px;
        width: 100%;
    }
    .el-row{
        margin-bottom: 10px;
    }
</style>
