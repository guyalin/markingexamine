package com.woniu.markingsystem.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserRole implements Serializable {
    private static final long serialVersionUID = -5860089342769058703L;
    private String userId;
    private String roleId;
}
