<template>
  <div class="container">
    <p>这是input</p>
    <input @input="handleInput" :value="value" />
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: [String, Number],
      default: ""
    }
  },
  components: {},
  methods: {
    handleInput(event) {
      const value = event.target.value;
      this.$emit("input", value);
    }
  }
};
</script>

<style scoped lang="scss">
</style>
