package com.oval.grabweb.logic.part202301.impl.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.StringUtil;
import com.oval.util.WebUtil;

/**
 * 徐州润百隆医药有限公司
 * 
 * @author youzi
 *
 */
public class Code22399Action extends AbstractAction {

	public static String PID = "";

	public Code22399Action() {
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
		Map<String, String> params = new HashMap<String, String>();
		params.put("op", "verify|login");
		params.put("targetpage", "");
		params.put("errorpage", "login.jsp?login=local");
		params.put("mark", "");
		params.put("tzo", "480");
		params.put("username", "萌蒂");
		params.put("password", "123456");
		HttpResponse response = WebUtil.repeatPost1(paramHttpClient, params, "http://58.218.51.202:10855/login");
		if (response != null) {
			Header[] headers = response.getHeaders("Location");
			if (headers.length > 0) {
				String location = headers[0].getValue();
				PID = location.substring(location.indexOf("=") + 1, location.length());
				String str = WebUtil.repeatGet(paramHttpClient, location);
				str = WebUtil.repeatGet(paramHttpClient,
						"http://58.218.51.202:10855/workspace/frame.thtml?_pid=" + PID);
			}
		} else {
			System.out.println("徐州润百隆医药有限公司登录失败！");
		}
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String now = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd");
		String params = "{\"md\":\"总部库存查询\"}";
		String str = WebUtil.repeatGet(paramHttpClient,
				"http://58.218.51.202:10855/4pdyh2ho841t1qt9.prog?_tm=workspace.frame&_tmf=content"
						+ "&_curtnid=4&_pid=" + PID + "&_params=" + params);
		String tranid = StringUtil.getValue(str, "\"连锁库存查询\", \"", "\");", 1);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "text/xml;charset=utf-8");
		String content = "{\"openmode\":\"sql\",\"openparam\":\"uf_djmxmd_sql\",\"pageno\":1,\"pagesize\":-1,\"variables\":{\"BusinessName\":\"\",\"GoodsCode\":\"\",\"GoodsName\":\"\",\"LX_PinP\":\"萌蒂\",\"Manufacturer\":\"\",\"RfId\":\"\",\"goodsid\":\"\",\"kzEndDate\":\"\",\"kzStartDate\":\"\",\"spLogogram\":\"\",\"suppliersid\":\"\",\"zzentid\":\"\",\"zzorgid\":\"\"}}";
		str = WebUtil.repeatGetByHeader(paramHttpClient, header,
				"http://58.218.51.202:10855/formservice?service=openDataSet" + "&_pid=" + PID + "&_tranid=" + tranid
						+ "&_moduleid=0xscxdbw1klcrcjy&_action=new"
						+ "&_formid=1j617m2x5d3hpxcu&_progid=4pdyh2ho841t1qt9&content=" + content);
		str = StringUtil.getValue(str, "<data>", "</data>", 1);
		String[] trs = StringUtils.substringsBetween(str, "<tr>", "</tr>");
		List<String> data = createStock();
		// 库存时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 生产日期 失效日期 数量 库存状态 入库时间
		for (int i = 0; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			StringBuilder sb = new StringBuilder();
			sb.append(now).append(column_speractor);// 库存时间
			sb.append(tds[4]).append(column_speractor);// 生产厂家
			sb.append(column_speractor);// 供应商代码
			sb.append(column_speractor);// 供应商名称
			sb.append(tds[1]).append(column_speractor);// 产品代码
			sb.append(tds[2]).append(column_speractor);// 产品名称
			sb.append(tds[3]).append(column_speractor);// 产品规格
			sb.append(column_speractor);// 剂型
			sb.append(tds[5]).append(column_speractor);// 单位
			sb.append(column_speractor);// 批号
			sb.append(column_speractor);// 生产日期
			sb.append(column_speractor);// 失效日期
			sb.append(tds[6]).append(column_speractor);// 数量
			sb.append(column_speractor);// 库存状态
			sb.append(column_speractor);// 入库时间
			data.add(sb.toString());
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String params = "{\"md\":\"商品采购查询\"}";
		String str = WebUtil.repeatGet(paramHttpClient,
				"http://58.218.51.202:10855/0pjedprt5svaeple.prog?_tm=workspace.frame"
						+ "&_tmf=content&_curtnid=2&_pid=" + PID + "&_params=" + params);
		String tranid = StringUtil.getValue(str, "\"商品流向查询\", \"", "\");", 1);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "text/xml;charset=utf-8");
		String content = "{\"openmode\":\"sql\",\"openparam\":\"uf_djmx_sql\",\"pageno\":1,\"pagesize\":50000,"
				+ "\"variables\":{\"BatchCode\":\"\",\"BusinessName\":\"\",\"EndDate\":\"" + enddate + "\","
				+ "\"GoodsCode\":\"\",\"GoodsName\":\"\",\"LX_PinP\":\"萌蒂\",\"Manufacturer\":\"\","
				+ "\"RfId\":\"\",\"StartDate\":\"" + startdate + "\",\"billcode\":\"%\",\"goodsid\":\"\","
				+ "\"kzEndDate\":\"\",\"kzStartDate\":\"\",\"spLogogram\":\"\",\"suppliersid\":\"\","
				+ "\"zzentid\":\"\",\"zzorgid\":\"\"}}";
		str = WebUtil.repeatGetByHeader(paramHttpClient, header,
				"http://58.218.51.202:10855/formservice?service=openDataSet" + "&_pid=" + PID + "&_tranid=" + tranid
						+ "&_moduleid=0xscxdbw1klcrcjy&_action=new"
						+ "&_formid=3li48p889mzpnifm&_progid=0pjedprt5svaeple" + "&content=" + content);
		str = StringUtil.getValue(str, "<data>", "</data>", 1);
		String[] trs = StringUtils.substringsBetween(str, "<tr>", "</tr>");
		List<String> data = createPurchas();
		// 入库时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 进货类型 供应商出库单号
		for (int i = 0; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			StringBuilder sb = new StringBuilder();
			sb.append(tds[2]).append(column_speractor);// 入库时间
			sb.append(tds[11]).append(column_speractor);// 生产厂家
			sb.append(column_speractor);// 供应商代码
			sb.append(column_speractor);// 供应商名称
			sb.append(tds[7]).append(column_speractor);// 产品代码
			sb.append(tds[8]).append(column_speractor);// 产品名称
			sb.append(tds[9]).append(column_speractor);// 产品规格
			sb.append(column_speractor);// 剂型
			sb.append(tds[12]).append(column_speractor);// 单位
			sb.append(tds[13]).append(column_speractor);// 批号
			sb.append(tds[6]).append(column_speractor);// 数量
			sb.append(column_speractor);// 含税单价
			sb.append(column_speractor);// 含税金额
			sb.append(tds[5]).append(column_speractor);// 进货类型
			sb.append(column_speractor);// 供应商出库单号
			data.add(sb.toString());
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		String params = "{\"md\":\"商品出库查询\"}";
		String str = WebUtil.repeatGet(paramHttpClient, "http://58.218.51.202:10855/4yn6xf4t3m95gpa5.prog?"
				+ "_tm=workspace.frame" + "&_tmf=content&_curtnid=3" + "&_pid=" + PID + "&_params=" + params);
		String tranid = StringUtil.getValue(str, "\"商品纯销流向查询\", \"", "\");", 1);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "text/xml;charset=utf-8");
		String content = "{\"openmode\":\"sql\",\"openparam\":\"uf_djmx_sql\",\"pageno\":1,\"pagesize\":50000,"
				+ "\"variables\":{\"BatchCode\":\"\",\"BusinessName\":\"\",\"EndDate\":\"" + enddate + "\","
				+ "\"GoodsCode\":\"\",\"GoodsName\":\"\",\"LX_PinP\":\"萌蒂\",\"Manufacturer\":\"\","
				+ "\"RfId\":\"\",\"StartDate\":\"" + startdate + "\",\"billcode\":\"%\",\"goodsid\":\"\","
				+ "\"kzEndDate\":\"\",\"kzStartDate\":\"\",\"spLogogram\":\"\",\"suppliersid\":\"\","
				+ "\"zzentid\":\"\",\"zzorgid\":\"\"}}";
		str = WebUtil.repeatGetByHeader(paramHttpClient, header,
				"http://58.218.51.202:10855/formservice?" + "service=openDataSet" + "&_pid=" + PID + "&_tranid="
						+ tranid + "&_moduleid=0xscxdbw1klcrcjy&_action=new"
						+ "&_formid=5dt2bufr49mp90ke&_progid=4yn6xf4t3m95gpa5&content=" + content);
		str = StringUtil.getValue(str, "<data>", "</data>", 1);
		String[] trs = StringUtils.substringsBetween(str, "<tr>", "</tr>");
		List<String> data = createSale();
		// 出库时间 生产厂家 客户代码 客户名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 出货类型 客户城市 客户地址
		// 经销商发货单号 备注
		for (int i = 0; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			StringBuilder sb = new StringBuilder();
			sb.append(tds[2]).append(column_speractor);// 出库时间
			sb.append(tds[11]).append(column_speractor);// 生产厂家
			sb.append(column_speractor);// 客户代码
			sb.append(tds[17]).append(column_speractor);// 客户名称
			sb.append(tds[7]).append(column_speractor);// 产品代码
			sb.append(tds[8]).append(column_speractor);// 产品名称
			sb.append(tds[9]).append(column_speractor);// 产品规格
			sb.append(column_speractor);// 剂型
			sb.append(tds[12]).append(column_speractor);// 单位
			sb.append(tds[13]).append(column_speractor);// 批号
			sb.append(tds[6]).append(column_speractor);// 数量
			sb.append(column_speractor);// 含税单价
			sb.append(column_speractor);// 含税金额
			sb.append(column_speractor);// 出货类型
			sb.append(column_speractor);// 客户城市
			sb.append(column_speractor);// 客户地址
			sb.append(column_speractor);// 经销商发货单号
			sb.append(column_speractor);// 备注
			data.add(sb.toString());
		}
		return data.toArray(new String[1]);
	}

	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "10840");
		params.put("callId", "5104");
		params.put("uuid", "0D8842A9-311C-4CA8-A3AD-4E7EAB314A69");
		try { // 22399 徐州润百隆医药有限公司
			(new Code22399Action()).exec(client, params, "22399", "徐州润百隆医药有限公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

}
