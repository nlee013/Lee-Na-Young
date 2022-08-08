package com.springboot.lookoutside.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.lookoutside.dto.ResponseDto;

//오류 뜰시 에러 메시지 납치
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
	}
	
}
