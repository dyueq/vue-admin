const Mock = require("mockjs");


const Random = Mock.Random;
let Result = {
    code: 200,
    data: null,
    msg: '操作成功'
}
Mock.mock('/captcha', 'get', () => {
    Result.data = {
        token: Random.string(32),
        captchaImg: Random.dataImage('100x40', 'p7n5w')
    }
    return Result
})

Mock.mock('/login', 'post', () => {

    // Result.code = 500,
    // Result.msg = "验证码错误"
    //mock无法在headers中传入参数jwt
    return Result
})
Mock.mock('/userInfo', 'get', () => {
    Result.data = {
        id: 1,
        userName: '123',
        avatar: require("../img/atm03.jpg")
    }
    return Result
})
Mock.mock('/logOut', 'post', () => {
    return Result
})

Mock.mock('/menu/nav', 'get', () => {
    let menuList = [
        {
            path: "/home",
            name: "home",
            lable: "首页",
            icon: "s-home",
            component: "",
        },
        {
            lable: "系统管理",
            icon: "s-operation",
            component: "",
            children: [
                {
                    path: "/user",
                    name: "user",
                    lable: "用户管理",
                    icon: "user",
                    component: "/User/User",
                },
                {
                    path: "/role",
                    name: "role",
                    lable: "角色管理",
                    icon: "rank",
                    component: "/Role/Role",
                },
                {
                    path: "/menu",
                    name: "menu",
                    lable: "菜单管理",
                    icon: "menu",
                    component: "/Menu/Menu",
                },
            ]
        },
        {
            lable: "系统工具",
            icon: "s-tools",
            component: "",
            children: [
                {
                    path: "/game",
                    name: "game",
                    lable: "数字字典",
                    icon: "s-order",
                    component: "/Game/Game",
                },
            ],
        },
    ]

    let authorities = ['user:add', 'user:update', 'user:delete']
    Result.data = {

        menuList: menuList,
        authorities: authorities
    }
    return Result
})

Mock.mock('/menu/list', 'get', () => {
    let tableData = [
        {
            id: 1,
            tableName: '2016',
            tablePram: '王小虎',
            tableIcon: '上海市普陀区金沙江路 1518 弄',
            tableType: 0,
            tablePath: '',
            tableComponent: '',
            tableOrder: '',
            tableStatu: 1,
            children: []
        }, {
            id: 2,
            tableName: '2016',
            tablePram: '王小虎',
            tableIcon: '上海市普陀区金沙江路 1518 弄',
            tableType: 0,
            tablePath: '',
            tableComponent: '',
            tableOrder: '',
            tableStatu: 1,
            children: []
        }, {
            id: 3,
            tableName: '2016',
            tablePram: '王小虎',
            tableIcon: '上海市普陀区金沙江路 1518 弄',
            tableType: 0,
            tablePath: '',
            tableComponent: '',
            tableOrder: '',
            tableStatu: 1,
            children: [{
                id: 31,
                tableName: '20162',
                tablePram: '王小虎',
                tableIcon: '上海市普陀区金沙江路 1518 弄',
                tableType: 1,
                tablePath: '',
                tableComponent: '',
                tableOrder: '',
                tableStatu: 0
            }, {
                id: 32,
                tableName: '20162',
                tablePram: '王小虎',
                tableIcon: '上海市普陀区金沙江路 1518 弄',
                tableType: 2,
                tablePath: '',
                tableComponent: '',
                tableOrder: '',
                tableStatu: 0
            }]
        }
    ]
    Result.data = {
        tableData: tableData
    }
    return Result
})
Mock.mock(RegExp('/role/list*'), 'get', () => {
    let roleList = [{
        id: 1,
        name: '普通用户',
        code: 'normal',
        remark: '上海市普陀区金沙江路 1518 弄',
        statu: 1
    }, {
        id: 2,
        name: '管理员',
        code: 'admin',
        remark: '上海市普陀区金沙江路 1518 弄',
        statu: 1
    }, {
        id: 3,
        name: '王小虎',
        code: '',
        remark: '上海市普陀区金沙江路 1518 弄',
        statu: 0
    }, {
        id: 4,
        name: '王小虎',
        code: '',
        remark: '上海市普陀区金沙江路 1518 弄',
        statu: 0
    }]
    Result.data = {
        roleList: roleList,
        page: {
            size: 10,
            currentPage: 1,
            total: 4,
        }
    }
    return Result
})
Mock.mock(RegExp('/user/list*'), 'get', () => {
    let userList = [{
        id: 1,
        userImg: require('@/assets/img/atm03.jpg'),
        userName: '普通用户',
        roleName: ['用户'],
        email: '',
        phone: '',
        userStatu: 1
    },
    {
        id: 2,
        userImg: require('@/assets/img/atm03.jpg'),
        userName: '管理员',
        roleName: ['用户', '管理员'],
        email: '',
        phone: '',
        userStatu: 1
    }]
    Result.data = {
        userList: userList,
        page: {
            size: 10,
            currentPage: 1,
            total: 4,
        }
    }
    return Result
})

Mock.mock('/menu/info/1', 'get', () => {
    Result.data = {
        id: 1,
        parentId: 1,
        tableName: '2016',
        tablePram: '王小虎',
        tableIcon: '上海市普陀区金沙江路 1518 弄',
        tableType: 0,
        tablePath: '',
        tableComponent: '',
        tableOrder: '',
        tableStatu: 1,
        children: []
    }
    return Result
})
Mock.mock('/menu/add', 'post', () => {
    return Result
})
Mock.mock('/menu/update', 'post', () => {
    return Result
})
Mock.mock('/menu/delete/1', 'post', () => {
    return Result
})

Mock.mock('/role/info/1', 'get', () => {
    Result.data = {
        id: 1,
        name: '普通用户',
        code: 'normal',
        remark: '上海市普陀区金沙江路 1518 弄',
        statu: 1,
        menuIds: [1, 3]
    }
    return Result
})
Mock.mock('/role/add', 'post', () => {
    return Result
})
Mock.mock('/role/update', 'post', () => {
    return Result
})
Mock.mock('/role/delete', 'post', () => {
    return Result
})
Mock.mock('/role/pram/1', 'post', () => {
    return Result
})

Mock.mock('/user/info/1', 'get', () => {
    Result.data = {
        id: 1,
        userImg: '',
        userName: '普通用户',
        roleName: 1,
        email: '',
        phone: '',
        userStatu: 1,
        menuIds: [1, 3]
    }
    return Result
})
Mock.mock('/user/add', 'post', () => {
    return Result
})
Mock.mock('/user/update', 'post', () => {
    return Result
})
Mock.mock('/user/delete', 'post', () => {
    return Result
})
Mock.mock('/user/pram/1', 'post', () => {
    return Result
})

