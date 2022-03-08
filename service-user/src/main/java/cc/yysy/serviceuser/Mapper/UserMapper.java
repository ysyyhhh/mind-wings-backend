package cc.yysy.serviceuser.Mapper;


import cc.yysy.utilscommon.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

//    private Integer id;
//    private String username;
//    private String userPhone;
//    private String password;
//    private String nickname;
//    private String email;

    @Insert("INSERT INTO `sys_user`(`username`,`user_phone`,`password`,`nickname`,`email`) VALUES (" +
            "#{record.username},#{record.userPhone},#{record.password},#{record.nickname},#{record.email}" +
            ");")
    int insertSysUser(@Param("record") SysUser record);

    @Select("select password from sys_user where user_phone = #{record}")
    String selectPassword(@Param("record")String userPhone);

    @Select("select user_phone from sys_user where user_phone = #{record} or username = #{record} or email = #{record}")
    String selectPhone(@Param("record") String loginName);

    @Select("select * from sys_user where user_phone = #{record}")
    SysUser selectByPhone(@Param("record") String userPhone);
}