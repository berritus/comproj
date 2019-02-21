package com.berritus.mis.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义403响应内容
@Component
public class MyAccessDeniedHandle implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
//        response.setStatus(HttpServletResponse.SC_ACCEPTED);
//        response.setCharacterEncoding("GBK");
//        PrintWriter out = response.getWriter();
//        out.write("权限不足，请联系管理员");
//        out.flush();
//        out.close();

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

        //Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        //String url = request.getRequestURI();
        new DefaultRedirectStrategy().sendRedirect(request, response, basePath + "/user/403");
    }
}
