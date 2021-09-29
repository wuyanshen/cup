<template>
    <div class="tags" v-if="showTags">
        <ul class="tags-ul">
            <li class="tags-li" v-for="(item, index) in tagsList" :class="{ active: isActive(item.path) }" :key="index">
                <router-link :to="item.path" class="tags-li-title">
                    {{ item.title }}
                </router-link>
                <span class="tags-li-icon" v-if="item.path !== '/welcome'" @click="closeTags(index)"><i class="el-icon-close"></i></span>
            </li>
        </ul>
        <div class="tags-close-box">
            <el-dropdown @command="handleTags">
                <el-button size="mini" type="primary"> 标签选项<i class="el-icon-arrow-down el-icon--right"></i> </el-button>
                <el-dropdown-menu size="small" slot="dropdown">
                    <el-dropdown-item command="other">关闭其他</el-dropdown-item>
                    <el-dropdown-item command="all">关闭所有</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import bus from './bus'
export default {
    data() {
        return {
            tagsList: []
        }
    },
    methods: {
        isActive(path) {
            return path === this.$route.fullPath
        },
        // 关闭单个标签
        closeTags(index) {
            const delItem = this.tagsList.splice(index, 1)[0]
            const item = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1]
            if (item) {
                delItem.path === this.$route.fullPath && this.$router.push(item.path)
            } else {
                this.$router.push('/welcome')
            }
        },
        // 关闭全部标签
        closeAll() {
            this.tagsList = this.tagsList.filter(item => item.path === '/welcome')
            this.$route.fullPath !== '/welcome' && this.$router.push('/welcome')
        },
        // 关闭其他标签
        closeOther() {
            const curItem = this.tagsList.filter(item => {
                return item.path === this.$route.fullPath || item.path === '/welcome'
            })
            this.tagsList = curItem
        },
        // 设置标签
        setTags(route) {
            const isExist = this.tagsList.some(item => {
                return item.path === route.fullPath
            })
            if (!isExist) {
                if (this.tagsList.length >= 8) {
                    this.tagsList.shift()
                }
                this.tagsList.push({
                    title: route.meta.title,
                    path: route.fullPath
                    //name: route.matched[1].components.default.name
                })
            }
            bus.$emit('tags', this.tagsList)
        },
        handleTags(command) {
            command === 'other' ? this.closeOther() : this.closeAll()
        }
    },
    computed: {
        showTags() {
            return this.tagsList.length > 0
        }
    },
    watch: {
        $route(newValue, oldValue) {
            this.setTags(newValue)
        }
    },
    created() {
        this.setTags(this.$route)
        // 监听关闭当前页面的标签页
        bus.$on('close_current_tags', () => {
            for (let i = 0, len = this.tagsList.length; i < len; i++) {
                const item = this.tagsList[i]
                if (item.path === this.$route.fullPath) {
                    if (i < len - 1) {
                        this.$router.push(this.tagsList[i + 1].path)
                    } else if (i > 0) {
                        this.$router.push(this.tagsList[i - 1].path)
                    } else {
                        this.$router.push('/welcome')
                    }
                    this.tagsList.splice(i, 1)
                    break
                }
            }
        })
    }
}
</script>

<style scoped lang="less">
.tags {
    position: relative;
    height: 30px;
    overflow: hidden;
    background: #fff;
    padding-left: 5px;
    box-shadow: 0 5px 10px #ddd;

    a {
        text-decoration: none;
    }

    .tags-ul {
        box-sizing: border-box;
        width: 100%;
        height: 100%;

        .tags-li {
            float: left;
            list-style: none;
            margin: 3px 5px 2px 3px;
            border-radius: 12px;
            font-size: 12px;
            overflow: hidden;
            cursor: pointer;
            height: 23px;
            line-height: 23px;
            border: 1px solid #e9eaec;
            background: #fff;
            padding: 0 12px;
            vertical-align: middle;
            color: #666;
            -webkit-transition: all 0.3s ease-in;
            -moz-transition: all 0.3s ease-in;
            transition: all 0.3s ease-in;

            .tags-li-title {
                color: #666;
            }
        }
    }
}

.tags-li:not(.active):hover {
    background: #f8f8f8;
}

.tags-close-box {
    position: absolute;
    right: 0;
    top: 0;
    box-sizing: border-box;
    padding-top: 1px;
    text-align: center;
    width: 100px;
    height: 33px;
    background: #fff;
    box-shadow: -3px 0 15px 3px rgba(0, 0, 0, 0.1);
    z-index: 10;
}

.tags-li.active {
    border: 1px solid #409eff !important;
    background-color: #409eff !important;
    .tags-li-title {
        color: #fff !important;
    }
}

.tags-li.active::before {
    content: '';
    display: inline-block;
    height: 8px;
    width: 8px;
    background-color: #fff;
    margin: 7px 7px 0 0;
    border-radius: 50%;
}
</style>
