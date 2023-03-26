package com.sb.config.security;

import com.sb.bean.User;
import com.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/20
 * description: 描述
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户名或密码不正确");
        }

        return new AccountUser(user.getId(),user.getUsername(),user.getPassword(),getUserAuthority(user.getId()));
    }

    /**
     * 获取用户权限信息（角色，菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId){
        //角色菜单操作权限
        String authority = userService.getUserAuthorityInfo(userId);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
