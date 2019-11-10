package com.project_management.belfazt.security;

import static com.project_management.belfazt.security.SecurityConstants.EXPIRATION_TIME;
import static com.project_management.belfazt.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.project_management.belfazt.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTTokenProvider {

	//Generate token
	public String generateToken(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		
		String userId =Long.toString(user.getId());
		
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("id", (Long.toString(user.getId())));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
	
	//Validate token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		}catch(SignatureException e) {
			System.out.println("Invalid JWT signeture");
		}catch (MalformedJwtException e) {
			System.out.println("Invalid JWT signeture");
		}catch (ExpiredJwtException e) {
			System.out.println("Expired JWT token");
		}catch (UnsupportedJwtException e) {
			System.out.println("Unsupported JWT excepton");
		}catch (IllegalArgumentException e) {
			System.out.println("JWT cliam string is empty");
		}
		return false;
	}
	
	//get user id from token
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String)claims.get("id");
		
		return Long.parseLong(id);
	}
}
