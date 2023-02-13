package com.oval.grabweb.logic.part202301.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.grabweb.logic.part201905.impl.SSLClient;
import com.oval.util.DateUtil;
import com.oval.util.WebUtil;

/**
 * 山东康诺盛世医药有限公司
 * 
 * @author
 *
 */
public class Code22120Action extends AbstractAction {
	
	public static final String ROOTPATH = Constant.DIR_AUTO + "22120";
	public static final List<String> PRODUCES = Arrays.asList("盐酸羟考酮注射液", "盐酸羟考酮胶囊", "盐酸羟考酮缓释片", "硫酸吗啡缓释片", "丁丙诺啡透皮贴剂","盐酸曲马多缓释片", "盐酸曲马多胶囊", "盐酸曲马多注射液");
	private String JSESSIONID = "";
	private HttpClient httpClient = null;
	
	public Code22120Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
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

	@Override
	protected void login(HttpClient paramHttpClient, Map<String, String> paramMap) throws Exception {
		httpClient = new SSLClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("t", "0");
		HttpResponse resp = WebUtil.repeatPost1(httpClient, params, "https://lx.sdknss.com.cn/pub/login/login.do");
		JSESSIONID = resp.getAllHeaders()[8].getElements()[0].getValue();
		HttpEntity entity = resp.getEntity();
		InputStream content = entity.getContent();
		InputStreamReader isr = new InputStreamReader(content);
		char[] cha = new char[1024];
		int len = isr.read(cha);
		String str = new String(cha, 0, len);
		isr.close();
		String data = StringUtils.substringBetween(str, "\"data\":\"", "\",");
		String s = testJS(data);
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("str", s);
		params2.put("os", "other");
		params2.put("bi", "chrome109.0.0.0");
		params2.put("cip", "127.0.0.1");
		params2.put("lang", "zh");
		str = WebUtil.repeatPost(httpClient, params2, "https://lx.sdknss.com.cn/pub/login/login.do");

		str = WebUtil.repeatGet(httpClient, "https://lx.sdknss.com.cn/pub/login/index.html");
		Map<String, String> header = new HashMap<String, String>();
		header.put("Cookie", "JSESSIONID=" + JSESSIONID + "; iu=https://www.sdknss.com.cn:3003; lang=zh; _rf=1");

		str = WebUtil.repeatPostByHeader(httpClient, params, header, "https://lx.sdknss.com.cn/pub/login/init.do");
	}

	public String testJS(String data) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		String url = Code22120Action.class .getResource("/").getPath();
		String jsFileName;
		if (url.indexOf("WEB-INF") > 0) {
			jsFileName = url.substring(0 ,url.indexOf("WEB-INF")) + "js/crypto.js";// 读取js文件
		} else {
			jsFileName = url.substring(0 ,url.indexOf("build")) + "WebContent/js/crypto.js";// 读取js文件
		}
		FileReader reader = new FileReader(jsFileName); // 执行指定脚本
		engine.eval(reader);
		String c = "";
		if (engine instanceof Invocable) {
			Invocable invoke = (Invocable) engine; // 调用merge方法，并传入两个参数

			c = (String) invoke.invokeFunction("cryptoLoginFormInvoke", data, "10647", "65636800md");
		}
		reader.close();
		return c;
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String stockPath = String.format("%s\\%s", ROOTPATH, "temp1.csv");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}

		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> header = new HashMap<String, String>();
		header.put("Cookie", "JSESSIONID=" + JSESSIONID + "; iu=https://www.sdknss.com.cn:3003; lang=zh; _rf=1");

		WebUtil.repeatPostByHeader(httpClient, params, header, "https://lx.sdknss.com.cn/flow/func/st/index.do");
		params.put("funcid", "3001");
		params.put("filterid", "1");
		params.put("action", "get");
		WebUtil.repeatPostByHeader(httpClient, params, header, "https://lx.sdknss.com.cn/flow/func/st/getFilter.do");
		params.clear();
		params.put("comname", "grid1");
		params.put("uiconf",
				"{\"fields\":\"goodsid,goodsname,goodsqty,goodstype,invaliddate,lotno,prodarea\",\"sortRules\":\"goodsid asc\",\"summary\":{\"goodsqty\":\"sum\"},\"stats\":{\"goodsqty\":1}}");
		params.put("where",
				"{\"rules\":[{\"field\":\"prodarea\",\"op\":\"like\",\"value\":\"萌蒂\",\"type\":\"string\"}],\"op\":\"and\"}");
		params.put("childwhere", "");
		params.put("type", "");
		params.put("ids", "");
		params.put("statswhere", "");
		params.put("cv", "1");
		params.put("extra", "");
		params.put("sortRules", "goodsid asc");
		WebUtil.repeatPostMN(httpClient, params, "https://lx.sdknss.com.cn/flow/func/st/export.do", stockPath);
		List<String> data = createStock();
		String now = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd");
		FileInputStream fileInputstream = new FileInputStream(stockPath);
		XSSFWorkbook xss = new XSSFWorkbook(fileInputstream);
		try {
			
			XSSFSheet sheet = xss.getSheetAt(0);
			if (sheet.getPhysicalNumberOfRows() > 1) {
				// 库存时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 生产日期 失效日期 数量 库存状态 入库时间
				for (int i = 1; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
					XSSFRow xr = sheet.getRow(i);
					for (String strs : PRODUCES) {
						if (!xr.getCell(1).toString().isEmpty() && xr.getCell(1).toString().indexOf(strs) > -1) {
							StringBuilder sb = new StringBuilder();
							sb.append(now).append(column_speractor);// 库存时间
							sb.append(xr.getCell(6).toString()).append(column_speractor);// 生产厂家
							sb.append(column_speractor);// 供商代码
							sb.append(column_speractor);// 供商名称
							sb.append(xr.getCell(0).toString()).append(column_speractor);// 产品代码
							sb.append(xr.getCell(1).toString()).append(column_speractor);// 产品名称
							sb.append(xr.getCell(3).toString()).append(column_speractor);// 规格
							sb.append(column_speractor);// 剂型
							sb.append(column_speractor);// 单位
							sb.append(xr.getCell(5).toString()).append(column_speractor);// 批号
							sb.append(column_speractor);// 生产日期
							sb.append(xr.getCell(4).toString()).append(column_speractor);// 失效日期
							sb.append(xr.getCell(2).toString()).append(column_speractor);// 数量
							sb.append(column_speractor);// 库存状态
							sb.append(column_speractor);// 入库时间
							data.add(sb.toString());
						}
					}

				}
			}
		} finally {
			if (xss != null) {
				xss.close();
			}
			if (fileInputstream != null) {
				fileInputstream.close();
			}
			if (new File(stockPath).exists()) {
				new File(stockPath).delete();
			}
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		String purchasPath = String.format("%s\\%s", ROOTPATH, "temp2.csv");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}

		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> header = new HashMap<String, String>();
		header.put("Cookie", "JSESSIONID=" + JSESSIONID + "; iu=https://www.sdknss.com.cn:3003; lang=zh; _rf=1");

		WebUtil.repeatPostByHeader(httpClient, params, header, "https://lx.sdknss.com.cn/flow/func/su/index.do");
		params.put("funcid", "3002");
		params.put("filterid", "1");
		params.put("action", "get");
		WebUtil.repeatPostByHeader(httpClient, params, header, "https://lx.sdknss.com.cn/flow/func/su/getFilter.do");
		params.clear();

		Long startDate = DateUtil.getBeforeDayTime(60, "yyyy-MM-dd");
		Long endDate = DateUtil.getBeforeDayTime(1, "yyyy-MM-dd");

		params.put("comname", "grid1");
		params.put("uiconf","{\"fields\":\"sudate,goodsname,goodstype,goodsqty,invaliddate,lotno,prodarea\",\"sortRules\":\"sudate asc\",\"summary\":{\"goodsqty\":\"sum\"}}");
		params.put("where", "{\"rules\":[{\"field\":\"sudate\",\"op\":\"scope\",\"value\":\"" + startDate + ","
				+ endDate + "\",\"type\":\"date\"}],\"op\":\"and\"}");
		params.put("childwhere", "");
		params.put("type", "");
		params.put("ids", "");
		params.put("statswhere", "");
		params.put("cv", "1");
		params.put("extra", "");
		params.put("sortRules", "sudate asc");
		WebUtil.repeatPostMN(httpClient, params, "https://lx.sdknss.com.cn/flow/func/su/export.do", purchasPath);
		List<String> data = createPurchas();

		FileInputStream fileInputstream = new FileInputStream(purchasPath);
		XSSFWorkbook xss = new XSSFWorkbook(fileInputstream);
		try {
			XSSFSheet sheet = xss.getSheetAt(0);
			if (sheet.getPhysicalNumberOfRows() > 1) {
				// 入库时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 进货类型 供应商出库单号
				for (int i = 1; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
					XSSFRow xr = sheet.getRow(i);
					StringBuilder sb = new StringBuilder();
					String value = "";
					if (HSSFDateUtil.isCellDateFormatted(xr.getCell(0))) {
						// 如果是date类型则 ，获取该cell的date值
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						value = format.format(xr.getCell(0).getDateCellValue());
					}
					sb.append(value).append(column_speractor);// 购进日期
					sb.append(xr.getCell(6).toString()).append(column_speractor);// 生产厂家
					sb.append(column_speractor);// 供商代码
					sb.append(column_speractor);// 供商名称
					sb.append(column_speractor);// 商品代码
					sb.append(xr.getCell(1).toString()).append(column_speractor);// 商品名称
					sb.append(xr.getCell(2).toString()).append(column_speractor);// 规格
					sb.append(column_speractor);// 剂型
					sb.append(column_speractor);// 单位
					sb.append(xr.getCell(5).toString()).append(column_speractor);// 批号
					sb.append(xr.getCell(3).toString()).append(column_speractor);// 数量
					sb.append(column_speractor);// 含税单价
					sb.append(column_speractor);// 含税金额
					sb.append(column_speractor);// 进货类型
					sb.append(column_speractor);// 含税单价
					sb.append(column_speractor);// 单号
					data.add(sb.toString());
				}
			}
		} finally {
			if (xss != null) {
				xss.close();
			}
			if (fileInputstream != null) {
				fileInputstream.close();
			}
			if (new File(purchasPath).exists()) {
				new File(purchasPath).delete();
			}
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		String salePath = String.format("%s\\%s", ROOTPATH, "temp3.csv");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}

		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> header = new HashMap<String, String>();
		header.put("Cookie", "JSESSIONID=" + JSESSIONID + "; iu=https://www.sdknss.com.cn:3003; lang=zh; _rf=1");

		WebUtil.repeatPostByHeader(httpClient, params, header, "https://lx.sdknss.com.cn/flow/func/sa/index.do");
		params.put("funcid", "3003");
		params.put("filterid", "1");
		params.put("action", "get");
		WebUtil.repeatPostByHeader(httpClient, params, header, "https://lx.sdknss.com.cn/flow/func/sa/getFilter.do");
		params.clear();

		Long startDate = DateUtil.getBeforeDayTime(60, "yyyy-MM-dd");
		Long endDate = DateUtil.getBeforeDayTime(1, "yyyy-MM-dd");
		params.put("comname", "grid1");
		params.put("uiconf",
				"{\"fields\":\"sadate,customname,goodsname,goodsqty,goodstype,invaliddate,lotno,prodarea\",\"sortRules\":\"sadate asc\",\"summary\":{\"goodsqty\":\"sum\"}}");
		params.put("where", "{\"rules\":[{\"field\":\"sadate\",\"op\":\"scope\",\"value\":\"" + startDate + ","
				+ endDate + "\",\"type\":\"date\"}],\"op\":\"and\"}");
		params.put("childwhere", "");
		params.put("type", "");
		params.put("ids", "");
		params.put("statswhere", "");
		params.put("cv", "1");
		params.put("extra", "");
		params.put("sortRules", "sadate asc");
		WebUtil.repeatPostMN(httpClient, params, "https://lx.sdknss.com.cn/flow/func/sa/export.do", salePath);
		List<String> data = createSale();

		FileInputStream fileInputstream = new FileInputStream(salePath);
		XSSFWorkbook xss = new XSSFWorkbook(fileInputstream);
		try {
			XSSFSheet sheet = xss.getSheetAt(0);
			if (sheet.getPhysicalNumberOfRows() > 1) {
				// 出库时间 生产厂家 客户代码 客户名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 出货类型 客户城市 客户地址
				// 经销商发货单号 备注
				for (int i = 1; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
					XSSFRow xr = sheet.getRow(i);
					StringBuilder sb = new StringBuilder();
					String value = "";
					if (HSSFDateUtil.isCellDateFormatted(xr.getCell(0))) {
						// 如果是date类型则 ，获取该cell的date值
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						value = format.format(xr.getCell(0).getDateCellValue());
					}
					sb.append(value).append(column_speractor);// 出库时间
					sb.append(xr.getCell(7).toString()).append(column_speractor);// 生产厂家
					sb.append(column_speractor);// 客户代码
					sb.append(xr.getCell(1).toString()).append(column_speractor);// 客户名称
					sb.append(column_speractor);// 商品代码
					sb.append(xr.getCell(2).toString()).append(column_speractor);// 商品名称
					sb.append(xr.getCell(4).toString()).append(column_speractor);// 规格
					sb.append(column_speractor);// 剂型
					sb.append(column_speractor);// 单位
					sb.append(xr.getCell(6).toString()).append(column_speractor);// 批号
					sb.append(xr.getCell(3).toString()).append(column_speractor);// 数量
					sb.append(column_speractor);// 含税单价
					sb.append(column_speractor);// 含税金额
					sb.append(column_speractor);// 出货类型
					sb.append(column_speractor);// 客户城市
					sb.append(column_speractor);// 经销商发货单号
					sb.append(column_speractor);// 备注
					data.add(sb.toString());
				}
			}
		} finally {
			if (xss != null) {
				xss.close();
			}
			if (fileInputstream != null) {
				fileInputstream.close();
			}
			if (new File(salePath).exists()) {
				new File(salePath).delete();
			}
		}
		return data.toArray(new String[1]);
	}

//	public static void main(String[] args) {
//		HttpClient client = WebUtil.getDefaultHttpClient();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("distId", "2416");
//		params.put("callId", "5051");
//		params.put("uuid", "7AD013F8-B402-4160-A272-38A3E7FD7483");
//		try { // 22120 山东康诺盛世医药有限公司
//			(new Code22120Action()).exec(client, params, "22120", "山东康诺盛世医药有限公司", new StringBuffer());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			client.getConnectionManager().shutdown();
//		}
//	}

}
