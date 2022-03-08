package cc.yysy.serviceuser.Controller;


import cc.yysy.serviceuser.service.impl.UserServiceImpl;
import cc.yysy.utilscommon.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Component
public class ServiceController {

    @Autowired
    UserServiceImpl userService;
    @RequestMapping("/getUser/{userPhone}")
    public SysUser getUser(@PathVariable String userPhone){
        return userService.getUser(userPhone);
    }

    @RequestMapping("/test")
    public String test(){
        return "success!";
    }
}
