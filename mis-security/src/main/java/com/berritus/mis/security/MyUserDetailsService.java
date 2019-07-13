package com.berritus.mis.security;


import com.alibaba.dubbo.config.annotation.Reference;
import com.berritus.mis.bean.security.TbSysRole;
import com.berritus.mis.bean.security.TbSysUser;
import com.berritus.mis.dubbo.api.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 根据用户名获取用户信息
 */
@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Reference(timeout = 30000)
    private SecurityService securityService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        String pwd = passwordEncoder.encode("123456");
//        User user = new User(userName, pwd,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        TbSysUser user = securityService.selectByUserName(userName);
        if(user == null){
            throw new UsernameNotFoundException("没有该用户");
        }

        List<TbSysRole> userRoles = securityService.getUserRoles(userName);

        return new UserDetailsImpl(user, userRoles);
    }
}
