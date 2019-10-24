package com.woniu.markingsystem.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 1387220344906481541L;
    private String userId;
    private String userName;
    private String userPassword;
    private Integer errorTimes;
}
