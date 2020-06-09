<template>
  <el-card>
	  <!-- 查询区 -->
	  <el-row :gutter="20">
	    <el-col :span="6">
	      <el-input size="small" clearable v-model="queryInfo.title" placeholder="请输入要查询的日志标题" @change="handleSearch">
	      </el-input>
	    </el-col>
		<el-col :span="3">
			<el-select size="small" v-model="queryInfo.type" placeholder="请求选择">
				<el-option v-for="item in logTypes"
				:key="item.value"
				:label="item.label"
				:value="item.value"
				></el-option>
			</el-select>
		</el-col>
		<el-col :span="2">
		  <el-button type="primary" icon="el-icon-search" size="small" @click="handleSearch">查询</el-button>
		</el-col>
	  </el-row>
	  <!-- 表格 -->
	  <el-table :data="this.page.tableData" stripe border size="small" class="log_table">
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
		  <el-table-column label="请求时间(毫秒)" prop="time"></el-table-column>
		  <el-table-column label="请求人" prop="createBy"></el-table-column>
		  <el-table-column label="创建时间" prop="createTime" :formatter="formatDate"></el-table-column>
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
  </el-card>
</template>

<script>
import {mapActions} from 'vuex'
import moment from 'moment'

export default {
  data() {
    return {
		value:'',
		logTypes:[{
			value: '',
			label: '请选择'
		},{
			value: '1',
			label: '用户日志'
		},{
			value: '2',
			label: '菜单日志'
		},{
			value: '3',
			label: '角色日志'
		}],
		page:{
			tableData:[],
			pageSize: 10,
			total: 0,
			currentPage: 1,
		},
		queryInfo: {
		  title: '',
		  type: '',
		  size: 0,
		  current: 0
		},
	};
  },
  async mounted() {
	  const res  = await this.logPage({size:5})
	  if(res.code === 0){
		  this.copyPageValue(res)
	  }
  },
  methods: {
	  ...mapActions('log',['logPage']),
	  //修改每页显示条数
	  async handleSizeChange(size) {
	    this.queryInfo.size = size;
	    let res = await this.logPage(this.copyQueryValue(this.queryInfo.title,this.queryInfo.type,size,this.page.currentPage));
	    this.copyPageValue(res)
	  },
	  //修改当前第几页
	  async handleCurrentChange(current) {
	    let res = await this.logPage(this.copyQueryValue(this.queryInfo.title,this.queryInfo.type,this.page.pageSize,current));
	    this.copyPageValue(res)
	  },
	  //条件查询
	  async handleSearch() {
	    let res = await this.logPage(this.copyQueryValue(this.queryInfo.title,this.queryInfo.type,this.page.pageSize,''));
	    this.copyPageValue(res)
	  },
	  //格式化table日期格式
	  formatDate(row,column){
		  let date = row[column.property]
		  return moment(date).format("YYYY-MM-DD HH:mm:ss")
	  },
	  //封装查询条件
	  copyQueryValue(title,type,size,current){
		  return {
			  title: title?title:null,
			  type: type?type:null,
			  size: size?size:null,
			  current: current?current:null,
		  }
	  },
	  //封装分页属性值
	  copyPageValue(res){
		  this.page.tableData = res.data.records;
		  this.page.total = res.data.total;
		  this.page.pageSize = res.data.size;
		  this.page.currentPage = res.data.current;
	  }
  }
};
</script>

<style scoped lang="less">
	.log_table{
		width: 100%;
		margin-top: 10px;
	}
</style>
