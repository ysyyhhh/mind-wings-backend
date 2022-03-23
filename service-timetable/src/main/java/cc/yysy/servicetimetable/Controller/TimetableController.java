package cc.yysy.servicetimetable.Controller;

import cc.yysy.servicetimetable.Service.Impl.TimetableServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/timetable")
@Component
public class TimetableController {



    @Resource
    TimetableServiceImpl timetableService;
    @PostMapping("/insert")
    public Result insert(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);

        return Result.success(timetableService.insert(params,user));
    }
    @PostMapping("/selectByDate")
    public Result params(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        return Result.success(timetableService.selectByDate(params,user));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        return Result.success(timetableService.update(params,user));
    }

    @PostMapping("/updateCourseColor")
    public Result updateCourseColor(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        return Result.success(timetableService.updateCourseColor(params,user));
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        SysUser user = (SysUser) JSONObject.toJavaObject( JSONObject.parseObject(userStr),SysUser.class);
        return Result.success(timetableService.delete(params,user));
    }


}
