package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		 return http
		.csrf(customizer ->customizer.disable()) //enable the csrf
		
		.authorizeHttpRequests(request -> request
				.requestMatchers("login", "register")
				.permitAll()
				
				.anyRequest().authenticated()) //Require every req must be authenticated or login
		.httpBasic(Customizer.withDefaults()) //provide basic authentication by spring security so
//		.formLogin(Customizer.withDefaults()) //provide a form-based auth to enter user their credentials
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS.STATELESS))   //enabling csrf by making http stateless
		
//		 Customizer<CsrfConfigurer<HttpSecurity>> custcsrf=new  Customizer<CsrfConfigurer<HttpSecurity>> () {
//           @Override
//			public void customize(CsrfConfigurer<HttpSecurity> t) {
//				t.disable();
//			}
//			 
//		 };            //ananymous class
		   
		
		 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				 .build();
		
        	  
          }
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user1= User.withDefaultPasswordEncoder()
//		 .username("guru")
//		 .password("g@123")
//		 .roles("user")
//		 .build();
//		
//		UserDetails user2= User.withDefaultPasswordEncoder()
//				 .username("theivanai")
//				 .password("t@123")
//				 .roles("admin")
//				 .build();
//		         
//		
//		return new InMemoryUserDetailsManager(user1,user2);
//	}
	
	
	
	 @Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
	        provider.setUserDetailsService(userDetailsService);
	        // Use DelegatingPasswordEncoder
	      
	        return provider;
	    }

//	    @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        // This will allow passwords with {noop} prefix
//	        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	    }
//    @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder(10); // Adjust strength as needed
//	    }
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		 return config.getAuthenticationManager();
	 }
}
