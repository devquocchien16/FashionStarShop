package com.group4.fashionstarshop.configuration.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 7*24*60*60;

    public static final long JWT_REFRESH_TOKEN_VALIDITY = 7*24*60*60;

    @Value("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEjBX5KgsnM0uEPoetqN5ffu8QoQHY2lOo3FzRvJ9XO9ks6zJ3OZsdtw4HsVEb0EBZUHks3Nbxb1ER0bYSLodsPLtZPQasdas")
    private String secret;

    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        // Parses the token and extracts its claims
        return Jwts.parser()
                   // Sets the signing key used to verify the token's signature
                   .setSigningKey(secret).build().parseClaimsJws(token)
                   .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getId() + "");
    }
    public String generateAdminToken(Admin admin) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, admin.getId() + "");
    }
    public String generateSellerToken(Seller seller) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, seller.getId() + "");
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String token, User user) {
        final String id = getIdFromToken(token);
        return (id.equals(user.getId().toString()) && !isTokenExpired(token));
    }

    public Boolean validateSellerToken(String token, Seller seller) {
        final String id = getIdFromToken(token);
        return (id.equals(seller.getId().toString()) && !isTokenExpired(token));
    }
    public Boolean validateAdminToken(String token, Admin admin) {
        final String id = getIdFromToken(token);
        return (id.equals(admin.getId().toString()) && !isTokenExpired(token));
    }
    public Long getIdFromJwtToken(String jwt) {
        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(secret.getBytes()).build()
                                .parseClaimsJws(jwt)
                                .getBody();

            return Long.parseLong(claims.get("id").toString());
        } catch (Exception e) {
            return null;
        }
    }
    public String getUserIdByToken(HttpHeaders headers, String secret) {
    	log.debug("Token util start!");
    	String token = headers.get("Authorization").get(0);
    	String jwt = token.replace("Bearer ", "");
    	
    	log.info("Jwt:"+jwt);
    	String userId = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(jwt)
                .getBody().getSubject();
    	log.info("getUserId");
    	return userId;
    }
    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7); // Remove "Bearer " prefix if present
        Claims claims = Jwts.parser()
                            .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                            .build()
                            .parseClaimsJws(jwt)
                            .getBody();
        String email = String.valueOf(claims.get("email"));
        return email;  // Adjust the claim key as needed
    }

}
