package com.woniu.markingsystem.service;

import com.woniu.markingsystem.entity.auth.AuthPrivilege;
import com.woniu.markingsystem.entity.auth.AuthUser;

import java.util.List;

public interface AuthUserService {

    AuthUser getUser(String userId);

    List<AuthPrivilege> getPrivileges(String userId);

    List<AuthUser> getUserList();

}
