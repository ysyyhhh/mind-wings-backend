package cc.yysy.servicetimetable.Service.Impl;

import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.entity.Timetable;
import cc.yysy.utilscommon.utils.ClassUtils;
import cc.yysy.servicetimetable.mapper.TimetableMapper;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class TimetableServiceImpl {
    static Logger logger = Logger.getLogger("TimetableServiceImpl");
    @Resource
    TimetableMapper timetableMapper;

    public Date getDate(Integer year, Integer month, Integer day) throws ParseException {
        Date nDate = new Date();
        logger.info(year + " " + month + " "  + day);
        String dateStr = Integer.toString(year) + "-"+((month < 10) ? "0":"") +Integer.toString(month) +"-"+ ((day < 10) ? "0":"")+Integer.toString(day);

        logger.info(year + " " + month + " "  + day);
        logger.info(dateStr);
        nDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        return nDate;
    }
    public Map<String, Object>  changeDate(Map<String, Object> params){
        Date nDate = new Date();
        try {
            nDate = getDate((Integer) params.get("year"),(Integer)params.get("month"),(Integer)params.get("day") );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        params.remove("year");params.remove("month");params.remove("day");
        params.put("date",nDate);
        return params;
    }

    public Object insert(Map<String, Object> params, SysUser user) {
        params.put("userPhone",user.getUserPhone());
//        timetable.setUserPhone(user.getUserPhone());
        params = changeDate(params);
        Date nDate = (Date) params.get("date");
        logger.info(nDate.getYear() + " "+ nDate.getMonth() + " " + nDate.getDate());

        logger.info(nDate.toGMTString());
//        logger.info(params.get("date").toString());
        return timetableMapper.insert(params);
    }

    /**
     *  根据日期区间选择
     * @param params
     * @param user
     * @return
     */
    public Object selectByDate(Map<String, Object> params, SysUser user) {
        params.put("userPhone",user.getUserPhone());
        Date begDate = new Date();
        try {
            begDate = getDate((Integer) params.get("begYear"),(Integer)params.get("begMonth"),(Integer)params.get("begDay") );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        params.remove("begYear");params.remove("begMonth");params.remove("begDay");
        params.put("begDate",begDate);

        Date endDate = new Date();
        try {
            endDate = getDate((Integer) params.get("endYear"),(Integer)params.get("endMonth"),(Integer)params.get("endDay") );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        params.remove("endYear");params.remove("endMonth");params.remove("endDay");
        params.put("endDate",endDate);


        return timetableMapper.selectByDate((params));
    }

    /**
     * 更新一条数据
     * @param params
     * @param user
     * @return
     */
    public Object update(Map<String, Object> params, SysUser user){
        params.put("userPhone",user.getUserPhone());
        params = changeDate(params);
//        logger.info(par)
        params.put("date",new SimpleDateFormat("yyyy-MM-dd").format(params.get("date")));
        return timetableMapper.update((params));
    }

    /**
     * 更新某个课程的颜色
     * @param params
     * @param user
     * @return
     */
    public Object updateCourseColor(Map<String, Object> params, SysUser user){
        Timetable timetable = new Timetable();
        params.put("userPhone",user.getUserPhone());
        return timetableMapper.updateCourseColor((params));
    }

    /**
     * 删除某节课程
     * @param params
     * @param user
     * @return
     */
    public Object delete(Map<String, Object> params, SysUser user){
        Timetable timetable = new Timetable();
        params.put("userPhone",user.getUserPhone());
        return timetableMapper.delete(changeDate(params));
    }
}
