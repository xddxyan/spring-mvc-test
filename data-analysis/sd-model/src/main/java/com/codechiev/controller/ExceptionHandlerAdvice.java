package com.codechiev.controller;

import org.postgresql.util.PSQLException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(PSQLException.class)
    public @ResponseBody String handlePrivilegeError(PSQLException ex) {
    	return ex.toString();
    }
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody String handleException(Exception ex) {
		return ex.toString()+": "+ex.getLocalizedMessage();
	}
}