<template>
  <div class="act-container">
    <el-card class="act-card">
      <!-- 按钮 -->
      <el-button type="danger" size="mini" @click="showActEditor">工作流编辑器</el-button>
      <el-button type="danger" size="mini" @click="showUploadDialog">通过zip部署</el-button>
      <!-- 表格 -->
      <el-table size="mini" border stripe :data="processList">
        <el-table-column align="center" label="流程ID" prop="id"></el-table-column>
        <el-table-column align="center" label="流程KEY" prop="key"></el-table-column>
        <el-table-column align="center" label="部署ID" prop="deploymentId"></el-table-column>
        <el-table-column align="center" label="名称" prop="name"></el-table-column>
        <el-table-column align="center" label="版本" prop="version"></el-table-column>
        <el-table-column align="center" label="状态" prop="suspended">
          <template v-slot="scope">
            <el-tag v-if="scope.row.suspended === false" type="success">激活</el-tag>
            <el-tag v-if="scope.row.suspended === true" type="warning">挂起</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="danger" @click="deleteProcess(scope.row)">删除</el-button>
            <el-button size="mini" type="warning" @click="doSuspend(scope.row)" v-if="scope.row.suspended === false">挂起</el-button>
            <el-button size="mini" type="success" @click="doSuspend(scope.row)" v-if="scope.row.suspended === true">激活</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 上传dialog -->
    <el-dialog title="上传流程图" width="30%" :visible.sync="showUpload">
      <el-upload ref="upload" drag action="/act/publishByZip" :auto-upload="false" :headers="headerObj" accept=".zip" :on-change="uploadOnChange" :on-success="uploadOnSuccess">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip" style="color: red">注意：只能上传zip文件</div>
      </el-upload>

      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="showUpload = false">取 消</el-button>
        <el-button size="mini" type="primary" :disabled="uploadBtn" @click="submitUpload">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑器弹框dialog -->
    <el-dialog title="工作流编辑器" width="80%" :visible.sync="showAct">
      <bpmn-editor></bpmn-editor>
    </el-dialog>
  </div>
</template>

<script>
import BpmnEditor from '@/components/BpmnEditor';
export default {
  components: { BpmnEditor },
  data() {
    return {
      showAct: false,
      showUpload: false,
      processList: [],
      uploadBtn: true,
      headerObj: {
        Authorization: window.sessionStorage.getItem("token"),
      },
    };
  },
  created() {

  },
  mounted() {
    this.getProcessList();
  },
  methods: {
    // 上传有文件变化时
    uploadOnChange(file, fileList) {
      if (file) {
        this.uploadBtn = false;
      }
    },
    // 上传文件成功时
    uploadOnSuccess(response, file, fileList) {
      if (response.code === 0) {
        this.$message.success('部署成功')
        this.getProcessList();
      }
    },
    showActEditor() {
      this.showAct = true
    },
    // 提交上传
    submitUpload() {
      this.$refs.upload.submit();
      this.showUpload = false;
      this.getProcessList();
    },
    // 显示上传弹框
    showUploadDialog() {
      this.showUpload = true;
    },
    // 删除流程
    deleteProcess(item) {
      this.$confirm("确认要删除这个流程吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$api.activiti.deleteProcess(item.deploymentId).then(res => {
            this.getProcessList();
          })
        })
        .catch(() => { });

    },
    // 挂起/激活流程
    doSuspend(item) {
      this.$api.activiti.suspend({ key: item.key }).then(res => {
        this.getProcessList();
      })

    },
    // 查询流程部署
    getProcessList() {
      this.$api.activiti.getProcessList().then(res => {
        this.processList = res.data;
      })
    }
  }
};
</script>

<style scoped lang="less">
::v-deep .el-card__body {
  height: 100%;
}
.act-container {
  height: 100%;

  .act-card {
    height: 100%;
  }
}
</style>
