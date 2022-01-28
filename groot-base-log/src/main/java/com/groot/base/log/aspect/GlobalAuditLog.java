package com.groot.base.log.aspect;

import com.alibaba.fastjson.JSON;
import com.groot.base.common.OperatorInfo;
import com.groot.base.log.bean.AuditLogBean;
import com.groot.base.web.util.LoginUserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 全局日志处理
 */
@Aspect
@Slf4j
@Component
public class GlobalAuditLog {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MultipartResolver multipartResolver;

    @Value(value = "${groot.rabbit-mq.exchange-name}")
    private String exchangeName;

    @Value(value = "${groot.rabbit-mq.routing-key}")
    private String routingKey;

    /**
     * 获取切点
     */
    @Pointcut(value = "execution(public * *..controller..*(..)) && !execution(public * *..AuditLogController.*(..))")
    public void pointCut() {
    }

    /**
     * 判断是否进行日志审计
     *
     * @return 是否进行日志审计
     */
    private boolean isAuditLog() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        assert response != null;
        String header = response.getHeader("content-disposition");
        // 如果为文件操作则不审计日志
        return !multipartResolver.isMultipart(request) && header == null;
    }

    /**
     * 请求处理成功日志录入
     *
     * @param joinPoint 切入点
     * @param result    响应
     */
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfterReturning(final JoinPoint joinPoint, Object result) {
        boolean flag = isAuditLog();
        if (flag) {
            AuditLogBean auditLogBean = getAuditLog(joinPoint);
            auditLogBean.setResponseData(JSON.toJSONString(result));
            auditLogBean.setSuccess(true);
            auditLogBean.setResolved(true);
            rabbitTemplate.convertAndSend(exchangeName, routingKey, auditLogBean);
            log.info(JSON.toJSONString(auditLogBean));
        }

    }

    /**
     * 异常日志录入
     *
     * @param joinPoint 切入点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void doAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        if (isAuditLog()) {
            AuditLogBean auditLogBean = getAuditLog(joinPoint);
            auditLogBean.setResponseData(JSON.toJSONString(e.getMessage()));
            auditLogBean.setSuccess(false);
            auditLogBean.setResolved(false);
            rabbitTemplate.convertAndSend(exchangeName, routingKey, auditLogBean);
            log.error(JSON.toJSONString(auditLogBean));
        }

    }

    /**
     * 获取日志信息
     *
     * @param joinPoint 切入点
     * @return 日志DTO
     */
    private AuditLogBean getAuditLog(final JoinPoint joinPoint) {
        AuditLogBean auditLogBean = new AuditLogBean();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        // 操作人信息
        OperatorInfo operatorInfo = LoginUserInfoUtil.getOperatorInfo();
        if (operatorInfo != null) {
            BeanUtils.copyProperties(operatorInfo, auditLogBean);
        }
        // 方法名
        String methodName = joinPoint.getSignature().getName();
        auditLogBean.setMethodName(methodName);
        // 简单类名
        String simpleClassName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        auditLogBean.setSimpleClassName(simpleClassName);
        // 全类名
        String fullClassName = joinPoint.getSignature().getDeclaringTypeName();
        auditLogBean.setFullClassName(fullClassName);
        // 请求参数列表
        Object[] requestParams = joinPoint.getArgs();
        List<Object> params = new ArrayList<>();
        for (Object requestParam : requestParams) {
            if (requestParam instanceof ServletRequest || requestParam instanceof ServletResponse || requestParam instanceof MultipartFile) {
                continue;
            }
            params.add(requestParam);
        }

        // 请求参数列表
        String requestData = JSON.toJSONString(params);
        auditLogBean.setRequestData(requestData);

        // 请求资源定位符
        String requestURI = request.getRequestURI();
        auditLogBean.setRequestURI(requestURI);
        // 请求方法
        String requestMethod = request.getMethod();
        auditLogBean.setRequestMethod(requestMethod);
        // 日志创建时间
        auditLogBean.setCreateTime(new Date());
        // 获取IP
        auditLogBean.setIp(getIpAddress(request));
        return auditLogBean;
    }


    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
