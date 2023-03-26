import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import store from './store'
import axios from "./assets/axios/axios";
import globle from "./assets/config/globle"


axios.defaults.baseURL = '/api'
Vue.prototype.$axios = axios

Vue.config.productionTip = false
Vue.use(ElementUI);

// require('./assets/mock/mock') ----- (前后联调时前端修改了一些数据，mock数据没用就没改)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
