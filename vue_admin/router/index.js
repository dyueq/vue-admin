import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Login from "../views/Login.vue";
import axios from 'axios';
import store from '@/store';

const originPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Main',
    component: Main,
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('@/views/Home/Home')
      },
      {
        path: '/userCenter',
        name: 'userCenter',
        component: () => import('@/views/userCenter/userCenter')
      },
    ],
  },
  {
    path: '/',
    name: 'login',
    component: Login
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//路由拦截
router.beforeEach((to, from, next) => {

  let hasRoute = store.state.tab.hasRoute
  if (!hasRoute) {
    //获取路由信息
    axios.get(`/api/menu/nav`, {
      headers: {
        Authorization: localStorage.getItem('token')
      }
    })
      .then((res) => {
        //拿到menuList
        store.commit("setMenuList", res.data.data.nav)
        //拿到用户权限
        store.commit("setAuthorities", res.data.data.authorities)
        //动态绑定路由
        let newRoutes = router.options.routes

        res.data.data.nav.forEach((menu) => {
          if (menu.children) {
            menu.children.forEach((e) => {
              //动态转换成路由
              let route = menuToRoute(e)
              //把路由添加到路由管理
              if (route) {
                newRoutes[0].children.push(route)
              }
            })
          }
        })
        router.addRoutes(newRoutes)
        hasRoute = true
        store.commit("changeRouteStates", hasRoute)
      })
  }
  next()
})

//导航信息动态转换成路由
const menuToRoute = (menu) => {
  if (!menu.component) {
    return null
  }
  let route = {
    name: menu.name,
    path: menu.path,
    meta: {
      icon: menu.icon,
      lable: menu.lable
    }
  }
  route.component = () => import(`@/views${menu.component}${menu.component}`)
  return route
}

export default router
