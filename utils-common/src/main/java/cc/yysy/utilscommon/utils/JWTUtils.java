package cc.yysy.utilscommon.utils;


import cc.yysy.utilscommon.entity.SysUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;
import java.util.Date;


public class JWTUtils {

    static Logger logger = Logger.getLogger("JWTUtils log");
    @Value("${audience.name}")
    private String name;
    @Value("${audience.expiresSecond}")
    private Long expiresSecond;



    /**
     * 设置用户的token
     * 私有，只允许被getToken调用
     * @param user
     * @return
     */
    private String setToken(SysUser user){
        String token="";
        Date now = new Date();
        Date expiresTime = new Date(now.getTime() + expiresSecond);
        token= JWT.create()
                .withAudience(user.getUserPhone())
                .withIssuer(name)
                .withIssuedAt(now)
                .withExpiresAt(expiresTime)
                .sign(Algorithm.HMAC256(user.getPassword()));

        logger.info(now.toString());
        logger.info(expiresTime.toString());
//        redisUtils.set(user.getUserPhone(),token,expiresSecond/2/1000);
        return token;
    }

    /**
     * 获取用户的Token
     * @param user
     * @return     */
    public String getToken(SysUser user) {

//        if(redisUtils.hasKey(user.user_id)){
//            return (String) redisUtils.get(user.user_id);
//        }
        return setToken(user);
    }

    /**
     * 验证该token 和 用户的区别
     * @param token
     * @param user
     * @return
     */
    public boolean verify(String token,SysUser user){
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token不合法！");
        }
        return true;
    }
}
