package com.security.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.security.model.Users;

@Service
public class JWTService {

	   @Value("${app-jwt-expiration-milliseconds}")
	    private long jwtExpirationDate;
	   
	   @Value("${app-jwt-secret-key}")
	     private String secretKey="";
	     
	     
//	public JWTService() {
//		 try {
//			KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk = keyGen.generateKey();
//			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}
	public String generateToken(Users user) {
		String username = user.getUsername();
		Map<String, Object> claims=new HashMap<>();
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
	    return Jwts.builder()
	    		.claims()
	    		.add(claims)
	    		.subject(username)
	    		.issuedAt(currentDate)
	    		.expiration(expireDate)
	    		.and()
	    		.signWith(getKey())
	    		.compact();
	
//		return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Im11bmkiLCJpYXQiOjE1MTYyMzkwMjJ9.A4VFakk6kbWio5nKIMQE2LNXIIdjy_UHwGj-8xmuHPY" ;
	}
	private Key getKey() {
		
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

}
