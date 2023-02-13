package com.oval.grabweb.logic.part202301.impl.copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.StringUtil;
import com.oval.util.WebUtil;

import sevenphase.verifycode.VerifyAbstractAction;

/**
 * 
 * @author 江苏三江医药有限公司
 */
public class Code22388VerifyAction extends VerifyAbstractAction {
	
	private long basetime = (long)(1970*365 + (25*20-8) - 19 + 5) * 24 * 60 * 60 * 10000;
	private long timestamp = basetime + (new Date()).getTime();
	
	private CookieStore store;
	private Map<String, String> headers;

	public Code22388VerifyAction() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}
	@Override
	protected String getLoginUrl() {
		return "http://218.90.236.138/Default.aspx";
	}

	@Override
	public void addVerifyCodeParam(String verifyCode) {
		loginParams.put("saveCodeTextBox", verifyCode);

	}

	@Override
	protected String getPictureUrl() {
		return "http://218.90.236.138/dataLib/saveCode.ashx?sn=" + timestamp*1000 + (new Random()).nextInt(1000);
	}

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

	public void getVerifyCode() throws Exception {
		client = WebUtil.getDefaultHttpClient();
		
		if (headers == null) {
			headers = new HashMap<String, String>();
			this.store = new BasicCookieStore();
		    CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(store).build();
		    //测试逻辑
		    HttpGet httpGet = new HttpGet("http://218.90.236.138/Default.aspx");
		    try {
				httpclient.execute(httpGet);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		    //读取cookie信息
		    List<org.apache.http.cookie.Cookie> cookielist = store.getCookies();
		    for(org.apache.http.cookie.Cookie cookie: cookielist){
		        String name=cookie.getName();
		        String value=cookie.getValue();
		        headers.put("Cookie", name + "=" +value);
		    }
		    headers.put("Referer","http://218.90.236.138/Default.aspx");
		    headers.put("Host","218.90.236.138");
			
		}
		FileUtils.deleteQuietly(new File(GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg"));
		WebUtil.repeatGetFile(client, getPictureUrl(), headers, GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg");
	}

	protected void login() throws Exception {
		loginParams.put("mainScriptManager", "mainUpdatePanel|loginButton");
		loginParams.put("__EVENTTARGET", "");
		loginParams.put("__EVENTARGUMENT", "");
		loginParams.put("__VIEWSTATE", "/wEPDwUJNzU4NTY4MzAzZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAgUTc2F2ZUNvZGVJbWFnZUJ1dHRvbgULbG9naW5CdXR0b27NbkR7PKaXPq18Q4dzpbQFPPgz6A==");
		loginParams.put("__EVENTVALIDATION", "/wEWBgKYp5FeAtDAn90OAvn6vrwEAtzYsvcHAt+zlosKAt6Mzp8PyDkFj1bKcWPNLf13StbDKlFgSYE=");
		loginParams.put("__ASYNCPOST", "true");
		loginParams.put("loginButton.x", "65");
		loginParams.put("loginButton.y", "24");
		
		WebUtil.repeatPostByHeader(client, loginParams, headers, "http://218.90.236.138/Default.aspx");
		WebUtil.repeatGetByHeader(client, headers, "http://218.90.236.138/WebGXLS.aspx");
	}

	@Override
	protected void login(HttpClient paramHttpClient, Map<String, String> paramMap) throws Exception {
		String str;
	 
	    // 获取cookies信息
	    this.store= new BasicCookieStore();
	    CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(store).build();
	    //测试逻辑
	    HttpGet httpget = new HttpGet("http://218.90.236.138/Default.aspx");
	    CloseableHttpResponse response = httpclient.execute(httpget);
	    //打印返回值
	    str = EntityUtils.toString(response.getEntity());
	 
	    //读取cookie信息
	    List<org.apache.http.cookie.Cookie> cookielist = store.getCookies();
	    for(org.apache.http.cookie.Cookie cookie: cookielist){
	        String name=cookie.getName();
	        String value=cookie.getValue();
	        headers.put("Cookie", name + "=" +value);
	    }
	    
	    headers.put("Referer","http://218.90.236.138/Default.aspx");
	    headers.put("Host","218.90.236.138");
	    
		WebUtil.FilePath = "d:/W159028.jpg";
		WebUtil.repeatGet(paramHttpClient, "http://218.90.236.138/dataLib/saveCode.ashx?sn=" + timestamp*1000 + (new Random()).nextInt(1000), headers, "MN");
		File file = new File("d:/verifyCode.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String verifyCode = br.readLine();

		String VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		String EVENTVALIDATION = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("mainScriptManager", "mainUpdatePanel|loginButton");
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__EVENTVALIDATION", EVENTVALIDATION);
		params.put("userNameTextBox", "bjmd");
		params.put("userPassTextBox", "111111");
		params.put("saveCodeTextBox", verifyCode);
		params.put("__ASYNCPOST", "true");
		params.put("loginButton.x", "65");
		params.put("loginButton.y", "24");
		
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/Default.aspx");
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/WebGXLS.aspx");

	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String dateStr = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/DBKCList.aspx");
		String VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		String EVENTVALIDATION = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
		
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers,"http://218.90.236.138/viewDBKCList.aspx");
		String VIEWSTATE_1 = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		String EVENTVALIDATION_1 = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
		Map<String, String> params = new HashMap<String, String>();

		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|printExcelImageButton");
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__EVENTVALIDATION", EVENTVALIDATION);
		params.put("__ASYNCPOST", "true");
		params.put("printExcelImageButton.x", "27");
		params.put("printExcelImageButton.y", "11");
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/DBKCList.aspx");

		params.clear();
		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|exportLinkButton");
		params.put("__EVENTTARGET", "exportLinkButton");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE_1);
		params.put("__EVENTVALIDATION", EVENTVALIDATION_1);
		params.put("__ASYNCPOST", "true"); 
		params.put("", ""); 
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/viewDBKCList.aspx");
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/exportFile.aspx?exportType=DBKCList");
		
		List<String> data = createStock();
		str = str.substring(str.indexOf("<table"), str.indexOf("</table>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "库存时间","生产厂家","供应商代码","供应商名称","产品代码","产品名称","产品规格","剂型","单位","批号","生产日期","失效日期","数量","库存状态","入库时间" };
		String[] contents = new String[] { "产品代码","商品名称","产品名称","产品规格","单位","批号","数量","失效日期"};
		int[] indexs = getContentsIndexs(title, contents);

		for (int i = 2; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < indexs.length; j++) {
				if (indexs[j] == -1) {
					if (j == 0) {
						sb.append(dateStr).append(column_speractor);
					} else {
						sb.append("").append(column_speractor);
					}
				} else {
					sb.append(tds[indexs[j]]).append(column_speractor);
				}
			}
			data.add(sb.toString());
		}
		
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/WebGXLS.aspx");
		String VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		String EVENTVALIDATION = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
			
		Map<String, String> params = new HashMap<String, String>();
		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|searchButton");
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__EVENTVALIDATION", EVENTVALIDATION);
		params.put("searchBHTextBox", "");
		params.put("searchDateStartTextBox", startdate);
		params.put("searchDateEndTextBox", enddate);
		params.put("__ASYNCPOST", "true");
		params.put("searchButton.y", "6");
		params.put("searchButton.x", "31");
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/WebGXLS.aspx");
		
		VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		EVENTVALIDATION = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
		params.clear();
		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|printExcelImageButton");
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__EVENTVALIDATION", EVENTVALIDATION);
		params.put("searchBHTextBox", "");
		params.put("searchDateStartTextBox", startdate);
		params.put("searchDateEndTextBox", enddate);
		params.put("__ASYNCPOST", "true");
		params.put("printExcelImageButton.x", "30");
		params.put("printExcelImageButton.y", "8");
		params.put("__AjaxControlToolkitCalendarCssLoaded", "");
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/WebGXLS.aspx");
		
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/viewWebGXLS.aspx");
		VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		params.clear();
		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|exportLinkButton");
		params.put("__EVENTTARGET", "exportLinkButton");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__ASYNCPOST", "true");
		params.put("", "");
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/viewWebGXLS.aspx");
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/exportFile.aspx?exportType=GXLSList");
		List<String> data = createPurchas();

		str = str.substring(str.indexOf("<table"), str.indexOf("</table>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "入库时间","生产厂家","供应商代码","供应商名称","产品代码","产品名称","产品规格","剂型","单位","批号","数量","含税单价","含税金额","进货类型","供应商出库单号" };
		String[] contents = new String[] { "入库时间","供应商出库单号","产品代码","商品名称","产品名称","产品规格","单位","生产厂家","批号","供应商名称","数量","含税单价","含税金额","进货类型","供应商代码","库存" };
		int[] indexs = getContentsIndexs(title, contents);
		for (int i = 2; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if(tds != null && tds[13].equals("购进")) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						String s = tds[indexs[j]].indexOf("&nbsp;") < 0 ? tds[indexs[j]] : "";
						sb.append(s).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
		}
		return data.toArray(new String[1]);

	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/WebGXLS.aspx");
		String VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		String EVENTVALIDATION = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
			
		Map<String, String> params = new HashMap<String, String>();
		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|searchButton");
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__EVENTVALIDATION", EVENTVALIDATION);
		params.put("searchBHTextBox", "");
		params.put("searchDateStartTextBox", startdate);
		params.put("searchDateEndTextBox", enddate);
		params.put("__ASYNCPOST", "true");
		params.put("searchButton.y", "6");
		params.put("searchButton.x", "31");
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/WebGXLS.aspx");

		VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		EVENTVALIDATION = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
		params.clear();
		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|printExcelImageButton");
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__EVENTVALIDATION", EVENTVALIDATION);
		params.put("searchBHTextBox", "");
		params.put("searchDateStartTextBox", startdate);
		params.put("searchDateEndTextBox", enddate);
		params.put("__ASYNCPOST", "true");
		params.put("printExcelImageButton.x", "30");
		params.put("printExcelImageButton.y", "8");
		params.put("__AjaxControlToolkitCalendarCssLoaded", "");
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/WebGXLS.aspx");
		
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/viewWebGXLS.aspx");
		VIEWSTATE = StringUtil.getValue(str, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		params.clear();
		params.put("WebHeadeParts$mainScriptManager", "mainUpdatePanel|exportLinkButton");
		params.put("__EVENTTARGET", "exportLinkButton");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__ASYNCPOST", "true");
		params.put("", "");
		str = WebUtil.repeatPostByHeader(paramHttpClient, params, headers, "http://218.90.236.138/viewWebGXLS.aspx");
		str = WebUtil.repeatGetByHeader(paramHttpClient, headers, "http://218.90.236.138/exportFile.aspx?exportType=GXLSList");
		
		str = str.substring(str.indexOf("<table"), str.indexOf("</table>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "出库时间","生产厂家","客户代码","客户名称","产品代码","产品名称","产品规格","剂型","单位","批号","数量","含税单价","含税金额","出货类型","客户城市","客户地址","经销商发货单号","备注" };
		String[] contents = new String[] { "出库时间","经销商发货单号","产品代码","商品名称","产品名称","产品规格","单位","生产厂家","批号","客户名称","数量","含税单价","含税金额","出货类型","客户代码","库存" };
		int[] indexs = getContentsIndexs(title, contents);
		List<String> data = createSale();
		for (int i = 2; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if(tds != null && tds[13].equals("销售")) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						String s = tds[indexs[j]].indexOf("&nbsp;") < 0 ? tds[indexs[j]] : "";
						sb.append(s).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
		}
		return data.toArray(new String[1]);

	}

	
	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
//		params.put("distId", "2416");
//		params.put("callId", "5051");
//		params.put("uuid", "7AD013F8-B402-4160-A272-38A3E7FD7483");
		try { // 22120 山东康诺盛世医药有限公司
			(new Code22388VerifyAction()).exec(client, params, "22388", "江苏三江医药有限公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
}