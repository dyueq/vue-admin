<template>
    <div>
        <el-card :body-style="{ padding: '0px' }" class="firstCard">
            <img :src="userInfo.avatar" class="image">
            <div style="padding: 14px;">
                <h3>{{ userInfo.username }}</h3>
                <div class="bottom clearfix">
                    <time class="time">{{ currentDate }}</time>
                </div>
            </div>
        </el-card>
        <div class="box_middle"></div>
        <el-card class="box-card">
            <div slot="header" class="head">
                <p>基本信息</p>
            </div>
            <div v-for="(value, key) in userChineseKey" :key="key" class="text">
                <span class="span_key">{{ key }}</span>
                <span class="span_value">{{ value }}</span>
                <span class="span_middle"></span>
            </div>
        </el-card>
    </div>
</template>

<script>
import moment from 'moment';
export default {
    name: 'userCenter',
    data() {
        return {
            userInfo: {},
            userChineseKey: {},
            currentDate: moment(new Date()).format("YYYY-MM-DD"),
        }
    },
    created() {
        this.getUserInfo()
    },
    methods: {
        getUserInfo() {
            this.$axios.get(`/userInfo`)
                .then((res) => {
                    this.userInfo = res.data.data
                    this.userChineseKey["姓名"] = this.userInfo.username
                    this.userChineseKey["城市"] = this.userInfo.city
                    this.userChineseKey["邮箱"] = this.userInfo.email
                    this.userChineseKey["最后登录时间"] = moment(this.userInfo.lastLogin).format("YYYY-MM-DD")
                })
        },
    }
}
</script>

<style scoped>
.firstCard {
    margin-top: 10px;
}

p {
    margin: 0;
    font-size: larger;
    font-weight: 600;
}

.head {
    height: 20px;
}

.time {
    font-size: 13px;
    color: #999;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
    padding: 0;
    float: right;
}

.image {
    margin-left: 20px;
    margin-top: 20px;
    width: 100px;
    border-radius: 50%;
    display: block;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.box_middle {
    height: 20px;
}

.text {
    vertical-align: middle;
}

.span_key {
    display: inline-block;
    width: 100px;
}

.span_value {
    margin-left: 50px;

}

.span_middle {
    display: inline-block;
    height: 50px;
}
</style>