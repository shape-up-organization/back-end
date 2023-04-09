package br.com.shapeup.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    private static final String SECRET = Encoders.BASE64.encode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437".getBytes());

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String createToken(Map<String, Object> claims, String userName, String name, String id, String accountName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .claim("id", id)
                .setSubject(name)
                .claim("name", name)
                .claim("username", accountName)
                .setSubject(userName)
                .claim("email", userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .setHeaderParam("typ", "JWT")
                .base64UrlEncodeWith(Encoders.BASE64URL)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(String userName, String name, String id, String accountName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName, name, id, accountName);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static String extractEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Decoders.BASE64.decode(SECRET))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public static String extractAccountNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Decoders.BASE64.decode(SECRET))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }
}
