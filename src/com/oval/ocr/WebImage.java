package com.oval.ocr;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;

import com.oval.grabweb.logic.part201905.impl.SSLClient;


public class WebImage {
	private final static String AUTH_URL="https://aip.baidubce.com/rest/2.0/ocr/v1/webimage";
	static Logger logger = Logger.getLogger(WebImage.class);
	 /**
	    * 重要提示代码中所需工具类
	    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
	    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	    * 下载
	    */
	
	/**
	 *	识别 本地图片
	 * @param filePath
	 * @return
	 */
	    public static String webImage(String filePath) {
	        // 请求url
	        String url = AUTH_URL;
	        try {
	            // 本地文件路径
	            byte[] imgData = FileUtils.readFileByBytes(filePath);
	            String imgStr = Base64Util.encode(imgData);
	            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
	            
	            String param = "image=" + imgParam;

	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	            String accessToken = AuthService.getAuth();
	            
	            String result = HttpUtil.post(url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	        	logger.error("识别 本地图片失败",e);
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    public static String webImage(HttpClient client,String filePath) {
	        // 请求url
	        String url = AUTH_URL;
	        try {
	            // 本地文件路径
	            byte[] imgData = FileUtils.readFileByBytes(filePath);
	            String imgStr = Base64Util.encode(imgData);
	            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
	            
	            String param = "image=" + imgParam;

	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	            String accessToken = AuthService.getAuth();

	            String result = HttpUtil.post(client,url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	        	logger.error("识别 本地图片失败",e);
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    
	    
	    
	    public static String webImage2(HttpClient client,byte[] imgData) {
	        // 请求url
	        String url = AUTH_URL;
	        try {
	            // 图片的Byte数组
	            String imgStr = Base64Util.encode(imgData);
	            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
	            
	            String param = "image=" + imgParam;

	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	            String accessToken = AuthService.getAuth();
	            client=new SSLClient();
	            String result = HttpUtil.post(client,url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	        	logger.error("识别 本地图片失败",e);
	            e.printStackTrace();
	        }
	        return null;
	    }
	    /**
	     * 	通过图片二进制识别
	     * @param imgData
	     * @return
	     */
	    public static String webImage2(byte[] imgData) {
	        // 请求url
	        String url = AUTH_URL;
	        try {
	        	// 图片的Byte数组
	            String imgStr = Base64Util.encode(imgData);
	            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
	            
	            String param = "image=" + imgParam;

	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	            String accessToken = AuthService.getAuth();

	            String result = HttpUtil.post(url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	        	logger.error("识别 本地图片失败",e);
	            e.printStackTrace();
	        }
	        return null;
	    }
}
