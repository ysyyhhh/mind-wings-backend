package cc.yysy.utilscommon.entity;











import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户
 * @author wds
 */

@Entity
@Table(name="class_list")
public class ClassList implements Serializable {

    @Id
    String userPhone;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Id
    String time; //1

    String begTime;//08:30

    String endTime;//09:10

}