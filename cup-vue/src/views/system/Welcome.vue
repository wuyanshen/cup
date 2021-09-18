<template>
  <div class="main">
    <h1>欢迎来到{{this.$store.state.appName}}</h1>
  </div>
</template>

<script>

export default {
  data() {
    return {
      //
    };
  },
  components: {
  },
  watch: {
    //
  },
  computed: {

  },
  methods: {

  }
};
</script>

<style scoped lang="less">
  .main{
    font-size: 30px;
    height: 500px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>
