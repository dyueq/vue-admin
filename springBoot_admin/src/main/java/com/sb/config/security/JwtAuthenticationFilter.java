package com.sb.config.security;

import cn.hutool.core.util.StrUtil;
import com.sb.bean.User;
import com.sb.service.UserService;
import com.sb.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: dyq
 * Time: 2023/3/20
 * description: 描述
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserService userService;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtil.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)){
            chain.doFilter(request,response);
            return;
        }
        Claims claims = jwtUtil.getClaimByToken(jwt);
        if(claims == null){
            throw new JwtException("token异常");
        }
        if (jwtUtil.isTokenExpired(claims)){
            throw new JwtException("token已过期");
        }
        String username = claims.getSubject();
        //获取用户的权限等信息

        User user = userService.getUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,null,userDetailsService.getUserAuthority(user.getId()));

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request,response);
    }
}
