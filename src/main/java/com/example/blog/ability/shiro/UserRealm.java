package com.example.blog.ability.shiro;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.example.blog.core.utils.ApplicationContextUtil;
import com.example.blog.core.utils.BeanMapper;
import com.example.blog.modules.system.user.entity.SysUser;
import com.example.blog.modules.system.user.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

public class UserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        SysUserService sysUserService = (SysUserService) ApplicationContextUtil.getBean("SysUserServiceImpl");
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("account",principal));
        if(!ObjectUtils.isEmpty(sysUser)){
            return new SimpleAuthenticationInfo(sysUser.getAccount(),sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), this.getName());
        }
        return null;
    }
}
