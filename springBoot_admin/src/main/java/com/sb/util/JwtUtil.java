package com.sb.util;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * author: dyq
 * Time: 2023/3/19
 * description: 描述
 */
@Data
@Component
@ConfigurationProperties(prefix = "admin.jwt")
public class JwtUtil {
    private long expire;
    private String secret;
    private String header;

    Date now = new Date();
    Date expireDate = new Date(now.getTime() + 604800000);

    //生成jwt
    public String generateToken(String username) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //解析jwt
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
    //jwt是否过期
    public boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}