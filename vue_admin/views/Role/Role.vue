<template>
    <div>
        <el-form :inline="true">
            <el-form-item>
                <el-input v-model="searchForm.name" placeholder="名称" clearable>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getRoleList">搜索</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit" v-if="hasAuthority('user:save')">新增</el-button>
            </el-form-item>
            <el-form-item>
                <el-popconfirm title="确定删除吗？" @confirm="deleteForm()">
                    <el-button slot="reference" type="danger" :disabled="deleteStatu"
                        v-if="hasAuthority('user:delete')">批量删除</el-button>
                </el-popconfirm>
            </el-form-item>
        </el-form>
        <el-table border ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="name" label="名称" width="120">
            </el-table-column>
            <el-table-column prop="code" label="唯一编码" width="300">
            </el-table-column>
            <el-table-column prop="remark" label="描述">
            </el-table-column>
            <el-table-column prop="statu" label="状态" width="120">
                <template slot-scope="scope">
                    <el-tag size="small" v-if="scope.row.statu === 1" type="success">正常</el-tag>
                    <el-tag size="small" v-if="scope.row.statu === 0" type="danger">禁用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button type="text" @click="pramHandle(scope.row.id)">分配权限</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-button type="text" @click="handleForm(scope.row.id)">编辑</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-popconfirm title="这是一段内容确定删除吗？" @confirm="deleteForm(scope.row.id)">
                        <el-button type="text" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="pagination.currentPage" :page-sizes="[10, 20, 30, 40]" :page-size="pagination.size"
            layout="total, sizes, prev, pager, next, jumper" :total="pagination.total">
        </el-pagination>


        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
            <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px">
                <el-form-item label="角色名称" prop="name">
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
                <el-form-item label="唯一编码" prop="code">
                    <el-input v-model="editForm.code"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="remark">
                    <el-input v-model="editForm.remark"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="statu">
                    <el-radio-group v-model="editForm.statu">
                        <el-radio :label=1>正常</el-radio>
                        <el-radio :label=0>禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('editForm')">确定</el-button>
                    <el-button @click="resetForm('editForm')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog title="分配权限" :visible.sync="pramDialogVisible" width="30%">
            <el-form :model="pramForm" label-width="100px">
                <el-tree :data="pramTreeData" ref="tree" show-checkbox :check-strictly="true" node-key="id"
                    :props="defaultProps">
                </el-tree>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="pramDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitPramForm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'Role',
    data() {
        return {
            dialogVisible: false,
            pramDialogVisible: false,
            //能否批量删除
            deleteStatu: true,
            searchForm: {
                name: ""
            },
            tableData: [],
            editForm: {},
            pramForm: {},
            rules: {
                name: [
                    { required: true, message: '请输入角色名称', trigger: 'blur' },
                ],
                code: [
                    { required: true, message: '请输入唯一编码', trigger: 'blur' },
                ],
                statu: [
                    { required: true, message: '请选择状态', trigger: 'change' }
                ],
            },
            multipleSelection: [],
            //分页数据
            pagination: {
                currentPage: 1,
                size: null,
                total: null
            },
            pramTreeData: [],
            defaultProps: {
                children: 'children',
                label: 'name'
            }
        }
    },
    created() {
        this.getRoleList()
        this.$axios.get(`/menu/list`)
            .then((res) => {
                this.pramTreeData = res.data.data
            })
    },
    methods: {
        onSubmit() {
            this.dialogVisible = true
        },
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.deleteStatu = val.length == 0
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pagination.size = val
            this.getRoleList()
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pagination.currentPage = val
            this.getRoleList()
        },
        //关闭对话框
        handleClose() {
            this.resetForm('editForm')
        },
        //获取表格数据
        getRoleList() {
            this.$axios.get(`/role/list`, {
                params: {
                    name: this.searchForm.name,
                    current: this.pagination.currentPage,
                    size: this.pagination.size
                }
            })
                .then((res) => {
                    this.tableData = res.data.data.records
                    this.pagination.currentPage = res.data.data.current
                    this.pagination.size = res.data.data.size
                    this.pagination.total = res.data.data.total
                })
        },
        //提交表单
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$axios.post(`/role/${this.editForm.id ? 'update' : 'add'}`, this.editForm)
                        .then((res) => {
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                            })
                            this.handleClose()
                            this.dialogVisible = false
                            this.getRoleList()
                        })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        //分配权限
        pramHandle(id) {
            this.pramDialogVisible = true
            this.$axios.get(`/role/info/${id}`)
                .then((res) => {
                    this.$refs.tree.setCheckedKeys(res.data.data.menuIds)
                    this.pramForm = res.data.data
                })
        },
        // 提交权限
        submitPramForm() {
            let menuIds = this.$refs.tree.getCheckedKeys()
            this.$axios.post(`/role/pram/${this.pramForm.id}`, menuIds)
                .then((res) => {
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    })
                    this.pramDialogVisible = false
                    this.getRoleList()
                })
        },
        //编辑
        handleForm(id) {
            this.$axios.get(`/role/info/${id}`)
                .then((res) => {
                    this.editForm = res.data.data
                    this.dialogVisible = true
                })
        },
        //删除
        deleteForm(id) {
            let ids = []
            if (id) {
                ids.push(id)
            } else {
                this.multipleSelection.forEach((row) => {
                    console.log(row);
                    ids.push(row.id)
                })
            }
            this.$axios.post(`/role/delete`, JSON.stringify(ids))
                .then((res) => {
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    })
                    this.getRoleList()
                })
        },
        //重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
            this.dialogVisible = false
            this.editForm = {}
        },
    }
}
</script>

<style lang="scss" scoped></style>