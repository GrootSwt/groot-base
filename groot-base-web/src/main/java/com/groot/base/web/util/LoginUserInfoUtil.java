package com.groot.base.web.util;

import com.alibaba.fastjson.JSON;
import com.groot.base.common.OperatorInfo;
import com.groot.base.web.exception.BusinessRuntimeException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

public class LoginUserInfoUtil {

    private static final ThreadLocal<OperatorInfo> operatorInfoThreadLocal = new ThreadLocal<>();

    /**
     * 获取当前操作人员信息
     *
     * @return 操作人员信息
     */
    public static OperatorInfo getOperatorInfo() {
        if (operatorInfoThreadLocal.get() != null) {
            return operatorInfoThreadLocal.get();
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userInfo")) {
                    try {
                        return userDTOToOperatorInfo(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                    } catch (Exception e) {
                        throw new BusinessRuntimeException("数据解析出现异常！");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 存放当前登录人员信息
     *
     * @param operatorInfo 登录人员信息
     */
    public static void setOperatorInfo(OperatorInfo operatorInfo) {
        operatorInfoThreadLocal.set(operatorInfo);

    }

    /**
     * 用户信息转为操作员信息
     *
     * @param userInfoStr 登录用户信息
     * @return 操作员信息
     */
    private static OperatorInfo userDTOToOperatorInfo(String userInfoStr) {
        return JSON.parseObject(userInfoStr, OperatorInfo.class);
    }
}