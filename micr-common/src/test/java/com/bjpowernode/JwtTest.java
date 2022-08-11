package com.bjpowernode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 动力节点乌兹
 * 2022-6-10
 */
public class JwtTest {
    @Test
    public void testCreatJwt(){
        String key = "3cab490478e34389842cbfb443aa9797";
        Date curDate = new Date();

        //创建secretKey
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        //准备负载数据
        Map<String,Object> data = new HashMap<>();
        data.put("userId", 1001);
        data.put("name", "夏枯草");
        data.put("role", "库管");

        //使用Jwts类创建Jwt
        String jwt = Jwts.builder().signWith(secretKey, SignatureAlgorithm.HS256)
                     .setExpiration(DateUtils.addMinutes(curDate,10))
                     .setIssuedAt(curDate)
                     .setId(UUID.randomUUID().toString())
                     .addClaims(data).compact();

        System.out.println("jwt = " + jwt);

    }

    @Test
    public void testReadJwt(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTQ4NTAyODUsImlhdCI6MTY1NDg1MDI4NSwianRpIjoiZDEzNTkxN2MtNDBhZS00YjMyLTgxMDgtODRiZDQ4NzFjNDkxIiwicm9sZSI6IuW6k-euoSIsIm5hbWUiOiLlpI_mnq_ojYkiLCJ1c2VySWQiOjEwMDF9.l2FK3iJjGkG3GK7Ohrambe1PrpF3iMXQpKSEFCtdeEE";
        String key = "3cab490478e34389842cbfb443aa9797";

        //创建SecretKey
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        //解析jwt
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);

        //读数据
        Claims body = claims.getBody();
        Integer userId = body.get("userId",Integer.class);
        System.out.println("user = " + userId);

        Object uid = body.get("userId");
        System.out.println("uid = " + uid);

        Object name = body.get("name");
        if (name != null){
            String str = (String) name;
            System.out.println("name = " + name);
        }

        String jwtId = body.getId();
        System.out.println("jwtId = " + jwtId);

        Date expiration = body.getExpiration();
        System.out.println("过期时间 : " + expiration);
    }
}
