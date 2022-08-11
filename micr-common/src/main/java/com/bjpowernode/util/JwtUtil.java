package com.bjpowernode.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 动力节点乌兹
 * 2022-6-11
 */
public class JwtUtil {
    private String selfKey;

    public JwtUtil(String selfKey) {
        this.selfKey = selfKey;
    }

    //创建jwt
    public String createJwt(Map<String,Object> data,Integer minute) throws Exception{
        Date curDate = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor(selfKey.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder().signWith(secretKey,SignatureAlgorithm.HS256)
                    .setIssuedAt(curDate)
                    .setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase())
                    .addClaims(data)
                    .setExpiration(DateUtils.addMinutes(curDate, 10))
                    .compact();
        return jwt;
    }

    //读取jwt
    public Claims readJwt(String jwt) throws Exception{
        SecretKey secretKey = Keys.hmacShaKeyFor(selfKey.getBytes(StandardCharsets.UTF_8));
        Claims body = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        return body;
    }
}
