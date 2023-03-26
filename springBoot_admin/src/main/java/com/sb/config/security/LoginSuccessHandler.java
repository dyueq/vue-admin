package com.sb.config.security;

import cn.hutool.json.JSONUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.config.common.GlobalResult;
import com.sb.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * author: dyq
 * Time: 2023/3/19
 * description: 描述
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();

            //生成jwt，并放置到请求头
            String jwt = jwtUtil.generateToken(authentication.getName());
            response.setHeader(jwtUtil.getHeader(),jwt);

            GlobalResult result = GlobalResult.success("");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            outputStream.write(s.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } finally {
            if (outputStream != null){
                outputStream.close();
            }
        }

//        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
//
//        outputStream.flush();
//        outputStream.close();
    }
}
