package co.edu.iudigital.helpmeiud.utils;

import java.util.Date;

import static co.edu.iudigital.helpmeiud.utils.Constants.SECRET_KEY;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenSetUp {
    public static String token;

    public static String generateToken(Claims claims, String username) {
        String newToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .compact();

        token = newToken;
        return token;
    }

    public static Claims getClaims(String token) {

        return Jwts.parserBuilder().setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
