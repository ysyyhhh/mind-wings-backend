package cc.yysy.serviceuser.Controller;



import cc.yysy.serviceuser.service.impl.UserServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Component
public class UserController {
    @Autowired
    UserServiceImpl userService;
    /**
     * 注册1 密钥交付
     * @param params
     * @return 公钥
     */


    @PostMapping("/getPubKey")
    public Result getPubKey(@RequestBody Map<String,Object> params){
        String loginName = (String) params.get("loginName");
        if(loginName == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        String pubKey = userService.getPubKey(loginName);
        if(pubKey == null) {
            return Result.error(ResultCode.SYSTEM_ERROR);
        }
        return Result.success(pubKey);
    }

    /**
     * 注册2 正式注册
     * @param sysUser
     * @return
     */
    @PostMapping("/signup")
    public Result signup(@RequestBody SysUser sysUser){
//        SysUser user = new SysUser();
//        SysUser sysUser = (SysUser) ClassUtils.MapToObject(user,params);
        if(userService.signup(sysUser)){
            return Result.success("注册成功!");
        }
        return Result.error(-1,"注册失败!");
    }


    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,Object> params) throws UnsupportedEncodingException {
        String loginName = (String) params.get("loginName");
        String password = (String) params.get("password");
        System.out.println("password  :" + password);
        password = URLDecoder.decode(password,"UTF-8");
        System.out.println("utf-8 : " + password);

        String token = userService.login(loginName, password);
        if(!StringUtils.isNullOrEmpty(token)){
            return Result.success(token);
        }
        return  Result.error(ResultCode.USER_LOGIN_ERROR);
    }






}
