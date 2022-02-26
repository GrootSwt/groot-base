package com.groot.base.web.annotation;

import com.groot.base.web.config.ApplicationRunnerCustom;
import com.groot.base.web.config.GlobalExceptionHandler;
import com.groot.base.web.config.SwaggerConfig;
import com.groot.base.web.config.WebMvcConfig;
import com.groot.base.web.util.EncryptionUtil;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {GlobalExceptionHandler.class, WebMvcConfig.class, ApplicationRunnerCustom.class, SwaggerConfig.class, EncryptionUtil.class})
@EnableSwagger2
public @interface EnableBaseWeb {
}
