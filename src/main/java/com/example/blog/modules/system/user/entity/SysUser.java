package com.example.blog.modules.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;//ID
    private String account;//账号
    private String password;//密码
    private String salt;//密码盐
    private String username;//用户名
    private String roleId;//用户角色
    private Date gmtCreate;//创建时间
    private Date gmtUpdate;//更新时间
    private Date gmtLogin;//最后登录时间
}
