package com.jessica.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static String ISSUER = "jessica";
    private static String JWT_CLAIM = "jwt_claim";
    private static String BEARER = "Bearer";
    private static String DELIMITER = " ";

    public static String getTokenStr(JwtClaim jwtClaim, long ttlMillis, String rsaPrivateKey) throws Exception {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        PrivateKey key = RSAUtil.getPrivateKey(rsaPrivateKey);
        JwtBuilder builder = Jwts.builder()
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.RS256);
        if(ttlMillis >= 0){
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        builder.claim(JWT_CLAIM, jwtClaim);
        return String.join(DELIMITER, BEARER, builder.compact());
    }

    public static JwtClaim parseToken(String jwtToken, String publicKey) throws Exception{
        String[] splitStrAry = jwtToken.split(DELIMITER);
        if (splitStrAry.length != 2) {
            throw new RuntimeException("Invalid jwt token");
        }
        String payload = splitStrAry[1];
        Claims claims = Jwts.parser()
                .setSigningKey(RSAUtil.getPublicKey(publicKey))
                .parseClaimsJws(payload)
                .getBody();
        Object claimMap = claims.get(JWT_CLAIM);
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(claimMap), JwtClaim.class);
    }
}
