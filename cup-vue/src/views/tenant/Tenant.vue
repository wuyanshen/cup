<template>
    <div>
        <el-card>
            <!-- 条件查询 -->
            <el-row :gutter="20">
                <el-col :span="2">
                    <el-button icon="el-icon-plus" size="small" type="primary" @click="tenantAddDialog=true">新增</el-button>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input size="small" clearable v-model="queryInfo.tenantName" placeholder="请输入要查询的租户名称" @change="queryTenant"></el-input>
                </el-col>
                <el-col :span="2">
                    <el-button icon="el-icon-search" size="small" type="primary" @click="queryTenant">查询</el-button>
                </el-col>
            </el-row>
            
            <!-- 表格区 -->
            <el-table size="small" border stripe :data="this.page.tableData">
                <el-table-column align="center" label="序号" type="index" width="50px"></el-table-column>
                <el-table-column align="center" label="租户名称" prop="tenantName" ></el-table-column>
                <el-table-column align="center" label="租户编号" prop="tenantId"  ></el-table-column>
                <el-table-column align="center" label="创建时间" prop="createTime" :formatter="formatCreateDate"></el-table-column>
                <el-table-column align="center" label="更新时间" prop="updateTime" :formatter="formatUpdateDate"></el-table-column>
                <el-table-column align="center" label="操作" width="300px">
                    <template v-slot="scope">
                        <el-button size="mini" icon="el-icon-edit" type="primary" @click="handleTenantEidt(scope.row)">修改</el-button>
                        <el-button size="mini" icon="el-icon-delete" type="danger" @click="handleTenantDelete(scope.row.id)">删除</el-button>
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
        
        <!-- 修改租户dialog -->
        <el-dialog :show-close="false" title="修改租户" :visible.sync="tenantEditDialog" width="50%">
            <el-form size="small" label-width="80px" :model="tenantEditForm">
              <el-form-item label="租户名称">
                <el-input v-model="tenantEditForm.tenantName"></el-input>
              </el-form-item>
              <el-form-item label="租户编号">
                <el-input v-model="tenantEditForm.tenantId"></el-input>
              </el-form-item>
              <el-form-item label="备注">
                <el-input v-model="tenantEditForm.remark"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button size="small" @click="tenantEditDialog = false">取 消</el-button>
              <el-button size="small" type="primary" @click="handleTenantUpdate">确 定</el-button>
            </span>
        </el-dialog>
        
        <!-- 新增租户dialog -->
        <el-dialog :show-close="false" title="新增租户" :visible.sync="tenantAddDialog" width="50%">
            <el-form size="small" label-width="80px" :model="tenantAddForm">
              <el-form-item label="租户名称">
                <el-input v-model="tenantAddForm.tenantName"></el-input>
              </el-form-item>
              <el-form-item label="租户编号">
                <el-input v-model="tenantAddForm.tenantId"></el-input>
              </el-form-item>
              <el-form-item label="备注">
                <el-input v-model="tenantAddForm.remark"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button size="small" @click="tenantAddDialog = false">取 消</el-button>
              <el-button size="small" type="primary" @click="TenantAdd">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    import {formatDate} from '@/lib/util'
        
    export default{
        data(){
            return {
                tenantEditDialog: false,
                tenantAddDialog: false,
                tenantEditForm: {
                    id: '',
                    tenantId: '',
                    tenantName: '',
                    remark: ''
                },
                tenantAddForm: {
                    tenantId: '',
                    tenantName: '',
                    remark: ''
                },
                page:{
                    tableData:[],
                    pageSize:5,
                    currentPage:0,
                    total:0
                },
                queryInfo: {
                    tenantName: ''
                }
            }
        },
        mounted() {
            this.flush()
        },
        methods:{
            ...mapActions('tenant', ["tenantPage", "tenantAdd", "tenantUpdate", "tenantDelete"]),
            // 修改租户
            handleTenantEidt(row){
                this.tenantEditDialog = true;
                this.tenantEditForm.id = row.id
                this.tenantEditForm.tenantId = row.tenantId
                this.tenantEditForm.tenantName = row.tenantName
                this.tenantEditForm.remark = row.remark
            },
            // 提交修改
            async handleTenantUpdate(){
                let res = await this.tenantUpdate(this.tenantEditForm);
                if(res.code === 0 ){
                    this.tenantEditDialog = false
                    this.$message.success('更新成功')
                    this.flush()
                }
            },
            // 删除租户
            async handleTenantDelete(id){
                this.$confirm("此操作将永久删除该租户, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                  })
                .then(async () => {
                  let res = await this.tenantDelete(id)
                  if(res.code ===0){
                      this.flush()
                      this.$message.success('删除成功')
                  }
                })
                .catch(() => {});
            },
            // 条件查询租户
            queryTenant(){
                this.queryPage(this.queryInfo.tenantName, )
            },
            // 新增租户
            handleTenantAdd(){
                this.tenantAddDialog = true
            },
            // 提交新增
            async TenantAdd(){
                let res = await this.tenantAdd(this.tenantAddForm)
                if(res.code === 0 ){
                    this.tenantAddDialog = false
                    this.$message.success('新增成功')
                    this.flush()
                }
            },
            
            //------- 工具方法 -------
            //修改每页显示条数
            handleSizeChange(size) {
              this.queryPage(this.queryInfo.tenantName, size,this.page.currentPage)
            },
            //修改当前第几页
            handleCurrentChange(current) {
              this.queryPage(this.queryInfo.tenantName, this.page.pageSize, current)
            },
            //格式化创建时间
            formatCreateDate(row,column){
                return formatDate(row,column)
            },
            //格式化更新时间
            formatUpdateDate(row,column){
                return formatDate(row,column)
            },
            //封装查询条件
            copyQueryValue(tenantName, size, current){
              return {
                  tenantName: tenantName?tenantName:null,
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
            },
            //封装通用分页方法
            async queryPage(tenantName,size,current){
                let res = await this.tenantPage(this.copyQueryValue(tenantName, size, current));
                console.log(res)
                this.copyPageValue(res)
            },
            //刷新界面
            flush(){
                this.queryPage('', this.page.pageSize, '')
            }
        }
    }
</script>

<style lang="less">
    .el-row{
        margin: 10px auto;
    }
    
    .el-pagination{
        margin: 10px auto;
    }
</style>
