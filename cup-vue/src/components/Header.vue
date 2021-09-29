<template>
    <div class="header-container">
        <!-- 缩放按钮 -->
        <div class="collapse_btn_div" @click="handleCollapse">
            <i :class="handleClass()"></i>
        </div>
        <!-- 中间区域 -->
        <div class="middle_div">
            <el-tooltip content="打赏" placement="top">
                <a @click="open" href="#"><i class="el-icon-star-off"></i></a>
            </el-tooltip>
            <el-tooltip content="博客" placement="top">
                <a href="https://www.lvcoding.com" target="_blank"><i class="el-icon-reading"></i></a>
            </el-tooltip>
            <el-tooltip content="Gitee码云" placement="bottom">
                <a href="https://gitee.com/wuyanshen/cup" target="_blank"><i class="el-icon-medal-1"></i></a>
            </el-tooltip>

            <!-- 全屏显示 -->
            <div class="btn-fullscreen" @click="handleFullScreen">
                <el-tooltip effect="dark" :content="fullscreen ? `取消全屏` : `全屏`" placement="bottom">
                    <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
        </div>
        <!-- 头像 -->
        <div class="avatar">
            <el-dropdown trigger="click" @command="handleCommand">
                <span class="el-dropdown-link">
                    <el-avatar v-if="avatarUrl" :size="50" :src="avatarUrl"></el-avatar>
                    <el-avatar v-if="!avatarUrl" :size="50" icon="el-icon-user-solid"></el-avatar>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="info">个人信息</el-dropdown-item>
                    <el-dropdown-item command="preUpdatePwd">修改密码</el-dropdown-item>
                    <el-dropdown-item command="signout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <!-- 修改密码对话框 -->
        <el-dialog title="修改密码" :visible.sync="pwdDialog" width="30%" :show-close="false" @close="pwdClose">
            <el-form label-width="100px" ref="pwdForm" :rules="pwdRules" size="mini" :model="pwdForm">
                <el-form-item label="原密码" prop="old_password">
                    <el-input type="password" v-model="pwdForm.old_password"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="new_password">
                    <el-input type="password" v-model="pwdForm.new_password"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="re_password">
                    <el-input type="password" v-model="pwdForm.re_password"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="pwdDialog = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="handleUpdatePwd('pwdForm')">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 二维码显示 -->
        <el-dialog class="qrcode_dialog" title="" :visible.sync="qrCodeDialog" width="15%">
            <img src="../assets/images/dashang.jpg" width="200px" height="200px" />
        </el-dialog>
    </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    data() {
        var validateRePassword = (rule, value, callback) => {
            if (value !== this.pwdForm.new_password) {
                callback(new Error('两次输入的密码不一致'))
            } else {
                callback()
            }
        }
        var checkPassword = async (rule, value, callback) => {
            let res = await this.pwdCheck({
                password: value
            })
            if (res.code !== 0) {
                callback(new Error('原密码不正确'))
            } else {
                callback()
            }
        }
        return {
            fullscreen: false,
            qrCodeDialog: false,
            avatarUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
            pwdDialog: false,
            pwdForm: {
                old_password: '',
                new_password: '',
                re_password: ''
            },
            pwdRules: {
                old_password: [
                    {
                        required: true,
                        message: '原密码不能为空',
                        trigger: 'blur'
                    },
                    {
                        validator: checkPassword,
                        message: '原密码错误',
                        trigger: 'blur'
                    }
                ],
                new_password: [
                    {
                        required: true,
                        message: '新密码不能为空',
                        trigger: 'blur'
                    },
                    {
                        min: 6,
                        message: '密码至少6位',
                        trigger: 'blur'
                    }
                ],
                re_password: [
                    {
                        required: true,
                        message: '确认密码不能为空',
                        trigger: 'blur'
                    },
                    {
                        validator: validateRePassword,
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    computed: {
        ...mapState(['siderCollapse']),
        ...mapState({
            username: state => state.user.username
        })
    },
    methods: {
        ...mapActions('user', ['updatePwd', 'userInfoAction', 'pwdCheck', 'logout']),
        ...mapActions('tabs', ['addTab']),
        ...mapMutations(['UPDATE_COLLAPSE']),
        handleCommand(command) {
            this[command]()
        },
        //点击修改密码按钮 弹出dialog
        preUpdatePwd() {
            this.pwdDialog = true
        },
        //提交密码修改
        handleUpdatePwd(formName) {
            this.$refs[formName].validate(async valid => {
                if (!valid) {
                    return false
                } else {
                    //获取当前登录用户信息
                    let {
                        data: { id }
                    } = await this.userInfoAction()
                    let password = this.pwdForm.new_password
                    console.log(password)
                    //更新密码
                    let res = await this.updatePwd({
                        id,
                        password
                    })
                    if (res.code === 0) {
                        this.$message.success('密码修改成功')
                    }
                    this.pwdDialog = false
                }
            })
        },
        //关闭修改密码dialog后清空提示和数据
        pwdClose() {
            this.$refs['pwdForm'].resetFields()
            this.pwdForm = {}
        },
        //展示用户信息方法
        info() {
            this.$router
                .push({
                    name: 'Info'
                })
                .catch(err => {
                    err
                })
        },
        signout() {
            this.$confirm('此操作将退出系统, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(async () => {
                    const res = await this.logout()
                    if (res.code === 0) {
                        this.$router.push('/login').catch(err => {
                            err
                        })
                        window.sessionStorage.clear()
                        this.$message({
                            type: 'success',
                            message: '退出成功!'
                        })
                    }
                })
                .catch(() => {})
        },
        handleCollapse() {
            this.UPDATE_COLLAPSE(!this.siderCollapse)
        },
        handleClass() {
            return {
                'el-icon-s-fold': !this.siderCollapse,
                'el-icon-s-unfold': this.siderCollapse
            }
        },
        open() {
            this.qrCodeDialog = true
        },
        // 全屏事件
        handleFullScreen() {
            let element = document.documentElement
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen()
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen()
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen()
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen()
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen()
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen()
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen()
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen()
                }
            }
            this.fullscreen = !this.fullscreen
        }
    }
}
</script>
<style lang="less" scoped>
.header-container {
    display: flex;
    justify-content: space-between;

    //左边缩放按钮
    .collapse_btn_div {
        font-size: 25px;

        i:hover {
            cursor: pointer;
        }
    }

    //中间区域
    .middle_div {
        width: 100%;
        display: flex;
        justify-content: flex-end;
        align-items: center;

        a {
            color: white;
        }

        i {
            margin-top: 15px;
            margin-right: 20px;
            font-size: 30px;
        }

        .btn-fullscreen {
            transform: rotate(45deg);
            margin-top: 8px;
            cursor: pointer;
        }
    }

    //二维码图片dialog
    .qrcode_dialog {
        img {
            margin-left: 6px;
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
            margin-top: 10px;
        }
    }
}
</style>
