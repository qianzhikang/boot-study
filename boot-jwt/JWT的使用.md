# JWT（JSON WEB TOKEN）

## 简介

官网：https://jwt.io/introduction

## 传统的session认证和token认证

### session

1. 客户端向服务端发起账号密码进行校验，

2. 后台验证账号密码成功后，在session存入账号信息，返回session id，
3. 客户端将id存入cookie，每次请求携带session id。
4. 后端收到session id 进行查找取出session中的信息。

**存在的问题**：

- session保存服务端开销大。

- 多台服务器需要实现session共享

### token

1. 客户端向服务端发起账号密码进行校验
2. 后台检验账号密码成功后，生成token字符串返回给客户端。
3. 客户端收到token后，保存在本地，后续请求将token放在请求头一并发送。
4. 服务端接收后判断是否合法。



## JWT的构成

第一部分

Header：头，是一个json串，保存jwt的元数据 ，如 签名的加密方式和类型：

```json
{
	"alg":"HS256",

	"typ":"JWT"
}
```

第二部分

Payload：载荷，是一个json串，保存有效信息，含有：

- 公共的声明

- 私有的声明

第三部分

Signature：签名，需要编码后的Header、编码后的Payload和secret（加密盐）。

## 验证JWT的步骤

1. 服务端收到JWT之后，对第一部分进行解码，拿到加密方式。
2. 使用这种加密方式和secret对字符串的第一部分和第二部分进行加密，得到一个签名，如果一致则认为token合法。
3. 再对第二部分解码，得到需要的数据。



## 整合JJWT

> 1. 加入依赖

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.0</version>
</dependency>
```

> 2. 编写JWT生成工具类

```java
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

```

> 3. 运行main函数测试

![image-20220423015146127](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204230151326.png)

