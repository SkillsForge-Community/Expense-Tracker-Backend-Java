package com.SkillsForge.expensetracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for JWT (JSON Web Token) operations. Handles token generation, validation, and
 * claim extraction.
 */
@Component
public class JwtUtil {

  // Secret key from application.yaml - used to sign tokens
  @Value("${jwt.secret}")
  private String secret;

  // Token expiration time in milliseconds from application.yaml
  @Value("${jwt.expiration}")
  private Long expiration;

  /**
   * Generate a JWT token for a given username
   *
   * @param username the username to generate token for
   * @return JWT token string
   */
  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

  /**
   * Create the actual JWT token with claims
   *
   * @param claims additional claims to include in token
   * @param subject the subject (username) of the token
   * @return JWT token string
   */
  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject) // Username
        .setIssuedAt(new Date(System.currentTimeMillis())) // When token was created
        .setExpiration(new Date(System.currentTimeMillis() + expiration)) // When token expires
        .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign with secret key
        .compact();
  }

  /**
   * Extract username from JWT token
   *
   * @param token JWT token
   * @return username from token
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extract expiration date from JWT token
   *
   * @param token JWT token
   * @return expiration date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extract a specific claim from the token using a claims resolver function
   *
   * @param token JWT token
   * @param claimsResolver function to extract specific claim
   * @return the claim value
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extract all claims from the JWT token
   *
   * @param token JWT token
   * @return all claims in the token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Check if the token has expired
   *
   * @param token JWT token
   * @return true if expired, false otherwise
   */
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Validate the JWT token Checks if username matches and token is not expired
   *
   * @param token JWT token
   * @param userDetails user details to validate against
   * @return true if valid, false otherwise
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * Get the signing key from the secret Decodes the Base64 encoded secret and creates an HMAC key
   *
   * @return signing key
   */
  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
