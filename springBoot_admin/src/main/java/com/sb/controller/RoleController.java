package com.sb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sb.bean.Role;
import com.sb.bean.RoleMenu;
import com.sb.bean.UserRole;
import com.sb.config.common.Common;
import com.sb.config.common.GlobalResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: dyq
 * Time: 2023/3/21
 * description: 描述
 */
@RestController
public class RoleController extends BaseController{
    /**
     * 查找当前id的角色信息
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('role:list')")
    @GetMapping("/role/info/{id}")
    public GlobalResult roleInfo(@PathVariable("id") Long id){
        Role role = roleService.getById(id);
        List<RoleMenu> roleMenus = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", id));
        //获取角色相关的菜单id
        List<Long> menuIds = roleMenus.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList());
        role.setMenuIds(menuIds);

        return GlobalResult.success(role);
    }

    /**
     * 查找全部角色信息
     * @return
     */
    @PreAuthorize("hasAuthority('role:list')")
    @GetMapping("/role/list")
    public GlobalResult roleList(String name){
        Page<Role> page = roleService.page(getPage(), new QueryWrapper<Role>().like(StrUtil.isNotBlank(name), "name", name));
        return GlobalResult.success(page);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PreAuthorize("hasAuthority('role:save')")
    @PostMapping("/role/add")
    public GlobalResult save(@Validated @RequestBody Role role){
        role.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        role.setStatu(Common.STATU_ON);
        roleService.save(role);
        return GlobalResult.success(role);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PreAuthorize("hasAuthority('role:update')")
    @PostMapping("/role/update")
    public GlobalResult update(@Validated @RequestBody Role role){
        role.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        roleService.updateById(role);
        //更新缓存
        userService.clearUserAuthorityInfoByRoleId(role.getId());
        return GlobalResult.success(role);
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @PreAuthorize("hasAuthority('role:delete')")
    @PostMapping("/role/delete")
    @Transactional
    public GlobalResult delete(@RequestBody List<Long> ids){

        roleService.removeByIds(ids);
        //删除中间表
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("role_id",ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("role_id",ids));
        //缓存同步删除
        ids.forEach(id -> {
            userService.clearUserAuthorityInfoByRoleId(id);
        });
        return GlobalResult.success("");
    }

    /**
     * 修改权限
     * @param roleId
     * @param menuIds
     * @return
     */
    @PreAuthorize("hasAuthority('role:pram')")
    @PostMapping("/role/pram/{roleId}")
    @Transactional
    public GlobalResult delete(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds){
        List<RoleMenu> roleMenuList = new ArrayList<>();
        Arrays.asList(menuIds).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenuList.add(roleMenu);
        });
        //先删除与该角色关联的原来的记录，再保存新的记录
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        roleMenuService.saveBatch(roleMenuList);
        //删除缓存
        userService.clearUserAuthorityInfoByRoleId(roleId);
        return GlobalResult.success(menuIds);
    }
}
