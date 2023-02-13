package com.oval.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {

    public static String stringInMd5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(str.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            //默认小写，在tostring后加toUpperCase()即为大写加密
            str = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    public static String getArrayToSHA256(byte[] bytes) {
        String encodeStr = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        String captchakey = "msnz";
        //"23d6b0076bc442c216be56dfbf9d6160"
        String iv = "01234567";
        String firtMd5 = MD5Utils.stringInMd5(captchakey.toLowerCase());
        System.out.println(firtMd5);
        String captcha = MD5Utils.getArrayToSHA256(firtMd5.getBytes());
        System.out.println(captcha);
        String password = "123456";
        String passMd5 = MD5Utils.stringInMd5(password);
        System.out.println(passMd5);
        String result = TripleDesUtils.desEncryp(passMd5,firtMd5,iv);
        System.out.println(result);
        String decrypt =TripleDesUtils.desDecrypt("pFf4sCFG1YBNnLpj5/aYTz07Z8LADFVuWrf5Xzs37u4cVIQ0F2lFLQ==",firtMd5,iv);
        System.out.println(decrypt);

    }
    //{"account":"700309","password":"6yeQ1l40I+IzXPElnrCkktWQiva8y5A2MzHj5V0mitSXT5Mmkal9QQ==","captcha":"b751fc15cc094e470289c9d3274c66aaafd766ffaff909f17588d87f9cb6603f","captchaKey":"f2eb0841-cf36-449f-8617-6a24c0f29570"}

    /*J.a.TripleDES.encrypt(J.a.enc.Utf8.parse(J.a.MD5(g.password.toString())), J.a.enc.Utf8.parse(D), {
        iv: J.a.enc.Utf8.parse("01234567")
    }).toString()*/
}
