package com.example.authService.service;

import com.example.authService.dto.LoginDto;
import com.example.authService.entity.User;
import com.example.authService.exception.InvalidJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY = "RqxPOuVfHoBA8Uq40MhJvfY6qEHOOWWvg6N9W9vt23s=";
    private final long VALID_DURATION = 60 * 5 * 1000;

    public String generateToken(LoginDto loginDto){
        HashMap<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(loginDto.getUsername())
                .issuer(loginDto.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + VALID_DURATION))
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String jwtToken) {
        return extractClaims(jwtToken,Claims::getSubject);
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        try{
            Date expiaryDate = extractClaims(jwtToken,Claims::getExpiration);
            String usernameFromClaim = extractUsername(jwtToken);
            return (expiaryDate.after(new Date(System.currentTimeMillis()))
                    && usernameFromClaim.equals(userDetails.getUsername()));
        }
        catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new InvalidJwtException("Invalid Token",e);
        }
    }

    public boolean isTokenValid(String token){
        if (token.isBlank() || token.isEmpty()) return false;
        try{
            Date expiaryDate = extractClaims(token,Claims::getExpiration);
            if(expiaryDate == null) return false;

            return expiaryDate.after(new Date(System.currentTimeMillis()));
        }
        catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new InvalidJwtException("Invalid Token",e);
        }
    }

    private <T> T  extractClaims(String token, Function<Claims,T> claimResolver){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimResolver.apply(claims);
    }
}
