package com.company.toDoList.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log
public class JwtTokenProvider { // class for generating token from username

    @Value("$(jwt.secret)")
    private String jwtSecret;

    @Value("$(jwt.expiration)")
    private String expiration;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)//логин пользователя, чтобы потом его оттуда забрать в фильтре, когда пользователь будет делать запрос.
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // after 15 days, token shod be upgraded
                .signWith(SignatureAlgorithm.HS512, jwtSecret)//algorithm for encoding, and secret word for decoding
                .compact();
    }

    //to check the validity of token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token); //parseClaimsJws - throws exceptions
            return true;
        } catch (ExpiredJwtException expEx) {
            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
        } catch (SignatureException sEx) {
            log.severe("Invalid signature");
        } catch (Exception e) {
            log.severe("invalid token");
        }
        return false;
    }

    //to get username from token
    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
