package com.example.blog.core.exception;

import com.example.blog.core.api.ApiRest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    //业务异常

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ApiRest handle(ServiceException se){
        return ApiRest.error(se.getCode(),se.getMessage());
    }
}
