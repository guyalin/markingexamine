package com.woniu.markingsystem.service.impl;

import com.woniu.markingsystem.entity.auth.AuthPrivilege;
import com.woniu.markingsystem.entity.auth.AuthUser;
import com.woniu.markingsystem.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthUserService authUserService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        AuthUser user = authUserService.getUser(userId);
        // 判断用户是否存在
        if(user == null) {
            //throw new UsernameNotFoundException("用户名不存在");
            return null;
        }
        // 添加权限
        List<AuthPrivilege> authPrivileges = authUserService.getPrivileges(userId);
        for (AuthPrivilege privilege : authPrivileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getPrivilegeName()));
        }

        // 返回UserDetails实现类
        return new User(user.getUserId(), user.getUserPassword(), authorities);
    }
}
