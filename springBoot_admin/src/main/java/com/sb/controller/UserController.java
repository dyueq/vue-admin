package com.sb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sb.bean.Role;
import com.sb.bean.User;
import com.sb.bean.UserRole;
import com.sb.config.common.Common;
import com.sb.config.common.GlobalResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/21
 * description: 描述
 */
@RestController
public class UserController extends BaseController{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/user/info/{id}")
    public GlobalResult getInfo(@PathVariable("id") Long id){
        User user = userService.getById(id);
        //获取用户的角色
        List<Role> roleList = roleService.listRolesByUserId(id);
        user.setRoleList(roleList);
        return GlobalResult.success(user);
    }
    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/user/list")
    public GlobalResult getUserList(String username){
        Page<User> pageData = userService.page(getPage(), new QueryWrapper<User>().like(StrUtil.isNotBlank(username), "username", username));
        pageData.getRecords().forEach(user -> user.setRoleList(roleService.listRolesByUserId(user.getId())));
        return GlobalResult.success(pageData);
    }
    @PreAuthorize("hasAuthority('user:role')")
    @PostMapping("/user/add")
    public GlobalResult addUser(@Validated @RequestBody User user){
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setStatu(Common.STATU_ON);
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userService.save(user);
        return GlobalResult.success(user);
    }
    @PreAuthorize("hasAuthority('user:update')")
    @PostMapping("/user/update")
    public GlobalResult updateUser(@Validated @RequestBody User user){
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        userService.updateById(user);
        return GlobalResult.success(user);
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @Transactional
    @PostMapping("/user/delete")
    public GlobalResult deleteUser(@RequestBody List<Long> ids){
        userService.removeByIds(ids);
        //删除中间表相关信息
        userRoleService.remove(new QueryWrapper<UserRole>().in("user_id",ids));

        return GlobalResult.success("");
    }
    @PreAuthorize("hasAuthority('user:role')")
    @Transactional
    @PostMapping("/user/pram/{userId}")
    public GlobalResult rolePram(@PathVariable("userId") Long userId,@RequestBody List<Long> ids){
        List<UserRole> userRoleList = new ArrayList<>();

        ids.forEach(id -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(id);
            userRole.setUserId(userId);
            userRoleList.add(userRole);
        });
        //删除之前相关的记录
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id",userId));
        //保存当前的记录
        userRoleService.saveBatch(userRoleList);
        //删除缓存
        User user = userService.getById(userId);
        userService.clearUserAuthorityInfo(user.getUsername());

        return GlobalResult.success("");
    }
}
