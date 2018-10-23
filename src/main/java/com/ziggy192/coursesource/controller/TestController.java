package com.ziggy192.coursesource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {



	@GetMapping("testThymeleaf")
	public String testThymeleaf() {
		return "th_hihi";
	}

	@GetMapping("testJsp")
	public String testJsp() {
		return "home";
	}
}
