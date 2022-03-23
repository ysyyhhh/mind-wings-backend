package cc.yysy.servicetimetable.mapper;

import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.entity.Timetable;
import org.apache.ibatis.annotations.*;
import org.junit.runners.Parameterized;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TimetableMapper {


    @Insert("INSERT INTO `timetable`(`user_phone`,`date`,`beg_class`," +
            "`end_class`,`course_name`,`teacher`,`course_content`,`classroom`,`color`) VALUES (" +
            "#{record.userPhone},#{record.date},#{record.begClass}," +
            "#{record.endClass},#{record.courseName},#{record.teacher},#{record.courseContent},#{record.classroom}," +
            "#{record.color}" +
            ");")
    int insert(@Param("record") Map<String,Object> params);


    @Select("select * from `timetable` where user_phone = #{record.userPhone} and " +
            "(date between #{record.begDate} and #{record.endDate});")
    List<Timetable> selectByDate( @Param("record") Map<String,Object> params);

    @Update({"<script> UPDATE timetable " +
            "<set>" +
            "        <if test='record.color != null and record.color != \" \" '> color = '${record.color}', </if>" +
            "        <if test='record.courseName != null'> course_name = '${record.courseName}', </if>" +
            "        <if test='record.teacher != null'> teacher = '${record.teacher}',</if>" +
            "        <if test='record.courseContent != null'> course_content = '${record.courseContent}',</if>" +
            "        <if test='record.classroom != null'> classroom = '${record.classroom}',</if>" +
            "</set>" +
            "WHERE user_phone = '${record.userPhone}' and date = '${record.date}' and beg_class = '${record.begClass}' and end_class = '${record.endClass}'; " +
            "</script>"})
    int update(@Param("record")  Map<String,Object> params);

    @Update("update timetable set color = #{record.color} " +
            "where user_phone = #{record.userPhone} and course_name = #{record.courseName}")
    int updateCourseColor(@Param("record") Map<String,Object> params );

    @Delete({"<script> delete from timetable " +
            "WHERE user_phone = '${record.userPhone}' and date = '${record.date}' and beg_class = '${record.begClass}' and end_class = '${record.endClass}'; " +
            "</script>"})
    int delete(@Param("record")Map<String,Object> params );


}
