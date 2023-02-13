package com.oval.grabweb.logic.part202301.impl.copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.StringUtil;
import com.oval.util.WebUtil;

import sevenphase.verifycode.VerifyAbstractAction;

/**
 * 
 * @author youzi 佛山市医药有限公司
 */
public class Code22436VerifyAction extends VerifyAbstractAction {
	public static final String ROOTPATH = Constant.DIR_AUTO + "22436";

	public Code22436VerifyAction() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}

	String cookie = "";

	@Override
	protected String getLoginUrl() {
		try {
			WebUtil.repeatGet(client, "http://www.fsyygs.com:8066/default.aspx");
			HttpResponse response = WebUtil.repeatGet1(client, "http://www.fsyygs.com:8066/default.aspx", new HashMap<String, String>(), "");
			Header cookieHeader = response.getHeaders("Set-Cookie")[0];
			String value = cookieHeader.getValue();
			cookie = value.split(";")[0].trim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "http://www.fsyygs.com:8066/default.aspx";
	}

	@Override
	public void addVerifyCodeParam(String verifyCode) {
		loginParams.put("verify", verifyCode);

	}

	@Override
	protected String getPictureUrl() {
		return "http://www.fsyygs.com:8066/ValidateCode.aspx?id=" + Math.random();
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
		FileUtils.deleteQuietly(new File(GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg"));
		WebUtil.requestFile(client, getPictureUrl(), GlobalInfo.verifyStorePath + "/" + orgCode + ".jpg");
	}

	protected void login() throws Exception {
		loginParams.put("ScriptManager1", "ScriptManager1|imlogin");
		loginParams.put("__EVENTTARGET", "");
		loginParams.put("__EVENTARGUMENT", "");
		loginParams.put("__VIEWSTATE",
				"/wEPDwUKMTU1NDIyMjU5Ng9kFgICAw9kFgICCw8PZBYCHgdvbmNsaWNrBR5yZXR1cm4gQ2hlY2tmb3JtKCdsb2dpbmZvcm0nKTtkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQdpbWxvZ2luC4mlaq5YNGFPXapgOW1Da04aIiU=");
		loginParams.put("__VIEWSTATEGENERATOR", "CA0B0334");
		loginParams.put("__EVENTVALIDATION",
				"/wEWBQLhyMipDwKvpuq2CALyveCRDwKKp4O/BAKO/pyoB2iivOCnF9MIj93Py0mI9D4vbRcc");
		loginParams.put("imlogin.x", "22");
		loginParams.put("imlogin.y", "13");
		WebUtil.repeatPost1(client, loginParams, "http://www.fsyygs.com:8066/default.aspx");
		WebUtil.repeatGet(client, "http://www.fsyygs.com:8066/MainFrameSet.aspx");
		WebUtil.repeatGet(client, "http://www.fsyygs.com:8066/Frm_Top.aspx");
		WebUtil.repeatGet(client, "http://www.fsyygs.com:8066/Left.aspx");
		WebUtil.repeatGet(client, "http://www.fsyygs.com:8066/Frm_Middlewnd.aspx");
		WebUtil.repeatGet(client, "http://www.fsyygs.com:8066/Frm_Down.aspx");
	}

	@Override
	protected void login(HttpClient paramHttpClient, Map<String, String> paramMap) throws Exception {
		WebUtil.repeatGet(paramHttpClient, "http://www.fsyygs.com:8066/default.aspx");
		HttpResponse response = WebUtil.repeatGet1(paramHttpClient, "http://www.fsyygs.com:8066/default.aspx",
				new HashMap<String, String>(), "");

		Header cookieHeader = response.getHeaders("Set-Cookie")[0];
		String value = cookieHeader.getValue();
		cookie = value.split(";")[0].trim();

		WebUtil.requestFile(paramHttpClient, "http://www.fsyygs.com:8066/ValidateCode.aspx?id=" + Math.random(),
				"d:/W159028.jpg");
		File file = new File("d:/verifyCode.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String verifyCode = br.readLine();

		Map<String, String> params = new HashMap<String, String>();
		params.put("ScriptManager1", "ScriptManager1|imlogin");
		params.put("__EVENTTARGET", "");
		params.put("__EVENTARGUMENT", "");
		params.put("__VIEWSTATE",
				"/wEPDwUKMTU1NDIyMjU5Ng9kFgICAw9kFgICCw8PZBYCHgdvbmNsaWNrBR5yZXR1cm4gQ2hlY2tmb3JtKCdsb2dpbmZvcm0nKTtkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQdpbWxvZ2luC4mlaq5YNGFPXapgOW1Da04aIiU=");
		params.put("__VIEWSTATEGENERATOR", "CA0B0334");
		params.put("__EVENTVALIDATION", "/wEWBQLhyMipDwKvpuq2CALyveCRDwKKp4O/BAKO/pyoB2iivOCnF9MIj93Py0mI9D4vbRcc");
		params.put("username", "7gy");
		params.put("password", "llgry202207");
		params.put("verify", verifyCode);
		params.put("imlogin.x", "22");
		params.put("imlogin.y", "13");
		response = WebUtil.repeatPost1(paramHttpClient, params, "http://www.fsyygs.com:8066/default.aspx");

		String str = WebUtil.repeatGet(paramHttpClient, "http://www.fsyygs.com:8066/MainFrameSet.aspx");
		str = WebUtil.repeatGet(paramHttpClient, "http://www.fsyygs.com:8066/Frm_Top.aspx");
		str = WebUtil.repeatGet(paramHttpClient, "http://www.fsyygs.com:8066/Left.aspx");
		str = WebUtil.repeatGet(paramHttpClient, "http://www.fsyygs.com:8066/Frm_Middlewnd.aspx");
		str = WebUtil.repeatGet(paramHttpClient, "http://www.fsyygs.com:8066/Frm_Down.aspx");

	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String invPath = String.format("%s\\%s", ROOTPATH, "temp1.csv");
		String now = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		String str = requesContent(paramHttpClient, "http://www.fsyygs.com:8066/KC/Default.aspx", 0);
		str = StringUtil.getValue(str, "location.href='", "'", 1);
		String url = "http://www.fsyygs.com:8066" + str;
		str = WebUtil.repeatGetMN(paramHttpClient, url, invPath);

		List<String> data = createStock();

		FileInputStream fileInputstream = new FileInputStream(invPath);
		HSSFWorkbook xss = new HSSFWorkbook(fileInputstream);
		try {
			HSSFSheet sheet = xss.getSheetAt(0);
			// 库存时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 生产日期 失效日期 数量 库存状态 入库时间
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow xr = sheet.getRow(i);
				StringBuilder sb = new StringBuilder();
				sb.append(now).append(column_speractor);// 当前时间
				sb.append(xr.getCell(4).toString()).append(column_speractor);// 生产厂家
				sb.append(column_speractor);// 供应商代码
				sb.append(column_speractor);// 供应商名称
				sb.append(xr.getCell(1).toString()).append(column_speractor);// 商品代码
				sb.append(xr.getCell(2).toString()).append(column_speractor);// 商品名称
				sb.append(xr.getCell(3).toString()).append(column_speractor);// 规格
				sb.append(column_speractor);// 剂型
				sb.append(xr.getCell(9).toString()).append(column_speractor);// 单位
				sb.append(xr.getCell(5).toString()).append(column_speractor);// 批号
				sb.append(column_speractor);// 生产日期
				sb.append(xr.getCell(6).toString()).append(column_speractor);// 失效日期
				sb.append(xr.getCell(8).toString()).append(column_speractor);// 数量
				sb.append(column_speractor);// 库存状态
				sb.append(column_speractor);// 入库时间
				data.add(sb.toString());
			}
		} finally {
			if (xss != null) {
				xss.close();
			}
			if (fileInputstream != null) {
				fileInputstream.close();
			}
			if (new File(invPath).exists()) {
				new File(invPath).delete();
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
		String str = requesContent(paramHttpClient, "http://www.fsyygs.com:8066/GJ/Default.aspx", 1);
		str = StringUtil.getValue(str, "location.href='", "'", 1);
		String url = "http://www.fsyygs.com:8066" + str;
		str = WebUtil.repeatGetMN(paramHttpClient, url, purchasPath);

		List<String> data = createPurchas();

		FileInputStream fileInputstream = new FileInputStream(purchasPath);
		HSSFWorkbook xss = new HSSFWorkbook(fileInputstream);
		try {
			HSSFSheet sheet = xss.getSheetAt(0);
			// 入库时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 进货类型 供应商出库单号
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow xr = sheet.getRow(i);
				StringBuilder sb = new StringBuilder();
				sb.append(xr.getCell(3).toString()).append(column_speractor);// 购进日期
				sb.append(xr.getCell(9).toString()).append(column_speractor);// 生产厂家
				sb.append(xr.getCell(5).toString()).append(column_speractor);// 供商代码
				sb.append(xr.getCell(4).toString()).append(column_speractor);// 供商名称
				sb.append(xr.getCell(6).toString()).append(column_speractor);// 商品代码
				sb.append(xr.getCell(7).toString()).append(column_speractor);// 商品名称
				sb.append(xr.getCell(8).toString()).append(column_speractor);// 规格
				sb.append(column_speractor);// 剂型
				sb.append(xr.getCell(14).toString()).append(column_speractor);// 单位
				sb.append(xr.getCell(10).toString()).append(column_speractor);// 批号
				sb.append(xr.getCell(13).toString()).append(column_speractor);// 数量
				sb.append(column_speractor);// 含税单价
				sb.append(column_speractor);// 含税金额
				sb.append(column_speractor);// 进货类型
				sb.append(xr.getCell(2).toString()).append(column_speractor);// 单号
				data.add(sb.toString());
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
		String str = requesContent(paramHttpClient, "http://www.fsyygs.com:8066/XS/Default.aspx", 1);
		str = StringUtil.getValue(str, "location.href='", "'", 1);
		String url = "http://www.fsyygs.com:8066" + str;
		str = WebUtil.repeatGetMN(paramHttpClient, url, salePath);

		List<String> data = createSale();

		FileInputStream fileInputstream = new FileInputStream(salePath);
		HSSFWorkbook xss = new HSSFWorkbook(fileInputstream);
		try {
			HSSFSheet sheet = xss.getSheetAt(0);
			// 出库时间 生产厂家 客户代码 客户名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 出货类型 客户城市 客户地址
			// 经销商发货单号 备注
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow xr = sheet.getRow(i);
				StringBuilder sb = new StringBuilder();
				sb.append(xr.getCell(3).toString()).append(column_speractor);// 销售日期
				sb.append(xr.getCell(9).toString()).append(column_speractor);// 生产厂家
				sb.append(xr.getCell(4).toString()).append(column_speractor);// 客户代码
				sb.append(xr.getCell(5).toString()).append(column_speractor);// 客户名称
				sb.append(xr.getCell(6).toString()).append(column_speractor);// 商品代码
				sb.append(xr.getCell(7).toString()).append(column_speractor);// 商品名称
				sb.append(xr.getCell(8).toString()).append(column_speractor);// 规格
				sb.append(column_speractor);// 剂型
				sb.append(xr.getCell(14).toString()).append(column_speractor);// 单位
				sb.append(xr.getCell(10).toString()).append(column_speractor);// 批号
				sb.append(xr.getCell(13).toString()).append(column_speractor);// 数量
				sb.append(column_speractor);// 含税单价
				sb.append(column_speractor);// 含税金额
				sb.append(column_speractor);// 出货类型
				sb.append(column_speractor);// 客户城市
				sb.append(column_speractor);// 客户地址
				sb.append(xr.getCell(2).toString()).append(column_speractor);// 单号
				sb.append(column_speractor);// 备注
				data.add(sb.toString());
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

	protected String requesContent(HttpClient paramHttpClient, String url, int type) throws Exception {
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");

		String str = WebUtil.repeatGet(paramHttpClient, url);
		// System.out.print(str);
		String EVENTTARGET = StringUtil.getValue(str,
				"<input type=\"hidden\" name=\"__EVENTTARGET\" id=\"__EVENTTARGET\" value=\"", "\" /", 1);
		String EVENTARGUMENT = StringUtil.getValue(str,
				"<input type=\"hidden\" name=\"__EVENTARGUMENT\" id=\"__EVENTARGUMENT\" value=\"", "\" /", 1);
		String VIEWSTATE = StringUtil.getValue(str,
				"<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 1);
		String VIEWSTATEGENERATOR = StringUtil.getValue(str,
				"<input type=\"hidden\" name=\"__VIEWSTATEGENERATOR\" id=\"__VIEWSTATEGENERATOR\" value=\"", "\" /", 1);
		String EVENTVALIDATION = StringUtil.getValue(str,
				"<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" /", 1);
		Map<String, String> params = new HashMap<String, String>();
		params.put("ScriptManager1", "ScriptManager1|btnOutExcel");
		params.put("__EVENTTARGET", EVENTTARGET);
		params.put("__EVENTARGUMENT", EVENTARGUMENT);
		params.put("__VIEWSTATE", VIEWSTATE);
		params.put("__VIEWSTATEGENERATOR", VIEWSTATEGENERATOR);
		params.put("__EVENTVALIDATION", EVENTVALIDATION);
		if (type == 1) {
			params.put("txtsdate", startDate);
			params.put("txtedate", endDate);
		}
		params.put("txtKeyword", "");
		params.put("hf_uid", "7GY                 ");
		params.put("DDL_Pagesize", "500");
		params.put("btnOutExcel", "导出EXCEL");
		str = WebUtil.repeatPost(paramHttpClient, params, url);
		return str;
	}

}
