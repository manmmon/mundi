package com.oval.grabweb.logic.part202301.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;

import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.WebUtil;

/**
 * 西藏自治区医药有限责任公司
 * 
 * @author youzi
 *
 */
public class Code22197Action extends AbstractAction {
	
	public Code22197Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
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

	@Override
	protected void login(HttpClient paramHttpClient, Map<String, String> paramMap) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		String code = checkCode();
		params.put("employeeid", "1100");
		params.put("webpass", "");
		params.put("role", "161");
		params.put("captcha", code);
		params.put("sourcecode", code.toUpperCase());
		String str = WebUtil.repeatPost(paramHttpClient, params, "http://124.31.222.83:8088/ns_web/login.action");
		str = WebUtil.repeatGet(paramHttpClient, "http://124.31.222.83:8088/ns_web/index.action");

	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String date = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		Map<String, String> params = new HashMap<String, String>();
		params.put("condition", "hpbd");
		params.put("startgoodsid", "");
		params.put("condition", "mxbd");
		params.put("startgoodsdetailid", "");
		params.put("condition", "cjbd");
		params.put("startfactoryid", "");
		params.put("condition", "phbd");
		params.put("startlotid", "");
		params.put("condition", "zllj");
		params.put("qualitystatus", "");
		params.put("pageSize", "1000");
		params.put("page", "1");
		params.put("goodsmsg", "");
		String str = WebUtil.repeatPost(paramHttpClient, params,
				"http://124.31.222.83:8088/ns_web/supplyDetailsQty.action");
		str = str.substring(str.indexOf("<tbody"), str.indexOf("</tbody>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "库存时间", "生产厂家", "供应商代码", "供应商名称", "产品代码", "产品名称", "产品规格", "剂型", "单位", "批号",
				"生产日期", "失效日期", "数量", "库存状态", "入库时间" };
		String[] contents = new String[] { "行号", "产品代码", "通用名", "产品名称", "产品规格", "单位", "产地", "生产厂家", "包装", "包装大小", "批号",
				"生产日期", "失效日期", "数量", "质量状态", "厂家ID", "货品明细ID", "批号ID" };
		int[] indexs = getContentsIndexs(title, contents);
		List<String> data = createStock();
		for (int i = 1; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if (tds != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(date).append(column_speractor);
				for (int j = 1; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						sb.append(tds[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}

		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		Map<String, String> params = new HashMap<String, String>();
		params.put("condition", "shijianfanwei");
		params.put("startdate", startDate);
		params.put("enddate ", endDate);
		params.put("condition", "hpbdy");
		params.put("startgoodsid", "");
		params.put("condition", "cgbdy");
		params.put("startsupplyerid", "");
		params.put("condition", "phbdy");
		params.put("startlotid", "");
		params.put("condition", "lxlj");
		params.put("sutypeid", "");
		params.put("condition", "ztlj");
		params.put("usestatus", "");
		params.put("pageSize", "1000");
		params.put("page ", "1");
		params.put("goodsmsg", "");
		String str = WebUtil.repeatPost(paramHttpClient, params,
				"http://124.31.222.83:8088/ns_web/stockDetailsQty.action");
		str = str.substring(str.indexOf("<tbody"), str.indexOf("</tbody>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "入库时间", "生产厂家", "供应商代码", "供应商名称", "产品代码", "产品名称", "产品规格", "剂型", "单位", "批号",
				"数量", "含税单价", "含税金额", "进货类型", "供应商出库单号" };
		String[] contents = new String[] { "行号", "入库时间", "进货类型", "通用名", "产品名称", "单位", "产品规格", "产地", "生产厂家", "包装",
				"包装大小", "使用单位", "使用单位数量", "单价", "数量", "金额", "税率", "总金额", "批号", "采购员", "供应商名称", "委托人", "作废标志", "作废时间",
				"作废人", "备注", "细单备注", "采购员Id", "进货细单Id", "产品代码", "供应商代码", "委托人Id" };
		int[] indexs = getContentsIndexs(title, contents);
		List<String> data = createPurchas();
		for (int i = 1; i < trs.length; i++) {
			trs[i] = trs[i].replaceAll("<td >", "<td>");
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if (tds != null) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						sb.append(tds[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}

		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		Map<String, String> params = new HashMap<String, String>();
		params.put("condition", "shijianfanwei");
		params.put("startdate", startDate);
		params.put("enddate", endDate);
		params.put("condition", "khbdy");
		params.put("startcustomid", "");
		params.put("condition", "hpbdy");
		params.put("startgoodsid", "");
		params.put("condition", "slpbdy");
		params.put("startgoodsqty", "");
		params.put("condition", "phbdy");
		params.put("startlotid", "");
		params.put("pageSize", "1000");
		params.put("page", "1");
		params.put("supplymsg", "");
		String str = WebUtil.repeatPost(paramHttpClient, params, "http://124.31.222.83:8088/ns_web/flowQuery.action");
		str = str.substring(str.indexOf("<tbody"), str.indexOf("</tbody>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "出库时间", "生产厂家", "客户代码", "客户名称", "产品代码", "产品名称", "产品规格", "剂型", "单位", "批号", "数量",
				"含税单价", "含税金额", "出货类型", "客户城市", "客户地址", "经销商发货单号", "备注" };
		String[] contents = new String[] { "行号", "出库时间", "客户名称", "产品名称", "产品规格", "单位", "产地", "生产厂家", "批号", "生产日期",
				"有效期", "数量", "单价", "金额", "流向类型", "供应商", "委托人", "备注", "客户代码", "产品代码", "批号Id", "供应商Id" };
		int[] indexs = getContentsIndexs(title, contents);
		List<String> data = createSale();
		for (int i = 1; i < trs.length; i++) {
			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if (tds != null) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						sb.append(tds[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}

		}
		return data.toArray(new String[1]);
	}

	public String checkCode() {
		String code = "";
		String[] codeChars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C",
				"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		for (int i = 0; i < 4; i++) {
			double j = Math.floor(Math.random() * 52);
			code += codeChars[(int) j];
		}
		return code;
	}

	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "10786");
		params.put("callId", "5046");
		params.put("uuid", "C07D3177-5DE2-4A09-8402-3AB856E2D5B9");
		try { // 22197 西藏自治区医药有限责任公司
			(new Code22197Action()).exec(client, params, "22197", "西藏自治区医药有限责任公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

}
