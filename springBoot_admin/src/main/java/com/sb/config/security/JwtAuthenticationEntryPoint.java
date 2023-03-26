package com.sb.config.security;

import cn.hutool.json.JSONUtil;
import com.sb.config.common.GlobalResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: dyq
 * Time: 2023/3/20
 * description: 描述
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        //未认证
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ServletOutputStream outputStream = response.getOutputStream();

        GlobalResult result = GlobalResult.fail("请先登录");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
