package com.woniu.markingsystem.dao;

import com.woniu.markingsystem.entity.auth.AuthPrivilege;
import com.woniu.markingsystem.entity.auth.AuthRole;
import com.woniu.markingsystem.entity.auth.AuthUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthUserDao {
    AuthUser queryUserByUserId(String userId);

    AuthUser queryUserByUserName(String userName);

    List<AuthRole> queryRoleByUserId(String userId);

    List<AuthPrivilege> queryPrivilegeByUserId(String userId);

}
