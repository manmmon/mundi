/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package com.oval.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public String MD5(String plainText) {
		String md5 = new String();

		try {
			MessageDigest e = MessageDigest.getInstance("MD5");
			e.update(plainText.getBytes());
			byte[] b = e.digest();
			StringBuffer buf = new StringBuffer("");

			for (int offset = 0; offset < b.length; ++offset) {
				int i = b[offset];
				if (i < 0) {
					i += 256;
				}

				if (i < 16) {
					buf.append("0");
				}

				buf.append(Integer.toHexString(i));
			}

			md5 = buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException arg7) {
			arg7.printStackTrace();
		}

		return md5;
	}
	
	
	/**
	 * MD5加密获取编码
	 * @param code1
	 * @param code2
	 * @return
	 */
	public String getCode(String code1, String code2) {
		String codeMd5 = code2 + code1;
		String MD5code = MD5(codeMd5);
		String code = MD5code.substring(3, 9).toLowerCase()
				+ MD5code.substring(19, 25).toLowerCase();
		return code;
	}
}