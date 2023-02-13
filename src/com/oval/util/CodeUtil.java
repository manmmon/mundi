package com.oval.util;

import java.io.IOException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CodeUtil {

	private final static String KEY = "SPHKDLOVAL2020";
	
	public static String encodeingBase64(byte[] message)
	{
		String ret=null;
		if(message!=null)
		{
			BASE64Encoder encode = new BASE64Encoder(); 
			ret=encode.encode(message); 
		}

		return ret;
	}
	
	public static String encodeString(String password)
	{
		String ret=null;
		try
		{
			byte[] keyArr=KEY.getBytes("UTF-8");
			byte[] passwordArr=password.getBytes("UTF-8");
			for(int i=0;i<passwordArr.length;i++)
			{
				passwordArr[i]=(byte) (passwordArr[i] ^ keyArr[ i%keyArr.length ]); 
			}
			ret=encodeingBase64(passwordArr);
			
		}catch(Exception ex)
		{
			//logger.error("encodeString encrypt Err. ", ex);
			System.out.println("encodeString encrypt Err. ");
		}
		return ret;
	}
	
	//public static String decodePassword(String password,String key)
	public static String decodeString(String password)
	{
		String ret=null;
		try
		{
			
			byte[] keyArr=KEY.getBytes("UTF-8");
			byte[] passwordArr=decodeingBase64(password);
			for(int i=0;i<passwordArr.length;i++)
			{
				passwordArr[i]=(byte) (passwordArr[i] ^ keyArr[ i%keyArr.length ]); 
			}
			ret=new String(passwordArr,"UTF-8");
			
		}catch(Exception ex)
		{
			//logger.error("decodeString Err. ", ex);
			System.out.println("decodeString Err. ");
		}
		return ret;
	}
	
	public static byte[] decodeingBase64(String message)
	{
		byte[] ret=null;
		if(message!=null)
		{
			BASE64Decoder decoder = new BASE64Decoder(); 
			try {
				ret=decoder.decodeBuffer(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		return ret;
	}

}