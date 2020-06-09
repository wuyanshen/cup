<template>
  <div>
    <!-- 面包屑区 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
    </el-breadcrumb> -->

    <!-- 卡片区 -->
    <el-card>
      <!--新增-->
      <el-button type="primary" icon="el-icon-plus" size="small" @click="menuAddDialog=true">新增</el-button>
      <!-- 表格区 -->
      <el-table size="small" :data="tableData" border stripe row-key="id">
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="menuName" label="名称" width="180"></el-table-column>
        <el-table-column align="center" prop="type" label="类型">
          <template v-slot="scope">
            <el-tag size="small" type="success" v-if="scope.row.type==0">菜单</el-tag>
            <el-tag size="small" type="info" v-else>按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="url" label="路由路径"></el-table-column>
        <el-table-column align="center" prop="icon" label="图标"></el-table-column>
        <el-table-column align="center" prop="permission" label="权限标识"></el-table-column>
        <el-table-column align="center" label="操作">
          <template v-slot="scope">
            <el-button
                    type="primary"
                    size="small"
                    icon="el-icon-edit"
                    @click="handleEditMenu(scope.row)"
            >修改</el-button>
            <el-button
                    type="danger"
                    size="small"
                    icon="el-icon-delete"
                    @click="handleDeleteMenu(scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增菜单对话框 -->
    <el-dialog :show-close="false" title="新增菜单" :visible.sync="menuAddDialog" width="50%" @close="menuAddClose">
      <el-form size="small" label-width="80px" :model="menuForm" ref="menuForm">
        <el-form-item label="名称">
          <el-input v-model="menuForm.menuName"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio v-model="menuForm.type" label="0" border>菜单</el-radio>
          <el-radio v-model="menuForm.type" label="1" border>按钮</el-radio>
        </el-form-item>
        <el-form-item label="路由路径" v-if="menuForm.type==='0'">
          <el-input v-model="menuForm.url"></el-input>
        </el-form-item>
        <el-form-item label="图标" v-if="menuForm.type==='0'">
          <el-input v-model="menuForm.icon"></el-input>
        </el-form-item>
        <el-form-item label="权限标识">
          <el-input v-model="menuForm.permission"></el-input>
        </el-form-item>
        <el-form-item label="上级菜单">
          <el-tree-select
                  :elTreeProps="elTreeProps"
                  :elTreeData="tableData"
                  :defaultSelectedId="menuForm.parentId"
                  :disabled="false"
                  @handleTreeSelected="handleTreeSelected($event)"
                  @validateSelectTree="validateSelectTree"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="menuAddDialog = false">取 消</el-button>
        <el-button size="small" type="primary" @click="handleMenuAdd">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改菜单对话框 -->
    <el-dialog :show-close="false" title="修改菜单" :visible.sync="menuEditDialog" width="50%" @close="menuEditClose">
      <el-form size="small" label-width="80px" :model="menuForm" ref="menuForm">
        <el-form-item label="名称">
          <el-input v-model="menuForm.menuName"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio v-model="menuForm.type" label="0" border>菜单</el-radio>
          <el-radio v-model="menuForm.type" label="1" border>按钮</el-radio>
        </el-form-item>
        <el-form-item label="路由路径">
          <el-input v-model="menuForm.url"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="menuForm.icon"></el-input>
        </el-form-item>
        <el-form-item label="权限标识">
          <el-input v-model="menuForm.permission"></el-input>
        </el-form-item>
        <el-form-item label="上级菜单">
          <el-tree-select
                  :elTreeProps="elTreeProps"
                  :elTreeData="tableData"
                  :defaultSelectedId="menuForm.parentId"
                  :disabled="false"
                  @handleTreeSelected="handleTreeSelected($event)"
                  @validateSelectTree="validateSelectTree"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="menuEditDialog = false">取 消</el-button>
        <el-button size="small" type="primary" @click="handleMenuUpdate">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
import { mapActions } from 'vuex'
import ElTreeSelect from "@/components/TreeSelect";

export default {
  components: {ElTreeSelect},
  data() {
    return {
      tableData: [],
      menuEditDialog: false,
      menuAddDialog: false,
      menuForm: {
        id:null,
        parentId: null, // el-tree-select初始ID（可选）
        menuName:'',
        type: '0',
        url:'',
        icon:'',
        permission:''
      },
      elTreeDisabled:false,
      elTreeProps:{         // el-tree-select配置项（必选）
        value: 'id',
        label: 'menuName',
        children: 'children',
      }
    };
  },
  created() {},
  async mounted() {
    const res = await this.menuTreePage();
    this.tableData = res.data;
  },
  methods: {
    ...mapActions('menu',['menuTreePage','addMenu','updateMenu','deleteMenu']),
    menuAddClose(){
      this.menuForm = {
        id:null,
        parentId: null,
        menuName:'',
        type: '0',
        url:'',
        icon:'',
        permission:''
      }
    },
    menuEditClose(){
      this.menuForm = {
        id:null,
        parentId: null,
        menuName:'',
        type: '0',
        url:'',
        icon:'',
        permission:''
      }
    },
    //新增菜单
    async handleMenuAdd(){
      const res = await this.addMenu(this.menuForm)
	  this.menuAddDialog = false
	  this.refresh()
      if(res.code===0){
		  this.$message.success('添加成功')
	  }else{
		  this.$message.error('添加失败')
	  }
    },
	//删除菜单
    handleDeleteMenu(menuId){
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(async () => {
				let res = await this.deleteMenu(menuId);
				if(res.code===0){
					this.refresh()
					this.$message({
					  type: 'success',
					  message: '删除成功!'
					});
				}
              }).catch(() => {
                // this.$message({
                //   type: 'info',
                //   message: '已取消删除'
                // });          
              });
    },
	//弹出更新菜单dialog
	handleEditMenu(row){
	  this.menuEditDialog = true
	  this.menuForm = row
	},
	//更新菜单
    async handleMenuUpdate(){
	  console.log(this.menuForm)
      const res = await this.updateMenu(this.menuForm)
      this.menuEditDialog = false
      this.refresh()
      if(res.code===0){
		this.$message.success('更新成功')
      }
    },
    handleTreeSelected(value){
      this.menuForm.parentId = value
      this.$refs.menuForm.validateField("parentId");
    },
    validateSelectTree(){
      this.$refs.menuForm.validateField("parentId");
    },
	//刷新table
	async refresh() {
	  const res = await this.menuTreePage();
	  this.tableData = res.data;
	},
  }
};
</script>

<style scoped lang="less">
  .el-table{
    margin-top: 10px;
  }
</style>
