package cc.yysy.serviceuser.service.impl;

import cc.yysy.serviceuser.Mapper.UserMapper;
import cc.yysy.serviceuser.bean.SessionUserBean;
import cc.yysy.serviceuser.service.UserService;
import cc.yysy.utilscommon.entity.SysUser;
import cc.yysy.utilscommon.exception.BizException;
import cc.yysy.utilscommon.utils.HASHUtils;
import cc.yysy.utilscommon.utils.IOUtils;
import cc.yysy.utilscommon.utils.RsaUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    static Logger logger = Logger.getLogger("UserServiceImpl log");

    @Resource
    UserMapper userMapper;

    public static UserServiceImpl userService;

    //必须写上的，不能删
    @PostConstruct
    public void init(){
        userService=this;
    }

    public SysUser getUser(String userPhone){
        SysUser sysUser = userMapper.selectByPhone(userPhone);
        return sysUser;
    }

    /**
     * 登录和注册前都需要先获取RSA公钥
     * @param loginName
     * @return
     */
    @Override
    public String getPubKey(String loginName) {
        String pubKeyStr = null;
        String userPhone = getUserPhone(loginName);
        logger.info("获取到的手机号："+userPhone);
//        if(userPhone != null){
//            throw new BizException("已经存在用户名 | 手机号 | 邮箱");
//        }

        if(userPhone == null) userPhone = loginName;
        try {
            //找一下这个手机号之前有没有存过
            File file=new File("keys/pubKeys/"+userPhone+".dll");
            logger.info("===================");
            logger.info("开始尝试获取密钥");
            if(!file.exists())
            {
                logger.info("不存在，生成密钥");
                //如果不存在就生成
                RsaUtils.RsaKeyPair keyPair = RsaUtils.generateKeyPair();
//                System.out.println(keyPair);

                // 获取 公钥 和 私钥
                String pubKey = keyPair.getPublicKey();
                String priKey = keyPair.getPrivateKey();

                logger.info("用户 "+userPhone +"得到密钥：");

                logger.info("公钥 + "+pubKey);
                logger.info("私钥 + "+priKey);
                // 保存 公钥 和 私钥
                IOUtils.writeFile(pubKey, new File("keys/pubKeys/"+userPhone+".dll"));
                IOUtils.writeFile(priKey, new File("keys/priKeys/"+userPhone+".dll"));

                logger.info("公钥 + "+IOUtils.readFile(new File("keys/pubKeys/"+userPhone+".dll")));
                logger.info("私钥 + "+IOUtils.readFile(new File("keys/priKeys/"+userPhone+".dll")));
            }

            //因为需要BASE64格式的公钥，所以直接读文件就可以了。
            pubKeyStr = IOUtils.readFile(new File("keys/pubKeys/"+userPhone+".dll"));
            logger.info("得到公钥" + pubKeyStr);
            return pubKeyStr;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 注册
     * @param sysUser
     * @return
     */
    @Override
    public boolean signup(SysUser sysUser) {
        // 读取私钥文件, 创建私钥对象
        try {
            String priKey = IOUtils.readFile(new File("keys/priKeys/"+sysUser.getUserPhone()+".dll"));
            String password = sysUser.getPassword();
            logger.info("注册开始 当前用户发送的密码是" + password);
            logger.info("注册开始 当前的私钥是" + priKey);

            //RSA解密
            String newPassword = RsaUtils.decryptByPrivateKey(priKey,password);
            logger.info("解密后的密码是 "+newPassword);

            //加盐哈希
            String finalPassword = HASHUtils.saltEncode(newPassword);
            sysUser.setPassword(finalPassword);

            logger.info("加盐hash后的密码是 "+finalPassword);
            //存入用户信息到数据库中
            logger.info("存入用户信息");
            int res = userMapper.insertSysUser(sysUser);
            logger.info("存入结果 " + res);
            return res == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取用户的Phone
     * @param loginName
     * @return
     */
    private String getUserPhone(String loginName){
        return userMapper.selectPhone(loginName);
    }
    @Override
    public boolean login(String loginName, String password) {
        String priKey = null;
        //获取用户的手机号
        String userPhone = getUserPhone(loginName);
        logger.info("获取到的手机号为 "+userPhone);
        if(userPhone == null){
            throw new BizException("已经存在用户名 | 手机号 | 邮箱");
        }
        try {
            logger.info("用户手机号为 "+userPhone);
            logger.info("用户密码为 "+password);

            priKey = (IOUtils.readFile(new File("keys/priKeys/"+userPhone+".dll")));
            // 用私钥解密数据
            logger.info("取出私钥");
            logger.info(priKey);
//            byte[] plainData = RSAUtils.decrypt(password, priKey);
//            String newPassword = new String(plainData);
            String newPassword = RsaUtils.decryptByPrivateKey(priKey,password);
            System.out.println("解密后的密码：" + newPassword);
            //从数据库取出密码
            String userPassword = userMapper.selectPassword(userPhone);

            System.out.println("数据库取出的密码为：" + newPassword);
//            //匹配
            boolean match = HASHUtils.saltMatches(newPassword,userPassword);
            if(!match) {
                System.out.println("不匹配，登录失败！");
                return false;
            }
//
//            //登录成功
            System.out.println("登录成功！！！");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public SessionUserBean getUserByToken(String token) {
        return null;
    }
}
