<template>
  <div class="container">
    <!-- 按钮组 -->
    <div class="btn-group">
      <el-button-group>
        <el-button icon="el-icon-upload">导入</el-button>
        <el-button
          :disabled="canDownload"
          icon="el-icon-download"
          @click="handleDownloadSvg"
          >导出为svg</el-button
        >
        <el-button
          :disabled="canDownload"
          icon="el-icon-download"
          @click="handleDownloadXml"
          >导出为bpmn</el-button
        >
      </el-button-group>
    </div>

    <!-- 流程设计 -->
    <div class="act-container">
      <!-- bpmn主内容 -->
      <div class="main-panel"></div>
      <!-- 右侧工具条 -->
      <div class="right-panel"></div>
    </div>
  </div>
</template>

<script>
// 以下为bpmn工作流绘图工具的样式
import "bpmn-js/dist/assets/diagram-js.css"; // 左边工具栏以及编辑节点的样式
import "bpmn-js/dist/assets/bpmn-font/css/bpmn.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css";
import "bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css"; // 右边工具栏样式

// 依赖的js
import BpmnModeler from "bpmn-js/lib/Modeler"
import propertiesPanelModule from "bpmn-js-properties-panel";
import propertiesProviderModule from "bpmn-js-properties-panel/lib/provider/camunda";
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda';
// 3. 在实例化建模器时以自定义模块的方式传入参数
import customTranslate from "./customTranslate";

export default {
  name: 'BpmnEditor',
  data() {
    return {
      canDownload: true,
    }
  },
  mounted() {
    this.initModeler();
  },
  methods: {
    initModeler() {
      this.bpmnModeler = new BpmnModeler({
        container: '.main-panel',
        propertiesPanel: {
          parent: '.right-panel'
        },
        additionalModules: [
          // 右侧工具条
          propertiesPanelModule,
          propertiesProviderModule,
          // 汉化
          { translate: ["value", customTranslate] }],
        moddleExtensions: {
          //如果要在属性面板中维护camunda：XXX属性，则需要此 
          camunda: camundaModdleDescriptor
        }
      })
      // 内部调用了importXML方法，读取内部的默认xml字符串
      this.bpmnModeler.createDiagram();
      //   this.addEventBusListener();
      this.addBpmnListener();
    },
    addEventBusListener() {
      // 监听 element
      const eventBus = this.bpmnModeler.get('eventBus')
      const eventTypes = ['element.click', 'element.changed', 'element.updateLabel']
      eventTypes.forEach(function (eventType) {
        eventBus.on(eventType, function (e) {
          console.log(eventType)
          if (!e || e.element.type == 'bpmn:Process') return
          if (eventType === 'element.changed') {
            // that.elementChanged(e)
          } else if (eventType === 'element.click') {
            console.log('点击了element', e)
            // if (e.element.type === 'bpmn:Task') {
            // }
          } else if (eventType === 'element.updateLabel') {
            console.log('element.updateLabel', e.element)
          }
        })
      })
    },
    // 添加绑定事件
    addBpmnListener() {
      // 给图绑定事件，当图有发生改变就会触发这个事件
      this.bpmnModeler.on('commandStack.changed', () => {
        this.canDownload = false;
      })
    },
    // 下载xml/svg
    download(type, data, name) {
      // 下载xml/svg
      let dataTrack = ''
      const a = document.createElement('a')
      switch (type) {
        case 'xml':
          dataTrack = 'bpmn'
          break
        case 'svg':
          dataTrack = 'svg'
          break
        default:
          break
      }
      const reName = name || `diagram.${dataTrack}`
      a.setAttribute(
        'href',
        `data:application/bpmn20-xml;charset=UTF-8,${encodeURIComponent(data)}`
      )
      a.setAttribute('target', '_blank')
      a.setAttribute('dataTrack', `diagram:download-${dataTrack}`)
      a.setAttribute('download', reName)
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
    },
    // 下载 XML 格式
    handleDownloadXml() {
      this.bpmnModeler.saveXML({ format: true }, (err, xml) => {
        if (err) {
          this.$message.error('导出错误，请重试')
        } else {
          this.download('xml', xml)
        }
      })
    },
    // 下载 SVG 格式
    handleDownloadSvg() {
      this.bpmnModeler.saveSVG({ format: true }, (err, xml) => {
        if (err) {
          this.$message.error('导出错误，请重试')
        } else {
          this.download('svg', xml)
        }
      })
    },
  }

}
</script>

<style scoped lang="less">
.container {
  background-color: white;
  padding: 20px;
}
.btn-group {
  margin-left: 20px;
}

.act-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  .main-panel {
    width: 100%;
  }
  .right-panel {
    width: 500px;
  }
  ::v-deep .bpp-textfield input {
    padding-right: 0;
  }
}
</style>
