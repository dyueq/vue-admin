package com.sb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sb.bean.Role;
import com.sb.mapper.RoleMapper;
import com.sb.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<Role> listRolesByUserId(Long userId) {

        return this.list(new QueryWrapper<Role>().inSql("id", "select role_id from user_role where user_id = " + userId));
    }
}