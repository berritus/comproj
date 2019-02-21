package com.berritus.mis.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandle implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        PrintWriter out = httpServletResponse.getWriter();
//        StringBuffer buffer = new StringBuffer();
//
//        if(e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
//            buffer.append("用户名或者密码错误");
//        }else if(e instanceof DisabledException){
//            buffer.append("账户被禁用");
//        }else{
//            buffer.append("登陆失败");
//        }
//        out.write(buffer.toString());
//        out.flush();
//        out.close();

        String path = httpServletRequest.getContextPath();
        String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" +
                httpServletRequest.getServerPort() + path;

        new DefaultRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, basePath + "/tip/login/401");

    }
    //AuthenticationFailureHandler
}
