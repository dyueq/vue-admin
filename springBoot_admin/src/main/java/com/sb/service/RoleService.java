package com.sb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sb.bean.Role;

import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
public interface RoleService extends IService<Role> {
    List<Role> listRolesByUserId(Long id);

}
