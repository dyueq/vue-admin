<template>
    <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="6">
            <h2>欢迎来到后台管理系统</h2>
            <el-image :src="require('../assets/img/atm03.jpg')"></el-image>
        </el-col>
        <el-col :span="1">
            <el-divider direction="vertical"></el-divider>
        </el-col>
        <el-col :span="6">
            <el-form :model="form" :rules="rules" label-width="100px" ref="form">
                <el-form-item label="用户名" prop="username" style="width: 380px;">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" style="width: 380px;">
                    <el-input v-model="form.password" autocomplete="off" type="password"></el-input>
                </el-form-item>
                <el-form-item label="验证码" prop="code" style="width: 380px;">
                    <el-input v-model="form.code" autocomplete="off" style="width:170px;float: left;"></el-input>
                    <el-image class="codeImg" :title="msg" :src="captchaImg" @click="getCaptcha">{{ msg }}</el-image>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('form')">登录</el-button>
                    <el-button @click="resetForm('form')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script>
import QueryString from 'qs';

export default {
    name: 'Login',
    data() {
        return {
            labelWidth: "180px",
            msg: '看不清，换一张',
            form: {
                username: 'admin',
                password: '123',
                code: '',
                token: ''
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                code: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                ],
            },
            //验证码图片
            captchaImg: require('@/assets/img/atm03.jpg')
        }
    },
    created() {
        this.getCaptcha()
    },
    methods: {
        //提交表单
        submitForm(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.$axios.post(`/login?${QueryString.stringify(this.form)}`)
                        .then((res) => {
                            const jwt = res.headers['authorization']
                            //把jwt保存到token
                            this.$store.commit('SET_TOKEN', jwt)
                            this.$router.push("/home")

                        })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        //重置表单
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        //获取验证码图片
        getCaptcha() {
            this.$axios.get(`/captcha`)
                .then((res) => {
                    this.form.token = res.data.data.token
                    this.captchaImg = res.data.data.captchaImg
                    this.form.code = ''
                })
        },
    },

}
</script>

<style scoped>
.el-row {
    background-color: #fafafa;
    height: 100%;
    display: flex;
    align-items: center;
    text-align: center;
}

.el-divider {
    height: 200px;
}

.codeImg {
    float: left;
    margin-left: 8px;
    border-radius: 4px;
    cursor: pointer;
}
</style>