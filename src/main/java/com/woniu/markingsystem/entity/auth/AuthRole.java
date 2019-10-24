package com.woniu.markingsystem.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRole implements Serializable {
    private static final long serialVersionUID = -8380099848888876494L;
    private String roleId;
    private String roleName;
}
