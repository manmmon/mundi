package com.oval.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author mingge
 *
 */
public class Md5 {

        private final static String DES = "DES";
        
        private final static String KEY="opeddsaead323353484591dadbc382a18340bf83414536";
     
		public static String encrypt(String data)  {
            byte[] bt;
			try {
				bt = encrypt(data.getBytes(), KEY.getBytes());
	            String strs = new BASE64Encoder().encode(bt);
	            return strs;
			} catch (Exception e) {
				System.out.printf("MD5 encrypt Err. ");
			}
			return null;
        }
     
		public static String decrypt(String data)  {
            if (data == null){
                return null;
            }
 
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf;
			try {
				buf = decoder.decodeBuffer(data);
	            byte[] bt = decrypt(buf,KEY.getBytes());
	            return new String(bt);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.printf("MD5 decrypt Err. ");
			}
			return null;
        }
     
        private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            return cipher.doFinal(data);
        }
         
        private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            return cipher.doFinal(data);
        }

}