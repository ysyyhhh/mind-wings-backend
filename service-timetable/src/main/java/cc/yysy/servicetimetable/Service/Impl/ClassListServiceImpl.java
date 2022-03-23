package cc.yysy.servicetimetable.Service.Impl;

import cc.yysy.servicetimetable.mapper.ClassListMapper;
import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.entity.ClassList;
import cc.yysy.utilscommon.entity.ClassList;
import cc.yysy.utilscommon.utils.ClassUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ClassListServiceImpl {

    static Logger logger = Logger.getLogger("ClassListServiceImpl");
    @Resource
    ClassListMapper classListMapper;
            

    public Object insert(Map<String, Object> params, SysUser user) {
        params.put("userPhone",user.getUserPhone());
//        ClassList.setUserPhone(user.getUserPhone());
        return classListMapper.insert(params);
    }

    /**
     *  根据日期区间选择
     * @param
     * @param user
     * @return
     */
    public Object select(SysUser user) {
        logger.info(user.getUserPhone());
        return classListMapper.select(user.getUserPhone());
    }

    /**
     * 更新一条数据
     * @param params
     * @param user
     * @return
     */
    public Object update(Map<String, Object> params, SysUser user){
        params.put("userPhone",user.getUserPhone());
        return classListMapper.update(params);
    }


    /**
     * 删除某节课程
     * @param params
     * @param user
     * @return
     */
    public Object delete(Map<String, Object> params, SysUser user){
        params.put("userPhone",user.getUserPhone());
        return classListMapper.delete(params);
    }
}
