package com.sb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sb.bean.User;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
public interface UserService extends IService<User> {
    User getUserByUsername(String username);

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);
}
