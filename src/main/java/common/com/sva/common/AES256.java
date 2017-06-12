package com.sva.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.common.base.Throwables;
import com.google.common.io.BaseEncoding;


public class AES256 {

    private static final String ALGORITHM_AES256 = "AES/CBC/PKCS5Padding";
    private static final String iv = "0000000000000000";
    private static Logger Log = Logger.getLogger(AES256.class);
    /** 
     * @Title: startMain 
     * @Description: AES256加密/解密
     * @param message 要加密/解密的信息
     * @param type 类型0：加密 1：解密
     * @return 
     */
    public static  String startMain(HttpServletRequest request,String message,int type)
    {
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/java_keystore/wj_aec256-keystore.jck");
//        String path = System.getProperty("user.dir");
//        path = path.substring(0, path.indexOf("bin"))+"webapps/SVAProject/WEB-INF";
//        path = path.substring(1, path.indexOf("/classes"));
//        path = path + File.separator + "java_keystore" + File.separator;
        //kestore路径
        String keystoreFileLocation = path;
        String storePass = "SVA_demo@123";
        String alias = "jceksaes";
        String keyPass = "wjwoaini1314@";
        Log.debug("AES256 keystoreFileLocation："+keystoreFileLocation);
        Key key = getKeyStore(keystoreFileLocation, storePass, alias, keyPass);
        String result = getCipher(key.getEncoded(), iv.getBytes(), message, type);
        Log.debug("AES256 startMain:messge:"+message+" type:"+type+" result:"+result+" length:"+result.length());
        return result;
    }
    
    /** 
     * @Title: getKeyFromKeyStore 
     * @Description: 根据路径获取Key
     * @param keystoreFileLocation 路径
     * @param storePass 密码
     * @param alias 别名
     * @param keyPass key密码
     * @return 
     */
    private static Key getKeyStore(final String keystoreFileLocation, final String storePass, final String alias, final String keyPass)
    {
        Key key = null;
        try {
            InputStream keystoreStream = new FileInputStream(keystoreFileLocation);
            KeyStore keystore = KeyStore.getInstance("JCEKS");
            //加载keyStore
            keystore.load(keystoreStream, storePass.toCharArray());
            if (!keystore.containsAlias(alias)) {
               Log.debug("AES256 未找到别名为："+alias);
            }
            key = keystore.getKey(alias, keyPass.toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
    
    /** 
     * @Title: getCipher 
     * @Description: 根据key以及iv加密或者解密message
     * @param key key
     * @param iv 密匙
     * @param message 信息
     * @param type 0：加密，1解密
     * @return 
     */
    private static String getCipher(byte[] key,byte[] iv,String message,int type) 
    {
        //secretkeyspec获取key
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        //获取密匙
        IvParameterSpec ivp = new IvParameterSpec(iv);
        Cipher cipher = null;
        try {
            //获取Cipher类 
           cipher = Cipher.getInstance(ALGORITHM_AES256);
        } catch (NoSuchAlgorithmException e) {
            Log.debug("AES256 getCipher error:"+e.getMessage());
        } catch (NoSuchPaddingException e) {
            Log.debug("AES256 getCipher error:"+e.getMessage());
        }
            if (type==0) {
                //加密
                try {
                    //初始化Cipher类 为加密
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivp);
                    //编码转换
                    byte[] encryptedTextBytes = cipher.doFinal(message.getBytes("UTF-8"));
                    //返回值解密信息
                    return BaseEncoding.base64().encode(encryptedTextBytes);
                } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                    throw Throwables.propagate(e);
                }
            }else
            {
                //解密
                try {
                  //初始化Cipher类 为解密
                    cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivp);
                    //编码转换
                    byte[] encryptedTextBytes = BaseEncoding.base64().decode(message);
                    //解密
                    byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
                    //返回值解密信息
                    return new String(decryptedTextBytes);
                } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                    throw Throwables.propagate(e);
                } 
            }
    }
    
   
}
