<template>
  <div>
    <!-- 卡片区 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="2">
          <el-button size="mini" type="primary" icon="el-icon-plus" @click="handleOrgAdd">新增</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input size="mini" clearable v-model="queryInfo.orgName" placeholder="请输入机构名称"></el-input>
        </el-col>
        <el-col :span="6">
          <el-button icon="el-icon-search" size="mini" type="primary" @click="handleSearch">查询</el-button>
          <el-button icon="el-icon-refresh" size="mini" type="info" @click="resetQuery">重置</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" default-expand-all row-key="id" class="org_table" size="mini" stripe>
        <el-table-column type="index" label="序号" width="50" align="center"></el-table-column>
        <el-table-column prop="orgName" label="组织机构名称" align="left"></el-table-column>
        <el-table-column prop="status" label="状态" width="200" align="center">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === 1" size="mini" type="">有效</el-tag>
            <el-tag v-if="scope.row.status === 0" size="mini" type="danger">无效</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" :formatter="dateFormat" align="center"></el-table-column>
        <el-table-column label="操作" width="250" align="center">
          <template v-slot="scope">
            <el-button size="mini" icon="el-icon-edit" type="text" @click="handleEditOrg(scope.row)">修改</el-button>
            <el-button size="mini" icon="el-icon-delete" type="text" @click="handleDeleteOrg(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 修改dialog -->
    <el-dialog :visible.sync="orgEditDialog" title="修改" :show-close="false" width="30%" @close="orgEditClose">
      <el-form size="mini" label-width="100px" :model="orgForm" ref="orgForm">
        <el-form-item label="组织机构名称">
          <el-input v-model="orgForm.orgName" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="orgForm.sort" type="number" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch
            style="display: block"
            v-model="orgForm.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="启用"
            inactive-text="禁用"
            :active-value="1"
            :inactive-value="0"
          ></el-switch>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="orgForm.address" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="上级组织机构">
          <el-tree-select
            :elTreeProps="elTreeProps"
            :elTreeData="tableData"
            :defaultSelectedId="orgForm.parentId"
            :disabled="false"
            @handleTreeSelected="handleTreeSelected($event)"
            @validateSelectTree="validateSelectTree"
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="orgEditDialog = false">取消</el-button>
        <el-button type="primary" size="mini" @click="handleOrgUpdate">确定</el-button>
      </span>
    </el-dialog>

    <!-- 新增dialog -->
    <el-dialog :visible.sync="orgAddDialog" title="新增" :show-close="false" width="30%" @close="orgAddClose">
      <el-form size="mini" label-width="100px" :model="orgForm" ref="orgForm">
        <el-form-item label="组织机构名称">
          <el-input v-model="orgForm.orgName" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="orgForm.sort" type="number" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="orgForm.address" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="上级组织机构">
          <el-tree-select
            :elTreeProps="elTreeProps"
            :elTreeData="tableData"
            :defaultSelectedId="orgForm.parentId"
            :disabled="false"
            @handleTreeSelected="handleTreeSelected($event)"
            @validateSelectTree="validateSelectTree"
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="orgAddDialog = false">取消</el-button>
        <el-button type="primary" size="mini" @click="handleAddOrg">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import ElTreeSelect from '@/components/TreeSelect'
import { mapActions } from 'vuex'
import { formatDate } from '@/lib/util'
import { castToTree4 } from '@/lib/treeUtil'

export default {
  components: {
    ElTreeSelect
  },
  data() {
    return {
      tableData: [],
      orgEditDialog: false,
      orgAddDialog: false,
      orgForm: {
        id: null,
        parentId: null,
        orgName: '',
        sort: 0,
        status: 1,
        address: '',
      },
      elTreeProps: {// el-tree-select配置项（必选）
        value: 'id',
        label: 'orgName',
        children: 'children',
      },
      queryInfo: {
        orgName: ''
      },
    }
  },
  mounted() {
    this.handleSearch()
  },
  methods: {
    ...mapActions('org', ['orgTree', 'addOrg', 'updateOrg', 'deleteOrg']),
    // 格式化时间
    dateFormat(row, column) {
      return formatDate(row, column);
    },
    //关闭修改dialog
    orgEditClose() {
      //清空form的值
      this.orgForm = {}
    },
    //弹出修改dialog
    handleEditOrg(row) {
      this.orgEditDialog = true
      this.orgForm = row
    },
    //保存修改
    async handleOrgUpdate() {
      const res = await this.updateOrg(this.orgForm)
      if (res.code === 0) {
        this.handleSearch()
        this.orgEditDialog = false
        this.$message.success('修改成功')
      }
    },
    //删除组织机构
    handleDeleteOrg(id) {
      this.$confirm('此操作将永久删除，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const res = await this.deleteOrg(id)
        if (res.code === 0) {
          this.handleSearch()
          this.orgEditDialog = false
          this.$message.success('删除成功')
        }
      }).catch(() => { });
    },
    //显示机构新增dialog
    handleOrgAdd() {
      this.orgAddDialog = true
    },
    //关闭新增dialog
    orgAddClose() {
      //清空form的值
      this.orgForm = {}
    },
    //新增组织机构
    async handleAddOrg() {
      console.log(this.orgForm)
      const res = await this.addOrg(this.orgForm)
      if (res.code === 0) {
        this.handleSearch()
        this.orgAddDialog = false
        this.$message.success('新增成功')
      }
    },
    handleTreeSelected(value) {
      this.orgForm.parentId = value
      this.$refs.orgForm.validateField("parentId");
    },
    validateSelectTree() {
      this.$refs.orgForm.validateField("parentId");
    },
    // 查询
    handleSearch() {
      this.$api.org.orgList(this.queryInfo).then(res => {
        this.tableData = castToTree4(res.data, 'id', 'pid', 'children', 0)
      })
    },
    // 重置查询
    resetQuery() {
      this.queryInfo = {
        orgName: '',
      }
      this.handleSearch()
    }
  }
}
</script>

<style>
.org_table {
  margin: 10px 0;
}
.el-switch {
  margin-top: 3.3px;
}
</style>
