package com.example.boot.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description TODO
 * @Date 2022-04-21-20-19
 * @Author Courage
 */
public class JwtDemo {

    /**
     * 私钥，生成签名
     */
    private final static String SECRET = "123456789";


    public static String createJwtToken() {

        //头
        Map<String, Object> header = new HashMap<>(4);
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        //载荷
        Map<String, Object> claims = new HashMap<>(8);
        claims.put("iss", "qzk");
        claims.put("id", "123");
        claims.put("userName", "admin");

        //生成jwt
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                //设置jwi
                .setId(UUID.randomUUID().toString())
                //设置签发时间
                .setIssuedAt(new Date())
                //设置过期时间 1小时
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                //面向谁使用（非必要）
                .setSubject("tom")
                //用过签名算法和密钥生成签名
                .signWith(SignatureAlgorithm.HS256,SECRET)
                //生成jwt串
                .compact();
    }


    /**
     * 获取载荷
     * @return
     */
    public static Claims getClaims(String jwt){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
    }


    public static void main(String[] args) {
        String jwtToken = createJwtToken();
        System.out.println("Jwt Token: " + jwtToken);

        Claims claims = getClaims(jwtToken);
        System.out.println("claims: " + claims);
    }
}
