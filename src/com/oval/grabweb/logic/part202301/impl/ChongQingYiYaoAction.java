package com.oval.grabweb.logic.part202301.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.csvreader.CsvReader;
import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.grabweb.logic.part201905.impl.SSLClient;
import com.oval.util.DateUtil;
import com.oval.util.StringUtil;
import com.oval.util.WebUtil;

/**
 * 重庆医药集团药特分有限责任公司
 * 
 * @author youzi
 *
 */
public class ChongQingYiYaoAction extends AbstractAction {
	public static final String ROOTPATH = Constant.DIR_AUTO;
	HttpClient httpClient = null;
	private Map<String, String> headers;
	String token = "";
	private CookieStore store;
	private String SF_cookie_31 = "";
	private String OAMAuthnCookie = "";
	private String loginOAMRequestContext1 = "";

	private Map<String, String> mapCodes; 

	@Override
	protected String[] createPurchas(int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] createStock(int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] createSale(int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getMapCodeByName(String key) {
		if (mapCodes == null) {
			mapCodes = new HashMap<String, String>();
			mapCodes.put("22153", "药特分公司"); //43
			mapCodes.put("36735", "新特药品"); //07
			mapCodes.put("31011", "合川医司"); //29
			mapCodes.put("22154", "永川医药"); //E5
			mapCodes.put("34448", "长寿医司"); //97
			mapCodes.put("22156", "潼南医司"); //16
			mapCodes.put("54406", "特殊药品分公司"); //94
			mapCodes.put("52337", "川人福医药");//PA
			mapCodes.put("22172", "宁夏众欣方泽");//R7
		}
		return mapCodes.get(key);
	}

	/**
	 * 重新定义库存模板
	 */
	protected List<String> createStock() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("库存时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("供应商代码").append(column_speractor);
		sb.append("供应商名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
        sb.append("批号").append(column_speractor);
        sb.append("生产日期").append(column_speractor);
        sb.append("失效日期").append(column_speractor);
        sb.append("数量").append(column_speractor);
        sb.append("库存状态").append(column_speractor);
        sb.append("入库时间").append(column_speractor);
		list.add(sb.toString());
		return list;

	}
	
	/**
	 * 重新定义采购模板
	 */
	protected List<String> createPurchas() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();

		sb.append("入库时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("供应商代码").append(column_speractor);
		sb.append("供应商名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
        sb.append("批号").append(column_speractor);
        sb.append("数量").append(column_speractor);
        sb.append("含税单价").append(column_speractor);
        sb.append("含税金额").append(column_speractor);
        sb.append("进货类型").append(column_speractor);
        sb.append("供应商出库单号").append(column_speractor);
		list.add(sb.toString());

		return list;
	}
	
	/**
	 * 重新定义销售模板
	 */
	protected List<String> createSale() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("出库时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("客户代码").append(column_speractor);
		sb.append("客户名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
        sb.append("批号").append(column_speractor);
        sb.append("数量").append(column_speractor);
        sb.append("含税单价").append(column_speractor);
        sb.append("含税金额").append(column_speractor);
        sb.append("出货类型").append(column_speractor);
        sb.append("客户城市").append(column_speractor);
		sb.append("客户地址").append(column_speractor);
		sb.append("经销商发货单号").append(column_speractor);
		sb.append("备注").append(column_speractor);

		list.add(sb.toString());
		return list;
	}
	
	@Override
	protected void login(HttpClient paramHttpClient, Map<String, String> paramMap) throws Exception {
		httpClient = new SSLClient();

		String str = WebUtil.repeatGet(httpClient, "http://www.cq-p.com.cn/cqpwps/pages/index/common.html#/0/");
		String json = "{\"oldid\":\"mucy\",\"password\":\"Mundi2022\",\"idmsyscode\":\"10009\"}";
		Map<String, String> params = new HashMap<String, String>();
		str = WebUtil.repeatPostJson(httpClient, params, "http://www.cq-p.com.cn/bassrv/webservice/loginauth/loginverify", json);
		String idmtoken = StringUtils.substringBetween(str, "idmtoken=", ";\"");
		
		this.store = new BasicCookieStore();
		headers = new HashMap<String, String>();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(store).build();
		// 测试逻辑
		HttpGet httpGet = new HttpGet("http://ucss.cq-p.com.cn:6107/?idmtoken=" + idmtoken);
		try {
			httpclient.execute(httpGet);
		} catch (ClientProtocolException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String SF_cookie_28 = "";
		String OAM_REQ_0 = "";
		String idmoamlogin = "";
		String JSESSIONID = "";
		String cookies = "";
		// 读取cookie信息
		List<org.apache.http.cookie.Cookie> cookielist = store.getCookies();
		
	    for(org.apache.http.cookie.Cookie cookie: cookielist){
	    	if ("SF_cookie_28".equals(cookie.getName())) {
	    		SF_cookie_28 = cookie.getValue();
	    	}
	    	if ("SF_cookie_31".equals(cookie.getName())) {
	    		SF_cookie_31 = cookie.getValue();
	    	}
	    	if ("OAM_REQ_0".equals(cookie.getName())) {
	    		OAM_REQ_0 = cookie.getValue();
	    	}
	    }

		URL serverUrl = new URL("http://ucss.cq-p.com.cn:6107/Login.aspx?idmtoken=" + idmtoken);
		HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");
		//必须设置false，否则会自动redirect到重定向后的地址
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Cookie", "SF_cookie_31=" + SF_cookie_31);
		conn.connect();
		loginOAMRequestContext1 = conn.getHeaderField("Set-Cookie");
		loginOAMRequestContext1 = loginOAMRequestContext1.substring(0, loginOAMRequestContext1.indexOf(";"));
		int statusCode = conn.getResponseCode();
		if (302 == statusCode) {
			String location = conn.getHeaderField("Location");
			cookies = conn.getHeaderField("Set-Cookie");
			serverUrl = new URL(location);
			conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setInstanceFollowRedirects(false);
			conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28);
			conn.connect();

			statusCode = conn.getResponseCode();
			location = conn.getHeaderField("Location");
			cookies = conn.getHeaderField("Set-Cookie");
			
			if (302 == statusCode) {
				serverUrl = new URL(location);
				conn = (HttpURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setInstanceFollowRedirects(false);
				conn.addRequestProperty("Cookie", "listenLoginKey=1; lprUrl=http%3A//ucss.cq-p.com.cn%3A6107/Login.aspx; SF_cookie_28" + SF_cookie_28 + "; " + OAM_REQ_0 + "; OAM_REQ_COUNT=VERSION_4~1");
				conn.connect();

				statusCode = conn.getResponseCode();
				location = conn.getHeaderField("Location");
				idmoamlogin = conn.getHeaderField(7).substring(0, conn.getHeaderField(7).indexOf(";"));
			}
		}
		
		str = WebUtil.repeatGet(paramHttpClient, "http://idm.cq-p.com.cn:6102/oamlogin/oamlogin");
		
		serverUrl = new URL("http://idm.cq-p.com.cn:6102/bassrv/wsr/captcha/code");
		conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; " + OAM_REQ_0	+ "; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin + "; ");
		conn.connect();
		statusCode = conn.getResponseCode();
		JSESSIONID = conn.getHeaderField(7).substring(0, conn.getHeaderField(7).indexOf(";"));	
		
		serverUrl = new URL("http://idm.cq-p.com.cn:6102/alogin/html/auto.html?resourceUrl=http://ucss.cq-p.com.cn:6107/Login.aspx");
		conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; " + OAM_REQ_0	+ "; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin + "; ");
		conn.connect();
		statusCode = conn.getResponseCode();
		cookies = conn.getHeaderField("Set-Cookie");
		if (302 == statusCode) {
			serverUrl = new URL(conn.getHeaderField("Location"));
			conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setInstanceFollowRedirects(false);
			conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; " + OAM_REQ_0
					+ "; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin + "; " + JSESSIONID + "; " + cookies + "; ");
			conn.connect();
			statusCode = conn.getResponseCode();
			OAM_REQ_0 = conn.getHeaderField(6).substring(0, conn.getHeaderField(6).indexOf(";"));
			if (302 == statusCode) {
				serverUrl = new URL(conn.getHeaderField("Location"));
				conn = (HttpURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setInstanceFollowRedirects(false);
				conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; " + OAM_REQ_0
						+ "; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin + "; " + JSESSIONID + "; " + cookies + "; ");
				conn.connect();
			}
		}
		
		cookies = cookies.substring(0, cookies.indexOf(";"));
		headers.put("Cookie", "listenLoginKey=1; lprUrl=http://ucss.cq-p.com.cn:6107/Login.aspx; SF_cookie_28=" + SF_cookie_28 + "; " +  OAM_REQ_0
				+ "; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin + "; " + JSESSIONID + "; " + cookies + "; ");
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://idm.cq-p.com.cn:6102/oamlogin/oautologin");
		
		headers.put("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; " + OAM_REQ_0
				+ "; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin + "; " + JSESSIONID + "; " + cookies + "; ");
		HttpResponse resp = WebUtil.repeatGet1(paramHttpClient, headers, "http://idm.cq-p.com.cn:6102/bassrv/webservice/user/getUseridAndPasswordByToken/"+idmtoken);
		if (500 == resp.getStatusLine().getStatusCode()) {
			resp = WebUtil.repeatGet1(paramHttpClient, headers, "http://idm.cq-p.com.cn:6102/bassrv/webservice/user/getUseridAndPasswordByToken/"+idmtoken);
		}
		JSESSIONID = resp.getAllHeaders()[6].getElements()[0].getValue();
		
		serverUrl = new URL("http://idm.cq-p.com.cn:6102/oam/server/auth_cred_submit");
		conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; " + OAM_REQ_0
				+ "; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin  + "; JSESSIONID=" + JSESSIONID + cookies + "; ");
		conn.setDoOutput(true);
        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        osw.write("userid=100041967&password=Password1&request_id=null");
        osw.flush();
        osw.close();
		statusCode = conn.getResponseCode();
		String OAM_ID = conn.getHeaderField(6).substring(0, conn.getHeaderField(6).indexOf(";"));
		
		if (302 == statusCode) {
			serverUrl = new URL(conn.getHeaderField("Location"));
			conn = (HttpURLConnection) serverUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setInstanceFollowRedirects(false);
			conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; OAM_REQ_0=invalid; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin  + "; JSESSIONID=" + JSESSIONID + "; " + cookies + "; " + OAM_ID + "; ");
			conn.connect();
			
			statusCode = conn.getResponseCode();
			OAMAuthnCookie = conn.getHeaderField(7).substring(0, conn.getHeaderField(7).indexOf(";"));
			if (302 == statusCode) {
				serverUrl = new URL("http://idm.cq-p.com.cn:6102/alogin/html/auto.html?resourceUrl=http://ucss.cq-p.com.cn:6107/Login.aspx");
				conn = (HttpURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setInstanceFollowRedirects(false);
				conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; OAM_REQ_0=invalid; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin  + "; JSESSIONID=" + JSESSIONID + "; " + cookies + "; " + OAM_ID + "; "
						+ OAMAuthnCookie + "; OAMAuthnHintCookie=X");
				conn.connect();
				statusCode = conn.getResponseCode();
			}
		}

		serverUrl = new URL("http://ucss.cq-p.com.cn:6107/Login.aspx");
		conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Cookie", "SF_cookie_31=" + SF_cookie_31 + "; " + loginOAMRequestContext1);
		conn.connect();
		statusCode = conn.getResponseCode();
		cookies = conn.getHeaderField("Set-Cookie");
		cookies = cookies.substring(0, cookies.indexOf(";"));
		
		serverUrl = new URL(conn.getHeaderField("Location"));
		conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Cookie", "SF_cookie_28=" + SF_cookie_28 + "; OAM_REQ_0=invalid; OAM_REQ_COUNT=VERSION_4~1; " + idmoamlogin 
				+  "; JSESSIONID=" + JSESSIONID + "; " + OAM_ID + "; " + OAMAuthnCookie);
		conn.connect();
		statusCode = conn.getResponseCode();
		
		serverUrl = new URL(conn.getHeaderField("Location"));
		conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Cookie", "SF_cookie_31=" + SF_cookie_31 + "; " + loginOAMRequestContext1 + "; " + cookies + "; ");
		conn.connect();
		statusCode = conn.getResponseCode();
		OAMAuthnCookie = conn.getHeaderField(4).substring(0, conn.getHeaderField(4).indexOf(";"));
		
		serverUrl = new URL("http://ucss.cq-p.com.cn:6107/Login.aspx");
		conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Cookie", "SF_cookie_31=" + SF_cookie_31 + "; " + loginOAMRequestContext1 + "; " + OAMAuthnCookie + "; OAMAuthnHintCookie=X; ");
		conn.connect();
		statusCode = conn.getResponseCode();
		cookies = conn.getHeaderField("Set-Cookie");
		
		headers.put("Cookie", "SF_cookie_31=" + SF_cookie_31 + "; " + loginOAMRequestContext1 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ");
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://ucss.cq-p.com.cn:6107/xslx.aspx");
	}

	protected String[] getStock(HttpClient paramHttpClient, String paramString, String code) throws Exception {
		String stockPath = String.format("%s\\%s", ROOTPATH + paramString , paramString + "temp1.csv");
		String now = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd") + " 00:00:00";
		File originalPath = new File(ROOTPATH + paramString );

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		
		headers.put("Cookie", "SF_cookie_31=" + SF_cookie_31 + "; " + loginOAMRequestContext1 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ");
		headers.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
		headers.put("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5");
		WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://ucss.cq-p.com.cn:6107/kccx.aspx");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("__EVENTARGUMENT",  "BC:0");
		params.put("__EVENTTARGET",  "ctl00$ContentPlaceHolder1$ASPxRoundPanel1$ASPxComboBox1");
		params.put("__EVENTVALIDATION",  "/wEdAAcxqLKb5IMSjYzuqoSIBIVY/YX7m4Lhat9wbS/+QfY6Wa4DAr48Ifs7f8B4EqkgmSvf8HV1/8w5iOfFrm5DtcXfUpcwKxgzdRIwwR3ErfV+lZxXcFEHsDfKz1aYnO8k713PRbrJX4iowWhwjwqwVrn5V/xgvP4NWZppjCYMrURC4JttDvzTCoYNwtBp2rtJCi4=");
		params.put("__VIEWSTATE",  "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iOWuieaWr+azsOadpR8EBQ/msojlronmlq/ms7DmnaUfCWUfCmcUKwABFggfCAUN5oSP5aSn5YipRkZBUB8EBQ3mhI/lpKfliKlGRkFQHwllHwpnFCsAARYIHwgFCeiLsUJBUkQgUB8EBQnoi7FCQVJEIFAfCWUfCmcUKwABFggfCAUL6Iux5Zu9SEFNT0wfBAUL6Iux5Zu9SEFNT0wfCWUfCmdkZGRkAgMPZBYCZg9kFgJmD2QWAmYPZBYCAgEPPCsAFgEADxYCHwNnZGQCBw8PZA8QFgFmFgEWAh4OUGFyYW1ldGVyVmFsdWUFBG11Y3kWAQIFZGQCCQ8PZA8QFgJmAgEWAhYCHwsFBG11Y3kWAh8LBQElFgICBQIFZGQCDQ8PZA8QFgFmFgEWAh8LBQRtdWN5FgECBWRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYJBRFjdGwwMCRBU1B4TmF2QmFyMQUhY3RsMDAkQVNQeE5hdkJhcjEkR0NUQzQkYnRubG9nb3V0BTNjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJEFTUHhSb3VuZFBhbmVsMSRjYmZncyREREQFMmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQVNQeFJvdW5kUGFuZWwxJGNic3AkREREBTJjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJEFTUHhSb3VuZFBhbmVsMSRjYmNwJERERAUvY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRBU1B4Um91bmRQYW5lbDEkYnRuY3YFO2N0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQVNQeFJvdW5kUGFuZWwxJEFTUHhDb21ib0JveDEkREREBS9jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGNicGFuZWwkQVNQeEdyaWRWaWV3MQVDY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRjYnBhbmVsJEFTUHhHcmlkVmlldzEkY3RsMDAkRFhFZGl0b3I4JERERP1q1duFoI1QoTLxtIL4BCH1zeWYdLnvT7FCzEdieIqS");
		params.put("__VIEWSTATEGENERATOR",  "FF340776");
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$ASPxComboBox1",  "CVS");
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$ASPxComboBox1$DDD$L",  "CVS");
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$cbcp",  "");
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$cbcp$DDD$L",  "");
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$cbfgs",  getMapCodeByName(paramString));
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$cbfgs$DDD$L",  code);
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$cbsp",  "");
		params.put("ctl00$ContentPlaceHolder1$ASPxRoundPanel1$cbsp$DDD$L",  "");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$ASPxGridView1$CallbackState",  "/wEWBB4ERGF0YQWsDkFBQUFBQVVBQUFBRkFBQUFBQUFBQUFVQUFBQUFEQUFBQUFkUGQyNWxja2xFQ0U5M2JtVnlJRWxFQndBQUNWUnlZV1JsVFdGeWF3cFVjbUZrWlNCTllYSnJCd0FBQlV4dmRFNXZCa3h2ZENCT2J3Y0FBQVpPZFcxaVpYSUdUblZ0WW1WeUJRQUFCRTVoYldVRVRtRnRaUWNBQUFWTmIyUmxiQVZOYjJSbGJBY0FBQWRIYjI5a2MwbEVDRWR2YjJSeklFbEVCd0FBQm5CdFoyZGpjQVp3YldkblkzQUhBQUFFVlc1cGRBUlZibWwwQndBQUNrVjRjR2x5WlVSaGRHVUxSWGh3YVhKbElFUmhkR1VIQUFBSlUzUnZZMnRPWVcxbENsTjBiMk5ySUU1aGJXVUhBQUFDWW5vQ1lub0hBQUFSQUFBQUEydGxlUVpEZFhOMFNVUUpUM2R1WlhKT1lXMWxCSE53WW0wRVZIbHdaUWRyWTBSaGRHVXlCMU4wYjJOclNVUUtVM1Z3Y0d4cFpYSkpSQVpyWTBSaGRHVUhZM1Z5UkdGMFpRWlZjMlZ5U1VRSlEzSmxZWFJFWVhSbEJIQjZkMmdKZVhCemMzaHJZM2x5Q0hkMGMyTnhlVzFqQW1wNEJHTndjV01IQWdNSEJRVUdxZ1lIQUFjQUJ3QUcvLzhIQWdJME13Y0NEK1M2ck9pUWpPaVNndVdJdHVpTnJ3Y0NCakkxTWpBeE5BVUdWd0lIQWhqbm01RHBoYmpudnAvb2dJUHBoYTdudkpQcGg0cm5pWWNIQWdrME1HMW5LakV3WUhNSEFnb3hNREF3TWpJMk5UZzJCd0l3TkRCdFp5b3hNR0J6NTV1UTZZVzQ1NzZmNklDRDZZV3U1N3lUNlllSzU0bUg1THFzNkpDTTZKS0M1WWkyNkkydkJ3SUQ1NXVTQndJS01qQXlOQzB4TWkwek1RY0NGeWpvamEvbmlibmxpSVlwNVp5ZjVMaTc1THVUNWJxVERBY0FCd0FHLy84SEFnSTBNd2NDRCtTNnJPaVFqT2lTZ3VXSXR1aU5yd2NDQmpJek5ETXhOUVVHSUFNSEFpYm5tNURwaGJqbnZwL29nSVBwaGE3bnZKUHBoNHJuaVljbzVhV2w1cGE5NWJxMzVhNmFLUWNDQ1RFd2JXY3FNVEJnY3djQ0NqRXdNREF5TWpRMk56Y0hBajR4TUcxbktqRXdZSFBubTVEcGhiam52cC9vZ0lQcGhhN252SlBwaDRybmlZY281YVdsNXBhOTVicTM1YTZhS2VTNnJPaVFqT2lTZ3VXSXR1aU5yd2NDQStlYmtnY0NDakl3TWpNdE1Ea3RNekFIQWhjbzZJMnY1NG01NVlpR0tlV2NuK1M0dStTN2srVzZrd3dIQUFjQUJ2Ly9Cd0lDTkRNSEFnL2t1cXpva0l6b2tvTGxpTGJvamE4SEFnWXlNelF6TVRRRkIwOEhBaWJubTVEcGhiam52cC9vZ0lQcGhhN252SlBwaDRybmlZY281YVdsNXBhOTVicTM1YTZhS1FjQ0NURXdiV2NxTVRCZ2N3Y0NDakV3TURBeU1qUTJOemNIQWo0eE1HMW5LakV3WUhQbm01RHBoYmpudnAvb2dJUHBoYTdudkpQcGg0cm5pWWNvNWFXbDVwYTk1YnEzNWE2YUtlUzZyT2lRak9pU2d1V0l0dWlOcndjQ0ErZWJrZ2NDQ2pJd01qTXRNRGt0TXpBSEFoY282STJ2NTRtNTVZaUdLZVdjbitTNHUrUzdrK1c2a3d3SEFBY0FCdi8vQndJQ05ETUhBZy9rdXF6b2tJem9rb0xsaUxib2phOEhBZ1l5TXpRMk16a0ZCd29IQWhYbm9hdnBoYmpsa0pmbGxhSG52SlBwaDRybmlZY0hBZ2t6TUcxbktqRXdZSE1IQWdveE1EQXdOVE0zT1RFd0J3SXRNekJ0WnlveE1HQno1NkdyNllXNDVaQ1g1WldoNTd5VDZZZUs1NG1INUxxczZKQ002SktDNVlpMjZJMnZCd0lENTV1U0J3SUtNakF5TXkwd09TMHpNQWNDRnlqb2phL25pYm5saUlZcDVaeWY1TGk3NUx1VDVicVREQWNBQndBRy8vOEhBZ0kwTXdjQ0QrUzZyT2lRak9pU2d1V0l0dWlOcndjQ0JqSTFNakF3T0FVSDJnY0NGZWVocSttRnVPV1FsK1dWb2VlOGsrbUhpdWVKaHdjQ0NUTXdiV2NxTVRCZ2N3Y0NDakV3TURBMU16YzVNVEFIQWkwek1HMW5LakV3WUhQbm9hdnBoYmpsa0pmbGxhSG52SlBwaDRybmlZZmt1cXpva0l6b2tvTGxpTGJvamE4SEFnUG5tNUlIQWdveU1ESTBMVEV4TFRNd0J3SVhLT2lOcitlSnVlV0loaW5sbkova3VMdmt1NVBsdXBNTR4FU3RhdGUFgAFCd3dIQUFJQkJ3RUNBUWNDQWdFSEF3SUJCd1FDQVFjREFnRUhCUUlCQndZQ0FRY0hBZ0VIQ0FJQkJ3a0NBUWNLQWdFSEFBY0FCd0FIQUFiLy93a0NCMGR2YjJSelNVUUpBZ0FDQUFNSEJBSUFCd0FDQVFjQUJ3QUNBUWNBQndBPQ==");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$ASPxGridView1$DXFocusedRowInput",  "-1");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$ASPxGridView1$DXSelInput",  "");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$txtselect",  "SELECT * FROM view_kclx where UserID = 'mucy' and OwnerID = '" + code + "'");
		params.put("ctl00$ContentPlaceHolder1$txtValue2",  "0");
		params.put("ctl00_ASPxNavBar1GS",  "1;0;0;1;1");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_ASPxComboBox1_DDD_LCustomCallback",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_ASPxComboBox1_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_ASPxComboBox1_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_ASPxComboBox1_DDDWS",  "0:0:12000:782:175:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_ASPxComboBox1_VI",  "CVS");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbcp_DDD_LCustomCallback",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbcp_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbcp_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbcp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbcp_VI",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbfgs_DDD_LCustomCallback",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbfgs_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbfgs_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbfgs_DDDWS",  "0:0:12000:272:148:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbfgs_VI",  code);
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbsp_DDD_LCustomCallback",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbsp_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbsp_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbsp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_ASPxRoundPanel1_cbsp_VI",  "");
		params.put("DXScript",  "1_32,1_61,1_46,2_22,2_28,2_15,2_29,2_21,1_54,1_51,2_24,1_31,1_39,1_52,3_7");
		
		String url = "http://ucss.cq-p.com.cn:6107/kccx.aspx";
		headers.clear();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Cookie",
				"SF_cookie_31=" + SF_cookie_31 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ASP.NET_SessionId=3nh34z21fy0t5sxwckyt1lfr; sljx_pzwh_zjfgs=0; " + loginOAMRequestContext1 + "; " );
		WebUtil.repeatPostByHeader1(paramHttpClient, params, headers, url, stockPath);
		
		List<String> data = createStock();
		
		try {
			// 创建CSV读对象
			CsvReader csvReader = new CsvReader(stockPath,',',Charset.forName("GBK"));
			// 读表头
			csvReader.readHeaders();
			//商品代码	品名	规格	厂牌	批号	单位	库存数量	失效日期	部门名称	品规/厂牌	仓库	包装
			String[] title = new String[] { "库存时间","生产厂家","供应商代码","供应商名称","产品代码","产品名称","产品规格","剂型","单位","批号","生产日期","失效日期","数量","库存状态","入库时间" };
			String[] contents = new String[] { "产品代码","产品名称","产品规格","生产厂家","批号","单位","数量","失效日期","部门名称","品规/厂牌","仓库","包装" };
			int[] indexs = getContentsIndexs(title, contents);
			while (csvReader.readRecord()) {
				// 读一整行
				String strs[] = csvReader.getValues();
				if (strs == null || strs.length < 10 || StringUtil.isEmpty(strs[0])) {
					break;
				}
				
				if("奈妥匹坦帕洛诺司琼胶囊".equals(strs[1]) && "36735".equals(paramString)) {
					continue;
				}
				
				if("抗人T细胞兔免疫球蛋白".equals(strs[1]) && "54406".equals(paramString)) {
					continue;
				}
				
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						if (j == 0) {
							sb.append(now).append(column_speractor);
						} else {
							sb.append("").append(column_speractor);
						}
					} else {
						sb.append(strs[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		return null;
	}
	
	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		return null;
	}
	
	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		return null;
	}
	
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString, String code) throws Exception {
		String purchasPath = String.format("%s\\%s", ROOTPATH + paramString , paramString + "temp2.csv");
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String startDate1 = DateUtil.getBeforeDayAgainstToday(60, "MM/dd/yyyy").replace("%2F", "/");
		String endDate1 = DateUtil.getBeforeDayAgainstToday(1, "MM/dd/yyyy").replace("%2F", "/");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		headers.put("Cookie", "SF_cookie_31=" + SF_cookie_31 + "; " + loginOAMRequestContext1 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ");
		headers.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
		headers.put("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5");
		WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://ucss.cq-p.com.cn:6107/gjlx.aspx");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("__EVENTTARGET", "ctl00$ContentPlaceHolder1$db_$ASPxComboBox1");
		params.put("__EVENTARGUMENT", "BC:0");
		params.put("__VIEWSTATE", "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5a6J5pav5rOw5p2lHwQFD+ayiOWuieaWr+azsOadpR8JZR8KZxQrAAEWCB8IBQ3mhI/lpKfliKlGRkFQHwQFDeaEj+Wkp+WIqUZGQVAfCWUfCmcUKwABFggfCAUJ6IuxQkFSRCBQHwQFCeiLsUJBUkQgUB8JZR8KZxQrAAEWCB8IBQvoi7Hlm71IQU1PTB8EBQvoi7Hlm71IQU1PTB8JZR8KZ2RkZGQCEQ8UKwAFDxYCHwQGAMAWEkjr2ohkZGQ8KwAJAQg8KwAGAQAWBB4PU2hvd0NsZWFyQnV0dG9uaB4PVG9kYXlCdXR0b25UZXh0BQbku4rlpKlkZAIVDxQrAAUPFgIfBAYAAPA3NvraiGRkZDwrAAkBCDwrAAYBABYEHwtoHwwFBuS7iuWkqWRkAgMPZBYCZg9kFgJmD2QWAmYPZBYCAgEPPCsAFgEADxYCHwNnZGQCBw8PZA8QFgFmFgEWAh4OUGFyYW1ldGVyVmFsdWUFBG11Y3kWAQIFZGQCCQ8PZA8QFgJmAgEWAhYCHw0FBG11Y3kWAh8NBQElFgICBQIFZGQCCw8PZA8QFgFmFgEWAh8NBQRtdWN5FgECBWRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYQBRFjdGwwMCRBU1B4TmF2QmFyMQUhY3RsMDAkQVNQeE5hdkJhcjEkR0NUQzQkYnRubG9nb3V0BSdjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGRiXyRjYmZncyREREQFJmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkZGJfJGNiZ2ckREREBSZjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGRiXyRjYmNwJERERAUpY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRkYl8kZGVzdGFydCREREQFL2N0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkZGJfJGRlc3RhcnQkREREJEMkRk5QBSdjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGRiXyRkZWVuZCREREQFLWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkZGJfJGRlZW5kJERERCRDJEZOUAUoY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRkYl8kY2JuYmdzJERERAUjY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRkYl8kYnRuY3YFL2N0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkZGJfJEFTUHhDb21ib0JveDEkREREBSdjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGNicGFuZWwkZ3ZfemIFO2N0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkY2JwYW5lbCRndl96YiRjdGwwMCREWEVkaXRvcjAkREREBUFjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGNicGFuZWwkZ3ZfemIkY3RsMDAkRFhFZGl0b3IwJERERCRDJEZOUAU8Y3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRjYnBhbmVsJGd2X3piJGN0bDAwJERYRWRpdG9yMTEkREREiP+Hh3A52BOgkkzkHLV/wzr7UVKnoA+88DqR7eNNaxk=");
		params.put("__VIEWSTATEGENERATOR", "2561C018");
		params.put("__EVENTVALIDATION", "/wEdAAg28n5Yz6v27f7JOQRpOBQ7Z/TtGVzAXJeOuEuuFE0Z2ox2Qfb9wXOoWX0NV8zbyyQ0xLeZUQJ5b8WOGAukHQD0HWcQVVNIuu0bNoJDR1GGTa8M891GunuYGpIMrA5jiBucV3BRB7A3ys9WmJzvJO9d8kHZ7bN/ujcQFsqRbAKl2i5lLylnd7ai4hR08LRLm+TEOgRkQNLXCJO0BL1TZDeo");
		params.put("ctl00_ASPxNavBar1GS", "1;0;0;1;1");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_VI", code);
		params.put("ctl00$ContentPlaceHolder1$db_$cbfgs", getMapCodeByName(paramString));
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDDWS", "0:0:12000:271:148:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LDeletedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LInsertedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LCustomCallback", "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbfgs$DDD$L", code);
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_VI", "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbgg", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDDWS", "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LDeletedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LInsertedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LCustomCallback", "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbgg$DDD$L", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_VI", "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbcp", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDDWS", "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LDeletedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LInsertedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LCustomCallback", "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbcp$DDD$L", "");
		params.put("ctl00_ContentPlaceHolder1_db__destart_Raw", "1672531200000");
		params.put("ctl00$ContentPlaceHolder1$db_$destart", startDate);
		params.put("ctl00_ContentPlaceHolder1_db__destart_DDDWS", "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__destart_DDD_C_FNPWS", "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$destart$DDD$C", "" + startDate1 +":"+ startDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__deend_Raw", "1674172800000");
		params.put("ctl00$ContentPlaceHolder1$db_$deend", endDate);
		params.put("ctl00_ContentPlaceHolder1_db__deend_DDDWS", "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__deend_DDD_C_FNPWS", "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$deend$DDD$C", "" + endDate1 +":"+ endDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_VI", "%");
		params.put("ctl00$ContentPlaceHolder1$db_$cbnbgs", "全部");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDDWS", "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LDeletedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LInsertedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LCustomCallback", "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbnbgs$DDD$L", "%");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_VI", "CVS");
		params.put("ctl00$ContentPlaceHolder1$db_$ASPxComboBox1", "CVS");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDDWS", "0:0:-1:-10000G-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LDeletedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LInsertedItems", "");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LCustomCallback", "");
		params.put("ctl00$ContentPlaceHolder1$db_$ASPxComboBox1$DDD$L", "CVS");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$DXSelInput", "");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$DXFocusedRowInput", "-1");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$CallbackState", "/wEWBB4ERGF0YQWwNEFBQUFBQm9BQUFBYUFBQUFBQUFBQUE4QUFBQUFFZ0FBQUFsVGRHOWphMDVoYldVS1UzUnZZMnNnVG1GdFpRY0FBQWRwYm5OMFkybGtCMmx1YzNSamFXUUhBQUFKVkhKaFpHVk5ZWEpyQ2xSeVlXUmxJRTFoY21zSEFBQUZURzkwVG04R1RHOTBJRTV2QndBQUIyOTNibVZ5YVdRSGIzZHVaWEpwWkFjQUFBaEpibE4wWTA1MWJRcEpiaUJUZEdNZ1RuVnRCUUFBQ1VsdVUzUmpSR0YwWlF0SmJpQlRkR01nUkdGMFpRZ0FBQUp3YlFKd2JRY0FBQVZOYjJSbGJBVk5iMlJsYkFjQUFBaEpibE4wWTFCeWFRcEpiaUJUZEdNZ1VISnBCUUFBQ0dOMWMzUnVZVzFsQ0dOMWMzUnVZVzFsQndBQUIwZHZiMlJ6U1VRSVIyOXZaSE1nU1VRSEFBQUVWVzVwZEFSVmJtbDBCd0FBQW1KNkFtSjZCd0FBQ21WNGNHbHlaV1JoZEdVS1pYaHdhWEpsWkdGMFpRY0FBQWw1Y0hOemVHdGplWElKZVhCemMzaHJZM2x5QndBQUNIZDBjMk54ZVcxakNIZDBjMk54ZVcxakJ3QUFERWx1VTNSalVISnBYM1JoZUE1SmJpQlRkR01nVUhKcFgzUmhlQVVBQUFNQUFBQUVjM0JpYlFsSmJsTjBZMVI1Y0dVS1NXNVRkR05OYjI1bGVRY0NBd2NhQlFabElBY0FCd0FIQUFiLy93Y0NGeWpvamEvbmlibmxpSVlwNVp5ZjVMaTc1THVUNWJxVEJ3SUtOakF3TmpFM01ETTRPUWNDRCtTNnJPaVFqT2lTZ3VXSXR1aU5yd2NDQmpJek5ETXhOQWNDQWpRekJRWkFCZ2dDQkFDQURYVWQ3ZG9JQndJbTU1dVE2WVc0NTc2ZjZJQ0Q2WVd1NTd5VDZZZUs1NG1IS09XbHBlYVd2ZVc2dCtXdW1pa0hBZ2t4TUcxbktqRXdZSE1GQXpBWUFBQUFBQUFBQUFBQUFBQUFBZ0FIQWp2cGg0M2x1b2Jsakx2b2phOG82WnVHNVp1aUtlaUNvZVM3dmVhY2llbVprT1dGck9XUHVPZUp1ZWF1aXVpTnIrV1RnZVdJaHVXRnJPV1B1QWNDQ2pFd01EQXlNalEyTnpjSEFnUG5tNUlIQWdNME1EQUhBaE15TURJekxUQTVMVE13SURBd09qQXdPakF3QndJajZJdXg1WnU5SUU1QlVGQWdVRWhCVWsxQlEwVlZWRWxEUVV4VElFeEpUVWxVUlVRSEFnQUZBMVViQUFBQUFBQUFBQUFBQUFBQUFnQUhBQWNBQnYvL0J3SVhLT2lOcitlSnVlV0loaW5sbkova3VMdmt1NVBsdXBNSEFnbzJNREEyTVRjd016ZzVCd0lQNUxxczZKQ002SktDNVlpMjZJMnZCd0lHTWpVeU1ERTBCd0lDTkRNRkJpQURDQUlFQUlBTmRSM3QyZ2dIQWhqbm01RHBoYmpudnAvb2dJUHBoYTdudkpQcGg0cm5pWWNIQWdrME1HMW5LakV3WUhNRkEzNWRBQUFBQUFBQUFBQUFBQUFBQWdBSEFqdnBoNDNsdW9ibGpMdm9qYThvNlp1RzVadWlLZWlDb2VTN3ZlYWNpZW1aa09XRnJPV1B1T2VKdWVhdWl1aU5yK1dUZ2VXSWh1V0ZyT1dQdUFjQ0NqRXdNREF5TWpZMU9EWUhBZ1BubTVJSEFnTTBNREFIQWhNeU1ESTBMVEV5TFRNeElEQXdPakF3T2pBd0J3SWNUa0ZRVUNCUVNFRlNUVUZEUlZWVVNVTkJURk1nVEVsTlNWUkZSQWNDQUFVRHBXa0FBQUFBQUFBQUFBQUFBQUFDQUFjQUJ3QUcvLzhIQWhjbzZJMnY1NG01NVlpR0tlV2NuK1M0dStTN2srVzZrd2NDQ2pZd01EWXlNamd6TnpVSEFnL2t1cXpva0l6b2tvTGxpTGJvamE4SEFnWXlOVEl3TURnSEFnSTBNd1VHa0FFSUFnUUFBT0hKcis3YUNBY0NGZWVocSttRnVPV1FsK1dWb2VlOGsrbUhpdWVKaHdjQ0NUTXdiV2NxTVRCZ2N3VURRQjBBQUFBQUFBQUFBQUFBQUFBQ0FBY0NPK21IamVXNmh1V011K2lOcnlqcG00YmxtNklwNklLaDVMdTk1cHlKNlptUTVZV3M1WSs0NTRtNTVxNks2STJ2NVpPQjVZaUc1WVdzNVkrNEJ3SUtNVEF3TURVek56a3hNQWNDQStlYmtnY0NBelF3TUFjQ0V6SXdNalF0TVRFdE16QWdNREE2TURBNk1EQUhBaUxvaTdIbG03MU9RVkJRSUZCSVFWSk5RVU5GVlZSSlEwRk1VeUJNU1UxSlZFVkVCd0lBQlFNTklRQUFBQUFBQUFBQUFBQUFBQUlBQndBSEFBYi8vd2NDRnlqb2phL25pYm5saUlZcDVaeWY1TGk3NUx1VDVicVRCd0lLTmpBd05qSXlPRE0zTlFjQ0QrUzZyT2lRak9pU2d1V0l0dWlOcndjQ0JqSXpORE14TlFjQ0FqUXpCUVlnQXdnQ0JBQUE0Y212N3RvSUJ3SW01NXVRNllXNDU3NmY2SUNENllXdTU3eVQ2WWVLNTRtSEtPV2xwZWFXdmVXNnQrV3VtaWtIQWdreE1HMW5LakV3WUhNRkF6QVlBQUFBQUFBQUFBQUFBQUFBQWdBSEFqdnBoNDNsdW9ibGpMdm9qYThvNlp1RzVadWlLZWlDb2VTN3ZlYWNpZW1aa09XRnJPV1B1T2VKdWVhdWl1aU5yK1dUZ2VXSWh1V0ZyT1dQdUFjQ0NqRXdNREF5TWpRMk56Y0hBZ1BubTVJSEFnTTBNREFIQWhNeU1ESXpMVEE1TFRNd0lEQXdPakF3T2pBd0J3SWo2SXV4NVp1OUlFNUJVRkFnVUVoQlVrMUJRMFZWVkVsRFFVeFRJRXhKVFVsVVJVUUhBZ0FGQTFVYkFBQUFBQUFBQUFBQUFBQUFBZ0FIQUFjQUJ2Ly9Cd0lYS09pTnIrZUp1ZVdJaGlubG5KL2t1THZrdTVQbHVwTUhBZ28yTURBMk1qTTVNRFl5QndJUDViNjM1WnU5NXFDODVZV3c1ck93QndJR01EQTROVGhVQndJQ05ETUZCeFFJQWdRQXdFcjBlTy9hQ0FjQ0lPZWJrT21GdU9hYnN1bXByT1drbXVhenFPV3doT2Eyc2lqb2lKTG1sWThwQndJT01tMXNPakV3TUcxbktqWG1sSzhGQTBVUUFBQUFBQUFBQUFBQUFBQUFBZ0FIQWlUcGg0M2x1b2Jsakx2b2phL21sckRuaWJub2phL2xrNEhtbklucG1aRGxoYXpsajdnSEFnb3hNREF3TVRZeE16azJCd0lENTV1U0J3SURNemcwQndJVE1qQXlOeTB3TVMwek1TQXdNRG93TURvd01BY0NGZVcrdCtXYnZVZHlkVzVsYm5Sb1lXd2dSMjFpU0FjQ0FBVURZaElBQUFBQUFBQUFBQUFBQUFBQ0FBY0FCd0FHLy84SEFoY282STJ2NTRtNTVZaUdLZVdjbitTNHUrUzdrK1c2a3djQ0NqWXdNRFl5TXprME9EQUhBZy9sdnJmbG03M21vTHpsaGJEbXM3QUhBZ1l3TURnMU9GUUhBZ0kwTXdVSEdBZ0NCQURBU3ZSNDc5b0lCd0lnNTV1UTZZVzQ1cHV5NmFtczVhU2E1ck9vNWJDRTVyYXlLT2lJa3VhVmp5a0hBZzR5Yld3Nk1UQXdiV2NxTmVhVXJ3VURSUkFBQUFBQUFBQUFBQUFBQUFBQ0FBY0NKT21IamVXNmh1V011K2lOcithV3NPZUp1ZWlOcitXVGdlYWNpZW1aa09XRnJPV1B1QWNDQ2pFd01EQXhOakV6T1RZSEFnUG5tNUlIQWdNek9EUUhBaE15TURJM0xUQXhMVE14SURBd09qQXdPakF3QndJVjViNjM1WnU5UjNKMWJtVnVkR2hoYkNCSGJXSklCd0lBQlFOaUVnQUFBQUFBQUFBQUFBQUFBQUlBQndBSEFBYi8vd2NDRnlqb2phL25pYm5saUlZcDVaeWY1TGk3NUx1VDVicVRCd0lLTmpBd05qSXpPVFE0TXdjQ0QrVyt0K1didmVhZ3ZPV0ZzT2F6c0FjQ0JqQXdPRFU0VkFjQ0FqUXpCUWN5Q0FJRUFNQks5SGp2MmdnSEFpRG5tNURwaGJqbW03THBxYXpscEpybXM2amxzSVRtdHJJbzZJaVM1cFdQS1FjQ0RqSnRiRG94TURCdFp5bzE1cFN2QlFORkVBQUFBQUFBQUFBQUFBQUFBQUlBQndJazZZZU41YnFHNVl5NzZJMnY1cGF3NTRtNTZJMnY1Wk9CNXB5SjZabVE1WVdzNVkrNEJ3SUtNVEF3TURFMk1UTTVOZ2NDQStlYmtnY0NBek00TkFjQ0V6SXdNamN0TURFdE16RWdNREE2TURBNk1EQUhBaFhsdnJmbG03MUhjblZ1Wlc1MGFHRnNJRWR0WWtnSEFnQUZBMklTQUFBQUFBQUFBQUFBQUFBQUFnQUhBQWNBQnYvL0J3SVhLT2lOcitlSnVlV0loaW5sbkova3VMdmt1NVBsdXBNSEFnbzJNREEyTWpVNE16YzJCd0lQNWI2MzVadTk1cUM4NVlXdzVyT3dCd0lHTURBNE5UaFVCd0lDTkRNRkJ3VUlBZ1FBZ0xRZVF2RGFDQWNDSU9lYmtPbUZ1T2Fic3VtcHJPV2ttdWF6cU9Xd2hPYTJzaWpvaUpMbWxZOHBCd0lPTW0xc09qRXdNRzFuS2pYbWxLOEZBMFVRQUFBQUFBQUFBQUFBQUFBQUFnQUhBaVRwaDQzbHVvYmxqTHZvamEvbWxyRG5pYm5vamEvbGs0SG1uSW5wbVpEbGhhemxqN2dIQWdveE1EQXdNVFl4TXprMkJ3SUQ1NXVTQndJRE16ZzBCd0lUTWpBeU55MHdNUzB6TVNBd01Eb3dNRG93TUFjQ0ZlVyt0K1didlVkeWRXNWxiblJvWVd3Z1IyMWlTQWNDQUFVRFloSUFBQUFBQUFBQUFBQUFBQUFDQUFjQUJ3QUcvLzhIQWhjbzZJMnY1NG01NVlpR0tlV2NuK1M0dStTN2srVzZrd2NDQ2pZd01EWXlOVGcwTXpVSEFnL2x2cmZsbTczbW9MemxoYkRtczdBSEFnWXdNRGcxT0ZRSEFnSTBNd1VIOEFnQ0JBQ0F0QjVDOE5vSUJ3SWc1NXVRNllXNDVwdXk2YW1zNWFTYTVyT281YkNFNXJheUtPaUlrdWFWanlrSEFnNHliV3c2TVRBd2JXY3FOZWFVcndVRFJSQUFBQUFBQUFBQUFBQUFBQUFDQUFjQ0pPbUhqZVc2aHVXTXUraU5yK2FXc09lSnVlaU5yK1dUZ2VhY2llbVprT1dGck9XUHVBY0NDakV3TURBeE5qRXpPVFlIQWdQbm01SUhBZ016T0RRSEFoTXlNREkzTFRBeExUTXhJREF3T2pBd09qQXdCd0lWNWI2MzVadTlSM0oxYm1WdWRHaGhiQ0JIYldKSUJ3SUFCUU5pRWdBQUFBQUFBQUFBQUFBQUFBSUFCd0FIQUFiLy93Y0NGeWpvamEvbmlibmxpSVlwNVp5ZjVMaTc1THVUNWJxVEJ3SUtOakF3TmpJMU9EUXpOZ2NDRCtXK3QrV2J2ZWFndk9XRnNPYXpzQWNDQmpBd09EVTRWQWNDQWpRekJRY29DQUlFQUlDMEhrTHcyZ2dIQWlEbm01RHBoYmptbTdMcHFhemxwSnJtczZqbHNJVG10cklvNklpUzVwV1BLUWNDRGpKdGJEb3hNREJ0WnlvMTVwU3ZCUU5GRUFBQUFBQUFBQUFBQUFBQUFBSUFCd0lrNlllTjVicUc1WXk3NkkydjVwYXc1NG01NkkydjVaT0I1cHlKNlptUTVZV3M1WSs0QndJS01UQXdNREUyTVRNNU5nY0NBK2Via2djQ0F6TTROQWNDRXpJd01qY3RNREV0TXpFZ01EQTZNREE2TURBSEFoWGx2cmZsbTcxSGNuVnVaVzUwYUdGc0lFZHRZa2dIQWdBRkEySVNBQUFBQUFBQUFBQUFBQUFBQWdBSEFBY0FCdi8vQndJWEtPaU5yK2VKdWVXSWhpbmxuSi9rdUx2a3U1UGx1cE1IQWdvMk1EQTJNalU0T1RBNEJ3SVA1YjYzNVp1OTVxQzg1WVd3NXJPd0J3SUdNREE0TlRoVUJ3SUNORE1GQm9BSENBSUVBSUMwSGtMdzJnZ0hBaURubTVEcGhiam1tN0xwcWF6bHBKcm1zNmpsc0lUbXRySW82SWlTNXBXUEtRY0NEakp0YkRveE1EQnRaeW8xNXBTdkJRTkZFQUFBQUFBQUFBQUFBQUFBQUFJQUJ3SWs2WWVONWJxRzVZeTc2STJ2NXBhdzU0bTU2STJ2NVpPQjVweUo2Wm1RNVlXczVZKzRCd0lLTVRBd01ERTJNVE01TmdjQ0ErZWJrZ2NDQXpNNE5BY0NFekl3TWpjdE1ERXRNekVnTURBNk1EQTZNREFIQWhYbHZyZmxtNzFIY25WdVpXNTBhR0ZzSUVkdFlrZ0hBZ0FGQTJJU0FBQUFBQUFBQUFBQUFBQUFBZ0FIQUFjQUJ2Ly9Cd0lYS09pTnIrZUp1ZVdJaGlubG5KL2t1THZrdTVQbHVwTUhBZ28yTURBMk1qVTRPVEU0QndJUDVMcXM2SkNNNkpLQzVZaTI2STJ2QndJR01qVXlOVEV6QndJQ05ETUZCOGdJQWdRQWdMUWVRdkRhQ0FjQ0p1ZWJrT21GdU9lK24raUFnK21GcnVlOGsrbUhpdWVKaHlqbHBhWG1scjNsdXJmbHJwb3BCd0lKTVRCdFp5b3pNR0J6QlFQclJnQUFBQUFBQUFBQUFBQUFBQUlBQndJNzZZZU41YnFHNVl5NzZJMnZLT21iaHVXYm9pbm9ncUhrdTczbW5JbnBtWkRsaGF6bGo3am5pYm5tcm9yb2phL2xrNEhsaUlibGhhemxqN2dIQWdveE1EQXdNekF6TkRVMUJ3SUQ1NXVTQndJRE1qQXdCd0lUTWpBeU5TMHdNaTB5T0NBd01Eb3dNRG93TUFjQ0l1aUxzZVdidlU1QlVGQWdVRWhCVWsxQlEwVlZWRWxEUVV4VElFeEpUVWxVUlVRSEFnQUZBeU5RQUFBQUFBQUFBQUFBQUFBQUFnQUhBQWNBQnYvL0J3SVhLT2lOcitlSnVlV0loaW5sbkova3VMdmt1NVBsdXBNSEFnbzJNREEyTWpnMk56TTBCd0lQNUxxczZKQ002SktDNVlpMjZJMnZCd0lHTWpVeU5URXpCd0lDTkRNRkI4Z0lBZ1FBQUloejFQSGFDQWNDSnVlYmtPbUZ1T2UrbitpQWcrbUZydWU4ayttSGl1ZUpoeWpscGFYbWxyM2x1cmZscnBvcEJ3SUpNVEJ0Wnlvek1HQnpCUVByUmdBQUFBQUFBQUFBQUFBQUFBSUFCd0k3NlllTjVicUc1WXk3NkkydktPbWJodVdib2lub2dxSGt1NzNtbklucG1aRGxoYXpsajdqbmlibm1yb3JvamEvbGs0SGxpSWJsaGF6bGo3Z0hBZ294TURBd016QXpORFUxQndJRDU1dVNCd0lETWpBd0J3SVRNakF5TlMwd01pMHlPQ0F3TURvd01Eb3dNQWNDSXVpTHNlV2J2VTVCVUZBZ1VFaEJVazFCUTBWVlZFbERRVXhUSUV4SlRVbFVSVVFIQWdBRkF5TlFBQUFBQUFBQUFBQUFBQUFBQWdBSEFBY0FCdi8vQndJWEtPaU5yK2VKdWVXSWhpbmxuSi9rdUx2a3U1UGx1cE1IQWdvMk1EQTJNamcyTnpNMEJ3SUo2SXV4UWtGU1JDQlFCd0lHTWpReE9USTNCd0lDTkRNRkJ5Z0lBZ1FBQUloejFQSGFDQWNDRmVlYmtPbUZ1T2UrbitpQWcrbUZydWlEdHVXYmlnY0NDRFZ0WnlveE1HQnpCUU9BRFFBQUFBQUFBQUFBQUFBQUFBSUFCd0k3NlllTjVicUc1WXk3NkkydktPbWJodVdib2lub2dxSGt1NzNtbklucG1aRGxoYXpsajdqbmlibm1yb3JvamEvbGs0SGxpSWJsaGF6bGo3Z0hBZ294TURBd05EazNOREV4QndJRDU1dVNCd0lETWpBd0J3SVRNakF5TkMwd05DMHpNQ0F3TURvd01Eb3dNQWNDSXVpTHNlV2J2VTVCVUZBZ1VFaEJVazFCUTBWVlZFbERRVXhUSUV4SlRVbFVSVVFIQWdBRkEwRVBBQUFBQUFBQUFBQUFBQUFBQWdBSEFBY0FCdi8vQndJWEtPaU5yK2VKdWVXSWhpbmxuSi9rdUx2a3U1UGx1cE1IQWdvMk1EQTJNekEyTkRNeUJ3SVA1YjYzNVp1OTVxQzg1WVd3NXJPd0J3SUdNREE0TlRoVUJ3SUNORE1GQjJRSUFnUUF3UEdkbmZMYUNBY0NJT2Via09tRnVPYWJzdW1wck9Xa211YXpxT1d3aE9hMnNpam9pSkxtbFk4cEJ3SU9NbTFzT2pFd01HMW5LalhtbEs4RkEwVVFBQUFBQUFBQUFBQUFBQUFBQWdBSEFpVHBoNDNsdW9ibGpMdm9qYS9tbHJEbmlibm9qYS9sazRIbW5JbnBtWkRsaGF6bGo3Z0hBZ294TURBd01UWXhNemsyQndJRDU1dVNCd0lETXpnMEJ3SVRNakF5Tnkwd01TMHpNU0F3TURvd01Eb3dNQWNDRmVXK3QrV2J2VWR5ZFc1bGJuUm9ZV3dnUjIxaVNBY0NBQVVEWWhJQUFBQUFBQUFBQUFBQUFBQUNBQT09HgVTdGF0ZQWcAUJ4RUhBQUlCQndFQ0FRY0NBZ0VIQXdJQkJ3UUNBUWNEQWdFSEJRSUJCd1lDQVFjSUFnRUhCZ0lCQndjQ0FRY0pBZ0VIQ3dJQkJ3b0NBUWNNQWdFSERRSUJCdzRDQVFjQUJ3QUhBQWNBQnYvL0NRSUhhVzV6ZEdOcFpBa0NBQUlBQXdjRUFnQUhBQUlCQndBSEFBSUJCd0FIQUE9PQ==");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$ASPxTextBox1", "SELECT StockName,instcid, spbm, TradeMark, LotNo,ownerid, InStcNum, InStcType, InStcDate, pm, Model, InStcPri, InStcMoney, custname,GoodsID,Unit,bz,expiredate,ypssxkcyr,wtscqymc,InStcPri_tax FROM View_gjlx where UserID = 'mucy' and InStcDate >= '"+startDate+" 00:00:00' and InStcDate <= '"+endDate+" 23:59:59' and OwnerID = '"+code+"' and InStcType not in ('20')   order by InStcDate");
		params.put("ctl00$ContentPlaceHolder1$txtValue2", "0");
		params.put("DXScript", "1_32,1_61,1_46,2_22,2_28,2_15,2_29,2_21,1_54,1_51,2_24,1_36,2_16,1_31,1_39,1_52,3_7");
		String url = "http://ucss.cq-p.com.cn:6107/gjlx.aspx";
		headers.clear();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Cookie",
				"SF_cookie_31=" + SF_cookie_31 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ASP.NET_SessionId=3nh34z21fy0t5sxwckyt1lfr; sljx_pzwh_zjfgs=0; " + loginOAMRequestContext1 + "; " );
		WebUtil.repeatPostByHeader1(paramHttpClient, params, headers, url, purchasPath); 
		List<String> data = createPurchas();
		
		try {
			// 创建CSV读对象
			CsvReader csvReader = new CsvReader(purchasPath,',',Charset.forName("GBK"));
			// 读表头
			csvReader.readHeaders();
			//"入库日期","品名","规格","厂牌","购进数","单位","批号","失效日期","商品代码","供应商","单价","分子公司","包装","仓库","上市许可持有人","委托生产企业","含税单价"
			String[] title = new String[] { "入库时间","生产厂家","供应商代码","供应商名称","产品代码","产品名称","产品规格","剂型","单位","批号","数量","含税单价","含税金额","进货类型","供应商出库单号" };
			String[] contents = new String[] { "入库时间","产品名称","产品规格","生产厂家","数量","单位","批号","失效日期","产品代码","供应商名称","单价","分子公司","包装","仓库","上市许可持有人","委托生产企业","含税单价" };
			int[] indexs = getContentsIndexs(title, contents);
			while (csvReader.readRecord()) {
				// 读一整行
				String strs[] = csvReader.getValues();
				if (strs == null || strs.length < 17 || StringUtil.isEmpty(strs[1])) {
					break;
				}
				
				if("奈妥匹坦帕洛诺司琼胶囊".equals(strs[1]) && "36735".equals(paramString)) {
					continue;
				}
				
				if("抗人T细胞兔免疫球蛋白".equals(strs[1]) && "54406".equals(paramString)) {
					continue;
				}
				
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
							sb.append("").append(column_speractor);
					} else {
						sb.append(strs[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data.toArray(new String[1]);
	}
	
	
	protected String[] getSale(HttpClient paramHttpClient, String paramString, String code) throws Exception {
		String salePath = String.format("%s\\%s", ROOTPATH + paramString , paramString + "temp3.csv");
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		
		String startDate1 = DateUtil.getBeforeDayAgainstToday(60, "MM/dd/yyyy").replace("%2F", "/");
		String endDate1 = DateUtil.getBeforeDayAgainstToday(1, "MM/dd/yyyy").replace("%2F", "/");
		File originalPath = new File(ROOTPATH + paramString );

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		headers.put("Cookie", "SF_cookie_31=" + SF_cookie_31 + "; " + loginOAMRequestContext1 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ");
		headers.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
		headers.put("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5");
		String str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://ucss.cq-p.com.cn:6107/xslx.aspx");

		String VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		String EVENTVALIDATION = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
		String CallbackState = StringUtil.getValue(str, "<input type=\"hidden\" name=\"ctl00$ContentPlaceHolder1$cbpanel$gv_zb$CallbackState\" id=\"ctl00_ContentPlaceHolder1_cbpanel_gv_zb_CallbackState\" value=\"", "\" />", 1);

		Map<String, String> params = new HashMap<String, String>();
		params.put("__EVENTTARGET",  "");
		params.put("__EVENTARGUMENT",  "");
		params.put("__VIEWSTATE",  VIEWSTATE);
		params.put("__VIEWSTATEGENERATOR",  "0CA43D93");
		params.put("ctl00_ASPxNavBar1GS",  "1;0;0;1;1");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_VI",  code);
		params.put("ctl00$ContentPlaceHolder1$db_$cbfgs",  getMapCodeByName(paramString));
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDDWS",  "0:0:12000:300:148:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbfgs$DDD$L",  code);
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_VI",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbgg",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbgg$DDD$L",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_VI",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbcp",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbcp$DDD$L",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_VI",  "%");
		params.put("ctl00$ContentPlaceHolder1$db_$cbnbgs",  "全部");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbnbgs$DDD$L",  "%");
		params.put("ctl00_ContentPlaceHolder1_db__destart_kp_Raw",  "1672531200000");
		params.put("ctl00$ContentPlaceHolder1$db_$destart_kp",  startDate);
		params.put("ctl00_ContentPlaceHolder1_db__destart_kp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__destart_kp_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$destart_kp$DDD$C",  "" + startDate1 +":"+ startDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__deend_kp_Raw",  "1674864000000");
		params.put("ctl00$ContentPlaceHolder1$db_$deend_kp",  endDate);
		params.put("ctl00_ContentPlaceHolder1_db__deend_kp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__deend_kp_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$deend_kp$DDD$C",  ""+ endDate1 +":"+ endDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__destart_Raw",  "1672531200000");
		params.put("ctl00$ContentPlaceHolder1$db_$destart",  startDate);
		params.put("ctl00_ContentPlaceHolder1_db__destart_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__destart_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$destart$DDD$C",  ""+ startDate1 +":"+ startDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__deend_Raw",  "1674864000000");
		params.put("ctl00$ContentPlaceHolder1$db_$deend",  endDate);
		params.put("ctl00_ContentPlaceHolder1_db__deend_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__deend_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$deend$DDD$C",  ""+ endDate1 +":"+ endDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_VI",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbxskh",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbxskh$DDD$L",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_VI",  "%%");
		params.put("ctl00$ContentPlaceHolder1$db_$cbmc",  "全部");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbmc$DDD$L",  "%%");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_VI",  "excel");
		params.put("ctl00$ContentPlaceHolder1$db_$ASPxComboBox1",  "excel");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$ASPxComboBox1$DDD$L",  "excel");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$DXSelInput",  "");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$DXFocusedRowInput",  "-1");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$CallbackState",  CallbackState);
		params.put("ctl00$ContentPlaceHolder1$cbpanel$ASPxTextBox1",  "");
		params.put("ctl00$ContentPlaceHolder1$txtValue",  "0");
		params.put("ctl00$ContentPlaceHolder1$txtValue2",  "0");
		params.put("DXScript",  "1_32,1_61,1_46,2_22,2_28,2_15,2_29,2_21,1_54,1_51,2_24,1_36,2_16,1_31,1_39,1_52,3_7");
		params.put("__CALLBACKID",  "ctl00$ContentPlaceHolder1$cbpanel");
		params.put("__CALLBACKPARAM",  "c0:-1");
		params.put("__EVENTVALIDATION",  EVENTVALIDATION);
		String url = "http://ucss.cq-p.com.cn:6107/xslx.aspx";
		
		headers.clear();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Cookie",
				"SF_cookie_31=" + SF_cookie_31 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ASP.NET_SessionId=3nh34z21fy0t5sxwckyt1lfr; sljx_pzwh_zjfgs=0; " + loginOAMRequestContext1 + "; " );
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, url);
		
		CallbackState = StringUtil.getValue(str, "<input type=\"hidden\" name=\"ctl00$ContentPlaceHolder1$cbpanel$gv_zb$CallbackState\" id=\"ctl00_ContentPlaceHolder1_cbpanel_gv_zb_CallbackState\" value=\"", "\" />", 1);
		params.clear();
		params.put("__EVENTTARGET",  "ctl00$ContentPlaceHolder1$db_$ASPxComboBox1");
		params.put("__EVENTARGUMENT",  "BC:0");
		params.put("__VIEWSTATE",  VIEWSTATE);
		params.put("__VIEWSTATEGENERATOR",  "0CA43D93");
		params.put("__EVENTVALIDATION",  EVENTVALIDATION);
		params.put("ctl00_ASPxNavBar1GS",  "1;0;0;1;1");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_VI",  code);
		params.put("ctl00$ContentPlaceHolder1$db_$cbfgs",  getMapCodeByName(paramString));
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDDWS",  "0:0:12000:300:148:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbfgs_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbfgs$DDD$L",  code);
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_VI",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbgg",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbgg_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbgg$DDD$L",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_VI",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbcp",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbcp_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbcp$DDD$L",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_VI",  "%");
		params.put("ctl00$ContentPlaceHolder1$db_$cbnbgs",  "全部");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbnbgs_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbnbgs$DDD$L",  "%");
		params.put("ctl00_ContentPlaceHolder1_db__destart_kp_Raw",  "1672531200000");
		params.put("ctl00$ContentPlaceHolder1$db_$destart_kp",  startDate);
		params.put("ctl00_ContentPlaceHolder1_db__destart_kp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__destart_kp_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$destart_kp$DDD$C",  ""+ startDate1 +":"+ startDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__deend_kp_Raw",  "1674864000000");
		params.put("ctl00$ContentPlaceHolder1$db_$deend_kp",  endDate);
		params.put("ctl00_ContentPlaceHolder1_db__deend_kp_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__deend_kp_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$deend_kp$DDD$C",  ""+ endDate1 +":"+ endDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__destart_Raw",  "1672531200000");
		params.put("ctl00$ContentPlaceHolder1$db_$destart",  startDate);
		params.put("ctl00_ContentPlaceHolder1_db__destart_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__destart_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$destart$DDD$C",  ""+ startDate1 +":"+ startDate1+ "");
		params.put("ctl00_ContentPlaceHolder1_db__deend_Raw",  "1674864000000");
		params.put("ctl00$ContentPlaceHolder1$db_$deend",  endDate);
		params.put("ctl00_ContentPlaceHolder1_db__deend_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__deend_DDD_C_FNPWS",  "0:0:-1:-10000:-10000:0:0px:-10000:1");
		params.put("ctl00$ContentPlaceHolder1$db_$deend$DDD$C",  ""+ endDate1 +":"+ endDate1 +"");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_VI",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbxskh",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbxskh_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbxskh$DDD$L",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_VI",  "%%");
		params.put("ctl00$ContentPlaceHolder1$db_$cbmc",  "全部");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDDWS",  "0:0:-1:-10000:-10000:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__cbmc_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$cbmc$DDD$L",  "%%");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_VI",  "CVS");
		params.put("ctl00$ContentPlaceHolder1$db_$ASPxComboBox1",  "CVS");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDDWS",  "0:0:12000:838:254:0:-10000:-10000:1");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LDeletedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LInsertedItems",  "");
		params.put("ctl00_ContentPlaceHolder1_db__ASPxComboBox1_DDD_LCustomCallback",  "");
		params.put("ctl00$ContentPlaceHolder1$db_$ASPxComboBox1$DDD$L",  "CVS");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$DXSelInput",  "");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$DXFocusedRowInput",  "-1");
		params.put("ctl00$ContentPlaceHolder1$cbpanel$gv_zb$CallbackState",  CallbackState);
		params.put("ctl00$ContentPlaceHolder1$cbpanel$ASPxTextBox1",  "SELECT StockName,OutStcType_cn, spbm, OwnerID, hsdw, SCRQ,TradeMark, LotNo, SellPrice, SellMoney, CreatDate,OutStcDate,CONVERT(varchar(100),OutStcDate, 23) OutStcDate_New, GoodsID, CustID, CustName, Model, Name, Number, IsFiliale, xsdjh,remarks remarks,bz,expiredate,zdshdz,ypssxkcyr,wtscqymc,remarks1 FROM View_xslx where UserID = 'mucy' and OutStcDate >= '" + startDate + " 00:00:00' and OutStcDate <= '"+ endDate +" 23:59:59' and CreatDate >= '" + startDate + " 00:00:00' and CreatDate <= '"+ endDate +" 23:59:59' and OwnerID = '" + code + "' and OutStcType not in ('17','07','1b')  order by OutStcDate");
		params.put("ctl00$ContentPlaceHolder1$txtValue",  "0");
		params.put("ctl00$ContentPlaceHolder1$txtValue2",  "0");
		params.put("DXScript",  "1_32,1_61,1_46,2_22,2_28,2_15,2_29,2_21,1_54,1_51,2_24,1_36,2_16,1_31,1_39,1_52,3_7");
		url = "http://ucss.cq-p.com.cn:6107/xslx.aspx";
		
		headers.clear();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Cookie",
				"SF_cookie_31=" + SF_cookie_31 + "; " + OAMAuthnCookie + "; sjlx_yhm=mucy; sjlx_fgsbm=94; ASP.NET_SessionId=3nh34z21fy0t5sxwckyt1lfr; sljx_pzwh_zjfgs=0; " + loginOAMRequestContext1 + "; " );
		WebUtil.repeatPostByHeader1(paramHttpClient, params, headers, url, salePath);
		
		List<String> data = createSale();

		try {
			// 创建CSV读对象
			CsvReader csvReader = new CsvReader(salePath,',',Charset.forName("GBK"));
			// 读表头
			csvReader.readHeaders();
			
			//出库日期	品名	规格	厂牌	单位	批号	生产日期	失效日期	出库数	销售类型	开票日期	销售单据号	商品代码	客户代码	客户名称	分子公司	仓库	备注	包装	指定送货地址	上市许可持有人	委托生产企业
			String[] title = new String[] { "出库时间","生产厂家","客户代码","客户名称","产品代码","产品名称","产品规格","剂型","单位","批号","数量","含税单价","含税金额","出货类型","客户城市","客户地址","经销商发货单号","备注" };
			String[] contents = new String[] { "出库时间","产品名称","产品规格","生产厂家","单位","批号","生产日期","失效日期","数量","出货类型","开票日期","经销商发货单号","产品代码","客户代码","客户名称","分子公司","仓库","备注","包装","客户地址","上市许可持有人","托生产企业" };                                
			int[] indexs = getContentsIndexs(title, contents);
			while (csvReader.readRecord()) {
				// 读一整行
				String strs[] = csvReader.getValues();
				if (strs == null || strs.length < 20 || StringUtil.isEmpty(strs[1])) {
					break;
				}
				
				if("奈妥匹坦帕洛诺司琼胶囊".equals(strs[1]) && "36735".equals(paramString)) {
					continue;
				}
				
				if("抗人T细胞兔免疫球蛋白".equals(strs[1]) && "54406".equals(paramString)) {
					continue;
				}
				
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						sb.append(strs[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data.toArray(new String[1]);
	}
	
//	public static void main(String[] args) {
//		HttpClient client = WebUtil.getDefaultHttpClient();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("distId", "5403");
//		params.put("callId", "5050");
//		params.put("uuid", "07DF621E-1313-484C-8BC3-C74488D72806");
//		try { // 22153 重庆医药集团药特分有限责任公司
//			(new ChongQingYiYaoAction()).exec(client, params, "36735", "重庆医药新特药品有限公司", new StringBuffer());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			client.getConnectionManager().shutdown();
//		}
//	}

}
