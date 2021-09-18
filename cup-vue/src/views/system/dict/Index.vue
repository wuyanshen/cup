<template>
    <div>

        <!-- 卡片区 -->
        <el-card>
            <!--新增-->
            <el-row :gutter="20">
                <el-col :span="2">
                    <el-button size="mini" type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input size="mini" clearable v-model="queryInfo.typeName" placeholder="请输入字典类型名称"></el-input>
                </el-col>
                <el-col :span="6">
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="handleSearch">查询</el-button>
                    <el-button icon="el-icon-refresh" size="mini" type="info" @click="resetQuery">重置</el-button>
                </el-col>
            </el-row>
            <!-- 表格区 -->
            <el-table size="mini" :data="tableData" stripe row-key="id">
                <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                <el-table-column align="center" prop="typeName" label="类型名称" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column align="center" prop="typeCode" label="类型编码">
                    <template slot-scope="scope">
                        <router-link :to="`/dict/data/${scope.row.id}`" class="link-type">
                            <span>{{ scope.row.typeCode }}</span>
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="status" label="状态">
                    <template v-slot="scope">
                        <el-tag size="mini" type="success" v-if="scope.row.status === 1">正常</el-tag>
                        <el-tag size="mini" type="danger" v-if="scope.row.status === 0">停用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" align="center"></el-table-column>
                <el-table-column align="center" label="操作">
                    <template v-slot="scope">
                        <el-button type="text" size="mini" icon="el-icon-edit" @click="handleEdit(scope.row)">修改</el-button>
                        <el-button type="text" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页区 -->
            <el-pagination
                style="margin-top: 10px"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="this.page.current"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="this.page.size"
                layout="total, sizes, prev, pager, next, jumper"
                :total="this.page.total"
            ></el-pagination>
        </el-card>

        <!-- 新增/修改菜单对话框 -->
        <el-dialog :show-close="false" :title="title" :visible.sync="editDialog" width="30%" @close="editDialogClose">
            <el-form size="mini" label-width="80px" :model="dictForm" ref="dictForm">
                <el-form-item label="类型名称" prop="typeName">
                    <el-input v-model="dictForm.typeName"></el-input>
                </el-form-item>
                <el-form-item label="类型编码" prop="typeCode">
                    <el-input v-model="dictForm.typeCode"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model="dictForm.status" :label="0" border>停用</el-radio>
                    <el-radio v-model="dictForm.status" :label="1" border>正常</el-radio>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input type="textarea" :rows="3" v-model="dictForm.remark"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="editDialog = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="handleMenuEditSubmit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "Dict",
        data() {
            return {
                editDialog: false,
                title: '',
                dictForm: {
                },
                queryInfo: {
                    typeName: ''
                },
                tableData: [],
                page: {
                    size: 5,
                    current: 1,
                    total: 0
                },
            }
        },
        mounted() {
            this.handleSearch()
        },
        methods: {
            // dialog关闭
            editDialogClose() {
                this.$nextTick(() => {
                    this.$refs.dectForm.resetFields()
                });
            },
            // 重置查询
            resetQuery() {
                this.queryInfo = {
                    typeName: ''
                };
                this.handleSearch()
            },
            // 新增
            handleAdd(row) {
                this.editDialog = true;
                this.title = '新增字典类型';
                let rowInfo = JSON.parse(JSON.stringify(row));
                this.dictForm = rowInfo;
                this.dictForm.status = 1;
            },
            // 修改
            handleEdit(row) {
                this.editDialog = true;
                this.title = '修改字典类型';
                let rowInfo = JSON.parse(JSON.stringify(row));
                this.dictForm = rowInfo;
            },
            // 删除
            handleDelete(id) {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(async () => {
                        this.$api.dict.deleteDictType(id).then(res => {
                            if (res.code === 0) {
                                this.handleSearch();
                                this.$message({
                                    type: 'success',
                                    message: '删除成功!'
                                })
                            }
                        })
                    })
                    .catch(() => {})
            },
            // 查询
            handleSearch() {
                let params = Object.assign(this.queryInfo, this.page);
                this.$api.dict.dictTypePage(params).then(res => {
                    this.tableData = res.data.records;
                    this.page.total = res.data.total;
                    this.page.size = res.data.size;
                    this.page.current = res.data.current;
                })
            },
            // 保存或修改
            handleMenuEditSubmit() {
                this.editDialog = false;
                // 修改
                if(this.dictForm.id) {
                    this.$api.dict.updateDictType(this.dictForm).then(res=>{
                        this.$message.success("修改成功");
                        this.handleSearch();
                    })

                // 新增
                } else {
                    this.$api.dict.addDictType(this.dictForm).then(res=>{
                        this.$message.success("新增成功");
                        this.handleSearch();
                    })
                }

            },
            // 修改每页显示条数
            handleSizeChange(size) {
                this.page.size = size;
                this.handleSearch();
            },
            // 修改当前第几页
            handleCurrentChange(current) {
                this.page.current = current;
                this.handleSearch();
            },
        }
    }
</script>

<style scoped>

</style>
