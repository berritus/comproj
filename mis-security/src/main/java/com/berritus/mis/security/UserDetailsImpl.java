package com.berritus.mis.security;



import com.berritus.mis.bean.security.TbSysRole;
import com.berritus.mis.bean.security.TbSysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 2111076392135554976L;

    private String userName;
    private String password;
    private List<TbSysRole> roles;

    public UserDetailsImpl(){

    }

    public UserDetailsImpl(TbSysUser user){
        this.userName = user.getUserName();
        this.password = user.getPassword();
    }

    public UserDetailsImpl(TbSysUser user, List<TbSysRole> roles){
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(TbSysRole role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        //判断账号是否已经过期，默认没有过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //判断账号是否被锁定，默认没有锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //判断信用凭证是否过期，默认没有过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        //判断账号是否可用，默认可用
        return true;
    }

    public List<TbSysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<TbSysRole> roles) {
        this.roles = roles;
    }
}
