package com.woniu.markingsystem.controller.usercontroller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserApi {

    @RequestMapping("/login")
    public String userLogin(){
        return "demo-sign.html";
    }

    @RequestMapping("/login-error")
    public String loginError(){
        return "login-error.html";
    }

}
