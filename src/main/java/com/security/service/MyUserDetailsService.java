package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.model.UserPrincipal;
import com.security.model.Users;
import com.security.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if(user == null) {
            System.out.println("null");
            throw new UsernameNotFoundException("user not found");
        }
        
        // Add {noop} prefix to password if it doesn't already have it
        if (!user.getPassword().startsWith("{noop}")) {
            user.setPassword("{noop}" + user.getPassword());
            userRepo.save(user); // Save the updated password
        }
        
        return new UserPrincipal(user);
    }
}