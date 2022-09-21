package com.example.blog.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.blog.modules.system.user.entity.SysUser;
import com.example.blog.modules.system.user.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    static SysUserMapper staticUserMapper;

    @Autowired
    SysUserMapper userMapper;

    @PostConstruct
    public void setStaticTeacherMapper(){
        staticUserMapper = userMapper;
    }


    public static String getToken(SysUser user){
        return JWT.create().withAudience(user.getAccount())   //将account保存到token里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))   //2小时后token过期
                .sign(Algorithm.HMAC256(user.getPassword())); //以password作为token的密钥
    }


    public static String getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)){
                return  JWT.decode(token).getAudience().get(0);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
