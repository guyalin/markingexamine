package com.woniu.markingsystem.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthPrivilege implements Serializable {
    private static final long serialVersionUID = 6455014032864249920L;
    private String privilegeId;
    private String privilegeName;
}
