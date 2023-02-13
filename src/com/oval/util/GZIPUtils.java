package com.oval.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.StringUtils;

 
//import cn.sina.api.commons.util.ApiLogger;
 
/**
 * 
 * @author wenqi5
 * 
 */
public class GZIPUtils {
 
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";
 
    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";
 
    /**
     * 字符串压缩为GZIP字节数组
     * 
     * @param str
     * @return
     */
    public static byte[] compress(String str) {
        return compress(str, GZIP_ENCODE_UTF_8);
    }
 
    /**
     * 字符串压缩为GZIP字节数组
     * 
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
//            ApiLogger.error("gzip compress error.", e);
        }
        return out.toByteArray();
    }
 
    /**
     * GZIP解压缩
     * 
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
        	e.printStackTrace();
//            ApiLogger.error("gzip uncompress error.", e);
        }
 
        return out.toByteArray();
    }
 
    /**
     * 
     * @param bytes
     * @return
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }
 
    /**
     * 
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
//            ApiLogger.error("gzip uncompress to string error.", e);
        }
        return null;
    }
 
    /**
     * 解压deflate格式文件
     * @param src 源文件
     * @param target 目标文件
     */
    private static void inflate(File src, File target){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        InflaterOutputStream inflaterOutputStream = null;
        try {
            fileInputStream = new FileInputStream(src);
            fileOutputStream = new FileOutputStream(target);
            inflaterOutputStream = new InflaterOutputStream(fileOutputStream);

            byte[] b = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(b)) != -1) {
                inflaterOutputStream.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                inflaterOutputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * @param inputByte
     *      待解压缩的字节数组
     * @return 解压缩后的字节数组
     * @throws IOException
     */
    public static byte[] uncompress1(byte[] inputByte)  {
      int len = 0;
      Inflater infl = new Inflater();
      infl.setInput(inputByte);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] outByte = new byte[1024];
      try {
        while (!infl.finished()) {
          // 解压缩并将解压缩后的内容输出到字节输出流bos中
          len = infl.inflate(outByte);
          if (len == 0) {
            break;
          }
          bos.write(outByte, 0, len);
        }
        infl.end();
      } catch (Exception e) {
    	  e.printStackTrace();
        //
      } finally {
        try {
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      return bos.toByteArray();
    }
    public static void main(String[] args) {
        String str =    "库存分布报表"; //  "%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221";
//        System.out.println("原长度：" + str.length());
//        System.out.println("压缩后字符串：" + GZIPUtils.compress(str).toString());
//        System.out.println("解压缩后字符串：" + StringUtils.newStringUtf8(GZIPUtils.uncompress(GZIPUtils.compress(str))));
//        System.out.println("解压缩后字符串：" + GZIPUtils.uncompressToString(GZIPUtils.compress(str)));
        str="7ZlNT+JAGMfvJn4HvgDOPNPylkyaGD152Iv7BUaYKFkEharZmx5wxXePGozLYZPNxiyb7IVY1O9iKJSTX8FpmZrGw+BuyIZupumBZ57pQJ8f/3n+FPqB7ywymy1z25qdSSToR7ZS4hC8FtHKVq1cSaAw3GFVXixYGLCJgRCK5IBMV6qrZbbOrV7nxD3Yd7u7A+fG+9miKExElgni/q/vw/pVr/tjeOcMHk69w9tB48tTvYX8xJ3jHbXFqBh67h4MLu/dw69D5+K52xi979sVaxs8bxFTTB/lg1jmN6qVwlaeV63+UdPb/e0eO95Ju/d47e596zcbw8tzt37rnnXc83av0/Q/exBS9HqdXGedfeLlirW0DJAxshTJOKwWs/NrIpxfWCCiPgbOigNjYlIUpuTMYnmblYoFZnOLYEKSOCdOiiLDcmLNZqWSuA4wBgCKwjhy35v2Zys9h8Uxum8/DlCiCEvN9Z1cTZJOq7j6JcpJrngMVyMJkPTLqeRqKLhCToONLVhTATalucaWa0rBVet1clzVDXbyXNNar1OhV4JTkqvxLq4whmtG1WBNDfbfOeJQsEZqIo44qwU7FYL90414jGCBKLgS3WFjuxODyhGDVmxsrROoLLHusPEVrMoS6404vlxVllg/S4ytIwaVI85orlPriMf0V/FN0b90/jvfJEr+126Yosgfdy8=";
        
//        str  =  StringUtils.newStringUtf8(GZIPUtils.uncompress1(Base64.decodeBase64(str,"")));
//        str  = StringUtils.newStringUtf8(GZIPUtils.uncompress(str.getBytes()));
        System.out.println(str);
    }
}