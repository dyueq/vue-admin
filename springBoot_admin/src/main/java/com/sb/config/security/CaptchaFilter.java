package com.sb.config.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sb.config.common.Common;
import com.sb.config.exception.CaptchaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.hutool.extra.validation.ValidationUtil.validate;

/**
 * author: dyq
 * Time: 2023/3/19
 * description: 描述
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if ("/login".equals(url)&&request.getMethod().equals("POST")){
            //校验验证码
            try {
                validate(request);
            } catch (CaptchaException e) {
                //如果不正确就跳转到认证失败处理器
                loginFailureHandler.onAuthenticationFailure(request,response,e);
            }
        }
        filterChain.doFilter(request,response);
    }

    //校验验证码逻辑
    private void validate(HttpServletRequest request) {
        String code = request.getParameter("code");
        String token = request.getParameter("token");

        if (StringUtils.isBlank(code) || StringUtils.isBlank(token)){
            throw new CaptchaException("验证码错误");
        }
        if (!code.equals(redisTemplate.opsForHash().get(Common.CAPTCHA_KEY,token))){
            throw new CaptchaException("验证码错误");
        }
        redisTemplate.delete(Common.CAPTCHA_KEY);
        redisTemplate.delete(token);
    }
}
