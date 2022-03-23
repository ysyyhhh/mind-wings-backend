package cc.yysy.serviceuser.Controller;


import cc.yysy.serviceuser.service.impl.UserServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Component
public class ServiceController {
    static final Logger logger = Logger.getLogger("ServiceController log");
    @Resource
    UserServiceImpl userService;


    @RequestMapping("/getUser/{userPhone}")
    public SysUser getUser(@PathVariable("userPhone") String userPhone){
        System.out.println("getUser " + userPhone);
        return userService.getUser(userPhone);
    }

    @RequestMapping("/test")
    public String test(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        logger.info(userStr);
        logger.info(user.getUsername());
        return "success!";
    }

    @RequestMapping("/adminTest")
    public String adminTest(){
        return "success!";
    }



    @GetMapping("testToken")
    public Result testToken(){
        return Result.success("token验证正确！！！");
    }

    @GetMapping("adminTestToken")
    public Result adminTestToken(){
        return Result.success("admin token验证正确！！！");
    }

    @GetMapping("/getUser")
    public Result getUser(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);

        return Result.success(user);
    }

    @GetMapping("/getCoupleToken")
    public Result getCoupleToken(){
        logger.info("getCoupleToken");
//        console.log(getCoupleToken);
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);

        return Result.success(userService.getCoupleToken(user));
    }
}
