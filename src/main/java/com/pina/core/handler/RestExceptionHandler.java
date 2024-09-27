package com.pina.core.handler;

import com.pina.core.domain.Result;
import com.pina.core.enums.ReturnCode;
import com.pina.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 默认全局异常处理。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> exception(Exception e) {
        log.error("未知异常 ex={}", e.getMessage(), e);
        return Result.ofFail(ReturnCode.USER_ERROR_A0500);
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> baseException(BaseException e) {
        log.error("BaseException ex={}", e.getMessage(), e);
        return Result.ofFail(e.getCode(), e.getMessage());
    }
}
