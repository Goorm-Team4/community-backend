package com.team4.goorm.community.auth.jwt.utils;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.auth.dto.response.TokenRespDto;
import com.team4.goorm.community.auth.exception.JwtErrorCode;
import com.team4.goorm.community.auth.exception.JwtException;
import com.team4.goorm.community.member.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * JWT 토큰 생성 및 검증
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

	private final JwtProperties jwtProperties;
	private SecretKey secretKey;

	@PostConstruct
	public void initializeSecretKey() {
		byte[] decoded = Decoders.BASE64.decode(jwtProperties.getSecretKey());
		this.secretKey = Keys.hmacShaKeyFor(decoded);
	}

	public String extractToken(HttpServletRequest request) {
		String header = request.getHeader(JwtProperties.AUTHORIZATION_HEADER);

		if (header == null || !header.startsWith(JwtProperties.BEARER_PREFIX)) {
			return null;
		}
		return header.split(" ")[1];
	}

	public TokenRespDto issueAccessToken(String email, String username) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(jwtProperties.getAccessExpirationTime());
		String token = buildJwtToken(email, username, issuedAt, expiration);

		return TokenRespDto.builder()
			.email(email)
			.username(username)
			.accessToken(token)
			.build();
	}

	private String buildJwtToken(String email, String username, Instant issuedAt, Instant expiration) {
		return Jwts.builder()
			.setSubject(email)
			.claim("username", username)
			.claim("role", Role.ROLE_USER)
			.setIssuedAt(Date.from(issuedAt))
			.setExpiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();
	}

	// JWT 토큰 검증
	public Claims validateTokenAndGetClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey).build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			log.warn("Expired Token: {}", e.getMessage());
			throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
		} catch (MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
			log.warn("Invalid Token: {}", e.getMessage());
			throw new JwtException(JwtErrorCode.INVALID_TOKEN);
		} catch (Exception e) {
			log.error("Unexpected JWT Error: {}", e.getMessage());
			throw new JwtException(JwtErrorCode.JWT_ERROR);
		}
	}

	public Authentication getAuthentication(Claims claims) {
		CustomUserDetails userDetails = new CustomUserDetails(
			getSubject(claims), // email
			getUsername(claims),
			getAuthority(claims)
		);

		// 인증용 객체 생성
		return new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities());
	}

	private String getSubject(Claims claims) { // 현재 subject -> email
		return claims.getSubject();
	}

	private String getUsername(Claims claims) {
		return claims.get("username", String.class);
	}

	private String getAuthority(Claims claims) {
		return claims.get("role", String.class);
	}
}
