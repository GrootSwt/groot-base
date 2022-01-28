package com.groot.base.web.config;

import com.alibaba.fastjson.JSON;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.exception.BusinessRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${groot.login.expire-time}")
    private Long expireTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accountName = null;
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accountName".equals(cookie.getName())) {
                    accountName = cookie.getValue();
                }
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
            if (accountName == null || token == null ||"".equals(accountName) || "".equals(token)) {
                this.setFailureResponse(response, "未授权，请登录");
                return false;
            }
            String redisToken = stringRedisTemplate.opsForValue().get(token);
            if (redisToken != null) {
                if (redisToken.equals(accountName)) {
                    Long expire = stringRedisTemplate.getExpire(token, TimeUnit.MINUTES);
                    if (expire != null && expire < 5L) {
                        stringRedisTemplate.opsForValue().set(token, redisToken, expireTime, TimeUnit.MINUTES);
                    }
                    return true;
                } else {
                    this.setFailureResponse(response, "该账号已在别处登录！");
                    return false;
                }
            } else {
                this.setFailureResponse(response, "未授权，请登录");
                return false;
            }
        } else {
            this.setFailureResponse(response, "未授权，请登录");
            return false;
        }
    }

    private void setFailureResponse(HttpServletResponse response, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSON.toJSONString(ResultDTO.unauthorized(message)));
            writer.flush();
        } catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage());
        }
    }
}
