package com.security.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.service.JWTService;
import com.security.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter  extends OncePerRequestFilter{
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//req obj have lot of details
		//Extracting specific auth header from whole header in url
		//below sample jwt token for reference 
		
		  String authHeader = request.getHeader("Authorization"); //it starts with Bearer  xxxx
		 String token=null;
		  String username=null;
		
		  if(authHeader !=null && authHeader.startsWith("Bearer")) {
			 token = authHeader.substring(7);  //skip the first 7 index bccoz token start with 7th
		          username=jwtService.extractUsername(token);
		  }
		  
		  
		  //it should not be null &be a already auth user
		  if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) { //hold the current logged user(principle) state and roles and other details 
	
			   //it retrive the userdetails from  db through repo
			  UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
			  //userDetails or username is mentioning which part of db
	 if(jwtService.validateToken(token,userDetails)) {
		 //if token and userDetails is valid successfully it will move to next filter
		 UsernamePasswordAuthenticationToken authToken = 
				 new UsernamePasswordAuthenticationToken( userDetails, null,userDetails.getAuthorities());
	   //authtoken knows about the user
		 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		 Sets user details and authorities in SecurityContext
//		 Makes user information available throughout the request
		 SecurityContextHolder.getContext().setAuthentication(authToken);
		 //If token is valid → Forward to requested resource
		 //After request completion → Clear SecurityContext
	 }
		  }
		  filterChain.doFilter(request, response);

}}
