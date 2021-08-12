<template>
  <el-card>
    <!-- 查询区 -->
    <el-row :gutter="20">
      <el-col :span="2">
        <el-button icon="el-icon-plus" size="mini" type="primary" @click="handleAdd">新增</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-input size="mini" clearable v-model="queryInfo.jobName" placeholder="请输入要查询的任务名称" @change="handleSearch"> </el-input>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearch">查询</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-table :data="this.page.tableData" stripe size="mini" class="job_table">
      <el-table-column type="index" label="序号" width="50" align="center"></el-table-column>
      <el-table-column align="center" label="名称" min-width="120" prop="jobName"></el-table-column>
      <el-table-column label="状态" prop="type" width="110px" align="center">
        <template v-slot="scope">
          <el-tag size="mini" v-if="scope.row.status === '1'" type="success">运行中</el-tag>
          <el-tag size="mini" v-if="scope.row.status === '2'" type="info">暂停中</el-tag>
          <el-tag size="mini" v-if="scope.row.status === '3'" type="primary">未开始</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="cron表达式" min-width="120" prop="cron"></el-table-column>
      <el-table-column align="center" label="类名" min-width="100" prop="beanName"></el-table-column>
      <el-table-column align="center" label="方法名" prop="methodName"></el-table-column>
      <el-table-column align="center" label="描述" min-width="100" prop="description"></el-table-column>
      <el-table-column label="创建时间" prop="createTime" :formatter="formatDate" width="150" align="center"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime" :formatter="formatDate" width="150" align="center"></el-table-column>
      <el-table-column min-width="300" align="center" label="操作">
        <template v-slot="scope">
          <el-button type="text" size="mini" icon="el-icon-edit" @click="handleEdit(scope.row)">修改 </el-button>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row)">删除 </el-button>
          <el-button type="text" size="mini" icon="el-icon-video-pause" @click="handlePause(scope.row)">暂停 </el-button>
          <el-button type="text" size="mini" icon="el-icon-video-play" @click="handleResume(scope.row)">恢复 </el-button>
          <el-button type="text" size="mini" icon="el-icon-refresh" @click="handleTrigger(scope.row)">手动执行一次 </el-button>
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

    <!-- 新增/修改任务对话框 -->
    <el-dialog :title="title" :visible.sync="jobDialog" width="30%" :show-close="false" @close="jobDialogClose" :close-on-click-modal="false">
      <el-form size="mini" label-width="130px" :rules="rules" :model="form" ref="form">
        <el-form-item label="任务名称" prop="jobName">
          <el-input v-model="form.jobName" :disabled="form.id !== undefined"></el-input>
        </el-form-item>
        <el-form-item label="类名(首字母小写)" prop="beanName">
          <el-input v-model="form.beanName"></el-input>
        </el-form-item>
        <el-form-item label="方法名" prop="methodName">
          <el-input v-model="form.methodName"></el-input>
        </el-form-item>
        <el-form-item label="cron表达式" prop="cron">
          <el-input v-model="form.cron"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="jobDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
import { mapActions } from 'vuex'
import { formatDate } from '@/lib/util'
import moment from "moment";

export default {
  data() {
    const cronCheck = async (rule, value, callback) => {
      console.log(value)
      const res = await this.check({ cron: value });
      if (res.data) {
        callback();
      } else {
        callback(new Error('cron表达式错误'));
      }
    }
    return {
      value: '',
      title: '',
      jobDialog: false,
      form: {},
      page: {
        tableData: [],
        pageSize: 5,
        total: 0,
        currentPage: 1,
      },
      queryInfo: {
        jobName: '',
        size: 0,
        current: 0
      },
      rules: {
        jobName: [
          { required: true, message: '任务名称不能为空', trigger: 'blur' }
        ],
        beanName: [
          { required: true, message: '类名不能为空', trigger: 'blur' }
        ],
        cron: [
          { required: true, message: 'cron表达式不能为空', trigger: 'blur' },
          { validator: cronCheck, trigger: 'blur' }
        ],
        methodName: [
          { required: true, message: '方法名不能为空', trigger: 'blur' }
        ]
      }
    };
  },
  mounted() {
    this.flush()
  },
  methods: {
    ...mapActions('job', ['jobPage', 'addJob', 'deleteJob', 'pauseJob', 'resumeJob', 'triggerJob', 'check']),
    // 刷新页面
    async flush() {
      const res = await this.jobPage({
        size: this.page.pageSize,
        page: this.page.currentPage,
        jobName: this.queryInfo.jobName
      })
      if (res.code === 0) {
        this.copyPageValue(res)
      }
    },
    // dialog关闭事件
    jobDialogClose() {
      this.form = {}
      this.$refs.form.resetFields();
    },
    // 修改按钮
    async handleEdit(row) {
      this.title = '修改'
      this.jobDialog = true
      this.form = JSON.parse(JSON.stringify(row))
    },
    // 删除按钮
    async handleDelete(row) {
      this.form = JSON.parse(JSON.stringify(row))

      this.$confirm('确认要删除该任务吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteJob(this.form).then(res => {
          if (res.code === 0) {
            this.flush()
            this.$message.success('删除成功')
          }
        })
      }).catch(error => {
      })
    },
    // 新增按钮
    async handleAdd() {
      this.title = '新增'
      this.jobDialog = true
    },
    // 暂停按钮
    async handlePause(row) {
      if (row.status === '2') {
        return this.$message.warning('任务已经暂停')
      }

      row.status = '2' // 暂停中
      this.pauseJob(row).then(res => {
        if (res.code === 0) {
          this.flush()
          this.$message.success('暂停成功')
        }
      })
    },
    // 恢复按钮
    async handleResume(row) {
      if (row.status === '1') {
        return this.$message.warning('任务还在运行中，不需要恢复')
      }
      row.status = '1'
      this.resumeJob(row).then(res => {
        if (res.code === 0) {
          this.flush()
          this.$message.success('恢复成功')
        }
      })
    },
    // 手动执行按钮
    async handleTrigger(row) {
      this.triggerJob(row).then(res => {
        if (res.code === 0) {
          this.$message.success('手动执行成功')
        }
      })
    },
    // 提交修改
    async handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.form.status = '1' // 运行中
          this.form.createTime = null
          this.form.updateTime = null
          this.addJob(this.form).then(res => {
            this.jobDialog = false
            if (res.code === 0) {
              this.flush()
              if (this.form.id === undefined) {
                this.$message.success('新增成功')
              } else {
                this.$message.success('修改成功')
              }
            }
          })
        }
      })
    },
    //修改每页显示条数
    async handleSizeChange(size) {
      this.page.pageSize = size
      this.flush()
    },
    //修改当前第几页
    async handleCurrentChange(current) {
      this.page.currentPage = current
      this.flush()
    },
    // 条件查询
    async handleSearch() {
      this.flush()
    },
    // 格式化table日期格式
    formatDate(row, column) {
      return formatDate(row, column)
    },
    // 封装分页属性值
    copyPageValue(res) {
      this.page.tableData = res.data.records;
      this.page.total = res.data.total;
      this.page.pageSize = res.data.size;
      this.page.currentPage = res.data.current;
    }
  }
};
</script>

<style scoped lang="less">
.job_table {
  width: 100%;
  margin-top: 10px;
  margin-bottom: 10px;
}

.el-row {
  margin-bottom: 10px;
}
</style>
