package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.csrf(customizer ->customizer.disable()) //enable the csrf
		
		.authorizeHttpRequests(request -> request.anyRequest().authenticated()) //Require every req must be authenticated or login
		.httpBasic(Customizer.withDefaults()) //provide basic authentication by spring security so
//		.formLogin(Customizer.withDefaults()) //provide a form-based auth to enter user their credentials
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS.STATELESS));   //enabling csrf by making http stateless
		
//		 Customizer<CsrfConfigurer<HttpSecurity>> custcsrf=new  Customizer<CsrfConfigurer<HttpSecurity>> () {
//           @Override
//			public void customize(CsrfConfigurer<HttpSecurity> t) {
//				t.disable();
//			}
//			 
//		 };            //ananymous class
		
		
		return http.build();
		
        	  
          }
}
