package cc.yysy.servicetimetable.Controller;

import cc.yysy.servicetimetable.Service.Impl.ClassListServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/classList")
@Component
public class ClassListController {



    @Resource
    ClassListServiceImpl classListService;

    @PostMapping("/insert")
    public Result insert(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);

        return Result.success(classListService.insert(params,user));
    }
    @GetMapping("/select")
    public Result params(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        return Result.success(classListService.select(user));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        return Result.success(classListService.update(params,user));
    }


    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        return Result.success(classListService.delete(params,user));
    }


}
