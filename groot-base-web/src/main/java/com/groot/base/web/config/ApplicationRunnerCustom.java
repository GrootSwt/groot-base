package com.groot.base.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;


/**
 * 项目启动后结尾打印内容
 */
@Configuration
@Slf4j
public class ApplicationRunnerCustom implements ApplicationRunner {

    @Value(value = "${server.port}")
    private String port;

    @Override
    public void run(ApplicationArguments args) {
        log.info("**************************************************************");
        log.info("SWAGGER URL: http://localhost:" + port + "/swagger-ui.html");
        log.info("**************************************************************");
    }
}
