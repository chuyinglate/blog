package com.example.blog.ability.token;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blog.core.exception.ServiceException;
import com.example.blog.modules.system.user.entity.SysUser;
import com.example.blog.modules.system.user.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    SysUserMapper SysUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        //执行认证
        if (StrUtil.isBlank(token)){
            throw new ServiceException(401,"无token,请重新登陆");
        }

        String account;
        try {
            account = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(401,"token验证失败");
        }

        //根据token中的account查询数据库
        SysUser SysUser = SysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("account",account));
        if (SysUser == null){
            throw new ServiceException(401,"用户不存在,请重新登陆");
        }

        //用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SysUser.getPassword())).build();
        try {
            jwtVerifier.verify(token);  //验证token
        }catch (JWTVerificationException e){
            throw new ServiceException(401,"token验证失败,请重新登陆");
        }
        return true;
    }
}
