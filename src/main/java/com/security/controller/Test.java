package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Test {
  @GetMapping("/test")
	public String sayHello(HttpServletRequest req) {
		return "Welcome to spring security"+"sesson id:"+req.getSession().getId();
	}
}
