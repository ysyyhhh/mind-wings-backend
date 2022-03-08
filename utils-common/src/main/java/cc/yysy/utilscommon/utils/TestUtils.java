package cc.yysy.utilscommon.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUtils {
    /**
     * 客户端加密, 返回加密后的数据
     */
//    private static byte[] clientEncrypt(byte[] plainData, File pubFile) throws Exception {
        // 读取公钥文件, 创建公钥对象
//        PublicKey pubKey = RSAUtils.getPublicKey(IOUtils.readFile(pubFile));

//        // 用公钥加密数据
//        byte[] cipher = RSAUtils.encrypt(plainData, pubKey);
//
//        return cipher;
//    }

    /**
     * 服务端解密, 返回解密后的数据
     */
//    private static byte[] serverDecrypt(byte[] cipherData, File priFile) throws Exception {
        // 读取私钥文件, 创建私钥对象
//        PrivateKey priKey = RSAUtils.getPrivateKey(IOUtils.readFile(priFile));

        // 用私钥解密数据
//        byte[] plainData = RSAUtils.decrypt(cipherData, priKey);

//        return plainData;
//    }



}
