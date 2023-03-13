package br.com.shapeup.common.config.security.config;

import br.com.shapeup.adapters.output.repository.model.UserEntity;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWtTokenUtil {
    private static final long EXPIRE_DURATION = 25 * 60 * 60 * 1000;

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .setSubject(userEntity.getId() + "," + userEntity.getUsername())
                .setIssuer("shapeUp")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
