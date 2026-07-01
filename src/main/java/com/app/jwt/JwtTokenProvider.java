package com.app.jwt;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.app.entity.Role;
import com.app.entity.UserEntity;
import com.app.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${security.jwt.key.private}")
	private String privateKey;
	
	@Autowired
	UserRepository userRepository;
	
	private static final long JWT_EXPIRATION_DATE = 60000;  // milisegundos ; 1h = 3600s
	
    /**
     * Generates a JWT token from the authenticated user's details.
     *
     * @param authentication the Spring Security Authentication object
     * @return the JWT token as a String
     */
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION_DATE);
		
	    UserEntity user = userRepository.getByUsername(username);

		
	    List<String> roles = user.getRoles()
                .stream()
                .map(auth -> auth.getName())
                .toList();
	    
		
		return Jwts.builder()
				   .subject(username)
		           .claim("roles", roles)
		           .claim("userId", user.getUserId())
		           .claim("scrapYardId", (user.getScrapYard() != null) ? user.getScrapYard().getScrapYardId().toString() : null)
				   .issuedAt(new Date())
				   .expiration(expireDate)
				   .signWith(getSignInKey(), Jwts.SIG.HS256)
				   .compact();
	}
	

    /**
     * Decodes and returns the secret key used to sign the token.
     *
     * @return SecretKey instance
     */
	private SecretKey getSignInKey() {
		
		byte[] keyBytes = Decoders.BASE64.decode(privateKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
    /**
     * Extracts the subject (usually the username) from a JWT token.
     *
     * @param token the JWT token
     * @return the subject (username)
     */
	public String getSubjectFromToken(String token) {
		return extraClaim(token, Claims::getSubject);
	}
	
    /**
     * Checks whether the JWT token has expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
	private Date extractExpiration(String token) {
		return extraClaim(token, Claims::getExpiration);
	}
	
    /**
     * Validates the JWT token structure and signature.
     *
     * @param token the JWT token
     * @return true if valid, throws exception otherwise
     */
	public boolean validateToken(String token) {
		Jwts.parser()
			.verifyWith(getSignInKey())
			.build()
			.parseSignedClaims(token);
		return true;
	}
	
    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token
     * @param claimResolver a function defining which claim to extract
     * @return the claim value
     */
	private <T> T extraClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
    /**
     * Extracts all claims from the JWT token payload.
     *
     * @param token the JWT token
     * @return the Claims object
     */
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				   .verifyWith(getSignInKey())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}
}
