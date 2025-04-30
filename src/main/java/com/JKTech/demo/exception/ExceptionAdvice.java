package com.JKTech.demo.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.JKTech.demo.dto.ResponseDto;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ResponseDto<Object> error = new ResponseDto<Object>();
		error.setMessage(ex.getMessage());
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = UploadException.class)
	public ResponseEntity<ResponseDto<String>> notFoundException(UploadException e) {
		ResponseDto<String> error = new ResponseDto<String>();
		error.setMessage(e.getMessage());
		error.setStatusCode((HttpStatus.EXPECTATION_FAILED.value()));
		return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
	}

}
