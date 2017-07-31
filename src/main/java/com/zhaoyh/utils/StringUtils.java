package com.zhaoyh.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhaoyh on 2017/07/14.
 * 处理字符串的相关逻辑
 */
public class StringUtils {

    /**
     * 判断是否为空
     * @param s
     * @return
     */
    public static boolean isBlank(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * 产生32位小写的MD5加密值
     * @param str
     * @return
     */
    public static String parseStrToMd5(String str){
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b&0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public static String replaceSpecial(String text) {
        text = text.replaceAll("\\[", "");
        text = text.replaceAll("\\]", "");
        return text;
    }
}
