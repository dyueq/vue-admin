export default {
    state: {
        isCollapse: false,
        currentMenu: null,
        token: "",              //token
        menuList: [{
            path: "/home",
            name: "home",
            label: "首页",
            icon: "el-icon-s-home",
            component: "",
        }],           //导航栏信息
        authorities: [],      //权限
        hasRoute: false
    },
    mutations: {
        collapseMenu(state) {
            state.isCollapse = !state.isCollapse
        },
        selectMenu(state, val) {
            val.name === "home" ? (state.currentMenu = null) : (state.currentMenu = val)
        },
        //将token写入localStorage
        SET_TOKEN(state, token) {
            state.token = token
            localStorage.setItem("token", token)
        },
        //删除token信息
        resetState(state) {
            state.token = ""
        },
        //获取导航信息
        setMenuList(state, menuList) {
            menuList.forEach(e => {
                if (JSON.stringify(state.menuList).indexOf(JSON.stringify(e)) === -1) {
                    state.menuList.push(e)
                }
            });
            // state.menuList = menuList
        },
        //获取权限信息
        setAuthorities(state, authorities) {
            state.authorities = authorities
        },
        //改变路由信息
        changeRouteStates(state, hasRoute) {
            state.hasRoute = hasRoute
        }
    },
}
