package com.oval.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Decoder;

import com.oval.ocr.WebImage;

public class WebUtil2 {
	public static String strheads = "";
	public static String getparamvalue = "";
	public static String FilePath = "";
	public static String Mordcode = "";
	public static String Mordname = "";
	public static DefaultHttpClient httpclientauto;
//	public static DefaultHttpClient htpclientManual;
	public static String JSESSIONID = "";
	public static String getparam = "";
	public static String webparamstr = "";
	public static Map<String, String> ExeHeaderMap = new HashMap<String, String>();

	public static Header[] getHeaders(HttpClient client, String url) throws Exception {
		HttpPost post = null;
		post = new HttpPost(buildURI(url));
		HttpResponse response = client.execute(post);
		// 取头信息
		Header[] headers = response.getAllHeaders();
		post.abort();
		return headers;
	}

	public static void printCookie(HttpClient client) {
		List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			System.out.println("no cookie");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println("- " + cookies.get(i).toString());
			}
		}
	}

	/**
	 * 获取网络图片二进制数组 默认转成jpg类型
	 * 
	 * @param client 客户端
	 * @param url    图片链接
	 * @return 二进制数组
	 * @throws IOException
	 */

	public static byte[] getPicByte(HttpClient client, String url) throws IOException {
		HttpGet get = new HttpGet(url);
		HttpEntity entity = null;
		byte[] imgData = null;
		try {
			HttpResponse response = client.execute(get);

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
				return null;
			entity = response.getEntity();
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			ImageIO.write(ImageIO.read(entity.getContent()), "jpg", o);
			imgData = o.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			get.abort();
		}

		return imgData;

	}

	/**
	 * 获取验证码值,默认识别最多3次
	 * 
	 * @param client 客户端
	 * @param picUrl 验证码图片链接
	 * @param length 验证码识别长度
	 * @return 验证码
	 * @throws Exception
	 */
	public static String getVerifyCode(HttpClient client, String picUrl, int length) throws Exception {
		for (int i = 0; i < 3; i++) {
			byte[] imgData = WebUtil.getPicByte(client, picUrl);
			String verifyCode = WebImage.webImage2(client, imgData);
			verifyCode = StringUtil.getValue(verifyCode, "words\": \"", "\"}", 1);
			if (verifyCode.length() == length) {
				return verifyCode;
			}
		}
		return "";
	}

	/**
	 * 获取验证码值，默认4位的验证码
	 */
	public static String getVerifyCode(HttpClient client, String picUrl) throws Exception {
		return getVerifyCode(client, picUrl, 4);
	}

	public static void requestFile(HttpClient client, String url, String fileName) {
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
				return;

			HttpEntity entity = response.getEntity();
			if (entity == null)
				return;
			File storeFile = new File(fileName);
			FileOutputStream output = new FileOutputStream(storeFile);
			// 得到网络资源的字节数组,并写入文件
			entity.writeTo(output);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			get.abort();
		}
	}

	public static String repeatPost(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "1");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPost59208(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "59208");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostH154W2(HttpClient client, // 解决国药控股北京康辰生物医药有限公司乱码问题
			Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "H154W2");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostCN(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "CN");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostE(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostP(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "8");
			if (!result.equals("") || !strheads.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostH(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "5");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostK(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "4");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostZ(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "6");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostV(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "9");
			if (!result.equals("") || !strheads.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostJson(HttpClient client, Map<String, String> params, String url, String paramvalue)
			throws Exception {// 龙泉公司
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "Json");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String repeatPostEZ(HttpClient client,
			Map<String, String> params, String url, String paramvalue) throws Exception {
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "EZ");
			if (!result.equals(""))
				break;
		}
		return result;
	}
	
	public static String repeatPostEZS(HttpClient client,
			Map<String, String> params, String url, String paramvalue) throws Exception {
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "EZS");
			if (!result.equals(""))
				break;
		}
		return result;
	}
	
	public static String repeatPostEZP(HttpClient client,
			Map<String, String> params, String url, String paramvalue) throws Exception {
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "EZP");
			if (!result.equals(""))
				break;
		}
		return result;
	}
	
	// W329044华润湖南双舟医药有限公司
	public static String repeatPostHuaRun(HttpClient client, Map<String, String> params, String url, String paramvalue)
			throws Exception {// 华润系列
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "HuaRun");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	// 华润Code(W139052 W703988..)
	public static String repeatPostHuaRunMatch(HttpClient client, HashMap<String, String> hashMap, String url,
			String paramvalue) throws Exception {// 华润系列
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, hashMap, url, "HuaRun");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	// 福建泉安
	public static String repeatPostBN(HttpClient client, Map<String, String> params, String url, String paramvalue)
			throws Exception {
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "BN");
			if (!result.equals(""))
				break;
		}
//		System.out.println(result);
		return result;
	}

	// 福建泉安2
	public static String repeatPostBNN(HttpClient client, Map<String, String> params, String url, String paramvalue)
			throws Exception {
		String result = "";
		getparamvalue = paramvalue;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "BNN");
			if (!result.equals(""))
				break;
		}
//		System.out.println(result);
		return result;
	}

	public static String repeatPostSB(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "BN");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	// 宁波大红鹰医药供销有限公司
	public static String repeatPostMN(HttpClient client, Map<String, String> params, String url, String filePath)
			throws Exception {
		String result = "";
		FilePath = filePath;
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "MN");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	// 珠海市医药有限公司
	public static String repeatPostZH(HttpClient client, Map<String, String> params, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = post(client, params, url, "ZH");
			if (!result.equals(""))
				break;
		}
		return result;
	}

	
	private static String post(HttpClient client, Map<String, String> params, String url, String charsetcode)
			throws Exception {
		HttpPost httppost = null;

		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (String paramName : params.keySet()) {
				formparams.add(new BasicNameValuePair(paramName, params.get(paramName)));
			}
			if (charsetcode == "BN") {
				UrlEncodedFormEntity entity = null;
				try {
					if (charsetcode == "5") {
						entity = new UrlEncodedFormEntity(formparams, "gb2312");
					} else {
						entity = new UrlEncodedFormEntity(formparams, "utf-8");
					}

				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

				httppost = new HttpPost(buildURI(url));
				// httppost.setHeader("Cookie", JSESSIONID);
				httppost.addHeader("Pragma", "no-cache");
				httppost.addHeader("Accept", "*/*");
				httppost.addHeader("Accept-Encoding", "gzip, deflate");
				httppost.addHeader("Accept-Language", "zh-cn");
				httppost.addHeader("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA; InfoPath.2; .NET CLR 2.0.50727)");
				httppost.addHeader("Cookie", "WXDS_SGY_USERID=6223");
				httppost.addHeader("Cookie", "WXDS_SGY_DEPTID=27");
				httppost.addHeader("Cookie", "WXDS_SGY_USERNAME=XAYS");
				httppost.addHeader("Cookie", "WXDS_SGY_ROLEID=639");
				httppost.addHeader("Cookie", "WXDS_SGY_USERDWDM=10641");
				httppost.addHeader("Cookie", JSESSIONID);
				// httppost.addHeader("Content-Length", "214");
				httppost.addHeader("Content-Type", "text/plain");
				httppost.addHeader("Referer", "http://www.wxshyy.com/bdyy/CG/CGAP003.ftl");
				httppost.addHeader("Connection", "Keep-Alive");
				httppost.addHeader("Host", "www.wxshyy.com");
				httppost.setEntity(entity);

			} else if (charsetcode == "BNN") {
				UrlEncodedFormEntity entity = null;
				try {
					if (charsetcode == "5") {
						entity = new UrlEncodedFormEntity(formparams, "gb2312");
					} else {
						entity = new UrlEncodedFormEntity(formparams, "utf-8");
					}

				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

				// BN

				httppost = new HttpPost(buildURI(url));
				httppost.addHeader("Pragma", "no-cache");
				httppost.addHeader("Accept", "application/json, text/javascript, */*");
				httppost.addHeader("Accept-Encoding", "gzip, deflate");
				httppost.addHeader("Accept-Language", "zh-cn");
				httppost.addHeader("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; 360SE)");
				httppost.addHeader("ASP.NET_SessionId=0d34ckuwfj5gsn55wlmcgp45", "");
				httppost.addHeader("Content-Type", "application/json;utf-8");
				httppost.addHeader("Referer", "http://www.qayy.com/qayy/StorageQuery.aspx");
				httppost.addHeader("x-requested-with", "XMLHttpRequest");
				httppost.addHeader("Connection", "Keep-Alive");
				httppost.addHeader("Host", "www.qayy.com");
				httppost.setEntity(entity);
			} else if (charsetcode == "ZH") {
				UrlEncodedFormEntity entity = null;
				try {
					if (charsetcode == "5") {
						entity = new UrlEncodedFormEntity(formparams, "gb2312");
					} else {
						entity = new UrlEncodedFormEntity(formparams, "utf-8");
					}

				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				httppost = new HttpPost(buildURI(url));
				httppost.addHeader("Pragma", "no-cache");
				httppost.addHeader("Accept",
						"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
				httppost.addHeader("Accept-Encoding", "gzip, deflate");
				httppost.addHeader("Accept-Language", "zh-CN");
				httppost.addHeader("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; 360SE)");
				httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
				httppost.addHeader("Referer", "http://113.106.48.178:605/login.asp");
				httppost.addHeader("Connection", "Keep-Alive");
				httppost.addHeader("Host", "113.106.48.178:605");
				httppost.setEntity(entity);
			} else if (charsetcode.equals("DT000675")) {// EZJ028,EZJ065
				StringEntity entity = null;
				String reqString = webparamstr;
				entity = new StringEntity(reqString);
				try {
					entity = new StringEntity(reqString);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				httppost = new HttpPost(buildURI(url));
				Iterator<String> iter = ExeHeaderMap.keySet().iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					httppost.addHeader(key, ExeHeaderMap.get(key));
				}
				httppost.setEntity(entity);
			} else if (charsetcode == "Json") {
				StringEntity entity = null;
				entity = new StringEntity(getparamvalue, "UTF-8");
				httppost = new HttpPost(buildURI(url));
				httppost.setHeader("accept", "application/json, text/javascript, */*");
				httppost.setHeader("Content-Type", "application/json");
				httppost.setEntity(entity);
			}else if(charsetcode == "EZ"){
				StringEntity entity=null;				
				entity = new StringEntity(getparamvalue,"UTF-8");
				httppost = new HttpPost(buildURI(url));	
				
    			httppost.setHeader("Accept", "*/*");
    			httppost.setHeader("Accept-Encoding","gzip, deflate");
    			httppost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
    			httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
    			httppost.setHeader("Content-Type","text/plain; charset=UTF-8");
    			httppost.setHeader("accept", "application/json, text/javascript, */*");
    			httppost.setHeader("Content-type", "application/json");
    			httppost.setHeader("Referer", "http://kh.zjezyy.com/");
    			httppost.setHeader("X-AjaxPro-Method", "ShowAll");
    			httppost.setHeader("Origin", "http://kh.zjezyy.com");
    			httppost.setHeader("Connection", "keep-alive");	
    			httppost.setHeader("Host", "kh.zjezyy.com");	

				httppost.setEntity(entity);				
			} else if(charsetcode == "EZS"){
				StringEntity entity=null;				
				//entity = new StringEntity(getparamvalue,"UTF-8");
				entity = new UrlEncodedFormEntity(formparams, "utf-8");
				httppost = new HttpPost(buildURI(url));	
				
				httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
				httppost.setHeader("Accept-Encoding","gzip, deflate");
				httppost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
				httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
				httppost.setHeader("Referer","http://kh.zjezyy.com/GYS/SPLX.aspx");
				httppost.setHeader("Upgrade-Insecure-Requests","1");
				httppost.setHeader("Cookie",getparamvalue);
				httppost.setHeader("Connection", "keep-alive");
				httppost.setHeader("Host", "kh.zjezyy.com");
				httppost.setHeader("Cache-Control", "max-age=0");
				httppost.setHeader("Origin", "http://kh.zjezyy.com");
				httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");	

				httppost.setEntity(entity);				
			} else if(charsetcode == "EZP"){
				StringEntity entity=null;				
				//entity = new StringEntity(getparamvalue,"UTF-8");
				entity = new UrlEncodedFormEntity(formparams, "utf-8");
				httppost = new HttpPost(buildURI(url));	
				
				httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
				httppost.setHeader("Accept-Encoding","gzip, deflate");
				httppost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
				httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");
				httppost.setHeader("Referer","http://kh.zjezyy.com/GYS/EZGR.aspx");
				httppost.setHeader("Upgrade-Insecure-Requests","1");
				httppost.setHeader("Cookie",getparamvalue);
				httppost.setHeader("Connection", "keep-alive");
				httppost.setHeader("Host", "kh.zjezyy.com");
				httppost.setHeader("Cache-Control", "max-age=0");
				httppost.setHeader("Origin", "http://kh.zjezyy.com");
				httppost.setHeader("Content-type", "application/x-www-form-urlencoded");	

				httppost.setEntity(entity);				
			} else if (charsetcode == "HuaRun") {
				StringEntity entity = null;
				entity = new StringEntity(getparamvalue, "UTF-8");
				httppost = new HttpPost(buildURI(url));
				entity.setContentType("text/json");
				httppost.setHeader("accept", "application/json, text/javascript, */*");
				httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				httppost.setEntity(entity);
			} else if (charsetcode == "W014035") {
				UrlEncodedFormEntity entity = null;
				entity = new UrlEncodedFormEntity(formparams, "utf-8");
				httppost = new HttpPost(buildURI(url));
				httppost.addHeader("Pragma", "no-cache");
				httppost.addHeader("Accept",
						"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
				httppost.addHeader("Accept-Encoding", "gzip, deflate");
				httppost.addHeader("Accept-Language", "zh-CN");
				httppost.addHeader("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.2; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; 360SE)");
				// httppost.addHeader("Content-Length", "30");
				httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
				httppost.addHeader("Referer", "http://113.106.48.178:605/login.asp");
				httppost.addHeader("Connection", "Keep-Alive");
				httppost.addHeader("Host", "113.106.48.178:605");
				httppost.setEntity(entity);
			}else {
				UrlEncodedFormEntity entity = null;
				try {
					if (charsetcode == "5") {
						entity = new UrlEncodedFormEntity(formparams, "gb2312");
					} else if ("59208".equals(charsetcode)) { // 解决国药控股福建有限公司原福建省厦门医药采购供应站 服务器内部发生错误问题
						entity = new UrlEncodedFormEntity(formparams, "gb2312");
					} else {
						entity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);
					}
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				httppost = new HttpPost(buildURI(url));
				httppost.setEntity(entity);
			}
			HttpResponse response = client.execute(httppost);

			if (charsetcode == "CN") {
				for (Header header : response.getAllHeaders()) {
					if (header.getValue().contains("JSESSIONID")) {
						String[] s = header.getValue().split(";");
						JSESSIONID = s[0].trim();
						break;
					}
				}
			}
			if (charsetcode == "8") {
				Header[] headers = (Header[]) response.getAllHeaders();
				for (int i = 0; i < headers.length; i++) {
					if (headers[i].getName().equals("Location")) {
						strheads = headers[i].getValue();
						//
					}
				}
			}

			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_INTERNAL_SERVER_ERROR == statusCode)
				throw new Exception("服务器发生内部错误:" + EntityUtils.toString(response.getEntity()));

			if (HttpStatus.SC_OK != statusCode && statusCode != 302)
				return "";

			HttpEntity respEntity = response.getEntity();
			if (respEntity == null)
				return "";

			// ykc
			if (charsetcode.equals("MN")) {
				File storeFile = new File(FilePath);
				FileOutputStream output = new FileOutputStream(storeFile);
				respEntity.writeTo(output);
				output.flush();
				output.close();
				return "1";
			}

			byte[] bytes = EntityUtils.toByteArray(respEntity);
//			File storeFile = new File("d:/test.zip");
//			FileOutputStream output = new FileOutputStream(storeFile);
//			output.write(bytes);
//			output.flush();
//			output.close();
			String charSet = EntityUtils.getContentCharSet(respEntity);
			String str = "";
			if (charsetcode.equals("4")) {// 苏州礼安乱码解决方法
				str = new String(bytes, "utf-8");
			} else {
				str = new String(bytes);
			}

			if (charsetcode.equals("EZ")) {
				// str=new String(bytes,"gb2312"); 
				//str=EntityUtils.toString(respEntity);
				str = new String(bytes, "utf-8");
			}
			
			if (charsetcode.equals("6")) {
				// str=new String(bytes,"gb2312"); //浙江安泰医药有限公司乱码解决
				str = new String(bytes, "gbk");
			}

			if (charsetcode.equals("9")) {
				// str=new String(bytes,"gb2312"); //国药控股镇江乱码解决
				str = new String(bytes, "utf-8");
			}

			if (charsetcode.equals("HuaRun")) {
				str = new String(bytes, "utf-8");
			}

			if (charsetcode.equals("H154W2")) { // 解决国药控股北京康辰生物医药有限公司乱码问题
				str = new String(bytes, "UTF-8");
			}

			if (charsetcode.equals("59208")) {
				str = new String(bytes, "UTF-8"); // 解决国药控股福建有限公司原福建省厦门医药采购供应站乱码问题
			}

			if (StringUtil.isEmpty(charSet))
				charSet = getCharSet(str);// new
			// String(bytes)
			try {
				if (charsetcode.equals("")) {
					return new String(bytes);
				} else if (charsetcode.equals("9")) {
					return str;
				} else if (charsetcode.equals("4")) {
					return str;
				} else if (charsetcode.equals("6")) {
					return str;
				} else if (charsetcode.equals("HuaRun")) {
					return str;
				} else if (charsetcode.equals("H154W2")) {
					return str;
				} else if (charsetcode.equals("59208")) {
					return str;
				} else {
					return new String(bytes, charSet);
				}
			} catch (Exception e) {
				return new String(bytes, "ISO-8859-1");
			}
			// (nbytes, charSet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			httppost.abort();
		}

	}

	public static String getCharSet(String source) {
		int beginIndex = source.indexOf("charset=");
		if (beginIndex < 0)
			return "gb2312";

		int endIndex = source.indexOf("\"", beginIndex + 1);
		if (endIndex < 0) {
			return "gb2312";
		}
		/*
		 * try{ String s= source.substring(beginIndex + 8, endIndex); }catch(Exception
		 * e){ System.out.println(source.substring(beginIndex + 8, endIndex)); }
		 */
		return source.substring(beginIndex + 8, endIndex);
	}

	public static URI buildURI(String url) throws MalformedURLException, URISyntaxException {
		URL uri = new URL(url);
		return new URI(uri.getProtocol(), null, uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), null);
	}

	public static String postMultiPart(HttpClient client, Map<String, String> params, String url) throws Exception {
		MultipartEntity entity = new MultipartEntity();

		for (String paramName : params.keySet()) {
			entity.addPart(paramName, new StringBody(params.get(paramName), Charset.forName("UTF-8")));
		}
		HttpPost request = new HttpPost(url);
		request.setEntity(entity);
		HttpResponse response = client.execute(request);
		return EntityUtils.toString(response.getEntity());
	}

	public static void checkAndCreateDir(String rootDir, String orgCode) {

		String dir = rootDir + "\\" + orgCode;
		File dirFile = new File(dir);
		if (!dirFile.exists())
			dirFile.mkdir();
	}

	public static String getExt(HttpClient client, String url, Map<String, String> cookies) throws Exception {
		String info = "";
		HttpGet get = null;
		String localCharSet = null;
		try {
			get = new HttpGet(buildURI(url));
			if (cookies != null) {
				for (String name : cookies.keySet()) {
					get.addHeader("Cookie", name + "=" + cookies.get(name));
				}
			}

			HttpResponse response = client.execute(get);

			HttpEntity entity = response.getEntity();

			if (entity == null) {
				info = "";
			} else {
				String charSet = "";
				byte[] bytes = EntityUtils.toByteArray(entity);
				if (!StringUtil.isEmpty(charSet))
					localCharSet = charSet;
				else
					localCharSet = EntityUtils.getContentCharSet(entity);

				if (StringUtil.isEmpty(localCharSet))
					localCharSet = getCharSet(new String(bytes));
				try {
					info = new String(bytes, localCharSet);
				} catch (Exception e) {
					info = new String(bytes, "gb2312");
				}
			}
			// info = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			get.abort();
		}
		return info;
	}

	public static String getExt(HttpClient client, String url) throws Exception {
		return getExt(client, url, null);
	}

	private static String get(HttpClient client, String url, String charSet) throws Exception {
		String info = "";
		String localCharSet = null;
		HttpGet get = null;
		try {
			get = new HttpGet(buildURI(url));
			HttpResponse response = client.execute(get);
			if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusLine().getStatusCode())
				throw new Exception("服务器发生内部错误");

			if (HttpStatus.SC_MOVED_TEMPORARILY == response.getStatusLine().getStatusCode()) {
				if (response.getFirstHeader("Location") != null) {
					String location = response.getFirstHeader("Location").getValue();
					return location;
				}
			}

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
				return "";

			HttpEntity entity = response.getEntity();
			// gary
			if (entity == null) {
				return "";
			}
			// gary
			if (charSet.equals("MN")) {
				File storeFile = new File(FilePath);
				FileOutputStream output = new FileOutputStream(storeFile);
				entity.writeTo(output);
				output.flush();
				output.close();
				return "1";
			}

			byte[] bytes = EntityUtils.toByteArray(entity);
			if (!StringUtil.isEmpty(charSet))
				localCharSet = charSet;
			else
				localCharSet = EntityUtils.getContentCharSet(entity);

			if (StringUtil.isEmpty(localCharSet))
				localCharSet = getCharSet(new String(bytes));
			try {
				info = new String(bytes, localCharSet);
			} catch (Exception e) {
				info = new String(bytes, "gb2312");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			get.abort();
		}
		return info;
	}

	// gary
	private static byte[] getByte(HttpClient client, String url) throws Exception {
		HttpGet get = null;
		byte[] bytes = null;
		try {
			get = new HttpGet(buildURI(url));
			HttpResponse response = client.execute(get);

			if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusLine().getStatusCode())
				throw new Exception("服务器发生内部错误");

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
				return null;

			HttpEntity entity = response.getEntity();

			if (entity == null)
				return null;

			bytes = EntityUtils.toByteArray(entity);

			if (bytes.length < 1 || bytes == null) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			get.abort();
		}
		return bytes;
	}

	public static String repeatGet(HttpClient client, String url) throws Exception {
		return repeatGet(client, url, "");
	}

	// 获取结果为byte数组,方便处理乱码问题--gary
	public static byte[] repeatGetByte(HttpClient client, String url) throws Exception {
		byte[] bytes = null;
		for (int i = 0; i < 3; i++) {
			bytes = getByte(client, url);
			if (!(bytes == null || bytes.length < 1))
				break;
		}
		return bytes;
	}

	// gary
	public static String repeatGetMN(HttpClient client, String url, String filePath) throws Exception {
		FilePath = filePath;
		return repeatGet(client, url, "MN");
	}

	// 云南省医药

	public static String repeatGetK(HttpClient client, String url) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = get(client, url);
			if (!result.equals(""))
				break;
		}

		return result;
	}

	private static String get(HttpClient client, String url) throws Exception {
		String info = "";
		HttpGet get = null;
		try {
			get = new HttpGet(buildURI(url));
			HttpResponse response = client.execute(get);

			if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusLine().getStatusCode())
				throw new Exception("服务器发生内部错误");

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
				return "";

			HttpEntity entity = response.getEntity();
			if (entity == null)
				info = "";
			else {
				byte[] bytes = EntityUtils.toByteArray(entity);
				String charSet = EntityUtils.getContentCharSet(entity);
				if (StringUtil.isEmpty(charSet)) {
					String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
					Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(new String(bytes));
					m.find();
					if (m.groupCount() == 1) {
						charSet = m.group(1);
					} else {
						charSet = "";
					}
				}

				info = new String(bytes, charSet);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			get.abort();
		}

		return info;
	}

	public static String repeatGet(HttpClient client, String url, String charSet) throws Exception {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result = get(client, url, charSet);
			if (!result.equals(""))
				break;
		}
		return result;
	}

	public static String request(HttpClient client, HttpPost post) {
		HttpResponse response = null;
		String info = "";
		try {
			response = client.execute(post);
			if (response == null)
				return "";

			HttpEntity httpEntity = response.getEntity();
			if (httpEntity == null)
				return "";
			info = EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

	public static DefaultHttpClient getDefaultHttpClient() {
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setTimeout(params, 100000);
		// 设置连接超时
		HttpConnectionParams.setConnectionTimeout(params, 500000);
		// 设置socket超时
		HttpConnectionParams.setSoTimeout(params, 1000000);
		ConnManagerParams.setMaxTotalConnections(params, 100);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
		DefaultHttpClient httpclient = new DefaultHttpClient(conMgr, params);
		HttpClientParams.setCookiePolicy(httpclient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1))");
		httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		return httpclient;
	}


	public static DefaultHttpClient getDefaultHttpClient1() {
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
//		if (httpclientauto != null) {
//			httpclientauto.getConnectionManager().shutdown();
//		}
		httpclientauto = new DefaultHttpClient(conMgr, params);
		HttpClientParams.setCookiePolicy(httpclientauto.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		httpclientauto.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpclientauto.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1))");
		httpclientauto.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclientauto.setHttpRequestRetryHandler(requestRetryHandler);
		return httpclientauto;
	}

	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			if (executionCount >= 3) {
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				return false;
			}
			HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				return true;
			}
			return false;
		}
	};

	public static byte[] repeatPostByte(HttpClient client, Map<String, String> params, String url, String charSet)
			throws Exception {
		byte[] result = null;
		for (int i = 0; i < 3; i++) {
			result = postByte(client, params, url, charSet);
			if (!result.equals(""))
				break;
		}
		return result;
	}

	private static byte[] postByte(HttpClient client, Map<String, String> params, String url, String charsetcode)
			throws Exception {
		HttpPost httppost = null;

		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (String paramName : params.keySet()) {
				formparams.add(new BasicNameValuePair(paramName, params.get(paramName)));
			}

			UrlEncodedFormEntity entity = null;
			try {
				entity = new UrlEncodedFormEntity(formparams, charsetcode);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			httppost = new HttpPost(buildURI(url));
			httppost.setEntity(entity);

			HttpResponse response = client.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_INTERNAL_SERVER_ERROR == statusCode)
				throw new Exception("服务器发生内部错误:" + EntityUtils.toString(response.getEntity()));

			if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
				return null;

			HttpEntity respEntity = response.getEntity();
			if (respEntity == null)
				return null;

			byte[] bytes = EntityUtils.toByteArray(respEntity);
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			httppost.abort();
		}
		
		

	}
	
	public static void main(String[] args) {
		String dir = PropertiesUtil.getString("DIR_AUTO","paras")+"100441456";
		FilePath = PropertiesUtil.getString("DIR_AUTO","paras")+"100441456\\temp1.csv";
		String dirpath=FilePath.substring(0, FilePath.lastIndexOf("\\"));
		System.out.println(dirpath);
		if (!new File(dirpath).exists())
			new File(dirpath).mkdir();
		
		
		 try {
			new File(FilePath).createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
