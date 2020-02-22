package com.seckill.controller;

import com.seckill.error.BusinessException;
import com.seckill.error.EnumError;
import com.seckill.response.CommonResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        CommonResponseType commonResponseType = new CommonResponseType();
        Map<String, Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errorCode", businessException.getErrorCode());
            responseData.put("errorMsg", businessException.getErrorMsg());
        }
        else{
            responseData.put("errorCode", EnumError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMsg", EnumError.UNKNOWN_ERROR.getErrorMsg());
        }
        return commonResponseType.create(responseData, "fail");
    }
}
