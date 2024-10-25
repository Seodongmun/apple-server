package com.server.apple.config;

import com.server.apple.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class TokenProvider {

    private static final String SECRET_KEY = "FlRpX30MqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M10icegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String create(Member member) {
        System.out.println("토큰 만들때 member = " + member);
        return Jwts.builder()
                .signWith(secretKey)
                .setClaims(Map.of(
                 "id", member.getId(),
                 "email", member.getEmail()
        ))
        .setIssuedAt(new Date())
        .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
        .compact();

    }

    public Member validate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        System.out.println("토큰 프로바이더 토큰 = " + token);
        return Member.builder()
                .id((String) claims.get("id"))
                .email((String) claims.get("email"))
                .build();
    }

}
