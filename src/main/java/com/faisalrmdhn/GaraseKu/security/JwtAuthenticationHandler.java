package com.faisalrmdhn.GaraseKu.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.faisalrmdhn.GaraseKu.model.entity.MasterUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;

@Component
public class JwtAuthenticationHandler {
  @Value("${jwt.private.key}")
  private RSAPrivateKey privateKey;

  @Value("${jwt.public.key}")
  private RSAPublicKey publicKey;

  @Value("${jwt.expiration}")
  private int jwtExpirationMs;

  @Value("${jwt.refresh.token.expiration}")
  private int refreshTokenExpirationMs;

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationHandler.class);

  public JwtAuthenticationHandler() {
  }

  public JwtAuthenticationHandler(RSAPrivateKey privateKey, RSAPublicKey publicKey, int jwtExpirationMs,
      int refreshTokenExpirationMs) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    this.jwtExpirationMs = jwtExpirationMs;
    this.refreshTokenExpirationMs = refreshTokenExpirationMs;
  }

  private String createJwt(MasterUser user) throws InvalidKeyException, UnknownHostException {
    LOGGER.info("[GARASEKU LOG]: Start creating JWT");
    List<String> userRoles = user.getRoles()
        .stream()
        .map(role -> role.getVrolename())
        .collect(Collectors.toList());

    byte[] bytes = new byte[16];
    SecureRandom random = new SecureRandom(bytes);
    var jwtId = Integer.toHexString(random.nextInt());

    ZoneId zoneId = ZoneId.of("Asia/Jakarta");
    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Jakarta"));

    var exp = Date.from(now.plus(jwtExpirationMs, ChronoUnit.MILLIS).atZone(zoneId).toInstant());
    var iss = Date.from(now.atZone(zoneId).toInstant());
    var nbf = Date.from(now.atZone(zoneId).toInstant());
    var hostName = InetAddress.getLocalHost().getHostName();

    var customClaims = new HashMap<String, Object>();
    customClaims.put("name", user.getVfullname());
    customClaims.put("roles", userRoles);

    LOGGER.info("[GARASEKU LOG]: Finish creating JWT");
    return Jwts.builder()
        .claims(customClaims)
        .issuer(hostName)
        .subject(user.getMasterUserPk().getVemail())
        .expiration(exp)
        .notBefore(nbf)
        .issuedAt(iss)
        .id(jwtId)
        .signWith(privateKey)
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = decodeToken(token);
    return claimsResolver.apply(claims);
  }

  public Claims decodeToken(String token) {
    try {
      return Jwts.parser()
          .verifyWith(publicKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (JwtException e) {
      LOGGER.error("[GARASEKU LOG]: Invalid or expired token.", e);
      throw new RuntimeException("Invalid or expired token.", e);
    }
  }

  public String generateToken(MasterUser user) {
    try {
      return createJwt(user);
    } catch (InvalidKeyException | UnknownHostException e) {
      LOGGER.error("[GARASEKU LOG]: Error occurred while generating token.", e);
      throw new RuntimeException("Error occurred while generating token.", e);
    }
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String email = extractUsername(token);
    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
