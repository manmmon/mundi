package com.oval.grabweb.logic.part202302.impl;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.csvreader.CsvReader;
import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.WebUtil;

/**
 * 安徽省绿十字医药股份有限公司
 *
 */
public class Code22474Action extends AbstractAction {

	public static final String ROOTPATH = "D:\\SFTPDATAAuto\\auto\\22474";

	public Code22474Action() {
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
		String str = WebUtil.repeatGet(paramHttpClient, "http://antai.pukesoft.cn/index.php?m=&c=Index&a=login");
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "1002");
		params.put("password", "mdzg123");
		str = WebUtil.repeatPost(paramHttpClient, params, "http://antai.pukesoft.cn/index.php?m=&c=Index&a=login");
		str = WebUtil.repeatGet(paramHttpClient, "http://antai.pukesoft.cn/index.php?m=Supplier&c=Index&a=index");
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String stockPath = String.format("%s\\%s", ROOTPATH, "temp1.csv");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		String str = WebUtil.repeatGetMN(paramHttpClient,
				"http://antai.pukesoft.cn/index.php?m=Supplier" + "&c=Stock&a=index&excel=yes&good_id=请选择商品",
				stockPath);
		List<String> data = createStock();
		String now = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd");
		FileInputStream fileInputstream = new FileInputStream(stockPath);
		HSSFWorkbook xss = new HSSFWorkbook(fileInputstream);
		try {
			HSSFSheet sheet = xss.getSheetAt(0);
			if (sheet.getPhysicalNumberOfRows() > 1) {
				// 库存时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 生产日期 失效日期 数量 库存状态 入库时间
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					HSSFRow xr = sheet.getRow(i);
					StringBuilder sb = new StringBuilder();
					sb.append(now).append(column_speractor);// 库存时间
					sb.append(xr.getCell(4).toString()).append(column_speractor);// 生产厂家
					sb.append(column_speractor);// 供商代码
					sb.append(column_speractor);// 供商名称
					sb.append(xr.getCell(0).toString()).append(column_speractor);// 产品代码
					sb.append(xr.getCell(1).toString()).append(column_speractor);// 产品名称
					sb.append(xr.getCell(2).toString()).append(column_speractor);// 规格
					sb.append(column_speractor);// 剂型
					sb.append(xr.getCell(3).toString()).append(column_speractor);// 单位
					sb.append(xr.getCell(6).toString()).append(column_speractor);// 批号
					sb.append(xr.getCell(7).toString()).append(column_speractor);// 生产日期
					sb.append(xr.getCell(8).toString()).append(column_speractor);// 失效日期
					sb.append(xr.getCell(5).toString()).append(column_speractor);// 数量
					sb.append(column_speractor);// 库存状态
					sb.append(column_speractor);// 入库时间
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
			if (new File(stockPath).exists()) {
				new File(stockPath).delete();
			}
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String purchasPath = String.format("%s\\%s", ROOTPATH, "temp2.csv");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		String str = WebUtil.repeatGetMN(paramHttpClient, "http://antai.pukesoft.cn/index.php?m=Supplier"
				+ "&c=Purchase&a=purrec&excel=yes&end=" + enddate + "&start=" + startdate + "&good_id=请选择商品",
				purchasPath);
		List<String> data = createPurchas();
		FileInputStream fileInputstream = new FileInputStream(purchasPath);
		HSSFWorkbook xss = new HSSFWorkbook(fileInputstream);
		try {
			HSSFSheet sheet = xss.getSheetAt(0);
			if (sheet.getPhysicalNumberOfRows() > 1) {
				// 入库时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 进货类型 供应商出库单号
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					HSSFRow xr = sheet.getRow(i);
					StringBuilder sb = new StringBuilder();
					sb.append(xr.getCell(1).toString()).append(column_speractor);// 购进日期
					sb.append(xr.getCell(7).toString()).append(column_speractor);// 生产厂家
					sb.append(column_speractor);// 供商代码
					sb.append(xr.getCell(0).toString()).append(column_speractor);// 供商名称
					sb.append(xr.getCell(3).toString()).append(column_speractor);// 商品代码
					sb.append(xr.getCell(4).toString()).append(column_speractor);// 商品名称
					sb.append(xr.getCell(5).toString()).append(column_speractor);// 规格
					sb.append(column_speractor);// 剂型
					sb.append(xr.getCell(6).toString()).append(column_speractor);// 单位
					sb.append(xr.getCell(8).toString()).append(column_speractor);// 批号
					sb.append(xr.getCell(9).toString()).append(column_speractor);// 数量
					sb.append(column_speractor);// 含税单价
					sb.append(column_speractor);// 含税金额
					sb.append(xr.getCell(2).toString()).append(column_speractor);// 进货类型
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
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String salePath = String.format("%s\\%s", ROOTPATH, "temp3.xls");
		File originalPath = new File(ROOTPATH);

		if (!originalPath.exists()) {
			originalPath.mkdirs();
		}
		String url = "http://antai.pukesoft.cn/index.php?m=Supplier&c=Stock&a=flow"
				+ "&excel=yes&end="+enddate+"&start="+startdate+"&good_id=请选择商品";
		String str = WebUtil.repeatGetMN(paramHttpClient, url,salePath);
		List<String> data = createSale();
		FileInputStream fileInputstream = new FileInputStream(salePath);
		HSSFWorkbook xss = new HSSFWorkbook(fileInputstream);
		try {
			HSSFSheet sheet = xss.getSheetAt(0);
			if (sheet.getPhysicalNumberOfRows() > 1) {
				// 出库时间 生产厂家 客户代码 客户名称 产品代码 产品名称 产品规格 剂型 单位 批号 
				//数量 含税单价 含税金额 出货类型 客户城市 客户地址 经销商发货单号 备注
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					HSSFRow xr = sheet.getRow(i);
					StringBuilder sb = new StringBuilder();
					sb.append(xr.getCell(2).toString()).append(column_speractor);// 出库时间
					sb.append(xr.getCell(8).toString()).append(column_speractor);// 生产厂家
					sb.append(column_speractor);// 客户代码
					sb.append(xr.getCell(1).toString()).append(column_speractor);// 客户名称
					sb.append(xr.getCell(4).toString()).append(column_speractor);// 商品代码
					sb.append(xr.getCell(5).toString()).append(column_speractor);// 商品名称
					sb.append(xr.getCell(6).toString()).append(column_speractor);// 规格
					sb.append(column_speractor);// 剂型
					sb.append(xr.getCell(7).toString()).append(column_speractor);// 单位
					sb.append(xr.getCell(11).toString()).append(column_speractor);// 批号
					sb.append(xr.getCell(9).toString()).append(column_speractor);// 数量
					sb.append(column_speractor);// 含税单价
					sb.append(column_speractor);// 含税金额
					sb.append(xr.getCell(3).toString()).append(column_speractor);// 出货类型
					sb.append(column_speractor);// 客户城市
					sb.append(column_speractor);// 客户地址
					sb.append(xr.getCell(0).toString()).append(column_speractor);// 经销商发货单号
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

	public static void main(String[] args) {

		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "5450");
		params.put("callId", "5067");
		params.put("uuid", "0B0D6067-95C3-4436-A7BC-194C2A88351F");
		try { // 22474 安徽省绿十字医药股份有限公司
			(new Code22474Action()).exec(client, params, "22474", "安徽省绿十字医药股份有限公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

}
