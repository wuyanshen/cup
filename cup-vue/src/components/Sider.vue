<template>
    <div class="sider">
        <!--logo-->
        <div class="logo">
            <el-avatar shape="square" :size="60" :src="avatar"></el-avatar>
            <div class="title" v-if="!siderCollapse">{{appName}}</div>
        </div>
        <!-- 左侧菜单栏 -->
        <el-menu :default-active="activeRoute" :collapse="siderCollapse" :collapse-transition="false" router>

            <template v-for="menu in menuList">
                <!-- 只有一级菜单的时候 -->
                    <el-menu-item v-if="menu.id && menu.type!=1 && menu.children.length === 0" :key="menu.id" :index="menu.url"
                        @click="handleMenuClick(menu)">
                        <i :class="menu.icon"></i>
                        <span slot="title">{{ menu.menuName }}</span>
                    </el-menu-item>

                <!-- 大于一级菜单的时候 -->
                <!-- 一级菜单 -->
                <el-submenu :index="menu.url" :key="menu.id" v-if="menu.id && menu.type!=1 && menu.children.length >0">
                    <template slot="title">
                        <i :class="menu.icon"></i>
                        <span>{{menu.menuName}}</span>
                    </template>
                    <!-- 二级菜单 -->
                    <el-menu-item v-for="subMenu of menu.children" :key="subMenu.id" :index="subMenu.url" v-if="subMenu.type!=1"
                        @click="handleMenuClick(subMenu)">
                        <i :class="subMenu.icon"></i>
                        <span slot="title">{{ subMenu.menuName }}</span>
                    </el-menu-item>
                </el-submenu>
            </template>

        </el-menu>
    </div>
</template>

<script>
    import avatar from "@/assets/logo.png";
    import {
        mapState,
        mapMutations,
        mapActions
    } from "vuex";
    export default {
        data() {
            return {
                avatar,
            };
        },
        created() {
            for (let menu of this.menuList) {
                if (menu.children) {
                    console.log(menu.menuName)
                }
            }
        },
        mounted() {},
        computed: {
            ...mapState(["siderCollapse", "appName"]),
            ...mapState("tabs", ["activeRoute", "menuList"])
        },
        methods: {
            handleMenuClick(item) {
                // this.addTab(item)
            }
        }
    };
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
        background-color: #409EFF;
        border-right: solid 1px #e6e6e6;
        box-shadow: 2px 0 3px -1px #000;

        .el-avatar {
            background-color: #409EFF;
        }

        img {
            margin-top: 10px;
        }

        .title {
            font-size: 20px;
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
