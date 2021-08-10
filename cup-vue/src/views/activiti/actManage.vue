<template>
  <div class="act-container">
    <el-card class="act-card">
      <!-- 按钮 -->
      <el-button type="danger" size="mini" @click="showActEditor">工作流编辑器</el-button>
      <!-- 表格 -->
      <el-table size="mini" border stripe>
        <el-table-column align="center" label="流程ID"></el-table-column>
        <el-table-column align="center" label="流程KEY"></el-table-column>
        <el-table-column align="center" label="名称"></el-table-column>
        <el-table-column align="center" label="版本"></el-table-column>
        <el-table-column align="center" label="部署时间"></el-table-column>
        <el-table-column align="center" label="部署ID"></el-table-column>
        <el-table-column align="center" label="状态"></el-table-column>
        <el-table-column align="center" label="操作"></el-table-column>
      </el-table>
    </el-card>

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
    };
  },
  created() {

  },
  mounted() {

  },
  methods: {
    showActEditor() {
      this.showAct = true
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
