package com.security.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
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
	private SecretKey getKey() {
		
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	
	
	public String extractUsername(String token) {
		// extract username from token or claims
		return extractClaim(token,Claims::getSubject);
	}
	private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
		 final Claims claims=extractAllclaims(token); //It retrieve all the claims such as sub,user_id,expirdate
		 return claimResolver.apply(claims); //to get specific desired(Sub) claim user_name
	}
//	{
//	  "sub": "muni_gp",
//		  "user_id": "123456",
//		  "exp": 1699999999
//		}

	
	
	
	private Claims extractAllclaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser()
				.verifyWith(getKey()) //verify token with secret key
				.build()
				.parseSignedClaims(token) //Parses the token to retrieve the claims payload.
				.getPayload();   //Extract json payload refer below example
//		{
//			  "sub": "muni_gp",
//			  "user_id": "123456",
//			  "exp": 1699999999
//			}

	}
	public boolean validateToken(String token, UserDetails userDetails) {
	     final  String username = extractUsername(token); //muni_gp
	     return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		   
	}
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()); //check expire date of d token before the curr date  & time
	}
	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration); //Retrieves the exp claim as a Date (e.g., 1699999999 converted to date).
	}
	

}
