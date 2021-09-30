<template>
    <div class="main_div">
        <!-- 标签 -->
        <v-tags></v-tags>
        <!-- 主页面 -->
        <transition name="move" mode="out-in">
            <keep-alive :include="tagsList">
                <router-view v-if="routerViewShowHide"></router-view>
            </keep-alive>
        </transition>
        <myiframe></myiframe>
    </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import myiframe from '@/components/myiframe.vue'
import vTags from '@/components/Tags.vue'
// import bus from '@/components/bus.js'

export default {
    data() {
        return {
            tagsList: []
        }
    },
    components: {
        myiframe,
        vTags
    },
    created() {
        // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
        // bus.$on('tags', msg => {
        //     let arr = []
        //     for (let i = 0, len = msg.length; i < len; i++) {
        //         msg[i].name && arr.push(msg[i].name)
        //     }
        //     this.tagsList = arr
        // })
    },
    mounted() {},
    computed: {
        ...mapState('tabs', ['maintabs', 'activeRoute']),
        ...mapState(['routerViewShowHide'])
    },
    methods: {
        ...mapMutations(['UPDATE_IFRAME_URL', 'UPDATE_IFRAME_STYLE', 'UPDATE_ROUTER_VIEW']),
        //点击标签方法
        tabClick(item) {
            let url = item.name
            if (url.indexOf('http') != -1 || url.indexOf('https') != -1) {
                this.UPDATE_IFRAME_STYLE({ visibility: 'visible', height: '100%' })
                this.UPDATE_IFRAME_URL(url)
                this.UPDATE_ROUTER_VIEW(false)
            } else {
                this.UPDATE_ROUTER_VIEW(true)
                this.UPDATE_IFRAME_URL('')
                this.UPDATE_IFRAME_STYLE({ visibility: 'hidden', height: '0' })
                this.$router.push(item.name).catch(err => {
                    err
                })
            }
        }

    }
}
</script>

<style lang="less">
.main_div {
    height: 100%;
}

// 定义进入\离开过渡生效时的状态
.move-enter-active,
.move-leave-active {
    transition: 0.5s;
}

// 定义进入\离开过渡的开始状态
.move-enter,
.move-leave {
    opacity: 0;
}
</style>
