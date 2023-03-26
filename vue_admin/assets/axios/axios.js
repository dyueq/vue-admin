import axios from "axios";
import router from "@/router";
import Element from "element-ui";

const request = axios.create({
    baseURL: "/api",
    timeout: 5000,
    headers: {
        'Content-Type': "application/json; charset=utf-8"
    }
})
//axios请求拦截器：请求头有没有token
request.interceptors.request.use(config => {
    config.headers["Authorization"] = localStorage.getItem("token")
    return config
})
//axios反应拦截器：状态码是否等于200
request.interceptors.response.use(response => {
    if (response.data.code === 200) {
        return response
    } else {
        Element.Message.error(!response.data.msg ? '系统异常' : response.data.msg)
        return Promise.reject(response.data.msg)
    }
}, error => {
    if (error.response.data) {
        error.massage = error.response.data.msg
    }
    if (error.response.status === 401) {
        router.push('/')
    }
    Element.Message.error(error.massage, { duration: 3000 })
    return Promise.reject(error)
})
export default request

