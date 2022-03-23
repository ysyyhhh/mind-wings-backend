package cc.yysy.servicetimetable.mapper;

import cc.yysy.utilscommon.entity.ClassList;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ClassListMapper {

    @Insert("INSERT INTO `class_list`(`user_phone`,`time`,`beg_time`,`end_time`) VALUES (" +
            "#{record.userPhone},#{record.time},#{record.begTime},#{record.endTime}"+
            ");")
    int insert(@Param("record") Map<String,Object> params);


    @Select("select * from `class_list` where user_phone = '${userPhone}';")
    List<ClassList> select(@Param("userPhone") String userPhone );

    @Update({"<script> UPDATE class_list " +
            "<set>" +
            "        <if test='record.begTime != null'> beg_time = '${record.begTime}', </if>" +
            "        <if test='record.endTime != null'> end_time = '${record.endTime}', </if>" +
            "</set>" +
            "WHERE user_phone = '${record.userPhone}' and time = '${record.time}'"+
            "</script>"})
    int update(@Param("record") Map<String,Object> params);

    @Delete("delete from `class_list` " +
            "WHERE user_phone = '${record.userPhone}' and time = '${record.time}';")
    int delete(@Param("record")Map<String,Object> params );
}
