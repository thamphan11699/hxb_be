package com.anhtq.app.admin.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String KEY =
      "50655368566D5970337336763979244226452948404D635166546A576E5A7234";

  public String extractUsername(String jwt) throws Exception {
    return extractClaims(jwt, Claims::getSubject);
  }

  private Claims extractAllClaims(String jwt) throws Exception {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(jwt)
          .getBody();
    } catch (Exception e) {
      throw new Exception("token error");
    }
  }

  private Key getSigningKey() {
    byte[] keyByte = Decoders.BASE64.decode(KEY);
    return Keys.hmacShaKeyFor(keyByte);
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimsResolve) throws Exception {
    final Claims claims = extractAllClaims(token);
    return claimsResolve.apply(claims);
  }

  public String generateToken(Map<String, Objects> extractClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(extractClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 240))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isValidToken(String token, UserDetails userDetails) {
    try {
      final String username = extractUsername(token);
      return (Objects.equals(username, userDetails.getUsername()) && !isTokenExpired(token));
    } catch (Exception e) {
      return false;
    }
  }

  private boolean isTokenExpired(String token) {
    try {
      return extractExpiration(token).before(new Date());
    } catch (Exception e) {
      return true;
    }
  }

  private Date extractExpiration(String token) throws Exception {
    return extractClaims(token, Claims::getExpiration);
  }
}
