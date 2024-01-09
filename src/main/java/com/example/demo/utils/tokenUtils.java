package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.UserBasic;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public interface tokenUtils {
    static String getToken(UserBasic userBasic) {
        userBasic.setIsAdmin(0);
        Map<String, Object> claims = Map.of(
                "identity", Objects.requireNonNull(userBasic.getIdentity(), "Identity cannot be null"),
                "name", Objects.requireNonNull(userBasic.getName(), "Name cannot be null"),
                "isAdmin", Objects.requireNonNull(userBasic.getIsAdmin(), "IsAdmin cannot be null")
        );
        return JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(Algorithm.HMAC256("my-gin-oj-project"));
    }
    static Map<String, Object> parseToken(String token) {
        JWTVerifier jwt = JWT.require(Algorithm.HMAC256("my-gin-oj-project")).build();
        DecodedJWT decode = jwt.verify(token);
        return decode.getClaim("user").asMap();
    }
}
