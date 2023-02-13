package com.oval.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import net.sf.json.JSON;



public class HttpApiClient {
	
	public static Logger logger = LoggerFactory.getLogger(HttpApiClient.class);
	
	/** 使用HTTP GET方式获取数据 */
	public static final int HTTP_GET = 1;
	
	/** 使用HTTP POST方式获取数据 */
	public static final int HTTP_POST = 2;

	/** 代理主机 */
	private HttpHost proxy;
	
	public HttpApiClient() {
//		this.proxy = new HttpHost("cn-proxy.jp.oracle.com", 80, "http");
//		this.proxy = new HttpHost("121.43.60.123", 808, "http");
	}
	
	/**
	 * 设置代理服务器
	 * 
	 * @param	proxyUrl
	 * 			代理服务器地址
	 * 
	 * @param	port
	 * 			代理服务器端口号
	 * 
	 * @param	protocol
	 * 			代理服务器协议
	 * 
	 */
	public void setProxy(String proxyUrl, int port, String protocol) {
		proxy = new HttpHost(proxyUrl, port, protocol);  
	}
	

	/**
	 * 调用第三方API，支持GET和POST
	 * 
	 * @param	headers
	 * 			请求头
	 * 
	 * @param	params
	 * 			请求参数
	 * 
	 * @param	method
	 * 			HTTP请求方法
	 * 
	 * @return	响应字符串
	 */
	public String invoke(HttpClient client, String url , Map<String, String> headers, Map<String, String> params, int method) {

		if(client==null){
			client = getClient(url);
		} 
		
		HttpResponse response = null;
		
		try {
			
			// 将请求参数添加到请求体之中
			List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
			for (String paramKey : params.keySet()) {
				paramPairs.add(new BasicNameValuePair(paramKey, params.get(paramKey)));
			}
	
			if (method == HTTP_GET) {
				
				// 由于是GET请求，直接将请求参数添加到URL之中即可
				StringBuffer sb = new StringBuffer();
				sb.append(url);
				if (paramPairs.size() > 0) {
					sb.append("?").append(URLEncodedUtils.format(paramPairs, "UTF-8"));
				}
				
				// 调用API
				HttpGet httpGet = new HttpGet(sb.toString());
				
				// 添加请求头部
				if (headers != null) {
					for (String headerKey : headers.keySet()) {
						httpGet.addHeader(headerKey, headers.get(headerKey));
					}
				}
				
				// 有需要的话设置代理
				if (proxy != null) {
					RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
					httpGet.setConfig(config);
				}
				
				response = client.execute(httpGet);
			} else {
				
				// 调用API
				HttpPost httpPost = new HttpPost(url);
				
				// 添加请求头部
				if (headers != null) {
					for (String headerKey : headers.keySet()) {
						httpPost.addHeader(headerKey, headers.get(headerKey));
					}
				}
				
				// 有需要的话设置代理
				if (proxy != null) {
					RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
					httpPost.setConfig(config);
				}
				
				httpPost.setEntity(new UrlEncodedFormEntity(paramPairs, "GBK"));
				response = client.execute(httpPost);
			}
			
			// 返回响应字符串
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				String resp = EntityUtils.toString(httpEntity,"GB2312");
				return resp;
			} else {
			}
			
		} catch (ClientProtocolException e) {
			System.out.println(e);
		} catch (IOException e) {
		}
		
		return null;
	}
	
	
	/**
	 * 调用第三方API，支持GET和POST
	 * 
	 * @param	headers
	 * 			请求头
	 * 
	 * @param	params
	 * 			请求参数
	 * 
	 * @param	method
	 * 			HTTP请求方法
	 * 
	 * @return	响应字符串
	 */
	public String invokeFile(HttpClient client, String url , Map<String, String> headers, Map<String, String> params, int method,String fileName) {
		
		if(client==null){
			client = getClient(url);
		} 
		HttpResponse response = null;
		
		try {
			
			// 将请求参数添加到请求体之中
			List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
			for (String paramKey : params.keySet()) {
				paramPairs.add(new BasicNameValuePair(paramKey, params.get(paramKey)));
			}
			
			if (method == HTTP_GET) {
				
				// 由于是GET请求，直接将请求参数添加到URL之中即可
				StringBuffer sb = new StringBuffer();
				sb.append(url);
				if (paramPairs.size() > 0) {
					sb.append("?").append(URLEncodedUtils.format(paramPairs, "UTF-8"));
				}
				
				// 调用API
				HttpGet httpGet = new HttpGet(sb.toString());
				
				// 添加请求头部
				if (headers != null) {
					for (String headerKey : headers.keySet()) {
						httpGet.addHeader(headerKey, headers.get(headerKey));
					}
				}
				
				// 有需要的话设置代理
				if (proxy != null) {
					RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
					httpGet.setConfig(config);
				}
				
				response = client.execute(httpGet);
				
			} else {
				
				// 调用API
				HttpPost httpPost = new HttpPost(url);
				
				// 添加请求头部
				if (headers != null) {
					for (String headerKey : headers.keySet()) {
						httpPost.addHeader(headerKey, headers.get(headerKey));
					}
				}
				
				// 有需要的话设置代理
				if (proxy != null) {
					RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
					httpPost.setConfig(config);
				}
				
				httpPost.setEntity(new UrlEncodedFormEntity(paramPairs));
				response = client.execute(httpPost);
				HttpEntity httpEntity = response.getEntity();

				if (httpEntity != null) {
					File storeFile = new File(fileName);
			 	    FileOutputStream output = new FileOutputStream(storeFile);
			 	    httpEntity.writeTo(output);
					output.flush();
					output.close();
					logger.debug("Got response from third party api: {}", 1);
					return "1";
				} else {
					logger.debug("Failed to get response from third party api");
				}
			}
			
			// 返回响应图片
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				File storeFile = new File(fileName);
		 	    FileOutputStream output = new FileOutputStream(storeFile);
		 	    httpEntity.writeTo(output);
				output.flush();
				output.close();
				logger.debug("Got response from third party api: {}", 1);
				return "1";
			} else {
				logger.debug("Failed to get response from third party api");
			}
			
		} catch (ClientProtocolException e) {
			logger.debug("Failed to get response from third party api: {}", e.getMessage());
		} catch (IOException e) {
			logger.debug("Failed to get response from third party api: {}", e.getMessage());
		}
		
		return null;
	}
	/**
	 * 调用第三方API，只支持POST
	 * 
	 * @param	jsonParam
	 * 			JSON格式的参数
	 * 
	 * @return	响应字符串
	 */
	public String invoke(String url,JSONObject jsonParam) {
		return invoke(url,null, jsonParam);
	}
	
	/**
	 * 调用第三方API，只支持POST
	 * 
	 * @param	headers
	 * 			请求头
	 * 
	 * @param	jsonParam
	 * 			JSON格式的参数
	 * 
	 * @return	响应字符串
	 */
	public String invoke(String url , Map<String, String> headers, JSONObject jsonParam) {
		
		HttpClient client = getClient(url);
		HttpResponse response = null;
		
		//logger.debug("Calling third party api {} using POST method with JSON param {} ...", url, JSONObject.toString(jsonParam));
		
		try {
			// 调用API
			HttpPost httpPost = new HttpPost(url);
			
			// 添加请求头部
			if (headers != null) {
				for (String headerKey : headers.keySet()) {
					httpPost.addHeader(headerKey, headers.get(headerKey));
				}
			} else {
				httpPost.addHeader("Content-type","application/json; charset=utf-8");
				httpPost.setHeader("Accept", "application/json");
			}
			
			// 有需要的话设置代理
			if (proxy != null) {
				RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
				httpPost.setConfig(config);
			}
			
			httpPost.setEntity(new StringEntity(jsonParam.toString(), "utf-8"));
			response = client.execute(httpPost);
			
			// 返回响应字符串
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				String resp = EntityUtils.toString(httpEntity);
				logger.debug("Got response from third party api: {}", resp);
				return resp;
			} else {
				logger.debug("Failed to get response from third party api");
			}
			
		} catch (ClientProtocolException e) {
			logger.debug("Failed to get response from third party api: {}", e.getMessage());
		} catch (IOException e) {
			logger.debug("Failed to get response from third party api: {}", e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 调用第三方API，只支持POST
	 * 
	 * @param	headers
	 * 			请求头
	 * 
	 * @param	jsonParam
	 * 			String参数
	 * 
	 * @return	响应字符串
	 */
	public String invoke(String url,Map<String, String> headers, String jsonParam) {
		
		HttpClient client = getClient(url);
		HttpResponse response = null;
		
		//logger.debug("Calling third party api {} using POST method with JSON param {} ...", url, JSON.toJSONString(jsonParam));
		
		try {
			// 调用API
			HttpPost httpPost = new HttpPost(url);
			
			// 添加请求头部
			if (headers != null) {
				for (String headerKey : headers.keySet()) {
					httpPost.addHeader(headerKey, headers.get(headerKey));
				}
			}
			
			// 有需要的话设置代理
			if (proxy != null) {
				RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
				httpPost.setConfig(config);
			}
			
			httpPost.setEntity(new StringEntity(jsonParam));
			response = client.execute(httpPost);
			
			// 返回响应字符串
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				String resp = EntityUtils.toString(httpEntity);
				logger.debug("Got response from third party api: {}", resp);
				return resp;
			} else {
				logger.debug("Failed to get response from third party api");
			}
			
		} catch (ClientProtocolException e) {
			logger.debug("Failed to get response from third party api: {}", e.getMessage());
		} catch (IOException e) {
			logger.debug("Failed to get response from third party api: {}", e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 是否使用安全连接
	 * 
	 * @return	如果第三方API请求地址使用了HTTPS，返回true，否则返回false
	 */
	private boolean useSSL(String url) {
		return url.indexOf("https") != -1;
	}
	
	/**
	 * 获取HTTP Client对象
	 * 
	 * @return	HTTP Client对象
	 */
	public HttpClient getClient(String url) {
		if (useSSL(url)) {
			return createSSLClientDefault();
		} else {
			return HttpClients.createDefault();
		}
	}
	
	/**
	 * 创建一个支持SSL连接的HTTP客户端对象
	 * 
	 * @return	支持SSL连接的HTTP客户端对象
	 */
	private CloseableHttpClient createSSLClientDefault() {
		
		try {
			
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy(){
				@Override
				public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					return true;
				}
			}).build();
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
			
		} catch (KeyManagementException e) {
		} catch (NoSuchAlgorithmException e) {
		} catch (KeyStoreException e) {
		}
		return null;
	}
	
	/**
	 * 获取HTTP方法的字符串表示
	 * 
	 * @param	method
	 * 			HTTP方法
	 * 
	 * @return	HTTP方法的字符串表示
	 */
	private String getMethod(int method) {
		if (HTTP_GET == method) {
			return "GET";
		} else if (HTTP_POST == method) {
			return "POST";
		}
		
		return null;
	}
	
}
