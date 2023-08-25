package sn.esmt.tasks.taskmanager.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sn.esmt.tasks.taskmanager.dto.converters.JwtAuthenticationResponse;

import java.util.*;

@Component
public class TokenAuthentificationProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthentificationProvider.class);

    @Value("${jwt.jwtSecret}")
    private String jwtSecret;
    @Value("${jwt.jwtRefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${jwt.jwtExpirationInMs}")
    private long jwtExpirationInMs;
    @Value("${jwt.jwtExpirationActivateAccountInMs}")
    private long jwtExpirationActivateAccountInMs;
    @Value("${jwt.jwtRefreshExpirationInMs}")
    private long jwtRefreshExpirationInMs;

    public JwtAuthenticationResponse generateToken(Authentication authentication) {
        return generateToken((UserPrincipal) authentication.getPrincipal());
    }

    public JwtAuthenticationResponse generateToken(UserPrincipal userDetails) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Date refreshExpiryDate = new Date(now.getTime() + jwtRefreshExpirationInMs);

        // JWT for authentication
        String token = Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
        // JWT for refresh token
        Map<String, Object> claimsRefresh = new HashMap<>();
        String accessToken = Jwts.builder()
                .setClaims(claimsRefresh)
                .setSubject(userDetails.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(refreshExpiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret.getBytes())
                .compact();

        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        userDetails.getAuthorities().forEach(role -> {
            roles.add(role.getAuthority());
        });
        claims.put("roles", roles.toArray(new String[0]));
        // JWT for permission
        String tokenPermission = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
        return new JwtAuthenticationResponse(token, accessToken, expiryDate, tokenPermission);
    }

    public String generateSimpleToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationActivateAccountInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        return validateJWT(authToken, jwtSecret);
    }

    private boolean validateJWT(String authToken, String jwtRefreshSecret) {
        try {
            Jwts.parser().setSigningKey(jwtRefreshSecret.getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
