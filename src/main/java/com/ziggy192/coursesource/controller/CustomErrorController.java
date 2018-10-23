//package com.ziggy192.coursesource.controller;
//
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//
//@@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
//public class CustomErrorController extends BasicErrorController {
//
//	public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
//		super(errorAttributes, errorProperties);
//	}
//
//	@GetMapping("/error")
//	public String handleError(HttpServletRequest request) {
//
////		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
////
////		if (status != null) {
////			Integer statusCode = Integer.valueOf(status.toString());
////
////			if(statusCode == HttpStatus.NOT_FOUND.value()) {
////				return "error-404";
////			}
////			else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
////				return "error-500";
////			}
////		}
//		return "th_error";
//
//	}
//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
//}
