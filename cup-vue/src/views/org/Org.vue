<template>
    <el-card>
        <el-button size="mini" type="primary" icon="el-icon-plus">新增</el-button>
        <el-table :data="tableData" default-expand-all row-key="id" class="org_table" size="mini" border stripe :header-cell-style="{textAlign: 'center',background:'#F2F6FC'}">
             <el-table-column type="index" label="序号" width="50" align="center"></el-table-column>
             <el-table-column prop="orgName" label="组织机构名称" align="left"></el-table-column>
             <el-table-column prop="status" label="状态" width="200" align="center">
                 <template v-slot="scope">
                     <el-tag v-if="scope.row.status===1" size="mini" type="">有效</el-tag>
                     <el-tag v-if="scope.row.status===0" size="mini" type="danger">无效</el-tag>
                 </template>
             </el-table-column>
             <el-table-column label="操作" width="250" align="center">
                 <template v-slot="scope">
                     <el-button size="mini" icon="el-icon-edit" type="primary">修改</el-button>
                     <el-button size="mini" icon="el-icon-delete" type="danger">删除</el-button>
                 </template>
             </el-table-column>
        </el-table>
    </el-card>
</template>

<script>
    import {mapActions} from 'vuex'
    export default {
        data(){
            return {
                tableData:[]
            }
        },
        async mounted(){
            const res = await this.orgTree()
            console.log(res)
            this.tableData = res.data
        },
        methods:{
            ...mapActions('org',['orgTree'])
        }
    }
</script>

<style>
    .org_table{
        margin: 10px 0;
    }
</style>
