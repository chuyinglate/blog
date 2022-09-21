package com.example.blog.modules.system.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRegisterDTO {
    private String account;//账号
    private String password;//密码
    private String username;//用户名
    private String roleId;//用户角色
}
