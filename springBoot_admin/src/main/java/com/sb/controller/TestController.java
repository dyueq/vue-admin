package com.sb.controller;

import com.sb.config.common.GlobalResult;
import com.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
@RestController
public class TestController {
    @Autowired
    private UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/test")
    public GlobalResult test(){
        return GlobalResult.success(userService.list(null));
    }

    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping("/test/pass")
    public GlobalResult testPass(){
        //加密后密码
        String password = bCryptPasswordEncoder.encode("123");

        boolean matches = bCryptPasswordEncoder.matches("123",password);

        System.out.println("匹配结果" + matches);
        return GlobalResult.success(password);
    }
}
