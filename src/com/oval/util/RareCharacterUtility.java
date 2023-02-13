package com.oval.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RareCharacterUtility {
	public static boolean containsUserDefinedUnicode(String string) {
        if (string == null) {
            throw new NullPointerException("Stirng must be non-null");
        }
        int[] code = toCodePointArray(string);
        //  U+E000..U+F8FF
        for (int c : code) {
            if (c >= '\ue000' && c <= '\uf8ff') {
                return true;
            }
        }
        return false;
    }

    static int[] toCodePointArray(String str) {
        int len = str.length();
        int[] acp = new int[str.codePointCount(0, len)];

        for (int i = 0, j = 0; i < len; i = str.offsetByCodePoints(i, 1)) {
            acp[j++] = str.codePointAt(i);
        }
        return acp;
    }

    static String toHex(int[] chars) {
        String r = "[";
        for (int i=0; i<chars.length; i++) {
            if (r.length() > 1) {
                r += ",";
            }
            r += Integer.toHexString(chars[i]);
        }
        r += "]";
        return r;
    }

    /*
     * 中文转unicode编码
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }
    
    
    /*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }
    
    public static void main(String[] argu) {
        /*String rr = ("\u5f20\ue0bf\uD86C\uDE70\uD840\uDC10\uD86D\uDF44\uD87E\uDCAC\u9fc6");
        rr="有限公司清濛雅泰分店";
        System.out.println("Unicode = " + toHex(toCodePointArray(rr)));
       
        boolean r = (containsUserDefinedUnicode(rr));
        System.out.println("Test result = " + r + " should be true");
        */
    	 System.out.println(gbEncoding("濛")+"濛 result = " + containsUserDefinedUnicode(gbEncoding("濛")));

    	 System.out.println(gbEncoding("你")+"你 result = " + containsUserDefinedUnicode(gbEncoding("你")));
       /*try {
		String code = URLEncoder.encode("囧","gb2312");
		
		System.out.println(code);
		code = URLEncoder.encode("囧","gb18030");
		System.out.println(code);
		
		code = URLEncoder.encode("呵呵濛","gb2312");
		System.out.println(code);
		if(code.%3F) {
			
		}
		code = URLEncoder.encode("呵呵濛","gb18030");
		System.out.println(code);


		System.out.println(new String("呵呵濛".getBytes(),"gb18030"));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
    }
}
