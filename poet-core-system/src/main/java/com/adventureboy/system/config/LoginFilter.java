package com.adventureboy.system.config;

import com.adventureboy.system.bean.SysLoginModel;
import com.adventureboy.system.utils.VerificationCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //post方法
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //检查数据是否为json格式
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            SysLoginModel sysLoginModel = null;
            try {
                 sysLoginModel = new ObjectMapper().readValue(request.getInputStream(), SysLoginModel.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username = sysLoginModel.getUsername();
            username = username != null ? username : "";
            username = username.trim();
            String password = sysLoginModel.getPassword();
            password = password != null ? password : "";
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            if (!VerificationCodeUtil.checkCode(request.getParameter("verificationCode"))) {
                throw new RuntimeException("验证码出错");
            }
            //如果不是json数据格式,那么交给父类处理
            return super.attemptAuthentication(request, response);
        }
    }
}
