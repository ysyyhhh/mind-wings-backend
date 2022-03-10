package cc.yysy.serviceuser.service;

import cc.yysy.serviceuser.bean.*;
import cc.yysy.utilscommon.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public String login(String loginName, String password);



    /**
     * 登出
     * @param token
     */
    public void logout(String token);

}
