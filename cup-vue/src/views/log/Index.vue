<template>
    <el-card>
        <!-- 查询区 -->
        <el-row :gutter="20">
            <el-col :span="6">
                <el-input size="mini" clearable v-model="queryInfo.title" placeholder="请输入要查询的日志标题" @change="handleSearch"> </el-input>
            </el-col>
            <el-col :span="3">
                <el-select size="mini" v-model="queryInfo.type" @change="handleSearch" placeholder="请求选择">
                    <el-option v-for="item in logTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
                </el-select>
            </el-col>
            <el-col :span="6">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearch">查询</el-button>
                <el-button type="info" icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="12">
                <el-button type="danger" style="margin-top: 8px" size="mini" @click="clearLogs">清空日志</el-button>
            </el-col>
        </el-row>
        <!-- 表格 -->
        <el-table :data="this.page.tableData" stripe size="mini" class="log_table">
            <el-table-column type="index" label="序号" width="50" align="center"></el-table-column>
            <el-table-column label="标题" prop="title" width="180"></el-table-column>
            <el-table-column label="日志类型" prop="type" width="110px" align="center">
                <template v-slot="scope">
                    <el-tag v-if="scope.row.type === '1'" size="mini" type="success">用户日志</el-tag>
                    <el-tag v-if="scope.row.type === '2'" size="mini" type="success">菜单日志</el-tag>
                    <el-tag v-if="scope.row.type === '3'" size="mini" type="success">角色日志</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="主机" prop="ip"></el-table-column>
            <el-table-column label="操作地址" prop="addr"></el-table-column>
            <el-table-column label="请求uri" prop="requestUri"></el-table-column>
            <el-table-column label="请求方式" prop="method" width="80" align="center"></el-table-column>
            <el-table-column label="请求时间(毫秒)" prop="time" width="120" align="center"></el-table-column>
            <el-table-column label="请求人" prop="createBy" width="80" align="center"></el-table-column>
            <el-table-column label="请求时间" prop="createTime" :formatter="formatDate" width="180" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template v-slot="scope">
                    <el-button size="mini" icon="el-icon-view" type="text" @click="showLogDetail(scope.row)">详情</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页区 -->
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="this.page.currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="this.page.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="this.page.total"
        ></el-pagination>

        <!-- 日志详情对话框 -->
        <el-dialog title="日志详情" :visible.sync="showLogDialog">
            <el-form>
                <el-row>
                    <el-col :span="12"
                        ><el-form-item label="标题:">
                            {{ logDetail.title }}
                        </el-form-item></el-col
                    >
                    <el-col :span="12"
                        ><el-form-item label="主机:">
                            {{ logDetail.ip }}
                        </el-form-item></el-col
                    >
                </el-row>
                <el-row>
                    <el-col :span="12"
                        ><el-form-item label="操作地址:">
                            {{ logDetail.addr }}
                        </el-form-item></el-col
                    >
                    <el-col :span="12"
                        ><el-form-item label="请求uri:">
                            {{ logDetail.requestUri }}
                        </el-form-item></el-col
                    >
                </el-row>

                <el-row>
                    <el-col :span="12"
                        ><el-form-item label="请求方式:">
                            {{ logDetail.method }}
                        </el-form-item></el-col
                    >
                    <el-col :span="12"
                        ><el-form-item label="请求响应:">
                            <pre>{{ logDetail.response }}</pre>
                        </el-form-item></el-col
                    >
                </el-row>

                <el-row>
                    <el-col :span="12"
                        ><el-form-item label="请求人:">
                            {{ logDetail.createBy }}
                        </el-form-item></el-col
                    >
                    <el-col :span="12"
                        ><el-form-item label="请求时间:">
                            {{ logDetail.createTime | castDate }}
                        </el-form-item></el-col
                    >
                </el-row>
                <el-row>
                    <el-col :span="12"
                        ><el-form-item label="请求参数:">
                            {{ logDetail.params }}
                        </el-form-item></el-col
                    >
                </el-row>
            </el-form>
            <span slot="footer">
                <el-button size="mini" type="primary" @click="showLogDialog = false">确定</el-button>
            </span>
        </el-dialog>
    </el-card>
</template>

<script>
import { mapActions } from 'vuex'
import { formatDate, castDate } from '@/lib/util'

export default {
    data() {
        return {
            showLogDialog: false,
            logDetail: {},
            value: '',
            logTypes: [
                {
                    value: '',
                    label: '请选择'
                },
                {
                    value: '1',
                    label: '用户日志'
                },
                {
                    value: '2',
                    label: '菜单日志'
                },
                {
                    value: '3',
                    label: '角色日志'
                }
            ],
            page: {
                tableData: [],
                pageSize: 5,
                total: 0,
                currentPage: 1
            },
            queryInfo: {
                title: '',
                type: '',
                size: 0,
                current: 0
            }
        }
    },
    mounted() {
        this.flush()
    },
    filters: {
        castDate(val) {
            return castDate(val)
        }
    },
    methods: {
        ...mapActions('log', ['logPage', 'deleteLogs']),
        // 查看日志详情
        showLogDetail(row) {
            this.showLogDialog = true
            this.logDetail = JSON.parse(JSON.stringify(row))
        },
        // 清空日志
        async clearLogs() {
            const res = await this.deleteLogs()
            this.handleSearch()
        },
        //刷新页面
        async flush() {
            const res = await this.logPage({ size: this.page.pageSize })
            if (res.code === 0) {
                this.copyPageValue(res)
            }
        },
        // 修改每页显示条数
        async handleSizeChange(size) {
            let res = await this.logPage(this.copyQueryValue(this.queryInfo.title, this.queryInfo.type, size, this.page.currentPage))
            this.copyPageValue(res)
        },
        //修改当前第几页
        async handleCurrentChange(current) {
            let res = await this.logPage(this.copyQueryValue(this.queryInfo.title, this.queryInfo.type, this.page.pageSize, current))
            this.copyPageValue(res)
        },
        // 重置查询
        async resetQuery() {
            ;(this.queryInfo = {
                title: '',
                type: ''
            }),
                await this.handleSearch()
        },
        // 条件查询
        async handleSearch() {
            let res = await this.logPage(this.copyQueryValue(this.queryInfo.title, this.queryInfo.type, this.page.pageSize, ''))
            this.copyPageValue(res)
        },
        //格式化table日期格式
        formatDate(row, column) {
            return formatDate(row, column)
        },
        //封装查询条件
        copyQueryValue(title, type, size, current) {
            return {
                title: title ? title : null,
                type: type ? type : null,
                size: size ? size : null,
                current: current ? current : null
            }
        },
        //封装分页属性值
        copyPageValue(res) {
            this.page.tableData = res.data.records
            this.page.total = res.data.total
            this.page.pageSize = res.data.size
            this.page.currentPage = res.data.current
        }
    }
}
</script>

<style scoped lang="less">
.log_table {
    width: 100%;
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>
