package co.edu.iudigital.helpmeiud.utils;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public interface Constants {

    String HEADER_AUTH = "Authorization";
    String PREFIX_TOKEN = "Bearer ";
    Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    long TOKEN_EXPIRATION_TIME = 3600000L * 4; // 4 hours
    
}
