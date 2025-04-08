package com.hello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hello.common.vo.AjaxResponse;

@RestControllerAdvice // @CotrollerAdvice + @ResponseBody
public class AjaxGlobalExceptionHandler {
	
	@ExceptionHandler(AjaxException.class)
	public AjaxResponse handleAjaxException(AjaxException ajaxException) {
		return new AjaxResponse(HttpStatus.BAD_REQUEST.value(), ajaxException.getMessage());
	}
	
}
