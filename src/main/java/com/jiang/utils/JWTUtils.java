package com.jiang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jiang.dao.UserDO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * JWT工具类
 */
public class JWTUtils {

    private static final String SECRET = "nihao";

    private static final long EXPIRE = 604800;

    /**
     * 获取token
     * @param user 用户对象
     * @return 返回token
     */
    public static String getToken(UserDO user) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * EXPIRE);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                // 设置载荷信息
                .claim("uid", user.getId())
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(expireDate);

        //生成JWT
        return builder.compact();
    }

    /**
     * 获取token中的参数
     *
     * @param token 传入的token值
     * @return 返回用户id
     */
    public static Long getIdByToken(String token) {
        Long uid = null;
        DecodedJWT jwt = null;
        try {
            jwt = JWT.decode(token);
            uid = jwt.getClaim("uid").asLong();
            return uid;
        } catch (JWTDecodeException exception) {
            //Invalid token
        }
        return uid;
    }

}