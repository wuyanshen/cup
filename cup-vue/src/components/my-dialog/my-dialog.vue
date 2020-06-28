<template>
    <div v-if="show">
        <slot>
            我是dialog
        </slot>
        <el-button @click="close">关闭</el-button>
    </div>
</template>

<script>
    export default {
        name: 'myDialog',
        props: {
          show: {
              default: false,
              type: Boolean,
          }  
        },
        data(){
            return {
            }
        },
        methods:{
            close(){
                // this.show = false
                this.$emit('update:show', !this.show)
            }
        }
    }
</script>

<style>
</style>
