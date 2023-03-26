package com.sb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sb.bean.Menu;
import com.sb.bean.Role;
import com.sb.bean.User;
import com.sb.mapper.UserMapper;
import com.sb.service.MenuService;
import com.sb.service.RoleService;
import com.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public User getUserByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        User user = userMapper.selectById(userId);
        String autnority = "";

        if (redisTemplate.hasKey("GrantedAuthority:"+ user.getUsername())){
            autnority = (String) redisTemplate.opsForValue().get("GrantedAuthority:"+ user.getUsername());
        }else{
            //ROLE_admin，user:list,......
            //获取角色编码
            List<Role> roles = roleService.list(new QueryWrapper<Role>().inSql("id", "select role_id from user_role where user_id = " + userId));
            if (roles.size() > 0){
                String roleList = roles.stream().map(role -> "ROLE_" + role.getCode()).collect(Collectors.joining(","));
                autnority = roleList.concat(",");
            }

            //获取菜单操作编码
            List<Long> menuId = userMapper.getNavMenuIds(userId);
            if (menuId.size() > 0){
                List<Menu> menus = menuService.listByIds(menuId);
                String menuPram = menus.stream().map(menu -> menu.getPram()).collect(Collectors.joining(","));

                autnority = autnority.concat(menuPram);
            }
            redisTemplate.opsForValue().set("GrantedAuthority:"+ user.getUsername(),autnority,3600, TimeUnit.SECONDS);
        }

        return autnority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisTemplate.delete("GrantedAuthority:"+ username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<User> userList = this.list(new QueryWrapper<User>().inSql("id", "select user_id from user_role where role_id = " + roleId));
        userList.forEach(user -> {
            this.clearUserAuthorityInfo(user.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<User> userList = userMapper.userListByMenuId(menuId);
        userList.forEach(user -> {
            this.clearUserAuthorityInfo(user.getUsername());
        });
    }
}
