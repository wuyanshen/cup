<template>
  <div>
    <slot name="left"></slot>
    <span :class="countClass" ref="number" :id="eleId"></span>
    <slot name="right"></slot>
  </div>
</template>

<script>
import CountUp from "countup";
// import "./count-to.less";

export default {
  name: "CountTo",
  data() {
    return {
      counter: {}
    };
  },
  computed: {
    eleId() {
      return `count_up_${this._uid}`;
    },
    countClass() {
      return ["count-to-number", this.className];
    }
  },
  props: {
    //起始值
    startVal: {
      type: Number,
      default: 0
    },
    //最终值
    endVal: {
      type: Number,
      default: 0
    },
    //小数点后保留几位
    decimals: {
      type: Number,
      default: 0
    },
    //渐变时长，默认值是1秒
    duration: {
      type: Number,
      default: 1
    },
    //动画延迟，毫秒
    delay: {
      type: Number,
      default: 0
    },
    //是否使用变速效果
    useEasing: {
      type: Boolean,
      default: false
    },
    //是否使用分组
    useGrouping: {
      type: Boolean,
      default: true
    },
    //分组使用的分割符，默认逗号
    separator: {
      type: String,
      default: ","
    },
    //整数和小数的分割符号，默认句号
    decimal: {
      type: String,
      default: "."
    },
    className: {
      type: String,
      default: ""
    }
  },
  watch: {
    endVal(newVal, oldVal) {
      this.counter.update(newVal);
      this.emitEndEvent();
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.counter = new CountUp(
        this.eleId,
        this.startVal,
        this.endVal,
        this.decimals,
        this.duration,
        {
          userEasing: this.userEasing,
          useGrouping: this.useGrouping,
          separator: this.separator,
          decimal: this.decimal
        }
      );
      setTimeout(() => {
        this.counter.start();
        this.emitEndEvent();
      }, this.delay);
    });
  },
  methods: {
    emitEndEvent() {
      setTimeout(() => {
        this.$nextTick(() => {
          this.$emit("on-animation-end", this.getCount());
        });
      }, this.duration * 1000);
    },
    getCount() {
      return this.$refs.number.innerText;
    }
  }
};
</script>

<style lang="less">
@import "./count-to.less";
</style>