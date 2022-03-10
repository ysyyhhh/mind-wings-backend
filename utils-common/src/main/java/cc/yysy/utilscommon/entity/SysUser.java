package cc.yysy.utilscommon.entity;





import javax.persistence.*;
import java.sql.Timestamp;


/**
 * 用户
 * @author wds
 */

@Entity
@Table(name="sys_user")
public class SysUser {
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Id
    private String userPhone;

    @Column(unique=true)
    private String username;
    private String password;
    private String nickname;

    @Column(unique=true)
    private String email;
    private Timestamp gmtCreate; //用户创建时间
    private Timestamp gmtUpdate; //用户活动时间
    private Integer userType; //用户权限

    @Override
    public String toString() {
        return "SysUser{" +
                "userPhone='" + userPhone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", userType=" + userType +
                '}';
    }
}