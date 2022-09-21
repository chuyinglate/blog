package com.example.blog.modules.system.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserLoginDTO {
    private long id;//ID
    private String account;//账号
    private String username;//用户名
    private String roleId;//角色
    private Date gmtLogin;//最后登录时间
    private String token;//token
}
