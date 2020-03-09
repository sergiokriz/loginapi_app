package com.concrete.prj.login.security;

import com.concrete.prj.login.exception.response.CSxRequestTimeoutException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Description: JWT token resolver
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Service
public class CScJwtTokenResolver implements CSiJwtTokenResolver {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Override
    public String generateToken(Long aUserId) {

        Date now = new Date();
        Date expiringDateTime = new Date(now.getTime() + jwtExpirationInMs);

        String jwt = Jwts.builder()
                .setSubject(Long.toString(aUserId))
                .setIssuedAt(new Date())
                .setExpiration(expiringDateTime)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return jwt;
    }

    @Override
    public Long getUserIdFromJWT(String aToken) {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(aToken)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    @Override
    public boolean validateToken(String aAuthToken) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(aAuthToken);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new CSxRequestTimeoutException("JWT token has expired.");
        }
    }

    @Override
    public String getJwtFromRequest(HttpServletRequest aHttpRequest) {

        String jwt = aHttpRequest.getHeader("Authorization");

        if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }

        return null;
    }

}
