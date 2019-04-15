package com.jzx.personal.utils;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {
    // region MD5加密
    /**
     * 对字符串md5加密
     * @param inputs 要加密的字符串
     * @return 32位小写字符串
     */
    public static String md5(@NonNull String inputs) {
        try {
            byte[] bytes = inputs.getBytes("UTF-8");
            return md5(bytes);
        } catch (UnsupportedEncodingException e) {
            //
        }
        return null;
    }

    /**
     * 对byte数组md5加密
     * @param bytes 要加密的byte数组
     * @return 32位小写字符串
     */
    public static String md5(@NonNull byte[] bytes){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(bytes);
            return bytes2HexStr(digest.digest());
        }catch (NoSuchAlgorithmException e){
            //
        }
        return null;
    }

    // endregion

    //region SHA1加密
    /**
     * 对byte数组sha1加密
     * @param bytes 待加密byte数组
     * @return 40位小写字符串
     */
    public static String sha1(@NonNull byte[] bytes){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(bytes);
            return bytes2HexStr(digest.digest());
        }catch (NoSuchAlgorithmException e){
            //
        }
        return null;
    }

    /**
     * 对字符串sha1加密
     * @param inputs 待加密字符串
     * @return 40位小写字符串
     */
    public static String sha1(@NonNull String inputs){
        try{
            byte[] bytes = inputs.getBytes("UTF-8");
            return sha1(bytes);
        }catch (UnsupportedEncodingException e){
            //
        }
        return null;
    }

    // endregion

    // region RSA加密
    private static final int MAX_ENCRYPT_BLOCK = 117; //RSA最大加密明文大小
    private static final int MAX_DECRYPT_BLOCK = 128; //RSA最大解密密文大小
    private static final String CIPHER_TYPE="RSA/ECB/PKCS1Padding";//密码类型

    /**
     * RSA解密
     * @param inputStr 需要解密的字符串
     * @param privateKey rsa解密用的key
     * @return 解密后的字符串，如果发生异常，返回空字符串
     */
    public static String rsaDecryptByPrivateKey(String inputStr, Key privateKey){
        ByteArrayOutputStream out = null;
        String result="";
        try{
            Cipher cipher=Cipher.getInstance(CIPHER_TYPE);
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] inputBytes= Base64.decode(inputStr,Base64.DEFAULT);
            int inputLength=inputBytes.length;
            out=new ByteArrayOutputStream();
            int offset=0;
            byte[] cache;
            int i=0;
            //对数据分段解密
            for(;inputLength-offset>0;){
                if(inputLength-offset>MAX_DECRYPT_BLOCK){
                    cache=cipher.doFinal(inputBytes,offset,MAX_DECRYPT_BLOCK);
                }else{
                    cache=cipher.doFinal(inputBytes,offset,inputLength-offset);
                }
                out.write(cache,0,cache.length);
                i++;
                offset=i*MAX_DECRYPT_BLOCK;
            }
            result=new String(out.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     *RSA加密
     * @param inputStr 需要加密的字符串
     * @param publicKey rsa加密用的key
     * @return 加密后的字符串
     */
    public static String rsaEncryptByPublicKey(String inputStr, Key publicKey){
        ByteArrayOutputStream out=null;
        String result="";
        try{
            Cipher cipher=Cipher.getInstance(CIPHER_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] inputBytes=inputStr.getBytes();
            int inputLength=inputBytes.length;
            out=new ByteArrayOutputStream();
            int offset=0;
            int i=0;
            byte[] cache;
            //对数据分段加密
            for(;inputLength-offset>0;){
                if(inputLength-offset>MAX_ENCRYPT_BLOCK){
                    cache=cipher.doFinal(inputBytes,offset,MAX_ENCRYPT_BLOCK);
                }else{
                    cache=cipher.doFinal(inputBytes,offset,inputLength-offset);
                }
                out.write(cache,0,cache.length);
                i++;
                offset=i*MAX_ENCRYPT_BLOCK;
            }
            result=Base64.encodeToString(out.toByteArray(),Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    // endregion

    // region AES加密[密钥32位 iv16位]

    /**
     * aes加密
     * @param content 待加密字符串
     * @param password 密钥 32位
     * @param iv iv 16位
     * @return encrypted
     */
    public static String aesEncryptString2Base64(String content, String password, String iv){
        try {
            //密钥 32位长度
            SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"),
                    "AES");
            //iv 16位长度
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            return Base64.encodeToString(cipher.doFinal(content.getBytes("UTF-8")),
                    Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * aes解密
     * @param content 待解密内容
     * @param password 解密密钥 32位
     * @param iv 解密iv 16位
     * @return decrypted
     */
    public static String aesDecryptBase642String(String content, String password, String iv) {
        try{
            SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"),
                    "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            return new String(cipher.doFinal(Base64.decode(content,Base64.DEFAULT)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //endregion

    private static String bytes2HexStr(byte[] bytes){
        StringBuilder result = new StringBuilder();
        int temp;
        for(byte b : bytes){
            temp = b & 0xFF;
            if(temp <16){
                result.append(0);
            }
            result.append(Integer.toHexString(temp));
        }
        return result.toString();
    }
}
