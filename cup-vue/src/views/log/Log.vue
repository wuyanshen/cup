<template>
  <el-card>
	  <!-- 查询区 -->
	  <el-row :gutter="20">
	    <el-col :span="6">
	      <el-input size="small" clearable v-model="queryInfo.title" placeholder="请输入要查询的日志标题">
	        <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
	      </el-input>
	    </el-col>
	  </el-row>
	  <!-- 表格 -->
	  <el-table :data="tableData" stripe border size="small">
		  <el-table-column type="index" label="序号" width="50"></el-table-column>
		  <el-table-column label="标题" prop="title"></el-table-column>
		  <el-table-column label="日志类型" prop="type">
			  <template v-slot="scope">
			    <el-tag v-if="scope.row.type==='1'" type="success">用户日志</el-tag>
				<el-tag v-if="scope.row.type==='2'" type="success">菜单日志</el-tag>
				<el-tag v-if="scope.row.type==='3'" type="success">角色日志</el-tag>
			  </template>
		  </el-table-column>
		  <el-table-column label="ip地址" prop="ip"></el-table-column>
		  <el-table-column label="请求uri" prop="requestUri"></el-table-column>
		  <el-table-column label="请求参数" prop="params"></el-table-column>
		  <el-table-column label="请求响应" prop="response" :show-overflow-tooltip="true"></el-table-column>
		  <el-table-column label="请求方式" prop="method"></el-table-column>
		  <el-table-column label="请求时间" prop="time"></el-table-column>
		  <el-table-column label="请求人" prop="createBy"></el-table-column>
		  <el-table-column label="创建时间" prop="createTime"></el-table-column>
	  </el-table>
	  <!-- 分页区 -->
	  <el-pagination
	    @size-change="handleSizeChange"
	    @current-change="handleCurrentChange"
	    :current-page="currentPage"
	    :page-sizes="[5, 10, 15, 20]"
	    :page-size="pageSize"
	    layout="total, sizes, prev, pager, next, jumper"
	    :total="total"
	  ></el-pagination>
  </el-card>
</template>

<script>
import {mapActions} from 'vuex'
export default {
  data() {
    return {
		tableData:[],
		pageSize: 10,
		total: 0,
		currentPage: 1,
		queryInfo: {
		  title: "",
		  type: ""
		},
	};
  },
  created() {},
  async mounted() {
	  const res  = await this.logPage()
	  if(res.code === 0){
		  this.tableData = res.data.records
		  this.total = res.data.total;
		  this.pageSize = res.data.size;
		  this.currentPage = res.data.current;
	  }
  },
  methods: {
	  ...mapActions('log',['logPage']),
	  //修改每页显示条数
	  async handleSizeChange(size) {
	    this.queryInfo.size = size;
	    let res = await this.logPage(this.queryInfo);
	    this.tableData = res.data.records;
	  },
	  //修改当前第几页
	  async handleCurrentChange(current) {
	    this.queryInfo.current = current;
	    let res = await this.logPage(this.queryInfo);
	    this.tableData = res.data.records;
	  },
	  async handleSearch() {
	    let res = await this.logPage(this.queryInfo);
	    this.tableData = res.data.records;
		this.total = res.data.total;
		this.pageSize = res.data.size;
		this.currentPage = res.data.current;
	  },
  }
};
</script>

<style scoped lang="less">
</style>
