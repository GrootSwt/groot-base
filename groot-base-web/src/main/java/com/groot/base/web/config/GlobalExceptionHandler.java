package com.groot.base.web.config;


import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.exception.BusinessRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 全局异常处理器
     *
     * @param e Exception
     * @return Result.failure(异常信息)
     */
    @ExceptionHandler(value = Exception.class)
    public ResultDTO<Void> exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof BusinessRuntimeException) {
            return ResultDTO.failure(e.getMessage());
        }
        return ResultDTO.failure("服务异常！");
    }
}

