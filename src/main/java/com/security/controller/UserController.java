package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.Users;
import com.security.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
public class UserController {
	
	@Autowired
	private UserService userService;
 
	
	@PostMapping("/register")
	public Users newRegister(@RequestBody Users user) {
	    return 	userService.register(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
//		System.out.println(user);
		return userService.verify(user);
	}
}
