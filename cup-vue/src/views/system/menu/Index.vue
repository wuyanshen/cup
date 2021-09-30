<template>
    <div>
        <!-- 卡片区 -->
        <el-card>
            <!--新增-->
            <el-row class="mb-10" :gutter="20">
                <el-col :span="2">
                    <el-button size="mini" type="primary" icon="el-icon-plus" @click="handleMenuAdd">新增</el-button>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input size="mini" clearable v-model="queryInfo.menuName" placeholder="请输入菜单名称"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="handleSearch">查询</el-button>
                    <el-button icon="el-icon-refresh" size="mini" type="info" @click="resetQuery">重置</el-button>
                </el-col>
            </el-row>
            <!-- 表格区 -->
            <el-table size="mini" :data="tableData" stripe row-key="id">
                <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                <el-table-column align="center" prop="menuName" label="名称" width="180"></el-table-column>
                <el-table-column align="center" prop="type" label="类型">
                    <template v-slot="scope">
                        <el-tag size="mini" type="success" v-if="scope.row.type === '0'">菜单</el-tag>
                        <el-tag size="mini" type="info" v-else-if="scope.row.type === '1'">按钮</el-tag>
                        <el-tag size="mini" type="primary" v-else>目录</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="url" label="路由路径"></el-table-column>
                <el-table-column align="center" prop="sort" label="排序"></el-table-column>
                <el-table-column align="center" prop="icon" label="图标">
                    <template v-slot="scope">
                        <i :class="scope.row.icon"></i>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="icon" label="展示">
                    <template v-slot="scope">
                        <el-tag size="mini" type="success" v-if="scope.row.isShow === 1">是</el-tag>
                        <el-tag size="mini" type="danger" v-if="scope.row.isShow === 0">否</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="icon" label="路由缓冲">
                    <template v-slot="scope">
                        <el-tag size="mini" type="success" v-if="scope.row.keepAlive === '1'">开启</el-tag>
                        <el-tag size="mini" type="danger" v-if="scope.row.keepAlive === '0'">关闭</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="permission" label="权限标识"></el-table-column>
                <el-table-column align="center" label="操作" width="300px">
                    <template v-slot="scope">
                        <el-button type="text" size="mini" icon="el-icon-plus" @click="handleMenuAdd(scope.row)">新增子项</el-button>
                        <el-button type="text" size="mini" icon="el-icon-edit" @click="handleEditMenu(scope.row)">修改</el-button>
                        <el-button type="text" size="mini" icon="el-icon-delete" @click="handleDeleteMenu(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 新增/修改菜单对话框 -->
        <el-dialog :show-close="false" :title="title" :visible.sync="menuDialog" width="30%" @close="menuDialogClose">
            <el-form size="mini" label-width="80px" :model="menuForm" ref="menuForm" :rules="menuRules">
                <el-form-item label="名称" prop="menuName">
                    <el-input v-model="menuForm.menuName"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="type">
                    <el-radio-group v-model="menuForm.type" @change="menuTypeChange">
                        <el-radio label="0">菜单</el-radio>
                        <el-radio label="1">按钮</el-radio>
                        <el-radio label="2">目录</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="展示" prop="isShow" v-if="menuForm.type !== '1'" :rules="menuForm.type !== '1' ? { required: true, message: '展示不能为空', trigger: 'blur' } : null">
                    <el-radio-group v-model="menuForm.isShow">
                        <el-radio :label="1">展示</el-radio>
                        <el-radio :label="0">隐藏</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="路由缓冲" prop="keepAlive" v-if="menuForm.type === '0'" :rules="menuForm.type === '0' ? { required: true, message: '路由缓冲不能为空', trigger: 'blur' } : null">
                    <el-radio-group v-model="menuForm.keepAlive">
                        <el-radio label="1">开启</el-radio>
                        <el-radio label="0">关闭</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model="menuForm.sort" :min="1"></el-input-number>
                </el-form-item>
                <el-form-item label="路由路径" prop="url" v-if="menuForm.type === '0'" :rules="menuForm.type === '0' ? { required: true, message: '路由路径不能为空', trigger: 'blur' } : null">
                    <el-input v-model="menuForm.url"></el-input>
                </el-form-item>
                <el-form-item label="图标" prop="icon" v-if="menuForm.type === '0'">
                    <el-input v-model="menuForm.icon" v-popover:iconPop :prefix-icon="menuForm.icon"></el-input>
                    <el-popover ref="iconPop" placement="bottom" title="" width="400" trigger="click">
                        <icon-select @selected="handleIconSelected"></icon-select>
                    </el-popover>
                </el-form-item>
                <el-form-item label="权限标识" prop="permission" v-if="menuForm.type === '1'" :rules="menuForm.type === '1' ? { required: true, message: '权限标识不能为空', trigger: 'blur' } : null">
                    <el-input v-model="menuForm.permission"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单" prop="pid">
                    <el-tree-select
                        :elTreeProps="elTreeProps"
                        :elTreeData="tableData"
                        :defaultSelectedId="menuForm.pid"
                        :disabled="false"
                        @handleTreeSelected="handleTreeSelected($event)"
                        @validateSelectTree="validateSelectTree"
                    />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="menuDialog = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="handleMenuEditSubmit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import IconSelect from '../../../components/IconSelect.vue'
import ElTreeSelect from '@/components/TreeSelect'
import { castToTree4 } from '@/lib/treeUtil'

export default {
    components: { IconSelect, ElTreeSelect },
    data() {
        return {
            title: '',
            tableData: [],
            menuDialog: false,
            menuForm: {
                type: '0',
                isShow: 1,
                menuName: '',
                pid: 0,
                permission: '',
                sort: 1,
                keepAlive: '0',
                icon: '',
                url: ''
            },
            elTreeDisabled: false,
            elTreeProps: {
                // el-tree-select配置项（必选）
                value: 'id',
                label: 'menuName',
                children: 'children'
            },
            queryInfo: {
                menuName: ''
            },
            menuRules: {
                menuName: [{ required: true, message: '菜单名称不能为空', trigger: 'blur' }],
                type: [{ required: true, message: '菜单类型不能为空', trigger: 'blur' }],
                sort: [{ required: true, message: '排序不能为空', trigger: 'blur' }]
            }
        }
    },
    mounted() {
        this.handleSearch()
    },
    methods: {
        ...mapActions('menu', ['menuTreePage', 'addMenu', 'updateMenu', 'deleteMenu']),
        // 重置表单
        reset() {
            this.menuForm = {
                type: '0',
                isShow: 1,
                menuName: '',
                pid: 0,
                permission: '',
                sort: 1,
                keepAlive: '0',
                icon: '',
                url: ''
            }
            this.resetForm('menuForm')
        },
        // 图标选择
        handleIconSelected(name) {
            this.menuForm.icon = name
        },
        // 菜单类型radio改变事件
        menuTypeChange(val) {
            // 清除表单校验
            this.$refs['menuForm'].clearValidate()
            // 按钮
            if (val === '1') {
                this.menuForm.keepAlive = '0' // 不缓冲
                this.menuForm.isShow = 0 // 不展示

                // 菜单
            } else if (val === '0') {
                this.menuForm.keepAlive = '0' // 不缓冲
                this.menuForm.isShow = 1 // 展示

                // 目录
            } else {
                this.menuForm.keepAlive = '0' // 不缓冲
                this.menuForm.isShow = 1 // 展示
            }
        },
        // dialog关闭
        menuDialogClose() {
            this.reset()
        },
        // 新增按钮
        handleMenuAdd(item) {
            this.title = '新增菜单'
            this.menuDialog = true
            if (item) {
                this.menuForm.pid = item.id
            }
        },

        // 修改按钮
        handleEditMenu(row) {
            this.title = '修改菜单'
            this.menuDialog = true
            const rowInfo = JSON.parse(JSON.stringify(row))
            this.menuForm = rowInfo
        },

        // 提交表单
        handleMenuEditSubmit() {
            this.$refs['menuForm'].validate(async valid => {
                if (!valid) {
                    return false
                } else {
                    if (this.menuForm.id === undefined) {
                        const res = await this.addMenu(this.menuForm)
                        this.menuDialog = false
                        if (res.code === 0) {
                            this.$message.success('添加成功')
                        } else {
                            this.$message.error('添加失败')
                        }
                    } else {
                        const res = await this.updateMenu(this.menuForm)
                        this.menuDialog = false
                        if (res.code === 0) {
                            this.$message.success('更新成功')
                        }
                    }
                    this.handleSearch()
                }
            })
        },

        // 删除按钮
        handleDeleteMenu(menuId) {
            this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(async () => {
                    let res = await this.deleteMenu(menuId)
                    if (res.code === 0) {
                        this.handleSearch()
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        })
                    }
                })
                .catch(() => {})
        },

        handleTreeSelected(value) {
            this.menuForm.pid = value
            this.$refs.menuForm.validateField('pid')
        },
        validateSelectTree() {
            this.$refs.menuForm.validateField('pid')
        },

        // 查询
        handleSearch() {
            this.$api.menu.menuList(this.queryInfo).then(res => {
                this.tableData = castToTree4(res.data, 'id', 'pid', 'children', 0)
            })
        },
        // 重置查询
        resetQuery() {
            this.queryInfo = {
                menuName: ''
            }
            this.handleSearch()
        }
    }
}
</script>
