<template>
    <div>
        <el-form :inline="true" :model="headerForm" class="demo-form-inline">
            <el-form-item>
                <el-button type="primary" @click="onSubmit" v-if="hasAuthority('user:save')">新增</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData" style="width: 100%;margin-bottom: 20px;" row-key="id" border default-expand-all
            :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
            <el-table-column prop="name" label="名称" sortable width="180">
            </el-table-column>
            <el-table-column prop="pram" label="权限编码" sortable width="180">
            </el-table-column>
            <el-table-column prop="icon" label="图标">
            </el-table-column>
            <el-table-column prop="type" label="类型">
                <template slot-scope="scope">
                    <el-tag size="small" v-if="scope.row.type === 0" type="success">目录</el-tag>
                    <el-tag size="small" v-if="scope.row.type === 1" type="normal">菜单</el-tag>
                    <el-tag size="small" v-if="scope.row.type === 2" type="info">按钮</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="path" label="菜单">
            </el-table-column>
            <el-table-column prop="component" label="菜单组件">
            </el-table-column>
            <el-table-column prop="orderNum" label="排序号">
            </el-table-column>
            <el-table-column prop="statu" label="状态">
                <template slot-scope="scope">
                    <el-tag size="small" v-if="scope.row.statu === 1" type="success">正常</el-tag>
                    <el-tag size="small" v-if="scope.row.statu === 0" type="danger">禁用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="text" @click="handleForm(scope.row.id)">编辑</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-popconfirm title="这是一段内容确定删除吗？" @confirm="deleteForm(scope.row.id)">
                        <el-button type="text" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="提示" :visible.sync="dialogVisible" width="50%" :before-close="handleClose">
            <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px">
                <el-form-item label="上级菜单" prop="parentId">
                    <el-select v-model="editForm.parentId" placeholder="请选择上级菜单" :disabled="editForm.parentId == 0">
                        <template v-for="item in tableData">
                            <el-option :label="item.name" :value="item.id"></el-option>
                            <template v-for="child in item.children">
                                <el-option :label="child.name" :value="child.id">
                                    <span>{{ `- ${child.name}` }}</span>
                                </el-option>
                            </template>
                        </template>
                    </el-select>
                </el-form-item>
                <el-form-item label="菜单名称" prop="name">
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
                <el-form-item label="权限编码" prop="pram">
                    <el-input v-model="editForm.pram"></el-input>
                </el-form-item>
                <el-form-item label="图标" prop="icon">
                    <el-input v-model="editForm.icon"></el-input>
                </el-form-item>
                <el-form-item label="菜单" prop="path">
                    <el-input v-model="editForm.path"></el-input>
                </el-form-item>
                <el-form-item label="菜单组件" prop="component">
                    <el-input v-model="editForm.component"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="type">
                    <el-radio-group v-model="editForm.type">
                        <el-radio :label=0>目录</el-radio>
                        <el-radio :label=1>菜单</el-radio>
                        <el-radio :label=2>按钮</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="状态" prop="statu">
                    <el-radio-group v-model="editForm.statu">
                        <el-radio :label=1>正常</el-radio>
                        <el-radio :label=0>禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序号" prop="orderNum">
                    <el-input-number v-model="editForm.orderNum" :min=1 label="排序号">1</el-input-number>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('editForm')">确定</el-button>
                    <el-button @click="resetForm('editForm')">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'Menu',
    data() {
        return {
            dialogVisible: false,
            headerForm: {},
            tableData: [],
            editForm: {},
            rules: {
                parentId: [
                    { required: true, message: '请选择上级菜单', trigger: 'change' }
                ],
                name: [
                    { required: true, message: '请输入菜单名称', trigger: 'blur' },
                ],
                pram: [
                    { required: true, message: '请输入权限编码', trigger: 'blur' },
                ],
                type: [
                    { required: true, message: '请选择类型', trigger: 'change' }
                ],
                statu: [
                    { required: true, message: '请选择状态', trigger: 'change' }
                ],
                orderNum: [
                    { required: true, message: '请填写排序号', trigger: 'blur' }
                ]
            }
        }
    },
    created() {
        this.getMenuTree()
    },
    methods: {
        //打开对话框
        onSubmit() {
            this.dialogVisible = true
        },
        //关闭对话框
        handleClose() {
            this.resetForm('editForm')
        },
        getMenuTree() {
            this.$axios.get(`/menu/list`)
                .then((res) => {
                    this.tableData = res.data.data
                })
        },
        //提交表单
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    // this.editForm.id ? this.$axios.put(`/menu/update`,this.editForm) : this.$axios.post(`/menu/add`,this.editForm)
                    this.$axios.post(`/menu/${this.editForm.id ? 'update' : 'add'}`, this.editForm)
                        .then((res) => {
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                            })
                            this.dialogVisible = false
                            this.getMenuTree()
                        })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        //编辑
        handleForm(id) {
            this.$axios.get(`/menu/info/${id}`)
                .then((res) => {
                    this.editForm = res.data.data
                    this.dialogVisible = true
                })
        },
        //删除
        deleteForm(id) {
            this.$axios.delete(`/menu/delete/${id}`)
                .then((res) => {
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    })
                })
            this.getMenuTree()
        },
        //重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
            this.dialogVisible = false
            this.editForm = {}
        },
    },

}
</script>

<style lang="scss" scoped></style>