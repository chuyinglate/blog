package com.example.blog.modules.system.user.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.ability.shiro.SaltUtil;
import com.example.blog.core.api.ApiRest;
import com.example.blog.core.utils.BeanMapper;
import com.example.blog.core.utils.TokenUtils;
import com.example.blog.modules.system.user.dto.SysUserLoginDTO;
import com.example.blog.modules.system.user.dto.SysUserRegisterDTO;
import com.example.blog.modules.system.user.entity.SysUser;
import com.example.blog.modules.system.user.mapper.SysUserMapper;
import com.example.blog.modules.system.user.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service("SysUserServiceImpl")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Override
    public ApiRest login(String account, String password){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(account,password));
            UpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("account",account);
            userUpdateWrapper.set("gmt_login",new Date(new java.util.Date().getTime()));
            baseMapper.update(null,userUpdateWrapper);
            SysUserLoginDTO sysUserLoginDTO = new SysUserLoginDTO();
            SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("account",account));
            BeanMapper.copy(sysUser,sysUserLoginDTO);
            sysUserLoginDTO.setToken(TokenUtils.getToken(sysUser));
            return ApiRest.success(sysUserLoginDTO);
        }catch (UnknownAccountException e){
            e.printStackTrace();
            return ApiRest.error("用户名不存在");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return ApiRest.error("密码错误");
        }
    }

    @Override
    public ApiRest register(SysUserRegisterDTO sysUserRegisterDTO) {
        try {
            if(this.count(new QueryWrapper<SysUser>().eq("account",sysUserRegisterDTO.getAccount())) != 0){
                return ApiRest.error("该账户已存在");
            }
            SysUser sysUser = new SysUser();
            BeanMapper.copy(sysUserRegisterDTO,sysUser);
            sysUser.setSalt(SaltUtil.getSalt(4));
            Md5Hash md5Hash = new Md5Hash(sysUser.getPassword(),sysUser.getSalt(),1024);
            sysUser.setPassword(md5Hash.toHex());
            sysUser.setGmtCreate(new Date(new java.util.Date().getTime()));
            this.save(sysUser);
            return ApiRest.success("注册成功");
        }catch (Exception e){
            e.printStackTrace();
            return ApiRest.error("注册失败");
        }
    }
}
