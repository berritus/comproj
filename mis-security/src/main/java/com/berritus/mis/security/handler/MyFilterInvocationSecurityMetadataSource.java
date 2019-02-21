package com.berritus.mis.security.handler;


import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;

//接收用户请求的地址，返回访问该地址需要的所有权限
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//    @Autowired
//    private UserService userService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) obj).getRequestUrl();

        //如果登录页面就不需要权限
        if("/user/login".equals(requestUrl) || "/main/login.html".equals(requestUrl) ||
                "/".equals(requestUrl) || "/user/403".equals(requestUrl)){
            return null;
        }

//        TbSysResource resource = userService.getSysResource(requestUrl);
//        //如果没有匹配的url则说明大家都可以访问
//        if(resource == null){
//            return SecurityConfig.createList("ROLE_LOGIN");
//        }
//
//        //将resource所需要到的roles按框架要求封装返回
//        List<TbSysRole> roleRess = userService.getResourceRoles(resource.getResId());
//        int size = roleRess.size();
//        String[] value = new String[size];
//        for(int i = 0; i < size; i++){
//            value[i] = roleRess.get(i).getRoleName();
//        }
//
//        return SecurityConfig.createList(value);
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
