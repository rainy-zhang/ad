package com.hfzx.ad.exception;

import com.hfzx.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhangyu
 * @Description: 统一的异常处理
 * @Date: in 2020/4/21 16:56
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException e) {
        log.error("error uri: {}", request.getRequestURI());
        return new CommonResponse<>(-1, "business error", e.getMessage());
    }

}
