package com.sb.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sb.bean.Menu;
import com.sb.bean.RoleMenu;
import com.sb.bean.User;
import com.sb.bean.dto.MenuDto;
import com.sb.config.common.GlobalResult;
import com.sb.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/21
 * description: 描述
 */
@RestController
public class MenuController extends BaseController{


    /**
     * 获取当前用户导航信息
     * @param principal
     * @return
     */
    @GetMapping("/menu/nav")
    public GlobalResult getNav(Principal principal){
        User user = userService.getUserByUsername(principal.getName());

        //获取权限信息
        String userAuthority = userService.getUserAuthorityInfo(user.getId());
        String[] userAuthorityArray = StringUtils.tokenizeToStringArray(userAuthority, ",");
        //获取导航栏
        List<MenuDto> menuList = menuService.getCurrentUserMenu();
        //获取权限信息
        return GlobalResult.success(MapUtil.builder()
                .put("authorities",userAuthorityArray)
                .put("nav",menuList)
                .map()
        );
    }

    /**
     * 根据id查找菜单信息
     * @param id 菜单id
     * @return
     */
    @PreAuthorize("hasAuthority('menu:list')")
    @GetMapping("/menu/info/{id}")
    public GlobalResult getInfo(@PathVariable(name = "id") Long id){
        return GlobalResult.success(menuService.getById(id));
    }

    /**
     * 查找全部菜单信息
     * @return
     */
    @PreAuthorize("hasAuthority('menu:list')")
    @GetMapping("/menu/list")
    public GlobalResult list(){
        List<Menu> menuList = menuService.menuTree();
        return GlobalResult.success(menuList);
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @PreAuthorize("hasAuthority('menu:save')")
    @PostMapping("/menu/add")
    public GlobalResult save(@Validated @RequestBody Menu menu){
        menu.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        menuService.save(menu);
        return GlobalResult.success(menu);
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PreAuthorize("hasAuthority('menu:update')")
    @PostMapping("/menu/update")
    public GlobalResult update(@Validated @RequestBody Menu menu){
        menu.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        menuService.updateById(menu);
        //清楚所有与该菜单相关的全部缓存
        userService.clearUserAuthorityInfoByMenuId(menu.getId());
        return GlobalResult.success(menu);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('menu:delete')")
    @DeleteMapping("/menu/delete/{id}")
    @Transactional
    public GlobalResult delete(@PathVariable("id") Long id){
        int count = menuService.count(new QueryWrapper<Menu>().eq("parent_id", id));
        if (count > 0){
            return GlobalResult.fail("该菜单含有子菜单，无法删除");
        }
        //清楚所有与该菜单相关的全部缓存
        userService.clearUserAuthorityInfoByMenuId(id);
        //删除该菜单
        menuService.removeById(id);
        //删除关联表与此菜单相关的信息
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("menu_id",id));
        return GlobalResult.success("");
    }
}

