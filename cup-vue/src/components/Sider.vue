<template>
    <div class="sider">
        <!--logo-->
        <div class="logo">
            <el-avatar shape="square" :size="60" :src="avatar"></el-avatar>
            <div class="title" v-if="!siderCollapse">{{ appName }}</div>
        </div>
        <!-- 左侧菜单栏 -->
        <el-menu :default-active="activeRoute" :collapse="siderCollapse" :collapse-transition="false">
            <template v-for="menu in menuList">
                <!-- 只有一级菜单的时候 -->
                <el-menu-item v-if="menu.id && menu.type != 1 && menu.children.length === 0" :key="menu.id" :index="menu.url" @click="handleMenuClick(menu)">
                    <i :class="menu.icon"></i>
                    <span slot="title">{{ menu.menuName }}</span>
                </el-menu-item>

                <!-- 大于一级菜单的时候 -->
                <!-- 一级菜单 -->
                <el-submenu :index="menu.url" :key="menu.id" v-if="menu.id && menu.type != 1 && menu.children.length > 0">
                    <template slot="title">
                        <i :class="menu.icon"></i>
                        <span>{{ menu.menuName }}</span>
                    </template>
                    <!-- 二级菜单 -->
                    <template v-for="subMenu of menu.children" :index="subMenu.url">
                        <el-menu-item :index="subMenu.url" :key="subMenu.id" v-if="subMenu.type != 1" @click="handleMenuClick(subMenu)">
                            <i :class="subMenu.icon"></i>
                            <span slot="title">{{ subMenu.menuName }}</span>
                        </el-menu-item>
                    </template>
                </el-submenu>
            </template>
        </el-menu>
    </div>
</template>

<script>
import avatar from '@/assets/logo.png'
import { mapState, mapMutations } from 'vuex'
export default {
    data() {
        return {
            avatar
        }
    },
    created() {},
    mounted() {
        console.log(this.menuList)
    },
    computed: {
        ...mapState(['siderCollapse', 'appName', 'iframeUrl']),
        ...mapState('menu', ['activeRoute', 'menuList'])
    },
    methods: {
        ...mapMutations(['UPDATE_IFRAME_URL', 'UPDATE_IFRAME_STYLE', 'UPDATE_ROUTER_VIEW']),
        handleMenuClick(item) {
            let url = item.url || '/'
            //跳转iframe
            if (url.includes('http') || url.includes('https')) {
                this.UPDATE_IFRAME_STYLE({ visibility: 'visible', height: '100%' })
                this.UPDATE_IFRAME_URL(item.url)
                this.UPDATE_ROUTER_VIEW(false)

                //跳转vue页面
            } else {
                this.UPDATE_IFRAME_URL('')
                this.UPDATE_IFRAME_STYLE({ visibility: 'hidden', height: '0' })
                this.UPDATE_ROUTER_VIEW(true)
                this.$router.push(url).catch(err => {
                    err
                })
            }
        }
    }
}
</script>

<style scoped lang="less">
.el-menu {
    border: none;
}

.collapse_btn_div {
    line-height: 20px;
    color: #666;
    text-align: center;
    cursor: pointer;
    border-bottom: 1px solid #666;
}

// 左边的logo和标题
.logo {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 60px;
    color: #fff;
    background-color: #409eff;
    border-right: solid 1px #e6e6e6;
    box-shadow: 2px 0 3px -1px #000;

    .el-avatar {
        background-color: #409eff;
    }

    img {
        margin-top: 10px;
    }

    .title {
        font-size: 15px;
        font-weight: bold;
    }
}

// 右边的头像
.avatar {
    height: 60px;
    display: flex;
    align-items: center;

    .el-dropdown {
        height: 50px;
    }

    img {
        /*margin-top: 10px;*/
        width: auto;
        height: auto;
        max-width: 100%;
        max-height: 100%;
    }
}
</style>
