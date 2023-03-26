package com.sb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sb.bean.UserRole;
import com.sb.mapper.UserRoleMapper;
import com.sb.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
