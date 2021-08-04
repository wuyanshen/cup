<template>
  <div class="act-container">
    <el-card class="act-card">
      <bpmn-editor></bpmn-editor>
    </el-card>
  </div>
</template>

<script>
import BpmnEditor from '@/components/BpmnEditor';
export default {
  components: { BpmnEditor },
  data() {
    return {

    };
  },
  created() {

  },
  mounted() {

  },
  methods: {

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
