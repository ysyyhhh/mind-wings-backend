package cc.yysy.serviceuser.service;

import cc.yysy.serviceuser.bean.*;
import cc.yysy.utilscommon.entity.SysUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    /**
     * 注册1 密钥交付
     * @param userPhone
     * @return 公钥
     */
    public String getPubKey(String userPhone);

    /**
     * 注册2 正式注册
     * @param sysUser
     * @return
     */
    public boolean signup(SysUser sysUser);

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    public boolean login(String loginName, String password);



    /**
     * 登出
     * @param token
     */
    public void logout(String token);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public SessionUserBean getUserByToken(String token);
}
