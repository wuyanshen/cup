<template>
  <div>
    <el-card>
      <!-- 条件查询 -->
      <el-row :gutter="20">
        <el-col :span="2">
          <el-button icon="el-icon-plus" size="mini" type="primary" style="margin-bottom: 10px" @click="handlePreAdd">新增</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input size="mini" clearable placeholder="请假原因" v-model="queryForm.reason" @change="handleQuery"></el-input>
        </el-col>
        <el-col :span="2">
          <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">查询</el-button>
        </el-col>
      </el-row>
      <!-- 表格区 -->
      <el-table size="mini" :data="leaveBillList">
        <el-table-column align="center" label="id" prop="id"></el-table-column>
        <el-table-column label="原因" prop="reason"></el-table-column>
        <el-table-column label="状态" prop="status">
          <template v-slot="scope">
            <!-- (0待审核 -1驳回 -2不通过 1通过) -->
            <el-tag v-if="scope.row.status === '0'" type="info" size="mini">待审核</el-tag>
            <el-tag v-if="scope.row.status === '-1'" type="warning" size="mini">驳回</el-tag>
            <el-tag v-if="scope.row.status === '-2'" type="danger" size="mini">不通过</el-tag>
            <el-tag v-if="scope.row.status === '1'" type="success" size="mini">通过</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="开始时间" prop="startDate" :formatter="dateFormat"></el-table-column>
        <el-table-column label="结束时间" prop="endDate" :formatter="dateFormat"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="mini" icon="el-icon-edit" type="primary" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="mini" icon="el-icon-delete" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区 -->
      <el-pagination
        style="margin-top: 10px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="this.page.currentPage"
        :page-sizes="[1, 10, 15, 20]"
        :page-size="this.page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="this.page.total"
      ></el-pagination>
    </el-card>
    <!-- 新增/修改请假 -->
    <el-dialog :show-close="false" :title="title" :visible.sync="leaveShow" width="30%" @close="resetForm">
      <el-form size="mini" label-width="80px" ref="leaveBillForm" :model="leaveBillForm">
        <el-form-item label="请假原因" prop="reason">
          <el-input type="textarea" rows="6" v-model="leaveBillForm.reason"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="startDate">
          <el-date-picker v-model="leaveBillForm.startDate" placeholder=""></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endDate">
          <el-date-picker v-model="leaveBillForm.endDate" placeholder=""></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="leaveShow = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleLeaveBillSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
import { mapActions, mapState } from "vuex";
import moment from "moment";
export default {
  data() {
    return {
      leaveBillList: [],
      leaveShow: false,
      title: "",
      leaveBillForm: {
        startDate: "",
        endDate: "",
        reason: "",
      },
      queryForm: {
        reason: "",
      },
      page: {
        size: 5,
        current: 0,
        total: 0,
      },
    };
  },
  created() { },
  mounted() {
    this.getLeavePage();
  },
  computed: {
    ...mapState("user", ["userInfo"]),
  },
  methods: {
    ...mapActions("leave", [
      "leaveAdd",
      "leavePage",
      "leaveUpdate",
      "leaveDelete",
    ]),
    ...mapActions("user", ["userInfoAction"]),
    async handleLeaveBillSubmit() {
      if (this.leaveBillForm.id) {
        let res = await this.leaveUpdate(this.leaveBillForm);
        if (res.code === 0) {
          this.$message.success("修改请假成功");
          this.leaveShow = false;
          this.getLeavePage();
        }
      } else {
        console.log("从vuex中获取到的用户信息是：", this.userInfo);
        this.leaveBillForm.userId = this.userInfo.id;
        let res = await this.leaveAdd(this.leaveBillForm);
        if (res.code === 0) {
          this.$message.success("提交请假成功");
          this.leaveShow = false;
          this.getLeavePage();
        }
      }
    },
    // 新增按钮
    handlePreAdd() {
      this.leaveShow = true;
      this.title = "新增请假";
    },
    // 重置表单
    resetForm() {
      this.$refs.leaveBillForm.resetFields();
    },
    // 查询请假列表
    async getLeavePage() {
      let userInfo = this.userInfo;
      let params = Object.assign(
        { userId: userInfo.id },
        this.queryForm,
        this.page
      );
      let res = await this.leavePage(params);
      this.leaveBillList = res.data.records;
      this.page.total = res.data.total;
      this.page.current = res.data.current;
    },
    // 删除按钮
    async handleDelete(id) {
      this.$confirm("确认要删除该请假吗？", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          let res = await this.leaveDelete(id);
          if (res.code === 0) {
            this.$message.success("删除成功");
            this.getLeavePage();
          }
        })
        .catch((error) => { });
    },
    // 修改按钮
    handleUpdate(row) {
      this.title = "修改请假";
      this.leaveBillForm = JSON.parse(JSON.stringify(row));
      this.leaveShow = true;
    },
    // 格式化时间
    dateFormat(row, column, cellValue, index) {
      const date = row[column.property];
      if (date === undefined) {
        return "";
      }
      return moment(date).format("YYYY-MM-DD");
    },
    handleSizeChange(val) {
      this.page.size = val;
      this.getLeavePage();
    },
    handleCurrentChange(val) {
      this.page.current = val;
      this.getLeavePage();
    },
    // 查询按钮
    handleQuery() {
      this.getLeavePage();
    },
  },
};
</script>

<style scoped lang="less">
</style>
