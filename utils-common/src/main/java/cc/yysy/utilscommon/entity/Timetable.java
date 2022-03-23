package cc.yysy.utilscommon.entity;









import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 * @author wds
 */

@Entity
@Table(name="timetable")
public class Timetable implements Serializable {

    String color;
    @Id
    String userPhone;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }



    public Integer getBegClass() {
        return begClass;
    }

    public void setBegClass(Integer begClass) {
        this.begClass = begClass;
    }

    public Integer getEndClass() {
        return endClass;
    }

    public void setEndClass(Integer endClass) {
        this.endClass = endClass;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }



    @Id
    Date date;

    @Id
    private Integer begClass;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Id
    private Integer endClass;


    private String courseName;//  "医学影像学";
    private String teacher;//  "郭少华";
    private String courseContent;// "中枢神经系统",
    private String classroom;// "206大班（黄金：一教）"

}