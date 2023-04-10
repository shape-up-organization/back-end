package br.com.shapeup.common.utils;

import br.com.shapeup.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URL;

public class TokenUtils {

    private static JwtService jwtService = new JwtService();

    public static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }

    public static String updateProfilePictureAndGenerateNewToken(String tokenJwt, URL url) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSignKey())
                .build()
                .parseClaimsJws(tokenJwt)
                .getBody();

        claims.put("profilePicture", url.toString());
        String newToken = jwtService.generateTokenFromClaims(claims);

        return newToken;
    }
}
