<template>
  <div class="container">
    <!-- 按钮组 -->
    <div class="btn-group">
      <el-button-group>
        <el-button icon="el-icon-upload" @click="handleOpenFile">导入</el-button>
        <el-button :disabled="canDownload" icon="el-icon-upload" @click="handlePublish">部署</el-button>
        <el-button :disabled="canDownload" icon="el-icon-download" @click="handleDownloadSvg">导出为svg</el-button>
        <el-button :disabled="canDownload" icon="el-icon-download" @click="handleDownloadXml">导出为bpmn</el-button>
        <el-button icon="el-icon-sort" @click="hideRightPanel">{{ showRight ? '隐藏' : '显示' }}属性栏</el-button>
        <el-button icon="el-icon-back" @click="handleUndo">后退</el-button>
        <el-button icon="el-icon-right" @click="handleRedo">前进</el-button>
        <el-button icon="el-icon-plus" @click="handleZoom(0.1)">放大</el-button>
        <el-button icon="el-icon-minus" @click="handleZoom(-0.1)">缩小</el-button>
        <el-button icon="el-icon-refresh-left" @click="handleZoomBack">还原</el-button>
      </el-button-group>
    </div>

    <!-- 流程设计 -->
    <div class="act-container">
      <!-- bpmn主内容 -->
      <div class="main-panel"></div>
      <!-- 右侧工具条 -->
      <div v-show="showRight" class="right-panel"></div>
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
import propertiesProviderModule from './properties-panel/provider/activiti';
import activitiModdleDescriptor from './activiti.json';
import customControlsModule from './customControls'

// 3. 在实例化建模器时以自定义模块的方式传入参数
import customTranslate from "./customTranslate";


export default {
  name: 'BpmnEditor',
  data() {
    return {
      canDownload: true,
      showRight: true,
      scale: 1,
      bpmnInfo: {
        xmlStr: '',
      }
    }
  },
  mounted() {
    this.initModeler();
  },
  methods: {
    initModeler() {
      this.bpmnModeler = new BpmnModeler({
        container: '.main-panel',
        // 支持键盘快捷键
        // ctrl + z : 撤销 ctrl + y : 恢复 ctrl + c : 复制 ctrl + v : 粘贴
        // ctrl + + : 放大 ctrl + - : 缩小 ctrl + 0 : 恢复 ctrl + del : 删除 ctrl + 箭头 : 上下左右移动
        keyboard: { bindTo: window },
        propertiesPanel: {
          parent: '.right-panel'
        },
        additionalModules: [
          // 右侧工具条
          propertiesPanelModule,
          propertiesProviderModule,
          customControlsModule,
          // 汉化
          { translate: ["value", customTranslate] }],
        moddleExtensions: {
          activiti: activitiModdleDescriptor
        }
      })
      // 初始化bpmn的xml内容，开始默认有一个开始小圆点
      this.bpmnInfo.xmlStr = '<?xml version="1.0" encoding="UTF-8"?>' +
        '<bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd" id="sample-diagram" targetNamespace="http://activiti.org/bpmn">' +
        '  <bpmn2:process id="Process_1" isExecutable="true">' +
        '     <bpmn2:startEvent id="StartEvent_1"/>' +
        '  </bpmn2:process>' +
        '  <bpmndi:BPMNDiagram id="BPMNDiagram_1">' +
        '    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">' +
        '      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">' +
        '        <dc:Bounds height="36.0" width="36.0" x="412.0" y="240.0"/>' +
        '      </bpmndi:BPMNShape>' +
        '    </bpmndi:BPMNPlane>' +
        '  </bpmndi:BPMNDiagram>' +
        '</bpmn2:definitions>';
      this.createNewDiagram();
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
    // 前进
    handleRedo() {
      this.bpmnModeler.get('commandStack').redo()
    },
    // 后退
    handleUndo() {
      this.bpmnModeler.get('commandStack').undo()
    },
    // 放大缩小
    handleZoom(radio) {
      const newScale = !radio ? 1.0 : this.scale + radio;
      this.bpmnModeler.get("canvas").zoom(newScale);
      this.scale = newScale;
    },
    // 还原
    handleZoomBack() {
      this.bpmnModeler.get("canvas").zoom(1);
    },
    // 上传文件
    handleOpenFile() {
      const vm = this
      const input = document.createElement('input')
      document.body.appendChild(input)
      input.type = 'file'
      input.click()// 打开文件选择框
      input.onchange = function () {
        const file = input.files[0]
        if (window.FileReader) {
          try {
            var fr = new FileReader()
            fr.readAsText(file) // 将文件读取为文本
            fr.onload = function (e) {
              vm.bpmnInfo.xmlStr = fr.result
              vm.createNewDiagram()
            }
          } catch (e) {
            errorInfo(e.toString())
          }

        } else {
          errorInfo('您的浏览器可能不支持此操作')
        }
        document.body.removeChild(input)
      }

    },
    createNewDiagram() {
      // 将字符串转换成图显示出来
      let xmlStr = this.bpmnInfo.xmlStr;
      this.bpmnModeler.importXML(xmlStr, (err) => {
        if (err) {
          // console.error(err)
        } else {
          // 这里是成功之后的回调, 可以在这里做一系列事情
        }
      })
    },
    // 隐藏右侧属性栏
    hideRightPanel() {
      this.showRight = !this.showRight;
    },
    // 部署工作流
    handlePublish() {
      this.bpmnModeler.saveXML({ format: true }, (err, xml) => {
        if (err) {
          this.$message.error('导出错误，请重试')
        } else {
          console.log(xml)
          this.$api.activiti.publishByXml({ name: 'userDefineXml.bpmn', xml }).then(res => {
            this.$message.success('部署工作流成功')
          })
        }
      })
    }
  }

}
</script>

<style scoped lang="less">
.container {
  background-color: white;
  height: 100%;
  overflow-y: scroll;
}
.btn-group {
  margin-left: 20px;
}

.act-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;

  .main-panel {
    width: 100%;
  }
  .right-panel {
    height: 100%;
    width: 300px;
  }
  ::v-deep .bpp-textfield input {
    padding-right: 0;
  }
}

// 隐藏bpmnjs右下角图标
::v-deep .bjs-powered-by {
  display: none !important;
}
</style>
