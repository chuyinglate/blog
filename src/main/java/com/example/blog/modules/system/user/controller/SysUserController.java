package com.example.blog.modules.system.user.controller;

import com.example.blog.core.api.ApiRest;
import com.example.blog.modules.system.user.dto.SysUserRegisterDTO;
import com.example.blog.modules.system.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {

    @Qualifier("SysUserServiceImpl")
    @Autowired
    private SysUserService userService;

    @PostMapping("/login")
    public ApiRest login(@RequestParam String account,@RequestParam String password){
        return userService.login(account,password);
    }

    @PostMapping("/register")
    public ApiRest register(@RequestBody SysUserRegisterDTO sysUserRegisterDTO){
        return userService.register(sysUserRegisterDTO);
    }
}
