package com.sb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * author: dyq
 * Time: 2023/3/21
 * description: 描述
 */

public class BaseController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 获取页码
     * @return
     */
    public Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);
        return new Page(current,size);
    }
}
