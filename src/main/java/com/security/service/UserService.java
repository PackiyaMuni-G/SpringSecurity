package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.security.model.Users;
import com.security.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private  UserRepo userRepo;
	
  
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword())); //get the pw and encrypt & set to the user and save db
		
		return userRepo.save(user);
	}

}
