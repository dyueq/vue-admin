<template>
    <header>
        <div class="l_content">
            <el-button plain icon="el-icon-menu" size="mini" @click="handleMenu"></el-button>
            <el-breadcrumb>
                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="current.path" v-if="current">{{ current.label }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="r_content">
            <el-dropdown trigger="click" szie="mini">
                <span class="el_dropdown_link">
                    <img :src="userInfo.avatar" />
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="toUserCenter">个人中心</el-dropdown-item>
                    <el-dropdown-item @click.native="logOut">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </header>
</template>
  
<script>
import { mapState } from "vuex";
export default {
    name: "CommonHeader",
    data() {
        return {
            userInfo: {
                id: '',
                userName: '',
                avatar: ''
            }
        };
    },
    created() {
        this.getUserInfo()
    },
    methods: {
        handleMenu() {
            this.$store.commit("collapseMenu");
        },
        getUserInfo() {
            this.$axios.get(`/userInfo`)
                .then((res) => {
                    this.userInfo = res.data.data
                })
        },
        toUserCenter() {
            this.$router.push({ name: 'userCenter' })
        },
        logOut() {
            localStorage.clear()
            sessionStorage.clear()
            this.$store.commit('resetState')
            this.$router.push("/")
        }
    },
    computed: {
        ...mapState({
            current: state => state.tab.currentMenu
        })
    }
};
</script>
  
<style  scoped>
header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
}

.l_content {
    display: flex;
    align-items: center;
}

.el-button {
    margin-right: 15px;
}

img {
    cursor: pointer;
    margin-right: 20px;
    width: 50px;
    border-radius: 50%;
}
</style>
<style >
.el-breadcrumb__item .el-breadcrumb__inner {
    color: white !important;
    font-weight: normal !important;
    cursor: pointer !important;
}
</style>