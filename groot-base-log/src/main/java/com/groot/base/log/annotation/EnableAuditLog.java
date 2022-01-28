package com.groot.base.log.annotation;

import com.groot.base.log.aspect.GlobalAuditLog;
import com.groot.base.log.config.DirectRabbitConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {DirectRabbitConfig.class, GlobalAuditLog.class})
public @interface EnableAuditLog {
}
