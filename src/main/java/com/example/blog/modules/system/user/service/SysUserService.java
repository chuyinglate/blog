package com.example.blog.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.core.api.ApiRest;
import com.example.blog.modules.system.user.dto.SysUserRegisterDTO;
import com.example.blog.modules.system.user.entity.SysUser;
import org.springframework.stereotype.Service;

@Service
public interface SysUserService extends IService<SysUser> {
    ApiRest login(String account, String password);
    ApiRest register(SysUserRegisterDTO sysUserRegisterDTO);
}
