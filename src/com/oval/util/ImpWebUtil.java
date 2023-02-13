package com.oval.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ImpWebUtil extends WebUtil{
	
	public static String repeatPost(HttpClient client,
			Map<String, String> params, String url, String StrName,String StrValue) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, StrName, StrValue);			
			if (!result.equals(""))
				break;
		}
		System.out.println(result);
		return result;
	}
	
	
	private static String post(HttpClient client, Map<String, String> params,
			String url,String StrName, String StrValue) throws Exception {
		HttpPost httppost = null;
		//CookieStore cookieStore=null;
        
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (String paramName : params.keySet()) {
				formparams.add(new BasicNameValuePair(paramName, params
						.get(paramName)));
			}
			UrlEncodedFormEntity entity = null;
			try {
				entity = new UrlEncodedFormEntity(formparams, "utf-8");//entity = new UrlEncodedFormEntity(formparams, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			httppost = new HttpPost(buildURI(url));
			

			httppost.setEntity(entity);
			httppost.addHeader(StrName, StrValue);
			

			HttpResponse response = client.execute(httppost);
			

			int statusCode = response.getStatusLine().getStatusCode();
			
			if (HttpStatus.SC_INTERNAL_SERVER_ERROR == statusCode)
				throw new Exception("服务器发生内部错误:"
						+ EntityUtils.toString(response.getEntity()));

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
				return "";

			HttpEntity respEntity = response.getEntity();
			if (respEntity == null)
				return "";

			byte[] bytes = EntityUtils.toByteArray(respEntity);
			String charSet = EntityUtils.getContentCharSet(respEntity);
			if (StringUtil.isEmpty(charSet))
				charSet =getCharSet(new String(bytes));// new
			// String(bytes)
			
			try {
				return new String(bytes, charSet);
			} catch (Exception e) {
				return new String(bytes, "gb2312");
			}
			// (nbytes, charSet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			httppost.abort();
		}
	}

}
