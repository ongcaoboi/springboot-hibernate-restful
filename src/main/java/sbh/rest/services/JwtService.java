package sbh.rest.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.time}")
	private int jwtExpiration;

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			System.out.println("Token not found!");
			return false;
		}
	}

	public String getUserNameFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public String generateTokenUsingUserName(String userName) {
		Date now = new Date();
		Date tokenExpiration = new Date(now.getTime() + jwtExpiration);
		
		return Jwts.builder().setSubject(userName)
				.setIssuedAt(now)
				.setExpiration(tokenExpiration)
				.signWith(SignatureAlgorithm.HS256, jwtSecret)
				.compact();
	}
}
