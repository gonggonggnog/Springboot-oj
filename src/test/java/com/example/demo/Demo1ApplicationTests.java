package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.utils.SshUtil;
import com.jcraft.jsch.JSchException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

@SpringBootTest
class Demo1ApplicationTests {

    @Test
    void contextLoads() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "zhangsan");
        claims.put("id", 1);
        String token = JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(Algorithm.HMAC256("jwtTest"));
        System.out.println(token);
    }

    // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7Im5hbWUiOiJ6aGFuZ3NhbiIsImlkIjoxfSwiZXhwIjoxNzAzMzIxNzkxfQ.vVQlrTMYqDo97BHFnQXAEwIx1H2cdhCQB8uXRaGm8pw
    @Test
    void md5Test() {
        System.out.println(md5DigestAsHex("password".getBytes()));
    }

    @Test
    void Test() throws JSchException, IOException {
        SshUtil.runCommand("docker exec -it oj /bin/bash -c \"java -cp /usr/oj/main main\""
                , "1 2\n");
    }

    @Test
    public void test() {

    }
}
