package com.woniu.markingsystem.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRolePrivilege implements Serializable {
    private static final long serialVersionUID = -6368031856699125927L;
    private String roleId;
    private String privilege;
}
