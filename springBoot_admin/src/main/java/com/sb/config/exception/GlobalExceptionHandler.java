package com.sb.config.exception;

import com.sb.config.common.GlobalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * author: dyq
 * Time: 2023/3/17
 * description: 描述
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //实体校验异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public GlobalResult handler(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        ObjectError error = result.getAllErrors().stream().findFirst().get();
        log.error("异常信息:---------->",error.getDefaultMessage());
        return GlobalResult.fail(error.getDefaultMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public GlobalResult handler(IllegalArgumentException e){
        log.error("异常信息:---------->",e.getMessage());
        return GlobalResult.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public GlobalResult handler(RuntimeException e){
        log.error("异常信息:---------->",e.getMessage());
        return GlobalResult.fail(e.getMessage());
    }
}
