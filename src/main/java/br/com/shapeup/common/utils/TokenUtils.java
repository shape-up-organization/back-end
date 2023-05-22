package br.com.shapeup.common.utils;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.vavr.control.Try;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
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

    public static String updateUserAndGenerateNewToken(String jwtToken, UserRequest userRequest) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSignKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        for (Field field : userRequest.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Try.run(() -> {
                Object value = field.get(userRequest);
                if (value != null) {
                    claims.put(field.getName(), value);
                }
            }).onFailure(exception -> exception.printStackTrace());
        }

        return jwtService.generateTokenFromClaims(claims);
    }
}
