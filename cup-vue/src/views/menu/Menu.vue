<template>
  <div>
    <!-- 卡片区 -->
    <el-card>
      <!--新增-->
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleMenuAdd"
        >新增</el-button
      >
      <!-- 表格区 -->
      <el-table
        size="mini"
        :data="tableData"
        stripe
        row-key="id"
      >
        <el-table-column
          align="center"
          type="index"
          label="序号"
          width="50"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="menuName"
          label="名称"
          width="180"
        ></el-table-column>
        <el-table-column align="center" prop="type" label="类型">
          <template v-slot="scope">
            <el-tag size="mini" type="success" v-if="scope.row.type == 0"
              >菜单</el-tag
            >
            <el-tag size="mini" type="info" v-else>按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="url"
          label="路由路径"
        ></el-table-column>
        <el-table-column align="center" prop="icon" label="图标">
          <template v-slot="scope">
            <i :class="scope.row.icon"></i>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="permission"
          label="权限标识"
        ></el-table-column>
        <el-table-column align="center" label="操作">
          <template v-slot="scope">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              @click="handleEditMenu(scope.row)"
              >修改</el-button
            >
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="handleDeleteMenu(scope.row.id)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/修改菜单对话框 -->
    <el-dialog
      :show-close="false"
      :title="title"
      :visible.sync="menuDialog"
      width="30%"
      @close="menuDialogClose"
    >
      <el-form size="mini" label-width="80px" :model="menuForm" ref="menuForm">
        <el-form-item label="名称" prop="menuName">
          <el-input v-model="menuForm.menuName"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio v-model="menuForm.type" label="0" border>菜单</el-radio>
          <el-radio v-model="menuForm.type" label="1" border>按钮</el-radio>
        </el-form-item>
        <el-form-item label="路由路径" prop="url" v-if="menuForm.type === '0'">
          <el-input v-model="menuForm.url"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon" v-if="menuForm.type === '0'">
          <el-input
            v-model="menuForm.icon"
            v-popover:iconPop
            :prefix-icon="menuForm.icon"
          ></el-input>
          <el-popover
            ref="iconPop"
            placement="bottom"
            title=""
            width="400"
            trigger="click"
          >
            <icon-select @selected="handleIconSelected"></icon-select>
          </el-popover>
        </el-form-item>
        <el-form-item
          label="权限标识"
          prop="permission"
          v-if="menuForm.type === '1'"
        >
          <el-input v-model="menuForm.permission"></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" prop="menuPid">
          <el-tree-select
            :elTreeProps="elTreeProps"
            :elTreeData="tableData"
            :defaultSelectedId="menuForm.menuPid"
            :disabled="false"
            @handleTreeSelected="handleTreeSelected($event)"
            @validateSelectTree="validateSelectTree"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="menuDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleMenuEditSubmit"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import IconSelect from "../../components/IconSelect.vue";
import ElTreeSelect from "@/components/TreeSelect";

export default {
  components: { IconSelect, ElTreeSelect },
  data() {
    return {
      title: "",
      tableData: [],
      menuDialog: false,
      menuForm: {
        type: "0",
        menuName: "",
        menuPid: 0,
        permission: "",
      },
      elTreeDisabled: false,
      elTreeProps: {
        // el-tree-select配置项（必选）
        value: "id",
        label: "menuName",
        children: "children",
      },
    };
  },
  mounted() {
    this.flush();
  },
  methods: {
    ...mapActions("menu", [
      "menuTreePage",
      "addMenu",
      "updateMenu",
      "deleteMenu",
    ]),
    // 图标选择
    handleIconSelected(name) {
      this.$set(this.menuForm, "icon", name);
    },

    // dialog关闭
    menuDialogClose() {
      console.log("dialog关闭");
      this.$set(this.menuForm, "menuPid", undefined);
      this.$refs.menuForm.resetFields();
    },

    // 新增按钮
    handleMenuAdd() {
      this.title = "新增菜单";
      this.menuDialog = true;
    },

    // 修改按钮
    handleEditMenu(row) {
      this.title = "修改菜单";
      this.menuDialog = true;
      const rowInfo = JSON.parse(JSON.stringify(row));
      this.menuForm = rowInfo;
      this.menuForm.menuPid = rowInfo.parentId;
    },

    // 提交表单
    async handleMenuEditSubmit() {
      if (this.menuForm.id === undefined) {
        const res = await this.addMenu(this.menuForm);
        this.menuDialog = false;
        if (res.code === 0) {
          this.$message.success("添加成功");
        } else {
          this.$message.error("添加失败");
        }
      } else {
        const res = await this.updateMenu(this.menuForm);
        this.menuDialog = false;
        if (res.code === 0) {
          this.$message.success("更新成功");
        }
      }
      await this.flush();
    },

    // 删除按钮
    handleDeleteMenu(menuId) {
      this.$confirm("此操作将永久删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          let res = await this.deleteMenu(menuId);
          if (res.code === 0) {
            this.flush();
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          }
        })
        .catch(() => {});
    },

    handleTreeSelected(value) {
      this.menuForm.menuPid = value;
      this.$refs.menuForm.validateField("menuPid");
    },
    validateSelectTree() {
      this.$refs.menuForm.validateField("menuPid");
    },

    // 刷新table
    async flush() {
      const res = await this.menuTreePage();
      this.tableData = res.data;
    },
  },
};
</script>

