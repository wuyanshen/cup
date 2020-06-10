<template>
    <div>
        <iframe ref="iframe" :style="iframeStyle" :src="iframeUrl" frameborder="0" width="100%" scrolling="auto">
        </iframe>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    export default{
        data(){
            return{
            }
        },
        mounted(){
            this.iframeInit()
        },
        computed:{
            ...mapState(["iframeUrl","iframeStyle"]),
        },
        methods:{
            iframeInit(){
                const iframe = this.$refs.iframe
                const clientHeight = document.documentElement.clientHeight - (screen > 1 ? 200 : 105)
                iframe.style.height = `${clientHeight}px`
            }
        }
    }
</script>

<style>
</style>
