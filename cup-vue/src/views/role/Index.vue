<template>
    <div>
        <el-card>
            <!-- 条件查询 -->
            <el-row :gutter="20">
                <el-col :span="2">
                    <el-button icon="el-icon-plus" size="mini" type="primary" @click="handleAdd">新增
                    </el-button>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input size="mini" clearable v-model="queryInfo.roleName" placeholder="请输入要查询的角色名称"
                              @change="handleQuery"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-input size="mini" clearable v-model="queryInfo.roleCode" placeholder="请输入要查询的角色英文名称"
                              @change="handleQuery"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">查询</el-button>
                    <el-button icon="el-icon-refresh" size="mini" type="info" @click="resetQuery">重置</el-button>
                </el-col>
            </el-row>
            <!-- 表格区 -->
            <el-table size="mini" stripe :data="tableData">
                <el-table-column align="center" label="序号" type="index" width="50px"></el-table-column>
                <el-table-column align="center" label="角色名称" prop="roleName"></el-table-column>
                <el-table-column align="center" label="角色英文名称" prop="roleCode"></el-table-column>
                <el-table-column align="center" label="状态" prop="status">
                    <template v-slot="scope">
                        <el-tag size="mini" v-if="scope.row.status === 1">启用</el-tag>
                        <el-tag size="mini" v-if="scope.row.status === 0" type="danger">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="创建时间" prop="createTime"
                                 :formatter="formatCreateDate"></el-table-column>
                <el-table-column align="center" label="更新时间" prop="updateTime"
                                 :formatter="formatUpdateDate"></el-table-column>
                <el-table-column align="center" label="操作" width="300px">
                    <template v-slot="scope">
                        <el-button size="mini" icon="el-icon-edit" type="text" @click="handleEdit(scope.row)">修改
                        </el-button>
                        <el-button size="mini" icon="el-icon-delete" type="text"
                                   @click="handleRoleDelete(scope.row.id)">删除
                        </el-button>
                        <el-button size="mini" icon="el-icon-plus" type="text" @click="handlePermission(scope.row.id)">
                            权限
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页区 -->
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="this.page.current"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="this.page.size"
                layout="total, sizes, prev, pager, next, jumper"
                :total="this.page.total"
            ></el-pagination>
        </el-card>

        <!-- 新增/修改角色dialog -->
        <el-dialog :show-close="false" :title="title" :visible.sync="roleEditDialog" width="30%" @close="reset">
            <el-form size="mini" label-width="80px" :model="roleEditForm" ref="roleEditForm">
                <el-form-item label="id" v-if="roleEditForm.id">
                    <el-input v-model="roleEditForm.id" disabled></el-input>
                </el-form-item>
                <el-form-item label="角色名称">
                    <el-input v-model="roleEditForm.roleName"></el-input>
                </el-form-item>
                <el-form-item label="英文名称">
                    <el-input v-model="roleEditForm.roleCode"></el-input>
                </el-form-item>
                <el-form-item label="是否启用">
                    <el-switch
                        style="display: block"
                        v-model="roleEditForm.status"
                        active-color="#13ce66"
                        inactive-color="#ff4949"
                        active-text="启用"
                        inactive-text="禁用"
                        :active-value="1"
                        :inactive-value="0"
                    ></el-switch>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="roleEditForm.sort"></el-input>
                </el-form-item>
                <el-form-item label="数据权限">
                    <el-select style="width: 350px" v-model="roleEditForm.scopeType" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-if="roleEditForm.scopeType === '2'">
                    <el-tree
                        ref="org"
                        :data="orgTreeData"
                        show-checkbox
                        expand-on-click-node
                        node-key="id"
                        empty-text="加载中，请稍后"
                        :props="defaultProps"
                    />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="roleEditDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleSubmit">确 定</el-button>
      </span>
        </el-dialog>

        <!-- 权限dialog -->
        <el-dialog :show-close="false" title="分配权限" :visible.sync="permissionDialog" width="30%">
            <el-tree ref="tree" :data="treeData" show-checkbox node-key="id" :props="menuTreeProps"></el-tree>
            <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="permissionDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handlePermissionUpdate">确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    import {formatDate} from '@/lib/util'
    import {castToTree4} from '@/lib/treeUtil'


    export default {
        data() {
            return {
                title: '',
                permissionForm: {
                    id: 0,
                    menuIds: []
                },
                defaultProps: {
                    children: 'children',
                    label: 'orgName'
                },
                menuTreeProps: {
                    children: 'children',
                    label: 'menuName'
                },
                treeData: [],
                permissionDialog: false,
                roleEditDialog: false,
                roleEditForm: {
                    id: undefined,
                    roleName: undefined,
                    roleCode: undefined,
                    dataScope: undefined,
                    scopeType: undefined,
                    status: 1,
                    sort: 0,
                },
                options: [{
                    value: '1',
                    label: '全部数据'
                }, {
                    value: '2',
                    label: '自定义'
                }, {
                    value: '3',
                    label: '本部门及子部门'
                }, {
                    value: '4',
                    label: '本部门'
                }, {
                    value: '5',
                    label: '仅自己'
                }],
                tableData: [],
                orgTreeData: [],
                page: {
                    size: 5,
                    current: 1,
                    total: undefined
                },
                queryInfo: {
                    roleName: undefined,
                    roleCode: undefined,
                },
            };
        },
        created() {
        },
        mounted() {
            this.handelQueryOrgTree()
            this.handleQuery()
        },
        methods: {
            ...mapActions('org', ['orgTree']),
            ...mapActions("role", ["rolePage", "roleAdd", "roleUpdate", "roleDelete", "roleMenuIds", "permission"]),
            ...mapActions("menu", ["menuTreePage"]),
            //更新权限
            async handlePermissionUpdate() {
                this.permissionForm.menuIds = this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())
                let res = await this.permission(this.permissionForm)
                if (res.code === 0) {
                    this.permissionDialog = false
                    this.$message.success('分配权限成功')
                }
            },
            //弹出权限dialog
            async handlePermission(id) {
                this.permissionForm.id = id
                this.permissionDialog = true
                let res = await this.menuTreePage()
                if (res.code === 0) {
                    this.treeData = castToTree4(res.data, 'id', 'pid', 'children', 0)
                    //设置默认选中
                    let res1 = await this.roleMenuIds(id)
                    if (res1.code === 0) {
                        //树形菜单回显核心代码 --> 这里很重要！！！
                        res1.data.forEach(menuId => {
                            let node = this.$refs.tree.getNode(menuId);
                            console.log(`节点名:${node.label} --- 节点id:${node.key} --- 是否叶子节点:${node.isLeaf}`)
                            if (node.isLeaf) {
                                this.$refs.tree.setChecked(node, true);
                            }
                        });
                    }
                }
            },
            // 重置查询
            resetQuery() {
                this.queryInfo = {
                    roleName: undefined,
                    roleCode: undefined
                }
                this.page = {
                    current: 1
                }

                this.handleQuery()
            },
            // 查询机构树
            async handelQueryOrgTree() {
                // 查询机构树
                const res = await this.orgTree()
                this.orgTreeData = castToTree4(res.data, 'id', 'pid', 'children', 0)
            },
            // 重置dialog
            reset() {
                this.roleEditForm = {
                    id: undefined,
                    roleName: undefined,
                    roleCode: undefined,
                    dataScope: undefined,
                    scopeType: undefined,
                    status: 1,
                    sort: 0,
                }
                this.resetForm('roleEditForm')
            },
            //删除角色
            handleRoleDelete(id) {
                this.$confirm("此操作将永久删除该角色, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(async () => {
                        let res = await this.roleDelete(id)
                        if (res.code === 0) {
                            this.page.current = 1;
                            this.handleQuery()
                            this.$message.success('删除成功')
                        }
                    })
                    .catch(() => {
                    });
            },
            //弹出修改角色dialog
            handleEdit(row) {
                this.reset()
                this.title = "修改角色"
                this.roleEditDialog = true
                this.roleEditForm = JSON.parse(JSON.stringify(row))
                // 数据权限回显
                if (this.roleEditForm.scopeType === '2') {
                    const orgIds = this.roleEditForm.dataScope.split(',')
                    orgIds.forEach(orgId => {
                        this.$nextTick(() => { // 选中tree节点
                            this.setTreeNodeCheck('org', orgId)
                        })
                    })
                }
            },
            //弹出新增角色dialog
            handleAdd() {
                this.reset()
                this.title = "新增角色"
                this.roleEditDialog = true
            },
            //修改角色
            async handleSubmit() {

                // 处理数据权限
                if (this.roleEditForm.scopeType === '2') {
                    this.roleEditForm.dataScope = this.getTreeCheckedKeys('org').join(',')
                } else {
                    this.roleEditForm.dataScope = ''
                }
                // 修改
                if(this.roleEditForm.id) {
                    const res = await this.roleUpdate(this.roleEditForm)
                    this.roleEditDialog = false
                    if (res.code === 0) {
                        this.handleQuery()
                        this.$message.success('更新成功')
                    }

                    // 新增
                } else {
                    let res = await this.roleAdd(this.roleEditForm)
                    this.roleEditDialog = false
                    if (res.code === 0) {
                        this.handleQuery()
                        this.$message.success('新增成功')
                    }
                }

            },
            // 查询角色分页
            async handleQuery() {
                let params = Object.assign(this.page, this.queryInfo)
                let res = await this.rolePage(params);
                this.tableData = res.data.records;
                this.page.total = res.data.total;
                this.page.size = res.data.size;
                this.page.current = res.data.current;
            },
            //格式化创建时间
            formatCreateDate(row, column) {
                return formatDate(row, column)
            },
            //格式化更新时间
            formatUpdateDate(row, column) {
                return formatDate(row, column)
            },
            //修改每页显示条数
            handleSizeChange(size) {
                this.page.size = size
                this.handleQuery()
            },
            //修改当前第几页
            handleCurrentChange(current) {
                this.page.current = current
                this.handleQuery()
            },
        }
    };
</script>

<style scoped lang="less">
    .el-table {
        margin-bottom: 10px;
        width: 100%;
    }

    .el-row {
        margin-bottom: 10px;
    }

    .el-switch {
        margin-top: 6px;
    }
</style>
