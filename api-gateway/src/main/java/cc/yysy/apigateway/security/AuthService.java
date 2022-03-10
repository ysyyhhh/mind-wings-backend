package cc.yysy.apigateway.security;


import cc.yysy.apigateway.service.UserService;
import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.exception.BizException;
import cc.yysy.utilscommon.utils.JWTUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.logging.Logger;

@Service
public class AuthService {
    static Logger logger = Logger.getLogger("AuthService log");


    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    UserService userService;
    /**
     * token获取用户对象
     * @param token
     * @return
     */
    public SysUser verify(String token){

        logger.info(token);
        // 获取 token 中的 userPhone
        String userPhone;
        try {
            userPhone = JWT.decode(token).getAudience().get(0);
            Date pre = JWT.decode(token).getExpiresAt();
            Date now = new Date();
            if(now.after(pre)){
                throw new RuntimeException("过期了！！！");
//                return false;
            }
        } catch (JWTDecodeException j) {
            throw new RuntimeException("token 错误 401");
        }

        //获取user对象

        SysUser user = userService.getUser(userPhone);
        if (user == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }

        logger.info(user.toString());
        //校验token与user对象是否正确
        if(JWTUtils.verify(token,user)){
            return user;
        }

        return null;
    }

    public String verifyToken(String reqPath, String token) {
        //通过token获取用户
        SysUser user = verify(token);

        //判断权限
        if(verifyPathAuth(reqPath,user)){
            return user.getUserPhone();
        }

        throw new BizException("无权限！");
    }

    private boolean verifyPathAuth(String reqPath, SysUser user) {
        logger.info(reqPath);
        String urlPermission = getUrlPermission(reqPath);
        logger.info(urlPermission);
        // 如果url仅要求验证用户有效性，则直接通过
        logger.info(String.valueOf(StringUtil.isNullOrEmpty(urlPermission)));
        logger.info(String.valueOf(urlPermission.compareTo("authc")));
        if (!StringUtil.isNullOrEmpty(urlPermission) && (urlPermission.compareTo("authc") == 0) ){
            return true;
        }

        logger.info("进一步判断用户权限");
        // 进一步判断用户权限
        if (urlPermission.startsWith("perms")) {
            Set<String> userPerms = this.getUserPermissions(user);
            String perms = urlPermission.substring(urlPermission.indexOf("[") + 1, urlPermission.lastIndexOf("]"));
            logger.info("account:"+user.toString() +  userPerms +  perms);
            return userPerms.containsAll(Arrays.asList(perms.split(",")));
        }
        return false;
    }

    /**
     * 获取所有的接口url与用户权限的映射关系,格式仿造了shiro的权限配置格式
     */
    public Map<String, String> getAllUrlPermissionsMap() {
        Map<String, String> urlPermissionsMap = Maps.newHashMap();
        urlPermissionsMap.put("/service-user/api/test", "authc");
        urlPermissionsMap.put("/service-user/api/adminTest", "perms[admin]");
//        urlPermissionsMap.put("/user-service/signup", "authc");
//        urlPermissionsMap.put("/order-service/", "perms[order]");
//        urlPermissionsMap.put("/storage-service/api/storage/**", "perms[storage]");
        return urlPermissionsMap;
    }

    public static final Integer ADMIN_TYPE = 1;
    public static final Integer USER_TYPE = 0;

    /**
     * 根据用户的唯一标识获取该用户的权限列表
     */
    public Set<String> getUserPermissions(SysUser user) {
        if (user.getUserType() == ADMIN_TYPE) {
            return Sets.newHashSet("admin", "user");
        }else if(user.getUserType() == USER_TYPE){
            return Sets.newHashSet("user");
        }
        return Collections.emptySet();
    }

    /**
     * 根据一个确定url获取该url对应的权限设置
     * 利用AntPathMatcher进行模式匹配
     */
    private String getUrlPermission(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return null;
        }
        Map<String, String> urlPermissionsMap = getAllUrlPermissionsMap();
        logger.info(urlPermissionsMap.toString());
        Set<String> urlPatterns = urlPermissionsMap.keySet();
        for (String pattern : urlPatterns) {
            boolean match = false;
            if (antPathMatcher.isPattern(pattern)) {
                match = antPathMatcher.match(pattern, url);
            } else {
                match = url.equals(pattern);
            }
            if (match) {
                return urlPermissionsMap.get(pattern);
            }
        }
        return null;
    }


}