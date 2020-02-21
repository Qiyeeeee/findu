package com.cpt202.cw1.findu.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cpt202.cw1.findu.pojo.User;
import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getEmail())// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为 token 的密钥
        return token;
    }
}
