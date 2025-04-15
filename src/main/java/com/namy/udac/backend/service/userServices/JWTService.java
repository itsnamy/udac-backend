package com.namy.udac.backend.service.userServices;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    // Secret key for signing JWT tokens
    private String secretKey = "";

    // Constructor to generate a secret key for JWT signing when JWTService is initialized
    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256"); // Specify algorithm for key
            SecretKey sk = keyGen.generateKey(); // Generate the secret key
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded()); // Encode the key to a Base64 string
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw runtime exception if algorithm is not available
        }
    }

    // Method to generate a JWT token
    public String generateToken(String username, String role) {

        long expirationTime = 1000 * 60 * 60 * 24;      // 24 hours in milliseconds

        return Jwts.builder()
                .claims()                               // Start building the claims
                .add("role", role)                  // Add claims role to the JWT token
                .subject(username)                      // Set the subject to the username
                .issuedAt(new Date(System.currentTimeMillis())) // Set the issued date to now   
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // Set token expiration time to 24 hours
                .and()
                .signWith(getKey())                     // Sign the token with the secret key    
                .compact();                             // Compact the JWT to a string
    }

    public String generateResetPasswordToken(String username, long expirationMillis) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getKey())
                .compact();
    }

    //Converts the Base64-encoded string to a usable secret key for signing
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);    // Decode the Base64 string
        return Keys.hmacShaKeyFor(keyBytes);                    // Create the signing key using HMAC SHA-256
    }

    // Validates the token by checking if the username matches and it's not expired
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        //final String tokenRole = extractClaim(token, claims -> claims.get("role", String.class));

        return userName.equals(userDetails.getUsername())       // check if username matches
            && !isTokenExpired(token);                          // check if token is not expired
    }

    // Extracts the username (subject) from the JWT token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generic method to extract any claim using a resolver function
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    // Extracts all claims from the token after verifying its signature
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Checks if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extracts the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
}
