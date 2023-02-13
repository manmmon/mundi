package com.oval.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;

public class TripleDesUtils {
    /** Base64 编码 */
    private static final Base64 B64 = new Base64();
    /** DES加密算法 */
    private static final String DES_ALGORITHM = "DESede"; // 可用 DES,DESede,Blowfish
    public static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";
    private static final String CHARSET = "UTF-8";


    public static String desEncryp(String str, String key,String iv) {
        if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(key)) {
            try {
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
                DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
                SecretKey secretKey = SecretKeyFactory.getInstance(DES_ALGORITHM).generateSecret(spec);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes(CHARSET)));
                byte[] result = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
                return B64.encodeToString(result);
            } catch (BadPaddingException e) {
                System.out.println("DES加密失败, 密文:" + str + ", key:" + key + ", 错误:" + e.getMessage());
            } catch (Exception e) {
                System.out.println("DES加密失败, 密文:" + str + ", key:" + key + ", 错误:" + e.getMessage());
            }
        }
        return null;
    }
    public static String desDecrypt(String str, String key,String iv) {
        if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(key)) {
            try {
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
                DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
                SecretKey secretKey = SecretKeyFactory.getInstance(DES_ALGORITHM).generateSecret(spec);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes(CHARSET)));

                byte[] result = cipher.doFinal(B64.decode(str));
                return new String(result, "utf-8");
            } catch (BadPaddingException e) {
                System.out.println("DES解密失败, 密文:" + str + ", key:" + key + ", 错误:" + e.getMessage());
            } catch (Exception e) {
                System.out.println("DES解密失败, 密文:" + str + ", key:" + key + ", 错误:" + e.getMessage());
            }
        }
        return null;
    }

}
