package com.woniu.markingsystem.service.impl;

import com.woniu.markingsystem.dao.AuthUserDao;
import com.woniu.markingsystem.entity.auth.AuthPrivilege;
import com.woniu.markingsystem.entity.auth.AuthUser;
import com.woniu.markingsystem.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public AuthUser getUser(String userId) {
        AuthUser authUser = authUserDao.queryUserByUserId(userId);
        return authUser;
    }

    @Override
    public List<AuthPrivilege> getPrivileges(String userId) {
        List<AuthPrivilege> authPrivileges = authUserDao.queryPrivilegeByUserId(userId);
        return authPrivileges;
    }

    @Override
    public List<AuthUser> getUserList() {
        return null;
    }
}
